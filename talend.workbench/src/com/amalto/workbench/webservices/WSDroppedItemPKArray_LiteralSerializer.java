// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPSerializationContext;
import com.sun.xml.rpc.encoding.literal.LiteralObjectSerializerBase;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;
import com.sun.xml.rpc.streaming.XMLWriter;

public class WSDroppedItemPKArray_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_wsDroppedItemPK_QNAME = new QName("", "wsDroppedItemPK");
    private static final QName ns2_WSDroppedItemPK_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSDroppedItemPK");
    private CombinedSerializer ns2_myWSDroppedItemPK_LiteralSerializer;
    
    public WSDroppedItemPKArray_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSDroppedItemPKArray_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myWSDroppedItemPK_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSDroppedItemPK.class, ns2_WSDroppedItemPK_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSDroppedItemPKArray instance = new com.amalto.workbench.webservices.WSDroppedItemPKArray();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_wsDroppedItemPK_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_wsDroppedItemPK_QNAME))) {
                    value = ns2_myWSDroppedItemPK_LiteralSerializer.deserialize(ns1_wsDroppedItemPK_QNAME, reader, context);
                    if (value == null) {
                        throw new DeserializationException("literal.unexpectedNull");
                    }
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.amalto.workbench.webservices.WSDroppedItemPK[values.size()];
            member = values.toArray((Object[]) member);
            instance.setWsDroppedItemPK((com.amalto.workbench.webservices.WSDroppedItemPK[])member);
        }
        else if(!(reader.getState() == XMLReader.END)) {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSDroppedItemPKArray instance = (com.amalto.workbench.webservices.WSDroppedItemPKArray)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSDroppedItemPKArray instance = (com.amalto.workbench.webservices.WSDroppedItemPKArray)obj;
        
        if (instance.getWsDroppedItemPK() != null) {
            for (int i = 0; i < instance.getWsDroppedItemPK().length; ++i) {
                ns2_myWSDroppedItemPK_LiteralSerializer.serialize(instance.getWsDroppedItemPK()[i], ns1_wsDroppedItemPK_QNAME, null, writer, context);
            }
        }
    }
}
