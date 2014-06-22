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
package org.talend.mdm.repository.ui.wizards.imports;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileManipulations;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.TarException;
import org.eclipse.ui.internal.wizards.datatransfer.TarFile;
import org.eclipse.ui.internal.wizards.datatransfer.TarLeveledStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.progress.UIJob;
import org.talend.commons.exception.LoginException;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.mdm.repository.core.command.CommandManager;
import org.talend.mdm.repository.core.command.ICommand;
import org.talend.mdm.repository.core.service.ImportService;
import org.talend.mdm.repository.i18n.Messages;
import org.talend.mdm.repository.model.mdmproperties.MDMServerObjectItem;
import org.talend.mdm.repository.model.mdmserverobject.MDMServerObject;
import org.talend.mdm.repository.ui.dialogs.importexchange.ImportExchangeOptionsDialogR;
import org.talend.mdm.repository.ui.dialogs.lock.LockedObjectDialog;
import org.talend.mdm.repository.ui.navigator.MDMRepositoryView;
import org.talend.mdm.repository.ui.wizards.imports.viewer.ImportRepositoryObjectCheckTreeViewer;
import org.talend.mdm.repository.utils.RepositoryResourceUtil;
import org.talend.repository.RepositoryWorkUnit;
import org.talend.repository.imports.ImportItemUtil;
import org.talend.repository.imports.ItemRecord;
import org.talend.repository.imports.ResourcesManager;
import org.talend.repository.imports.ResourcesManagerFactory;

import com.amalto.workbench.dialogs.ImportExchangeOptionsDialog;
import com.amalto.workbench.export.ImportItemsWizard;
import com.amalto.workbench.widgets.WidgetFactory;

/**
 * DOC hywang class global comment. Detailled comment
 */
public class MDMImportRepositoryItemsWizard extends ImportItemsWizard {

    private ImportItemUtil repositoryUtil = new ImportItemUtil();

    private List<ItemRecord> selectedItems = new ArrayList<ItemRecord>();

    private ResourcesManager manager;

    private static Logger log = Logger.getLogger(MDMImportRepositoryItemsWizard.class);

    ImportRepositoryObjectCheckTreeViewer checkTreeViewer;

    public MDMImportRepositoryItemsWizard(IStructuredSelection sel) {
        super(sel);
    }

    @Override
    public boolean performFinish() {
        if (!showLockedObjDialog(getCheckedObjects())) {
            return false;
        }
        return super.performFinish();
    }

    List<ItemRecord> toImportItemRecords = new LinkedList<ItemRecord>();

