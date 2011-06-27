// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2011 Talend �C www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.mdm.repository.core.impl;

import org.eclipse.swt.graphics.Image;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.mdm.repository.core.IRepositoryNodeLabelProvider;
import org.talend.mdm.repository.model.mdmproperties.ContainerItem;
import org.talend.mdm.repository.model.mdmproperties.ContainerType;
import org.talend.mdm.repository.model.mdmproperties.MDMServerObjectItem;
import org.talend.mdm.repository.model.mdmproperties.WSMenuItem;

import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;

/**
 * DOC hbhong class global comment. Detailled comment <br/>
 * 
 */
public abstract class AbstractLabelProvider implements IRepositoryNodeLabelProvider {

    private static final Image FOLDER_IMG = ImageCache.getCreatedImage(EImage.FORDER.getPath());

    @Override
    public String getText(Object element) {
        Item item = getItem(element);
        if (item != null) {
            return getText(item);
        }
        return ""; //$NON-NLS-1$
    }

    protected Item getItem(Object element) {
        if (element instanceof IRepositoryViewObject) {
            Item item = ((IRepositoryViewObject) element).getProperty().getItem();
            return item;
        }
        return null;
    }

    @Override
    public Image getImage(Object element) {
        Item item = getItem(element);
        if (item != null) {

            if (item instanceof ContainerItem) {
                switch (((ContainerItem) item).getType()) {
                case CATEGORY:
                    return getCategoryImage();
                case FOLDER:
                case SYSTEM_FOLDER:
                    return FOLDER_IMG;
                }
            }
        }
        return null;
    }

    @Override
    public String getDescription(Object anElement) {
        return null;
    }

    private String getText(Item item) {
        if (item instanceof ContainerItem) {
            return getConainerItemText(item);
        }
        if (item instanceof MDMServerObjectItem) {
            return getServerObjectItemText(item);
        }
        return ""; //$NON-NLS-1$
    }

    protected String getConainerItemText(Item item) {
        return ((ContainerItem) item).getLabel();
    }

    protected abstract String getServerObjectItemText(Item item);

    protected abstract Image getCategoryImage();
}
