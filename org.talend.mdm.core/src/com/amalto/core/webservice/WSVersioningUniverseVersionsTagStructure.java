// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation ��1.1.2_01������� R40��
// Generated source version: 1.1.2

package com.amalto.core.webservice;


public class WSVersioningUniverseVersionsTagStructure {
    protected java.lang.String tagName;
    protected java.lang.String lastDate;
    protected java.lang.String lastAuthor;
    protected java.lang.String lastComment;
    protected com.amalto.core.webservice.WSStringArray clusters;
    
    public WSVersioningUniverseVersionsTagStructure() {
    }
    
    public WSVersioningUniverseVersionsTagStructure(java.lang.String tagName, java.lang.String lastDate, java.lang.String lastAuthor, java.lang.String lastComment, com.amalto.core.webservice.WSStringArray clusters) {
        this.tagName = tagName;
        this.lastDate = lastDate;
        this.lastAuthor = lastAuthor;
        this.lastComment = lastComment;
        this.clusters = clusters;
    }
    
    public java.lang.String getTagName() {
        return tagName;
    }
    
    public void setTagName(java.lang.String tagName) {
        this.tagName = tagName;
    }
    
    public java.lang.String getLastDate() {
        return lastDate;
    }
    
    public void setLastDate(java.lang.String lastDate) {
        this.lastDate = lastDate;
    }
    
    public java.lang.String getLastAuthor() {
        return lastAuthor;
    }
    
    public void setLastAuthor(java.lang.String lastAuthor) {
        this.lastAuthor = lastAuthor;
    }
    
    public java.lang.String getLastComment() {
        return lastComment;
    }
    
    public void setLastComment(java.lang.String lastComment) {
        this.lastComment = lastComment;
    }
    
    public com.amalto.core.webservice.WSStringArray getClusters() {
        return clusters;
    }
    
    public void setClusters(com.amalto.core.webservice.WSStringArray clusters) {
        this.clusters = clusters;
    }
}