    private boolean showLockedObjDialog(Object[] objs) {

        List<IRepositoryViewObject> viewObjs = new LinkedList<IRepositoryViewObject>();
        ProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        for (Object obj : objs) {
            if (obj instanceof ItemRecord) {
                ItemRecord itemRecord = (ItemRecord) obj;
                try {
                    IRepositoryViewObject viewObj = factory.getLastVersion(itemRecord.getProperty().getId());
                    if (viewObj != null) {
                        if (RepositoryResourceUtil.isLockedViewObject(viewObj)) {
                            viewObjs.add(viewObj);
                        } else {
                            toImportItemRecords.add(itemRecord);
                        }
                    } else {
                        toImportItemRecords.add(itemRecord);
                    }
                } catch (PersistenceException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        LockedObjectDialog lockDialog = new LockedObjectDialog(getShell(), Messages.ImportServerObjectWizard_lockedObjectMessage,
                viewObjs);
        if (lockDialog.needShowDialog() && lockDialog.open() == IDialogConstants.CANCEL_ID) {
            return false;
        }
        return true;
    }

    @Override
    public void doImport(final Object[] selectedObjs, final IProgressMonitor monitor) {
        ImportService.setImporting(true);
        monitor.beginTask("Import MDM object", toImportItemRecords.size() * 3); //$NON-NLS-1$

        RepositoryWorkUnit rwu = new RepositoryWorkUnit("MDM Import Items") { //$NON-NLS-1$

            @Override
            protected void run() throws LoginException, PersistenceException {
                repositoryUtil.importItemRecords(manager, toImportItemRecords, monitor, isOverride, null, ""); //$NON-NLS-1$

                for (ItemRecord itemRec : toImportItemRecords) {
                    MDMServerObject serverObj = null;
                    try {
                        ProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                        List<IRepositoryViewObject> allVersion = factory.getAllVersion(itemRec.getItemId());
                        for (IRepositoryViewObject object : allVersion) {
                            if (object.getVersion() != null && object.getVersion().equals(itemRec.getItemVersion())) {
                                Property property = object.getProperty();
                                Item item = property.getItem();
                                boolean needSave = false;
                                if (item instanceof MDMServerObjectItem) {

                                    serverObj = ((MDMServerObjectItem) item).getMDMServerObject();
                                    if (serverObj.getLastServerDef() != null) {
                                        serverObj.setLastServerDef(null);
                                        needSave = true;
                                    }

                                } else {
                                    EMap additionalProperties = property.getAdditionalProperties();
                                    if (additionalProperties != null) {
                                        additionalProperties.remove(RepositoryResourceUtil.PROP_LAST_SERVER_DEF);
                                        needSave = true;
                                    }
                                }
                                try {
                                    if (needSave) {
                                        factory.save(item);
                                    }
                                    if (itemRec.isValid()) {

                                        String[] split = itemRec.getLabel().split(" "); //$NON-NLS-1$
                                        String name = split.length > 0 ? split[0] : null;

                                        // // flagged as new
                                        if (name != null) {
                                            CommandManager.getInstance().pushCommand(ICommand.CMD_ADD, itemRec.getItemId(), name);
                                        }
                                    }
                                } catch (Exception e) {
                                    log.error(e.getMessage(), e);
                                }
                            }
                        }
                    } catch (PersistenceException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        };
        ProxyRepositoryFactory.getInstance().executeRepositoryWorkUnit(rwu);
        monitor.done();

        ImportService.setImporting(false);
        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                MDMRepositoryView.show().getCommonViewer().refresh();
            }
        });

    }

    protected void createOverwriteBtn(Composite composite) {
        btnOverwrite = new Button(composite, SWT.CHECK);
        btnOverwrite.setText(Messages.Overwrite_Exists_Items);
        btnOverwrite.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                isOverride = btnOverwrite.getSelection();
                parse();
            }

        });
    }
    
    @Override
    protected ImportExchangeOptionsDialog getExchangeOptionsDialog() {
        FormToolkit toolkit = WidgetFactory.getWidgetFactory();
        ImportExchangeOptionsDialog dlg = new ImportExchangeOptionsDialogR(getShell(), toolkit, true, zipFileRepository);
        dlg.create();

        return dlg;
    }

    @Override
    protected void createViewer() {
        checkTreeViewer = new ImportRepositoryObjectCheckTreeViewer(repositoryUtil);
    }

