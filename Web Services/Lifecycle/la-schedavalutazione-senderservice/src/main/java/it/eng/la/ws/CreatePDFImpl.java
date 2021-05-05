
package it.eng.la.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CreatePDFImpl", targetNamespace = "http://it.eng.la.livecyclews/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CreatePDFImpl {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns it.eng.la.ws.Esito
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "invokeFillPDFLifeCycle", targetNamespace = "http://it.eng.la.livecyclews/", className = "it.eng.la.ws.InvokeFillPDFLifeCycle")
    @ResponseWrapper(localName = "invokeFillPDFLifeCycleResponse", targetNamespace = "http://it.eng.la.livecyclews/", className = "it.eng.la.ws.InvokeFillPDFLifeCycleResponse")
    @Action(input = "http://it.eng.la.livecyclews/CreatePDFImpl/invokeFillPDFLifeCycleRequest", output = "http://it.eng.la.livecyclews/CreatePDFImpl/invokeFillPDFLifeCycleResponse")
    public Esito invokeFillPDFLifeCycle(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        byte[] arg1);

}
