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
package org.talend.mdm.repository.ui.wizards.process.composite;

import org.talend.mdm.repository.ui.wizards.process.IProcessTypeComposite;
import org.talend.mdm.repository.ui.wizards.process.NewProcessWizard;

/**
 * DOC hbhong class global comment. Detailled comment
 */
public class OtherTypeComposite implements IProcessTypeComposite {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.mdm.repository.ui.wizards.process.IProcessTypeComposite#getCurrentProcessType()
     */
    public int getCurrentProcessType() {

        return NewProcessWizard.OTHER_TYPE;
    }

    public boolean needShowSelectEntityBun() {
        return false;
    }

    public String getProcessPrefix() {
        return ""; //$NON-NLS-1$
    }

    public String getConfigWizardPageId() {
        return null;
    }

}
