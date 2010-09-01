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
package org.talend.process.model.simulation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.talend.process.model.simulation.SimulationLiteral;
import org.talend.process.model.simulation.SimulationLiteralData;
import org.talend.process.model.simulation.SimulationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Literal Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.process.model.simulation.impl.SimulationLiteralDataImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.talend.process.model.simulation.impl.SimulationLiteralDataImpl#isExpressionBased <em>Expression Based</em>}</li>
 *   <li>{@link org.talend.process.model.simulation.impl.SimulationLiteralDataImpl#getLiterals <em>Literals</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimulationLiteralDataImpl extends SimulationElementImpl implements SimulationLiteralData {
    /**
     * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected static final String EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected String expression = EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #isExpressionBased() <em>Expression Based</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isExpressionBased()
     * @generated
     * @ordered
     */
    protected static final boolean EXPRESSION_BASED_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isExpressionBased() <em>Expression Based</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isExpressionBased()
     * @generated
     * @ordered
     */
    protected boolean expressionBased = EXPRESSION_BASED_EDEFAULT;

    /**
     * The cached value of the '{@link #getLiterals() <em>Literals</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLiterals()
     * @generated
     * @ordered
     */
    protected EList<SimulationLiteral> literals;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimulationLiteralDataImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.SIMULATION_LITERAL_DATA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getExpression() {
        return expression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExpression(String newExpression) {
        String oldExpression = expression;
        expression = newExpression;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION, oldExpression, expression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isExpressionBased() {
        return expressionBased;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExpressionBased(boolean newExpressionBased) {
        boolean oldExpressionBased = expressionBased;
        expressionBased = newExpressionBased;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION_BASED, oldExpressionBased, expressionBased));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SimulationLiteral> getLiterals() {
        if (literals == null) {
            literals = new EObjectContainmentEList<SimulationLiteral>(SimulationLiteral.class, this, SimulationPackage.SIMULATION_LITERAL_DATA__LITERALS);
        }
        return literals;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case SimulationPackage.SIMULATION_LITERAL_DATA__LITERALS:
                return ((InternalEList<?>)getLiterals()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION:
                return getExpression();
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION_BASED:
                return isExpressionBased();
            case SimulationPackage.SIMULATION_LITERAL_DATA__LITERALS:
                return getLiterals();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION:
                setExpression((String)newValue);
                return;
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION_BASED:
                setExpressionBased((Boolean)newValue);
                return;
            case SimulationPackage.SIMULATION_LITERAL_DATA__LITERALS:
                getLiterals().clear();
                getLiterals().addAll((Collection<? extends SimulationLiteral>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION:
                setExpression(EXPRESSION_EDEFAULT);
                return;
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION_BASED:
                setExpressionBased(EXPRESSION_BASED_EDEFAULT);
                return;
            case SimulationPackage.SIMULATION_LITERAL_DATA__LITERALS:
                getLiterals().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION:
                return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
            case SimulationPackage.SIMULATION_LITERAL_DATA__EXPRESSION_BASED:
                return expressionBased != EXPRESSION_BASED_EDEFAULT;
            case SimulationPackage.SIMULATION_LITERAL_DATA__LITERALS:
                return literals != null && !literals.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (expression: "); //$NON-NLS-1$
        result.append(expression);
        result.append(", expressionBased: "); //$NON-NLS-1$
        result.append(expressionBased);
        result.append(')');
        return result.toString();
    }

} //SimulationLiteralDataImpl
