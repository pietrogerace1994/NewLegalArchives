
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

    private final static QName _InserisciCorredoContabileResponse_QNAME = new QName("http://webservices.servlet.web.snam.lucystar.idoq.it/", "inserisciCorredoContabileResponse");
    private final static QName _InserisciCorredoContabile_QNAME = new QName("http://webservices.servlet.web.snam.lucystar.idoq.it/", "inserisciCorredoContabile");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.la.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Risultato }
     * 
     */
    public Risultato createRisultato() {
        return new Risultato();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link Q1 }
     * 
     */
    public Q1 createQ1() {
        return new Q1();
    }

    /**
     * Create an instance of {@link Row }
     * 
     */
    public Row createRow() {
        return new Row();
    }

    /**
     * Create an instance of {@link InserisciCorredoContabile }
     * 
     */
    public InserisciCorredoContabile createInserisciCorredoContabile() {
        return new InserisciCorredoContabile();
    }

    /**
     * Create an instance of {@link Freeform }
     * 
     */
    public Freeform createFreeform() {
        return new Freeform();
    }

    /**
     * Create an instance of {@link InserisciCorredoContabileResponse }
     * 
     */
    public InserisciCorredoContabileResponse createInserisciCorredoContabileResponse() {
        return new InserisciCorredoContabileResponse();
    }

    /**
     * Create an instance of {@link Q3 }
     * 
     */
    public Q3 createQ3() {
        return new Q3();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InserisciCorredoContabileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.servlet.web.snam.lucystar.idoq.it/", name = "inserisciCorredoContabileResponse")
    public JAXBElement<InserisciCorredoContabileResponse> createInserisciCorredoContabileResponse(InserisciCorredoContabileResponse value) {
        return new JAXBElement<InserisciCorredoContabileResponse>(_InserisciCorredoContabileResponse_QNAME, InserisciCorredoContabileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InserisciCorredoContabile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.servlet.web.snam.lucystar.idoq.it/", name = "inserisciCorredoContabile")
    public JAXBElement<InserisciCorredoContabile> createInserisciCorredoContabile(InserisciCorredoContabile value) {
        return new JAXBElement<InserisciCorredoContabile>(_InserisciCorredoContabile_QNAME, InserisciCorredoContabile.class, null, value);
    }

}
