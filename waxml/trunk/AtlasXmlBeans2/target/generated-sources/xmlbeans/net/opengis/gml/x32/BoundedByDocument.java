/*
 * An XML document type.
 * Localname: boundedBy
 * Namespace: http://www.opengis.net/gml/3.2
 * Java type: net.opengis.gml.x32.BoundedByDocument
 *
 * Automatically generated - do not modify.
 */
package net.opengis.gml.x32;


/**
 * A document containing one boundedBy(@http://www.opengis.net/gml/3.2) element.
 *
 * This is a complex type.
 */
public interface BoundedByDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BoundedByDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s2D5DEDEB67075BF3EC00EA776296F2EE").resolveHandle("boundedby6b91doctype");
    
    /**
     * Gets the "boundedBy" element
     */
    net.opengis.gml.x32.BoundingShapeType getBoundedBy();
    
    /**
     * Tests for nil "boundedBy" element
     */
    boolean isNilBoundedBy();
    
    /**
     * Sets the "boundedBy" element
     */
    void setBoundedBy(net.opengis.gml.x32.BoundingShapeType boundedBy);
    
    /**
     * Appends and returns a new empty "boundedBy" element
     */
    net.opengis.gml.x32.BoundingShapeType addNewBoundedBy();
    
    /**
     * Nils the "boundedBy" element
     */
    void setNilBoundedBy();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static net.opengis.gml.x32.BoundedByDocument newInstance() {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static net.opengis.gml.x32.BoundedByDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static net.opengis.gml.x32.BoundedByDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static net.opengis.gml.x32.BoundedByDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static net.opengis.gml.x32.BoundedByDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static net.opengis.gml.x32.BoundedByDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (net.opengis.gml.x32.BoundedByDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}