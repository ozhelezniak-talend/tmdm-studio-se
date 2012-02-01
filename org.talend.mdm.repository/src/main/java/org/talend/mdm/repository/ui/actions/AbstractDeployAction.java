// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.repository.ui.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.mdm.repository.core.AbstractRepositoryAction;
import org.talend.mdm.repository.core.command.CommandManager;
import org.talend.mdm.repository.core.command.CompoundCommand;
import org.talend.mdm.repository.core.command.ICommand;
import org.talend.mdm.repository.core.command.common.PushCmdCommand;
import org.talend.mdm.repository.core.command.common.UpdateLastServerCommand;
import org.talend.mdm.repository.core.command.deploy.AbstractDeployCommand;
import org.talend.mdm.repository.core.command.deploy.DeployCompoundCommand;
import org.talend.mdm.repository.core.command.deploy.job.BatchDeployJobCommand;
import org.talend.mdm.repository.core.service.DeployService;
import org.talend.mdm.repository.core.service.DeployService.DeployStatus;
import org.talend.mdm.repository.i18n.Messages;
import org.talend.mdm.repository.model.mdmmetadata.MDMServerDef;
import org.talend.mdm.repository.plugin.RepositoryPlugin;
import org.talend.mdm.repository.ui.dialogs.message.MultiStatusDialog;
import org.talend.mdm.repository.utils.EclipseResourceManager;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public abstract class AbstractDeployAction extends AbstractRepositoryAction {

    private static final ImageDescriptor DEPLOY_IMG = EclipseResourceManager.getImageDescriptor(RepositoryPlugin.PLUGIN_ID,
            "/icons/deploy.png"); //$NON-NLS-1$

    protected AbstractDeployAction(String text) {
        super(text);
        setImageDescriptor(DEPLOY_IMG);
    }

    protected IStatus deploy(MDMServerDef serverDef, List<IRepositoryViewObject> viewObjs, int defaultCmdType) {
        return DeployService.getInstance().deploy(serverDef, viewObjs, defaultCmdType);
    }

    protected void showDeployStatus(IStatus status) {
        String prompt;
        int count = 0;
        if (status.isMultiStatus()) {
            for (IStatus child : status.getChildren()) {
                if (child.isMultiStatus()) {
                    count += child.getChildren().length;
                } else {
                    count++;
                }
            }
        }
        if (status.getSeverity() < IStatus.ERROR) {
            prompt = Messages.bind(Messages.AbstractDeployAction_deployMessage, count);
        } else {
            prompt = Messages.bind(Messages.AbstractDeployAction_deployFailure, count);

        }
        MultiStatusDialog dialog = new MultiStatusDialog(getShell(), prompt, status);
        dialog.open();

    }

    public String getGroupName() {
        return GROUP_DEPLOY;
    }

    protected void updateChangedStatus(IStatus status) {
        if (status.isMultiStatus()) {
            for (IStatus childStatus : status.getChildren()) {
                DeployStatus deployStatus = null;
                if (childStatus instanceof DeployStatus) {
                    deployStatus = (DeployStatus) childStatus;
                } else if (childStatus instanceof MultiStatus) {
                    deployStatus = (DeployStatus) ((MultiStatus) childStatus).getChildren()[0];
                }

                if (deployStatus.isOK()) {
                    ICommand command = deployStatus.getCommand();
                    CommandManager manager = CommandManager.getInstance();
                    manager.removeCommandStack(command, ICommand.PHASE_DEPLOY);
                    MDMServerDef serverDef = null;
                    if (command instanceof AbstractDeployCommand) {
                        serverDef = ((AbstractDeployCommand) command).getServerDef();
                    } else if (command instanceof DeployCompoundCommand) {
                        serverDef = ((DeployCompoundCommand) command).getServerDef();
                    }
                    if (command instanceof BatchDeployJobCommand) {
                        BatchDeployJobCommand deployJobCommand = (BatchDeployJobCommand) command;
                        pushRestoreCommand(manager, deployJobCommand.getSubCmds(), serverDef);
                        pushRestoreCommand(manager, deployJobCommand.getSubDeleteCmds(), serverDef);
                    } else {
                        // updateserver
                        pushRestoreCommand(manager, command, serverDef);
                    }
                }

            }
        }
    }

    private void pushRestoreCommand(CommandManager manager, List<ICommand> subCmds, MDMServerDef serverDef) {
        if (subCmds != null && subCmds.size() > 0) {
            for (ICommand command : subCmds) {
                pushRestoreCommand(manager, command, serverDef);
            }
        }
    }

    private void pushRestoreCommand(CommandManager manager, ICommand command, MDMServerDef serverDef) {
        // updateserver
        if (command.getCommandType() != ICommand.CMD_DELETE) {
            Item item = command.getViewObject().getProperty().getItem();
            UpdateLastServerCommand updateLastServerCommand = new UpdateLastServerCommand(item, serverDef);
            updateLastServerCommand.setToRunPhase(ICommand.PHASE_AFTER_DEPLOY);
            updateLastServerCommand.init(command.getCommandId(), command.getObjLastName());
            manager.pushCommand(updateLastServerCommand);
        }
        //
        if (command.getCommandType() == ICommand.CMD_DELETE && !(command instanceof CompoundCommand)) {

            UpdateLastServerCommand emptyLastServerCommand = new UpdateLastServerCommand();
            PushCmdCommand pushCmd = new PushCmdCommand();
            pushCmd.setPushCmdType(ICommand.CMD_ADD);
            pushRestoreCommand(manager, command, emptyLastServerCommand);
            pushRestoreCommand(manager, command, pushCmd);
        }
    }

    private void pushRestoreCommand(CommandManager manager, ICommand command, ICommand cmd) {
        cmd.init(command.getCommandId(), command.getObjLastName());
        cmd.setToRunPhase(ICommand.PHASE_RESTORE);
        manager.pushCommand(cmd);
    }

    protected void updateLastServer(IProgressMonitor monitor) {
        CommandManager manager = CommandManager.getInstance();
        Map<String, List<ICommand>> commandMap = manager.getAllCommandsByPhase(ICommand.PHASE_AFTER_DEPLOY);
        for (String id : commandMap.keySet()) {
            List<ICommand> cmds = commandMap.get(id);
            for (ICommand cmd : cmds) {
                cmd.execute(null, monitor);
            }
            manager.removeCommandStack(id, ICommand.PHASE_AFTER_DEPLOY);
        }

        commonViewer.refresh();
    }

    IProxyRepositoryFactory factory = CoreRuntimePlugin.getInstance().getProxyRepositoryFactory();

    protected List<IRepositoryViewObject> getSelectedRepositoryViewObject() {
        List<IRepositoryViewObject> viewObjs = new LinkedList<IRepositoryViewObject>();
        for (Object obj : getSelectedObject()) {
            if (obj instanceof IRepositoryViewObject)
                viewObjs.add((IRepositoryViewObject) obj);
        }
        return viewObjs;
    }
}
