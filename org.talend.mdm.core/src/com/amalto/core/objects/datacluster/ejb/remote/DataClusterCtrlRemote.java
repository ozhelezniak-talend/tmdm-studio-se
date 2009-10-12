 /*
 * Generated by XDoclet - Do not edit!
 * this class was prodiuced by xdoclet automagically...
 */
package com.amalto.core.objects.datacluster.ejb.remote;

import java.util.*;

/**
 * This class is remote adapter to DataClusterCtrl. It provides convenient way to access
 * facade session bean. Inverit from this class to provide reasonable caching and event handling capabilities.
 *
 * Remote facade for DataClusterCtrl.
 * @xdoclet-generated at 9-10-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */

public class DataClusterCtrlRemote extends Observable
{
    static DataClusterCtrlRemote _instance = null;
    public static DataClusterCtrlRemote getInstance() {
        if(_instance == null) {
	   _instance = new DataClusterCtrlRemote();
	}
	return _instance;
    }

  /**
   * cached remote session interface
   */
  com.amalto.core.objects.datacluster.ejb.remote.DataClusterCtrl _session = null;
  /**
   * return session bean remote interface
   */
   protected com.amalto.core.objects.datacluster.ejb.remote.DataClusterCtrl getSession() {
      try {
   	if(_session == null) {
	   _session = com.amalto.core.objects.datacluster.ejb.local.DataClusterCtrlUtil.getHome().create();
	}
	return _session;
      } catch(Exception ex) {
        // just catch it here and return null.
        // somebody can provide better solution
	ex.printStackTrace();
	return null;
      }
   }

   public com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK putDataCluster ( com.amalto.core.objects.datacluster.ejb.DataClusterPOJO dataCluster )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK retval;
       retval =  getSession().putDataCluster( dataCluster );

      return retval;

   }

   public com.amalto.core.objects.datacluster.ejb.DataClusterPOJO getDataCluster ( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datacluster.ejb.DataClusterPOJO retval;
       retval =  getSession().getDataCluster( pk );

      return retval;

   }

   public com.amalto.core.objects.datacluster.ejb.DataClusterPOJO existsDataCluster ( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datacluster.ejb.DataClusterPOJO retval;
       retval =  getSession().existsDataCluster( pk );

      return retval;

   }

   public com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK removeDataCluster ( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK retval;
       retval =  getSession().removeDataCluster( pk );

      return retval;

   }

   public java.util.Collection getDataClusterPKs ( java.lang.String regex )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.util.Collection retval;
       retval =  getSession().getDataClusterPKs( regex );

      return retval;

   }

   public int addToVocabulary ( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK pk,java.lang.String string )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        int retval;
       retval =  getSession().addToVocabulary( pk,string );

      return retval;

   }

   public java.util.Collection spellCheck ( com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK dcpk,java.lang.String sentence,int treshold,boolean ignoreNonExistantWords )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.util.Collection retval;
       retval =  getSession().spellCheck( dcpk,sentence,treshold,ignoreNonExistantWords );

      return retval;

   }

  /**
   * override this method to provide feedback to interested objects
   * in case collections were changed.
   */
  public void invalidate() {

  	setChanged();
	notifyObservers();
  }
}
