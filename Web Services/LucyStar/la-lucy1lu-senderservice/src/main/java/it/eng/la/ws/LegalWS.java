
package it.eng.la.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "LegalWS", targetNamespace = "http://xmlns.example.com/1467032449466")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface LegalWS {


    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns it.eng.la.ws.Risultato
     */
    @WebMethod(action = "\"\"")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "inserisciCorredoContabile", targetNamespace = "http://webservices.servlet.web.snam.lucystar.idoq.it/", className = "it.eng.la.ws.InserisciCorredoContabile")
    @ResponseWrapper(localName = "inserisciCorredoContabileResponse", targetNamespace = "http://webservices.servlet.web.snam.lucystar.idoq.it/", className = "it.eng.la.ws.InserisciCorredoContabileResponse")
    public Risultato inserisciCorredoContabile(
        @WebParam(name = "arg0", targetNamespace = "")
        Freeform arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        XMLGregorianCalendar arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

}
