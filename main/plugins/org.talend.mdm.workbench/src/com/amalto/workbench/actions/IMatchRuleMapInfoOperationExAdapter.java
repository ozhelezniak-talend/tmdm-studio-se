// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.actions;

import java.util.Set;

import com.amalto.workbench.exadapter.IExAdapter;

/**
 * created by HHB on 2017-5-9 Detailled comment
 *
 */
public interface IMatchRuleMapInfoOperationExAdapter<T> extends IExAdapter<T> {

    void deleteEntityMapInfo(String entityName);

    void renameEntityMapinfo(String oldName, String newName);

    void renameElementMapinfo(Set<String> paths, String newName);
}
