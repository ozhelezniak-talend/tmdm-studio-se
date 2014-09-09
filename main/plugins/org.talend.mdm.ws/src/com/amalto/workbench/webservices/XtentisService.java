package com.amalto.workbench.webservices;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.6.15
 * 2014-09-09T11:38:04.870+08:00
 * Generated source version: 2.6.15
 * 
 */
@WebServiceClient(name = "XtentisService", 
                  wsdlLocation = "file:/E:/GW/tmdm-studio-se/main/plugins/org.talend.mdm.ws/../../../../tmdm-server-se/org.talend.mdm.core.open/src/META-INF/wsdl/webservices.wsdl",
                  targetNamespace = "urn-com-amalto-xtentis-webservice") 
public class XtentisService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn-com-amalto-xtentis-webservice", "XtentisService");
    public final static QName XtentisPort = new QName("urn-com-amalto-xtentis-webservice", "XtentisPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/E:/GW/tmdm-studio-se/main/plugins/org.talend.mdm.ws/../../../../tmdm-server-se/org.talend.mdm.core.open/src/META-INF/wsdl/webservices.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(XtentisService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/E:/GW/tmdm-studio-se/main/plugins/org.talend.mdm.ws/../../../../tmdm-server-se/org.talend.mdm.core.open/src/META-INF/wsdl/webservices.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public XtentisService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public XtentisService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public XtentisService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public XtentisService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public XtentisService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public XtentisService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns XtentisPort
     */
    @WebEndpoint(name = "XtentisPort")
    public XtentisPort getXtentisPort() {
        return super.getPort(XtentisPort, XtentisPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns XtentisPort
     */
    @WebEndpoint(name = "XtentisPort")
    public XtentisPort getXtentisPort(WebServiceFeature... features) {
        return super.getPort(XtentisPort, XtentisPort.class, features);
    }

}