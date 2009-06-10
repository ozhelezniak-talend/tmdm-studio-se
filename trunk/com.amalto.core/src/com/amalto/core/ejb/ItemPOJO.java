package com.amalto.core.ejb;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJBException;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.exolab.castor.xml.Marshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amalto.core.ejb.local.XmlServerSLWrapperLocal;
import com.amalto.core.ejb.local.XmlServerSLWrapperLocalHome;
import com.amalto.core.objects.datacluster.ejb.DataClusterPOJO;
import com.amalto.core.objects.datacluster.ejb.DataClusterPOJOPK;
import com.amalto.core.objects.datacluster.ejb.local.DataClusterCtrlLocal;
import com.amalto.core.objects.datacluster.ejb.local.DataClusterCtrlLocalHome;
import com.amalto.core.objects.synchronization.ejb.SynchronizationPlanPOJOPK;
import com.amalto.core.objects.universe.ejb.UniversePOJO;
import com.amalto.core.util.LocalUser;
import com.amalto.core.util.Util;
import com.amalto.core.util.XtentisException;


/**
 * @author Bruno Grieder
 * 
 */
public class ItemPOJO implements Serializable{
   
	public final static String LOGGING_EVENT="logging_event";
	
    private String conceptName;
    private DataClusterPOJOPK dataClusterPOJOPK;
    private SynchronizationPlanPOJOPK planPK;
    private long insertionTime;
    private String[] itemIds;
    private Element projection;
    
    
    /**
     * 
     */
    public ItemPOJO() {
        super();
    }
    

	/**
	 * 
	 * @param clusterPK
	 * @param concept
	 * @param ids
	 * @param time
	 * @param projection
	 */
	public ItemPOJO(DataClusterPOJOPK clusterPK, String concept, String[] ids, long time, Element projection) {
		this.conceptName = concept;
		this.dataClusterPOJOPK = clusterPK;
		this.insertionTime = time;
		this.itemIds = ids;
		this.projection = projection;
		this.planPK = null;
	}
	
	/**
	 * 
	 * @param clusterPK
	 * @param concept
	 * @param ids
	 * @param time
	 * @param projectionAsString
	 */
	public ItemPOJO(DataClusterPOJOPK clusterPK, String concept, String[] ids, long time, String projectionAsString) {
		this.conceptName = concept;
		this.dataClusterPOJOPK = clusterPK;
		this.insertionTime = time;
		this.itemIds = ids;
		this.projectionString = projectionAsString;
		this.planPK = null;
	}


	/**
	 * @return Returns the conceptName.
	 */
	public String getConceptName() {
		return conceptName;
	}

