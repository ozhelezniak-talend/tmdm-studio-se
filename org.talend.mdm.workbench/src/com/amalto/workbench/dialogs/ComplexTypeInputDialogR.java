// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.dialogs;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.utils.Util;


public class ComplexTypeInputDialogR extends ComplexTypeInputDialog {

    private Text elementNameText = null;

    private Text minOccursText = null;

    private Text maxOccursText = null;

    protected Button inheritCheckBox;

    private String elementName = "";//$NON-NLS-1$

    private int minOccurs = 0;

    private int maxOccurs = 1;

    private boolean inherit = true;

    private boolean isPK = false;

    private XSDModelGroup group;
    
    public ComplexTypeInputDialogR(Shell parentShell, String title, XSDModelGroup modelGroup, XSDSchema schema,
            List<XSDComplexTypeDefinition> types, XSDTypeDefinition typeDefinition, boolean isPK, boolean isXSDModelGroup) {

        super(null, parentShell, title, schema, typeDefinition, types, isXSDModelGroup);

        this.group = modelGroup;
        this.isPK = isPK;
    }
    
    @Override
    protected void createTopPart(Composite parent) {
        Group basicPart = new Group(parent, SWT.NONE);
        basicPart.setText(Messages.getString("_BasicInfo")); //$NON-NLS-1$
        basicPart.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true));
        
        GridLayout layout = (GridLayout) basicPart.getLayout();
        if(layout == null) {
            layout = new GridLayout();
            basicPart.setLayout(layout);
        }
        layout.numColumns = 2;

        Label nameLabel = new Label(basicPart, SWT.NONE);
        nameLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        nameLabel.setText(Messages.getString("_BusinessName")); //$NON-NLS-1$

        elementNameText = new Text(basicPart, SWT.BORDER);
        elementNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        elementNameText.setText(getElementName() == null ? "" : getElementName());//$NON-NLS-1$
        ((GridData) elementNameText.getLayoutData()).widthHint = 200;
        
        Label minLabel = new Label(basicPart, SWT.NONE);
        minLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        minLabel.setText(Messages.getString("_MinOccur")); //$NON-NLS-1$

        minOccursText = new Text(basicPart, SWT.NONE);
        minOccursText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        minOccursText.setDoubleClickEnabled(false);
        minOccursText.setText("" + getMinOccurs());//$NON-NLS-1$

        Label maxLabel = new Label(basicPart, SWT.NONE);
        maxLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        maxLabel.setText(Messages.getString("_MaxOccur")); //$NON-NLS-1$

        maxOccursText = new Text(basicPart, SWT.NONE);
        maxOccursText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        maxOccursText.setText(getMaxOccurs() == -1 ? "" : "" + getMaxOccurs());//$NON-NLS-1$//$NON-NLS-2$

        if (Util.IsEnterPrise()) {
            inheritCheckBox = new Button(basicPart, SWT.CHECK);
            inheritCheckBox.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 2, 1));
            inheritCheckBox.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                };

                public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                    inherit = inheritCheckBox.getSelection();
                };
            });

            inheritCheckBox.setSelection(inherit);
            inheritCheckBox.setText(Messages.getString("_InheritStr")); //$NON-NLS-1$
        }
        // check pk can't edit Maximum/Minimum
        minOccursText.setEditable(!isPK);
        maxOccursText.setEditable(!isPK);
    }
    
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, 0, IDialogConstants.OK_LABEL, true);
        createButton(parent, 1, IDialogConstants.CANCEL_LABEL, false);
    }
    
    @Override
    protected void okPressed() {
        if (!valid()) {
            return;
        }
        
        super.okPressed();
        close();
    }
    
    private boolean valid() {
        elementName = elementNameText.getText().trim();
        if (((elementName == null) || ("".equals(elementName)))) {//$NON-NLS-1$
            MessageDialog.openError(this.getShell(), Messages.getString("_Error"), //$NON-NLS-1$
                    Messages.getString("_BusinessNameCannotEmpty")); //$NON-NLS-1$
            setReturnCode(-1);
            elementNameText.setFocus();
            return false;
        }

        if (elementName.replaceAll("\\s", "").length() != elementName.length()) {//$NON-NLS-1$//$NON-NLS-2$
            MessageDialog.openError(this.getShell(), Messages.getString("_Error"), Messages.getString("_BusinessNameCannotContainEmpty")); //$NON-NLS-1$ //$NON-NLS-2$
            setReturnCode(-1);
            elementNameText.setFocus();
            return false;
        }

        if ("".equals(minOccursText.getText()) && "".equals(maxOccursText.getText())) {//$NON-NLS-1$//$NON-NLS-2$
            minOccurs = 1;
            maxOccurs = 1;
            return false;
        }
        try {
            minOccurs = Integer.parseInt(minOccursText.getText());
        } catch (Exception e1) {
            MessageDialog.openError(this.getShell(), Messages.getString("_Error"), Messages.getString("_MinNoLessThanZero")); //$NON-NLS-1$ //$NON-NLS-2$
            setReturnCode(-1);
            minOccursText.setFocus();
            return false;
        }
        if (minOccurs < 0) {
            MessageDialog.openError(this.getShell(), Messages.getString("_Error"), Messages.getString("_MinNoLessThanZero")); //$NON-NLS-1$ //$NON-NLS-2$
            setReturnCode(-1);
            minOccursText.setFocus();
            return false;
        }

        if ("".equals(maxOccursText.getText())) {//$NON-NLS-1$
            maxOccurs = -1;
        } else {
            try {
                maxOccurs = Integer.parseInt(maxOccursText.getText());
            } catch (Exception e2) {
                MessageDialog.openError(this.getShell(), Messages.getString("_Error"), Messages.getString("_MaxOccBeNum"));  //$NON-NLS-1$ //$NON-NLS-2$
                setReturnCode(-1);
                maxOccursText.setFocus();
                return  false;
            }
            if ((maxOccurs < minOccurs) || (maxOccurs <= 0))
                maxOccurs = -1;
        }
        
        // check that this element does not already exist
        // get position of the selected particle in the container
        for (Iterator<XSDParticle> iter = group.getContents().iterator(); iter.hasNext();) {
            XSDParticle p = (XSDParticle) iter.next();
            if (p.getTerm() instanceof XSDElementDeclaration) {
                XSDElementDeclaration thisDecl = (XSDElementDeclaration) p.getTerm();
                if (thisDecl.getName().equals(elementName)) {
                    MessageDialog.openError(getShell(), Messages.getString("_Error"), Messages.getString("_BusinessEle", elementName));  //$NON-NLS-1$ //$NON-NLS-2$
                    return  false;
                }
            }
        }// for
        
        
        String typeName = getTypeName();
        
        if (!"".equals(typeName)) {//$NON-NLS-1$
            EList<XSDTypeDefinition> list = xsdSchema.getTypeDefinitions();
            for (Iterator<XSDTypeDefinition> iter = list.iterator(); iter.hasNext();) {
                XSDTypeDefinition td = iter.next();
                if (td.getName().equals(typeName)) {
                    if (td instanceof XSDSimpleTypeDefinition) {
                        MessageDialog.openError(getShell(), Messages.getString("_Error"), Messages.getString("_ThisType", typeName));  //$NON-NLS-1$ //$NON-NLS-2$
                        return false;
                    }
                }
            } 
        }

        return true;
    }
    
    public String getElementName() {
        return elementName;
    }
    public int getMaxOccurs() {
        return maxOccurs;
    }

    public int getMinOccurs() {
        return minOccurs;
    }
   
    public boolean isInherit() {
        return inherit;
    }

    public void setInherit(boolean inherit) {
        this.inherit = inherit;
    }

    public boolean isPK() {
        return isPK;
    }

    public void setPK(boolean isPK) {
        this.isPK = isPK;
    }
}
