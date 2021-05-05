
package it.eng.la.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.la.ws package. 
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

    private final static QName _InvokeFillPDFLifeCycleResponse_QNAME = new QName("http://it.eng.la.livecyclews/", "invokeFillPDFLifeCycleResponse");
    private final static QName _InvokeFillPDFLifeCycle_QNAME = new QName("http://it.eng.la.livecyclews/", "invokeFillPDFLifeCycle");
    private final static QName _InvokeFillPDFLifeCycleArg1_QNAME = new QName("", "arg1");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.la.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvokeFillPDFLifeCycle }
     * 
     */
    public InvokeFillPDFLifeCycle createInvokeFillPDFLifeCycle() {
        return new InvokeFillPDFLifeCycle();
    }

    /**
     * Create an instance of {@link InvokeFillPDFLifeCycleResponse }
     * 
     */
    public InvokeFillPDFLifeCycleResponse createInvokeFillPDFLifeCycleResponse() {
        return new InvokeFillPDFLifeCycleResponse();
    }

    /**
     * Create an instance of {@link Esito }
     * 
     */
    public Esito createEsito() {
        return new Esito();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvokeFillPDFLifeCycleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.eng.la.livecyclews/", name = "invokeFillPDFLifeCycleResponse")
    public JAXBElement<InvokeFillPDFLifeCycleResponse> createInvokeFillPDFLifeCycleResponse(InvokeFillPDFLifeCycleResponse value) {
        return new JAXBElement<InvokeFillPDFLifeCycleResponse>(_InvokeFillPDFLifeCycleResponse_QNAME, InvokeFillPDFLifeCycleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvokeFillPDFLifeCycle }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.eng.la.livecyclews/", name = "invokeFillPDFLifeCycle")
    public JAXBElement<InvokeFillPDFLifeCycle> createInvokeFillPDFLifeCycle(InvokeFillPDFLifeCycle value) {
        return new JAXBElement<InvokeFillPDFLifeCycle>(_InvokeFillPDFLifeCycle_QNAME, InvokeFillPDFLifeCycle.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = InvokeFillPDFLifeCycle.class)
    public JAXBElement<byte[]> createInvokeFillPDFLifeCycleArg1(byte[] value) {
        return new JAXBElement<byte[]>(_InvokeFillPDFLifeCycleArg1_QNAME, byte[].class, InvokeFillPDFLifeCycle.class, ((byte[]) value));
    }

}