    /** need to override this method to list all the import items to the viewer **/
    @Override
    protected void parses(boolean importFromArchieve, String zipFilePath, IProgressMonitor monitor) {
        selectedItems.clear();
        String importPath = importFromArchieve ? zipFilePath : importFolder;
        final Collection<ItemRecord> items = new ArrayList<ItemRecord>();

        monitor.beginTask(DataTransferMessages.WizardProjectsImportPage_SearchingMessage, 100);
        File directory = new File(importPath);
        monitor.worked(10);
        if (importFromArchieve && ArchiveFileManipulations.isTarFile(importPath)) {
            TarFile sourceTarFile = getSpecifiedTarSourceFile(importPath);
            if (sourceTarFile == null) {
                return;
            }
            TarLeveledStructureProvider provider = new TarLeveledStructureProvider(sourceTarFile);
            manager = ResourcesManagerFactory.getInstance().createResourcesManager(provider);

            if (!manager.collectPath2Object(provider.getRoot())) {
                return;
            }
        } else if (importFromArchieve && ArchiveFileManipulations.isZipFile(importPath)) {
            ZipFile sourceFile = getSpecifiedZipSourceFile(importPath);
            if (sourceFile == null) {
                return;
            }
            ZipLeveledStructureProvider provider = new ZipLeveledStructureProvider(sourceFile);
            manager = ResourcesManagerFactory.getInstance().createResourcesManager(provider);

            if (!manager.collectPath2Object(provider.getRoot())) {
                return;
            }
        } else if (!importFromArchieve && directory.isDirectory()) {
            manager = ResourcesManagerFactory.getInstance().createResourcesManager();

            if (!manager.collectPath2Object(directory)) {
                return;
            }
        } else {
            monitor.worked(60);
        }
        repositoryUtil.clearAllData();
        if (manager != null)
            items.addAll(repositoryUtil.populateItems(manager, isOverride, monitor));

        selectedItems.addAll(items);

        if (this.importServerItem(items)) {
            Display.getDefault().syncExec(new Runnable() {

                public void run() {
                    MessageDialog.openWarning(getContainer().getShell(), Messages.AddBrowseItemsWizardR_warning,
                            Messages.RepositoryViewImportRepositoryItem_nothingImport);
                    checkTreeViewer.getViewer().setInput(Collections.EMPTY_LIST);
                }
            });
        } else {
            this.getShell().getDisplay().syncExec(new Runnable() {

                public void run() {
                    checkTreeViewer.getViewer().setInput(repositoryUtil.getTreeViewInput());
                }
            });
        }
        monitor.done();

    }

    @Override
    protected Object[] getCheckedObjects() {
        return checkTreeViewer.getCheckNodes();
    }

    private ZipFile getSpecifiedZipSourceFile(String fileName) {
        if (fileName.length() == 0) {
            return null;
        }

        try {
            return new ZipFile(fileName);
        } catch (ZipException e) {
            displayErrorDialog(DataTransferMessages.ZipImport_badFormat);
        } catch (IOException e) {
            displayErrorDialog(DataTransferMessages.ZipImport_couldNotRead);
        }

        zip.getText().setFocus();
        return null;
    }

    private TarFile getSpecifiedTarSourceFile(String fileName) {
        if (fileName.length() == 0) {
            return null;
        }

        try {
            return new TarFile(fileName);
        } catch (TarException e) {
            displayErrorDialog(DataTransferMessages.TarImport_badFormat);
        } catch (IOException e) {
            displayErrorDialog(DataTransferMessages.ZipImport_couldNotRead);
        }

        zip.getText().setFocus();
        return null;
    }

    protected void displayErrorDialog(String message) {
        MessageDialog.openError(getContainer().getShell(), getErrorDialogTitle(), message);
    }

    protected String getErrorDialogTitle() {
        return IDEWorkbenchMessages.WizardExportPage_internalErrorTitle;
    }

    @Override
    protected void refreshViewJob() {
        new UIJob(Messages.MDMImportRepositoryItemsWizard_refreshServer) {

            @Override
            public IStatus runInUIThread(IProgressMonitor monitor) {
                MDMRepositoryView.show().getCommonViewer().refresh();
                return Status.OK_STATUS;
            }

        }.schedule();
    }

    protected Composite initItemTreeViewer(Composite composite) {
        Composite returnComposite = checkTreeViewer.createItemList(composite);
        checkTreeViewer.getViewer().setInput(null);
        checkTreeViewer.setItemText(Messages.MDMImportRepositoryItemsWizard_toImport);
        return returnComposite;
    }

    private boolean importServerItem(Collection<ItemRecord> items) {

        if (items.size() == 0) {
            return true;
        }
        Iterator<ItemRecord> itemIt = items.iterator();
        while (itemIt.hasNext()) {
            ItemRecord itemRecord = itemIt.next();
            if (itemRecord.getExistingItemWithSameId() == null && itemRecord.getErrors() != null
                    && itemRecord.getErrors().size() > 0) {
                return true;
            }
        }
        return false;
    }

}