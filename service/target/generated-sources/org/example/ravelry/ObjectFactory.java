
package org.example.ravelry;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.ravelry package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AccessDenied_QNAME = new QName("http://www.example.org/Ravelry", "AccessDenied");
    private final static QName _PatternNotFound_QNAME = new QName("http://www.example.org/Ravelry", "PatternNotFound");
    private final static QName _InvalidPrice_QNAME = new QName("http://www.example.org/Ravelry", "InvalidPrice");
    private final static QName _NameAlreadyExists_QNAME = new QName("http://www.example.org/Ravelry", "NameAlreadyExists");
    private final static QName _InsufficientFunds_QNAME = new QName("http://www.example.org/Ravelry", "InsufficientFunds");
    private final static QName _InvalidFile_QNAME = new QName("http://www.example.org/Ravelry", "InvalidFile");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.ravelry
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SellPatternResponse }
     * 
     */
    public SellPatternResponse createSellPatternResponse() {
        return new SellPatternResponse();
    }

    /**
     * Create an instance of {@link UploadPatternResponse }
     * 
     */
    public UploadPatternResponse createUploadPatternResponse() {
        return new UploadPatternResponse();
    }

    /**
     * Create an instance of {@link UploadPattern }
     * 
     */
    public UploadPattern createUploadPattern() {
        return new UploadPattern();
    }

    /**
     * Create an instance of {@link SellPattern }
     * 
     */
    public SellPattern createSellPattern() {
        return new SellPattern();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Ravelry", name = "AccessDenied")
    public JAXBElement<String> createAccessDenied(String value) {
        return new JAXBElement<String>(_AccessDenied_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Ravelry", name = "PatternNotFound")
    public JAXBElement<String> createPatternNotFound(String value) {
        return new JAXBElement<String>(_PatternNotFound_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Ravelry", name = "InvalidPrice")
    public JAXBElement<String> createInvalidPrice(String value) {
        return new JAXBElement<String>(_InvalidPrice_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Ravelry", name = "NameAlreadyExists")
    public JAXBElement<String> createNameAlreadyExists(String value) {
        return new JAXBElement<String>(_NameAlreadyExists_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Ravelry", name = "InsufficientFunds")
    public JAXBElement<String> createInsufficientFunds(String value) {
        return new JAXBElement<String>(_InsufficientFunds_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/Ravelry", name = "InvalidFile")
    public JAXBElement<String> createInvalidFile(String value) {
        return new JAXBElement<String>(_InvalidFile_QNAME, String.class, null, value);
    }

}
