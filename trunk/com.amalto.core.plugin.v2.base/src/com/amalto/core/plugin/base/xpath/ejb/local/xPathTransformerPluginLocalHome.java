/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.plugin.base.xpath.ejb.local;

/**
 * Local home interface for xPathTransformerPlugin.
 * @xdoclet-generated at 16-07-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface xPathTransformerPluginLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/xPathTransformerPluginLocal";
   public static final String JNDI_NAME="amalto/local/transformer/plugin/xpath";

   public com.amalto.core.plugin.base.xpath.ejb.local.xPathTransformerPluginLocal create()
      throws javax.ejb.CreateException;

}