	/**
	 * @param conceptName The conceptName to set.
	 */
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}


	/**
	 * @return Returns the dataClusterPK.
	 */
	public DataClusterPOJOPK getDataClusterPOJOPK() {
		return dataClusterPOJOPK;
	}

	/**
	 * @param dataClusterPOJOPK The dataClusterPK to set.
	 */
	public void setDataClusterPK(DataClusterPOJOPK dataClusterPOJOPK) {
		this.dataClusterPOJOPK = dataClusterPOJOPK;
	}


	/**
	 * @return Returns the insertionTime.
	 */
	public long getInsertionTime() {
		return insertionTime;
	}

	/**
	 * @param insertionTime The insertionTime to set.
	 */
	public void setInsertionTime(long insertionTime) {
		this.insertionTime = insertionTime;
	}

	/**
	 * If the item was not changed since last synchronization, 
	 * this will contain the name of the plan that synchronized it
	 * @return
	 * 		The {@link SynchronizationPlanPOJOPK}
	 */
	public SynchronizationPlanPOJOPK getPlanPK() {
    	return planPK;
    }

	public void setPlanPK(SynchronizationPlanPOJOPK planPK) {
    	this.planPK = planPK;
    }


	/**
	 * @return Returns the itemIds.
	 */
	public String[] getItemIds() {
		return itemIds;
	}

	/**
	 * @param itemIds The itemIds to set.
	 */
	public void setItemIds(String[] itemIds) {
		this.itemIds = itemIds;
	}

	private String projectionString = null;
	/**
	 * 
	 * @return The projection as a String
	 * @throws XtentisException
	 */
	public String getProjectionAsString() throws XtentisException{
		if (projectionString == null) {
			try {
				projectionString = Util.nodeToString(projection);
			} catch (Exception e) {
				String err = "Unable to serialize the Item "+this.getItemPOJOPK().getUniqueID()+": "+e.getLocalizedMessage();
				org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
				throw new XtentisException(err);
			}
		}
		return projectionString;
	}

	/**
	 * @param projection The projection to set.
	 */
	public void setProjectionAsString(String projection) throws XtentisException{
		this.projectionString = projection;
	}


	/**
	 * @return Returns the projection.
	 */
	public Element getProjection() throws XtentisException{
		if (projection==null) {
			try {
				projection = Util.parse(this.projectionString).getDocumentElement();
			} catch (Exception e) {
				String err = "Unable to parse the Item "+this.getItemPOJOPK().getUniqueID()+". "
				+e.getClass().getName()+": "+e.getLocalizedMessage();
				org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
				throw new XtentisException(err);
			}
		}
		return projection;
	}

	/**
	 * @param projection The projection to set.
	 */
	public void setProjection(Element projection) {
		this.projection = projection;
		projectionString = null;
	}
	
	
	/**
	 * The PK
	 * @return the pk, null if undefined
	 */
	public ItemPOJOPK getItemPOJOPK() {
		if ((getDataClusterPOJOPK() == null) || (getConceptName()==null) || (getItemIds()==null)) return null;
		return new ItemPOJOPK(
				getDataClusterPOJOPK(),
				getConceptName(),
				getItemIds()
		);
	}
	


	private static Pattern pLoad = Pattern.compile(".*?(<c>.*?</t>).*?<p>(.*)</p>.*",Pattern.DOTALL);
	
    
    /**
     * Loads an Item. User rights are checked.
     * 
     * @param itemPOJOPK
     * @return
     * 	the {@link ItemPOJO}
     * @throws XtentisException
     */
    public static ItemPOJO load(ItemPOJOPK itemPOJOPK) throws XtentisException {
//    	org.apache.log4j.Logger.getLogger(ItemPOJO.class).trace("load() "+itemPOJOPK.getUniqueID());
        
    	//Check authorizations
    	boolean authorized = false;
    	LocalUser user = LocalUser.getLocalUser();
    	if ("admin".equals(user.getUsername()) || LocalUser.UNAUTHENTICATED_USER.equals(user.getUsername())) { 
    		authorized = true;
    	} else if (user.userCanRead(ItemPOJO.class, itemPOJOPK.getUniqueID())) {
    		authorized = true;
    	}
    	
    	if (! authorized) {
    	    String err = 
    	    	"Unauthorized read access by " +
    	    	"user "+user.getUsername()+" on Item '"+itemPOJOPK.getUniqueID()+"'"; 
    	    org.apache.log4j.Logger.getLogger(ObjectPOJO.class).error(err);
    		throw new XtentisException(err);    				
    	}
    	
    	//get the universe and revision ID
    	UniversePOJO universe = LocalUser.getLocalUser().getUniverse();
    	if (universe == null) {
    		String err = "ERROR: no Universe set for user '"+LocalUser.getLocalUser().getUsername()+"'";
    		org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err);
    		throw new XtentisException(err);
    	}
    	String revisionID = universe.getConceptRevisionID(itemPOJOPK.getConceptName());
    	
    	//load the item
		return load(revisionID, itemPOJOPK);
	        	                                            

    }
    
    /**
     * Loads an Item<br/>
     * User rights are NOT checked
     * @param itemPOJOPK
     * @return
     * 	the {@link ItemPOJO}
     * @throws XtentisException
     */
    public static ItemPOJO load(String revisionID, ItemPOJOPK itemPOJOPK) throws XtentisException {
    	
    	XmlServerSLWrapperLocal server;
		try {
			server = ((XmlServerSLWrapperLocalHome)new InitialContext().lookup(XmlServerSLWrapperLocalHome.JNDI_NAME)).create();
		} catch (Exception e) {
			String err = "Unable to access the XML Server wrapper";
			org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
			throw new XtentisException(err);
		}

        try {
              //retrieve the item
            String urlid =getFilename(itemPOJOPK); 
            String item = server.getDocumentAsString(revisionID, itemPOJOPK.getDataClusterPOJOPK().getUniqueId(), urlid);
                                    
            if (item==null) {
                return null;
            }
            
            ItemPOJO newItem = new ItemPOJO();
                      
            //Build the result
            newItem.setDataClusterPK(itemPOJOPK.getDataClusterPOJOPK());
            newItem.setConceptName(itemPOJOPK.getConceptName());
            newItem.setItemIds(itemPOJOPK.getIds());
            
            Matcher m = null;
            m = pLoad.matcher(item);
            if (m.matches()) {
            	String h = "<header>"+m.group(1)+"</header>";
            	Element header = Util.parse(h).getDocumentElement();
            	newItem.setInsertionTime(Long.parseLong(Util.getFirstTextNode(header, "t")));
            	String plan = Util.getFirstTextNode(header, "sp");
            	if (plan !=null)
            		newItem.setPlanPK(new SynchronizationPlanPOJOPK(plan));
            	else
            		newItem.setPlanPK(null);
            	newItem.setProjectionAsString(m.group(2));
            }else {
            	throw new XtentisException("Cannot parse item read from XML Server");
            }
            
            return newItem;
	        	                                            
	    } catch (Exception e) {
    	    String err = "Unable to load the item  "+itemPOJOPK.getUniqueID()
    	    		+": "+e.getClass().getName()+": "+e.getLocalizedMessage();
    	    org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
    	    throw new EJBException(err);
	    } 
    }



    /**
     * Removes an item
     * @param itemPOJOPK
     * @return
     * 		The {@link ItemPOJOPK} of the item removed
     * @throws XtentisException
     */
    public static ItemPOJOPK remove(ItemPOJOPK itemPOJOPK) throws XtentisException {
    	
    	//org.apache.log4j.Logger.getLogger(this.getClass()).trace(
    	//        "remove() "+pk.getDataCluster().getName()+"."+Util.joinStrings(pk.getItemIds(), "."));
                
    	if (itemPOJOPK==null) return null;
    	
    	//for load we need to be admin, or have a role of admin , or role of write on instance or role of read on instance
    	boolean authorized = false;
    	LocalUser user = LocalUser.getLocalUser();
    	if ("admin".equals(user.getUsername()) || LocalUser.UNAUTHENTICATED_USER.equals(user.getUsername())) { 
    		authorized = true;
    	} else if (user.userCanWrite(ItemPOJO.class, itemPOJOPK.getUniqueID())) {
    		authorized = true;
    	}
    	
    	if (! authorized) {
    	    String err = 
    	    	"Unauthorized delete access by " +
    	    	"user "+user.getUsername()+" on Item '"+itemPOJOPK.getUniqueID()+"'"; 
    	    org.apache.log4j.Logger.getLogger(ObjectPOJO.class).error(err);
    		throw new XtentisException(err);    				
    	}
    	
    	//get the universe and revision ID
    	UniversePOJO universe = LocalUser.getLocalUser().getUniverse();
    	if (universe == null) {
    		String err = "ERROR: no Universe set for user '"+LocalUser.getLocalUser().getUsername()+"'";
    		org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err);
    		throw new XtentisException(err);
    	}
    	String revisionID = universe.getConceptRevisionID(itemPOJOPK.getConceptName());
    	
    	XmlServerSLWrapperLocal server;
    	
		try {
			server  =  ((XmlServerSLWrapperLocalHome)new InitialContext().lookup(XmlServerSLWrapperLocalHome.JNDI_NAME)).create();
		} catch (Exception e) {
			String err = "Unable to access the XML Server wrapper";
			org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
			throw new XtentisException(err);
		}
    	
        try {
 
            //remove the doc
            long res = server.deleteDocument(
            		revisionID,
            		itemPOJOPK.getDataClusterPOJOPK().getUniqueId(),
            		getFilename(itemPOJOPK)
            );
            if (res==-1) return null;
            
            return itemPOJOPK;
            
	    } catch (Exception e) {
    	    String err = "Unable to remove the item "+itemPOJOPK.getUniqueID()
    	    		+": "+e.getClass().getName()+": "+e.getLocalizedMessage();
    	    org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
    	    throw new XtentisException(err);
	    }  

    }
    
    /**
     * @param itemPOJOPK
     * @param partPath
     * @return DroppedItemPOJOPK
     * @throws XtentisException
     * 
     * drop an item to items-trash
     */
    public static DroppedItemPOJOPK drop(ItemPOJOPK itemPOJOPK,String partPath) throws XtentisException {
    	
        //validate input
    	if (itemPOJOPK==null) return null;
    	if (partPath==null||partPath.equals(""))partPath="/";
    	
    	//for load we need to be admin, or have a role of admin , or role of write on instance or role of read on instance
    	boolean authorized = false;
    	LocalUser user = LocalUser.getLocalUser();
    	if ("admin".equals(user.getUsername()) || LocalUser.UNAUTHENTICATED_USER.equals(user.getUsername())) { 
    		authorized = true;
    	} else if (user.userCanWrite(ItemPOJO.class, itemPOJOPK.getUniqueID())) {
    		authorized = true;
    	}
    	
    	if (! authorized) {
    	    String err = 
    	    	"Unauthorized drop access by " +
    	    	"user "+user.getUsername()+" on Item '"+itemPOJOPK.getUniqueID()+"'"; 
    	    org.apache.log4j.Logger.getLogger(ObjectPOJO.class).error(err);
    		throw new XtentisException(err);    				
    	}
    	String userName=user.getUsername();
    	
    	//get the universe and revision ID
    	UniversePOJO universe = LocalUser.getLocalUser().getUniverse();
    	if (universe == null) {
    		String err = "ERROR: no Universe set for user '"+LocalUser.getLocalUser().getUsername()+"'";
    		org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err);
    		throw new XtentisException(err);
    	}
    	String revisionID = universe.getConceptRevisionID(itemPOJOPK.getConceptName());
    	
    	
    	//get XmlServerSLWrapperLocal
    	XmlServerSLWrapperLocal server;
    	
		try {
			server  =  ((XmlServerSLWrapperLocalHome)new InitialContext().lookup(XmlServerSLWrapperLocalHome.JNDI_NAME)).create();
		} catch (Exception e) {
			String err = "Unable to access the XML Server wrapper";
			org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
			throw new XtentisException(err);
		}
		
		//get DataClusterCtrlLocal
		DataClusterCtrlLocal dataClusterCtrlLocal;
		try {
			dataClusterCtrlLocal  =  ((DataClusterCtrlLocalHome)new InitialContext().lookup(DataClusterCtrlLocalHome.JNDI_NAME)).create();
		} catch (Exception e) {
			String err = "Unable to access the DataClusterCtrlLocal";
			org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
			throw new XtentisException(err);
		}
		
        try {
        	//init MDMItemsTrash Cluster
        	if(dataClusterCtrlLocal.existsDataCluster(new DataClusterPOJOPK("MDMItemsTrash"))==null){
    			dataClusterCtrlLocal.putDataCluster(new DataClusterPOJO("MDMItemsTrash","Holds logical deleted items",null));
    			org.apache.log4j.Logger.getLogger(ItemPOJO.class).info("Init MDMItemsTrash Cluster");
    		}
        	
        	String dataClusterName=itemPOJOPK.getDataClusterPOJOPK().getUniqueId();
        	String uniqueID=getFilename(itemPOJOPK);
        	
        	StringBuffer xmlDocument=new StringBuffer();
        	Document sourceDoc=null;
        	NodeList toDeleteNodeList=null;
        	String xml = server.getDocumentAsString(revisionID, dataClusterName, uniqueID,null);
        	if (xml==null) return null;
        	//get to delete item content
        	if(partPath.equals("/")){
        		
            	xmlDocument.append(xml);
            	
        	}else{
        		
        		String xPath ="/ii/p"+partPath;
        		
        		sourceDoc=Util.parse(xml);
        		toDeleteNodeList=Util.getNodeList(sourceDoc, xPath);
        		if(toDeleteNodeList.getLength()==0)return null;
            	for (int i = 0; i < toDeleteNodeList.getLength(); i++) {
            		Node node=toDeleteNodeList.item(i);
            		xmlDocument.append(Util.nodeToString(node));
				}
        		
            	/* another way:
            	String query ="document('"+uniqueID+"')/ii/p"+partPath;
            	ArrayList<String> results=server.runQuery(revisionID, dataClusterName, query,null);
        		if (results==null||results.size()==0) return null; 
        		for (int i = 0; i < results.size(); i++) {
        			xmlDocument.append(results.get(i));
				}
        		*/
        	}
        	
        	//str 2 pojo
        	DroppedItemPOJO droppedItemPOJO=new DroppedItemPOJO(revisionID,itemPOJOPK.getDataClusterPOJOPK(),uniqueID,itemPOJOPK.getConceptName(),itemPOJOPK.getIds(),partPath,xmlDocument.toString(),userName,new Long(System.currentTimeMillis()));
        	
        	//Marshal
    		StringWriter sw = new StringWriter();
    		Marshaller.marshal(droppedItemPOJO, sw);
    		
        	//copy item content
        	long res=server.putDocumentFromString(sw.toString(), droppedItemPOJO.obtainDroppedItemPK().getUniquePK(), "MDMItemsTrash", null);
            if(res==-1)return null;
        	//delete source item
        	
            try {
				if (partPath.equals("/")) {

					server.deleteDocument(revisionID, dataClusterName,uniqueID);

				} else {
					if (toDeleteNodeList != null) {
						Node lastParentNode = null;
						Node formatSiblingNode = null;
						for (int i = 0; i < toDeleteNodeList.getLength(); i++) {
							Node node = toDeleteNodeList.item(i);
							lastParentNode = node.getParentNode();
							formatSiblingNode = node.getNextSibling();
							if (lastParentNode != null){
								lastParentNode.removeChild(node);
							}	
							if (formatSiblingNode != null && formatSiblingNode.getNodeValue().matches("\\s+")){
								lastParentNode.removeChild(formatSiblingNode);
							}
						}
					}

					server.putDocumentFromString(Util.nodeToString(sourceDoc),uniqueID, dataClusterName, revisionID);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// roll back
				server.deleteDocument(null, "MDMItemsTrash",droppedItemPOJO.obtainDroppedItemPK().getUniquePK());
				return null;
			}
            
            return droppedItemPOJO.obtainDroppedItemPK();
            
	    } catch (Exception e) {
    	    String err = "Unable to drop the item "+itemPOJOPK.getUniqueID()
    	    		+": "+e.getClass().getName()+": "+e.getLocalizedMessage();
    	    org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
    	    throw new XtentisException(err);
	    }  

    }

    /**
     * Stores the item in DB. Users rights will be checked
     * @return
     * 		The {@link ItemPOJOPK} of the stored item
     * @throws XtentisException
     */
    public ItemPOJOPK store() throws XtentisException {
        //org.apache.log4j.Logger.getLogger(this.getClass()).debug("store() "));
        
    	if (getItemPOJOPK()==null) return null;
    	    	
    	//for load we need to be admin, or have a role of admin , or role of write on instance or role of read on instance
    	boolean authorized = false;
    	LocalUser user = LocalUser.getLocalUser();
    	if ("admin".equals(user.getUsername()) || LocalUser.UNAUTHENTICATED_USER.equals(user.getUsername())) { 
    		authorized = true;
    	} else if (user.userCanWrite(ItemPOJO.class, getItemPOJOPK().getUniqueID())) {
    		authorized = true;
    	}
    	
    	if (! authorized) {
    	    String err = 
    	    	"Unauthorized write access by " +
    	    	"user "+user.getUsername()+" on Item '"+getItemPOJOPK().getUniqueID()+"'"; 
    	    org.apache.log4j.Logger.getLogger(ObjectPOJO.class).error(err);
    		throw new XtentisException(err);    				
    	}
    	
    	//get the universe and revision ID
    	UniversePOJO universe = LocalUser.getLocalUser().getUniverse();
    	if (universe == null) {
    		String err = "ERROR: no Universe set for user '"+LocalUser.getLocalUser().getUsername()+"'";
    		org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err);
    		throw new XtentisException(err);
    	}
    	String revisionID = universe.getConceptRevisionID(getItemPOJOPK().getConceptName());
    	
    	return store(revisionID);

    }
    
    /**
     * Stores the item in DB.<br/>
     * Users rights will NOT be checked
     * @return
     * 		The {@link ItemPOJOPK} of the stored item
     * @throws XtentisException
     */
    public ItemPOJOPK store(String revisionID) throws XtentisException {
    	
    	XmlServerSLWrapperLocal server;
    	
		try {
			server  =  ((XmlServerSLWrapperLocalHome)new InitialContext().lookup(XmlServerSLWrapperLocalHome.JNDI_NAME)).create();
		} catch (Exception e) {
			String err = "Unable to access the XML Server wrapper";
			org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
			throw new XtentisException(err);
		}

        try {
        	
        	String xml = serialize();
    		org.apache.log4j.Logger.getLogger(this.getClass()).trace("store() "+getItemPOJOPK().getUniqueID()+"\n"+xml);
 
            //store
            if ( -1 == server.putDocumentFromString(
            		xml, 
            		getFilename(getItemPOJOPK()),
            		getDataClusterPOJOPK().getUniqueId(),
            		revisionID
            	))
            	return null;
                        
            return getItemPOJOPK();
	    } catch (Exception e) {
    	    String err = "Unable to store the item "+getItemPOJOPK().getUniqueID()
    	    		+": "+e.getClass().getName()+": "+e.getLocalizedMessage();
    	    org.apache.log4j.Logger.getLogger(this.getClass()).error(err,e);
    	    throw new EJBException(err);
	    } 

    }

    /**
     * Parses a marshaled item back into an {@link ItemPOJO}
     * @param marshaledItem
     * @return
     * 		the {@link ItemPOJO}
     * @throws XtentisException
     */
    public static ItemPOJO parse(String marshaledItem) throws XtentisException {
    	try {
    		ItemPOJO newItem = new ItemPOJO();
            Matcher m = null;
            m = pLoad.matcher(marshaledItem);
            if (m.matches()) {
            	String h = "<header>"+m.group(1)+"</header>";
            	Element header = Util.parse(h).getDocumentElement();
            	newItem.setConceptName(Util.getFirstTextNode(header, "n"));
            	newItem.setDataClusterPK(new DataClusterPOJOPK(Util.getFirstTextNode(header, "c")));
            	newItem.setItemIds(Util.getTextNodes(header, "i"));
            	newItem.setInsertionTime(Long.parseLong(Util.getFirstTextNode(header, "t")));
            	String plan = Util.getFirstTextNode(header, "sp");
            	if (plan !=null)
            		newItem.setPlanPK(new SynchronizationPlanPOJOPK(plan));
            	else
            		newItem.setPlanPK(null);
            	newItem.setProjectionAsString(m.group(2));
            	return newItem;
            } else {
            	throw new XtentisException("Cannot parse item read from XML Server");
            }
        } catch (Exception e) {
        	String err = "Unable to parse the item \n"+marshaledItem;
        	org.apache.log4j.Logger.getLogger(ItemPOJO.class).error(err,e);
        	throw new XtentisException(err);
        }
        
    }
    
    /**
     * Serializes the object to an xml string
     * @return the xml string
     * 
     */
    public String serialize() throws XtentisException{
            String item =
                "<ii>" +
                "	<c>"+StringEscapeUtils.escapeXml(getDataClusterPOJOPK().getUniqueId())+"</c>" +
                "	<n>"+StringEscapeUtils.escapeXml(getConceptName())+"</n>" +
                "	<sp>"+(getPlanPK() == null ? "" : StringEscapeUtils.escapeXml(getPlanPK().getUniqueId()))+"</sp>";
            	String[] ids = getItemIds();
            	for (int i = 0; i < ids.length; i++) {
            		item+="	<i>"+(ids[i]==null? "" : StringEscapeUtils.escapeXml(ids[i].trim()))+"</i>";
    			}
                item+=
                "	<t>"+getInsertionTime()+"</t>";
                item+=        
                "	<p>"+getProjectionAsString()+"</p>" ; 
                item+=
                "</ii>";
            
        return item;
    }
    
    
	/**
     * Retrieve all {@link ItemPOJOPK}s of items matching a particular concept Pattern and instance pattern,
     * and that are unsynchronized against a particular plan<br/>
     * The user must have the "administration" role to perform this task
     * @param revisionID
     * @param conceptName
     * @param instancePattern
     * @param planPK
     * @return a Collection of ObjectPOJOPK
     * @throws XtentisException
     */
    public static ArrayList<ItemPOJOPK> findAllUnsynchronizedPKs(
    	String revisionID, 
    	DataClusterPOJOPK dataClusterPOJOPK,
    	String conceptName, 
    	String instancePattern, 
    	SynchronizationPlanPOJOPK planPK,
    	long start,
    	int limit
    ) throws XtentisException {
       	try {
       		
	    	//check if we are admin 
	    	LocalUser user = LocalUser.getLocalUser();
	    	if (!user.getRoles().contains("administration")) {
	    		String err = "Only an user with the 'administration' role can call the synchronization methods";
				org.apache.log4j.Logger.getLogger(ObjectPOJO.class).error(err);
				throw new XtentisException(err);
	    	}
	    		    	
            //get the xml server wrapper
            XmlServerSLWrapperLocal server = null;
			try {
				server  =  ((XmlServerSLWrapperLocalHome)new InitialContext().lookup(XmlServerSLWrapperLocalHome.JNDI_NAME)).create();
			} catch (Exception e) {
				String err = "Error Finding All Unsynchronized PKs: unable to access the XML Server wrapper";
				org.apache.log4j.Logger.getLogger(ObjectPOJO.class).error(err,e);
				throw new XtentisException(err);
			}
           
			String conceptPatternCondition = (conceptName == null || "".equals(conceptName)) ? "" : "[n/text() eq '"+conceptName+"']";
			String instancePatternCondition = (instancePattern == null || ".*".equals(instancePattern)) ? "" : "[matches(i/text(),'"+instancePattern+"')]";
			String synchronizationCondition = planPK == null ? "" : "[not (./sp/text() eq '"+planPK.getUniqueId()+"')]";
			String query =
				"let $a := /ii"+conceptPatternCondition+instancePatternCondition+synchronizationCondition+"\n"
				+"return subsequence($a,"+(start+1)+","+limit+")";
			
            //retrieve the objects
			ArrayList<String> res  = server.runQuery(
				revisionID, 
				dataClusterPOJOPK.getUniqueId(), 
				query, 
				null
			);
			
			ArrayList<ItemPOJOPK> list = new ArrayList<ItemPOJOPK>();
			for (Iterator<String> iterator = res.iterator(); iterator.hasNext(); ) {
				String marshaledItem = iterator.next();
				ItemPOJO pojo = parse(marshaledItem);
				list.add(pojo.getItemPOJOPK());
			}
			
			return list;
	    	
    	} catch (XtentisException e) {
    		throw(e);
	    } catch (Exception e) {
    	    String err = "Error Finding All Unsynchronized PKs"
    	    		+": "+e.getClass().getName()+": "+e.getLocalizedMessage();
    	    org.apache.log4j.Logger.getLogger(ObjectPOJO.class).error(err,e);
    	    throw new XtentisException(err);
	    } 
    }
    
    /*************************************************************************************************************
     * 
     *  UTILITIES
     * 
     *************************************************************************************************************/
    
    
    @Override
    public String toString() {
    	try {
			return serialize();
		} catch (XtentisException e) {
			String err = "Unable to serialize the item: "+e.getMessage();
			org.apache.log4j.Logger.getLogger(this.getClass()).info("ERROR SYSTRACE toString() "+err,e);
			throw new IllegalArgumentException(e.getMessage());
		}
    }

    public static String getFilename(ItemPOJOPK itemPOJOPK) throws UnsupportedEncodingException{
    	//return (new BASE64Encoder()).encode(getKeyAsString(itemPOJOPK).getBytes("utf-8"));
    	return itemPOJOPK.getUniqueID();
    }
    
    private static Pattern pathWithoutConditions = Pattern.compile("(.*?)[\\[|/].*");
	/**
	 * Returns the first part - eg. the concept - from the path
	 * @param path
	 * @return the Concept
	 */
    public static String getConceptFromPath(String path) {
    	if (!path.endsWith("/")) path+="/";
    	Matcher m = pathWithoutConditions.matcher(path);
    	if (m.matches()) {
    		return m.group(1);
    	} else {
    		return null;
    	}
    }

    

}
