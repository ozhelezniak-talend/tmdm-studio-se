// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoderBase;
import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

public class WSSynchronizationPlanActionCode_Encoder extends SimpleTypeEncoderBase {
    
    private static final SimpleTypeEncoder encoder = XSDStringEncoder.getInstance();
    private static final WSSynchronizationPlanActionCode_Encoder instance = new WSSynchronizationPlanActionCode_Encoder();
    
    private WSSynchronizationPlanActionCode_Encoder() {
    }
    
    public static SimpleTypeEncoder getInstance() {
        return instance;
    }
    
    public String objectToString(Object obj, XMLWriter writer) throws Exception {
        java.lang.String value = ((WSSynchronizationPlanActionCode)obj).getValue();
        return encoder.objectToString(value, writer);
    }
    
    public Object stringToObject(String str, XMLReader reader) throws Exception {
        return WSSynchronizationPlanActionCode.fromValue((java.lang.String)encoder.stringToObject(str, reader));
    }
    
}
