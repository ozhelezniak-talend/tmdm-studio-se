// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;

import java.util.List;

import javax.xml.namespace.QName;

import com.sun.xml.rpc.encoding.CombinedSerializer;
import com.sun.xml.rpc.encoding.DeserializationException;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
import com.sun.xml.rpc.encoding.SOAPSerializationContext;
import com.sun.xml.rpc.encoding.SerializationException;
import com.sun.xml.rpc.encoding.literal.LiteralObjectSerializerBase;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLReaderUtil;
import com.sun.xml.rpc.streaming.XMLWriter;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;

public class WSBackgroundJob_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_id_QNAME = new QName("", "id");
    private static final QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final QName ns1_description_QNAME = new QName("", "description");
    private static final QName ns1_status_QNAME = new QName("", "status");
    private static final QName ns2_BackgroundJobStatusType_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "BackgroundJobStatusType");
    private CombinedSerializer ns2myns2_BackgroundJobStatusType__BackgroundJobStatusType_LiteralSerializer;
    private static final QName ns1_message_QNAME = new QName("", "message");
    private static final QName ns1_percentage_QNAME = new QName("", "percentage");
    private static final QName ns3_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns3_myns3__int__java_lang_Integer_Int_Serializer;
    private static final QName ns1_timestamp_QNAME = new QName("", "timestamp");
    private static final QName ns1_pipeline_QNAME = new QName("", "pipeline");
    private static final QName ns2_WSPipeline_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSPipeline");
    private CombinedSerializer ns2_myWSPipeline_LiteralSerializer;
    private static final QName ns1_serializedObject_QNAME = new QName("", "serializedObject");
    private static final QName ns3_base64Binary_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BASE64_BINARY;
    private CombinedSerializer ns3_myns3_base64Binary__byte_Base64Binary_Serializer;
    
    public WSBackgroundJob_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSBackgroundJob_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2myns2_BackgroundJobStatusType__BackgroundJobStatusType_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.BackgroundJobStatusType.class, ns2_BackgroundJobStatusType_TYPE_QNAME);
        ns3_myns3__int__java_lang_Integer_Int_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Integer.class, ns3_int_TYPE_QNAME);
        ns2_myWSPipeline_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSPipeline.class, ns2_WSPipeline_TYPE_QNAME);
        ns3_myns3_base64Binary__byte_Base64Binary_Serializer = (CombinedSerializer)registry.getSerializer("", byte[].class, ns3_base64Binary_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSBackgroundJob instance = new com.amalto.workbench.webservices.WSBackgroundJob();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_id_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_id_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setId((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_id_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_description_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_description_QNAME, reader, context);
                instance.setDescription((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_description_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_status_QNAME)) {
                member = ns2myns2_BackgroundJobStatusType__BackgroundJobStatusType_LiteralSerializer.deserialize(ns1_status_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setStatus((com.amalto.workbench.webservices.BackgroundJobStatusType)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_status_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_message_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_message_QNAME, reader, context);
                instance.setMessage((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_message_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_percentage_QNAME)) {
                member = ns3_myns3__int__java_lang_Integer_Int_Serializer.deserialize(ns1_percentage_QNAME, reader, context);
                instance.setPercentage((java.lang.Integer)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_percentage_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_timestamp_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_timestamp_QNAME, reader, context);
                instance.setTimestamp((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_timestamp_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_pipeline_QNAME)) {
                member = ns2_myWSPipeline_LiteralSerializer.deserialize(ns1_pipeline_QNAME, reader, context);
                instance.setPipeline((com.amalto.workbench.webservices.WSPipeline)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_serializedObject_QNAME)) {
                member = ns3_myns3_base64Binary__byte_Base64Binary_Serializer.deserialize(ns1_serializedObject_QNAME, reader, context);
                instance.setSerializedObject((byte[])member);
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSBackgroundJob instance = (com.amalto.workbench.webservices.WSBackgroundJob)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSBackgroundJob instance = (com.amalto.workbench.webservices.WSBackgroundJob)obj;
        
        if (instance.getId() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getId(), ns1_id_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getDescription(), ns1_description_QNAME, null, writer, context);
        if (instance.getStatus() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2myns2_BackgroundJobStatusType__BackgroundJobStatusType_LiteralSerializer.serialize(instance.getStatus(), ns1_status_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getMessage(), ns1_message_QNAME, null, writer, context);
        ns3_myns3__int__java_lang_Integer_Int_Serializer.serialize(instance.getPercentage(), ns1_percentage_QNAME, null, writer, context);
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getTimestamp(), ns1_timestamp_QNAME, null, writer, context);
        ns2_myWSPipeline_LiteralSerializer.serialize(instance.getPipeline(), ns1_pipeline_QNAME, null, writer, context);
        ns3_myns3_base64Binary__byte_Base64Binary_Serializer.serialize(instance.getSerializedObject(), ns1_serializedObject_QNAME, null, writer, context);
    }
}