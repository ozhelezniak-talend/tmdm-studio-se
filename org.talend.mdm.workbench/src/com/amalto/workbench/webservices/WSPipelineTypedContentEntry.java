// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation （1.1.2_01，编译版 R40）
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


public class WSPipelineTypedContentEntry {
    protected java.lang.String output;
    protected com.amalto.workbench.webservices.WSExtractedContent wsExtractedContent;
    
    public WSPipelineTypedContentEntry() {
    }
    
    public WSPipelineTypedContentEntry(java.lang.String output, com.amalto.workbench.webservices.WSExtractedContent wsExtractedContent) {
        this.output = output;
        this.wsExtractedContent = wsExtractedContent;
    }
    
    public java.lang.String getOutput() {
        return output;
    }
    
    public void setOutput(java.lang.String output) {
        this.output = output;
    }
    
    public com.amalto.workbench.webservices.WSExtractedContent getWsExtractedContent() {
        return wsExtractedContent;
    }
    
    public void setWsExtractedContent(com.amalto.workbench.webservices.WSExtractedContent wsExtractedContent) {
        this.wsExtractedContent = wsExtractedContent;
    }
}