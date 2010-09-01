/**
 * Copyright (C) 2009 BonitaSoft S.A.
 * BonitaSoft, 31 rue Gustave Eiffel - 38000 Grenoble
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id$
 */
package org.talend.process.model.process;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attachment Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.process.model.process.AttachmentData#getBarPath <em>Bar Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.process.model.process.ProcessPackage#getAttachmentData()
 * @model
 * @generated
 */
public interface AttachmentData extends Data {
    /**
     * Returns the value of the '<em><b>Bar Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bar Path</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bar Path</em>' attribute.
     * @see #setBarPath(String)
     * @see org.talend.process.model.process.ProcessPackage#getAttachmentData_BarPath()
     * @model
     * @generated
     */
    String getBarPath();

    /**
     * Sets the value of the '{@link org.talend.process.model.process.AttachmentData#getBarPath <em>Bar Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Bar Path</em>' attribute.
     * @see #getBarPath()
     * @generated
     */
    void setBarPath(String value);

} // AttachmentData
