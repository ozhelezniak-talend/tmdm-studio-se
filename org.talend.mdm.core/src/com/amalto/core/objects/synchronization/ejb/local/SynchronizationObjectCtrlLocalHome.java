/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.synchronization.ejb.local;

/**
 * Local home interface for SynchronizationObjectCtrl.
 * @xdoclet-generated at 9-10-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface SynchronizationObjectCtrlLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/SynchronizationObjectCtrlLocal";
   public static final String JNDI_NAME="amalto/local/core/synchronizationObjectctrl";

   public com.amalto.core.objects.synchronization.ejb.local.SynchronizationObjectCtrlLocal create()
      throws javax.ejb.CreateException;

}
