/**
 * AuthorizationManagerLitLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.snamretegas.portal;

public class AuthorizationManagerLitLocator extends org.apache.axis.client.Service implements it.snamretegas.portal.AuthorizationManagerLit {

    public AuthorizationManagerLitLocator() {
    }


    public AuthorizationManagerLitLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AuthorizationManagerLitLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AuthorizationManagerSoap
    private java.lang.String AuthorizationManagerSoap_address = "http://ssows.snam.it:80/srgportalservices/AuthorizationManagerLit";

    public java.lang.String getAuthorizationManagerSoapAddress() {
        return AuthorizationManagerSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AuthorizationManagerSoapWSDDServiceName = "AuthorizationManagerSoap";

    public java.lang.String getAuthorizationManagerSoapWSDDServiceName() {
        return AuthorizationManagerSoapWSDDServiceName;
    }

    public void setAuthorizationManagerSoapWSDDServiceName(java.lang.String name) {
        AuthorizationManagerSoapWSDDServiceName = name;
    }

    public it.snamretegas.portal.AuthorizationManagerSoap getAuthorizationManagerSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AuthorizationManagerSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAuthorizationManagerSoap(endpoint);
    }

    public it.snamretegas.portal.AuthorizationManagerSoap getAuthorizationManagerSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.snamretegas.portal.AuthorizationManagerSoapStub _stub = new it.snamretegas.portal.AuthorizationManagerSoapStub(portAddress, this);
            _stub.setPortName(getAuthorizationManagerSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAuthorizationManagerSoapEndpointAddress(java.lang.String address) {
        AuthorizationManagerSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.snamretegas.portal.AuthorizationManagerSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                it.snamretegas.portal.AuthorizationManagerSoapStub _stub = new it.snamretegas.portal.AuthorizationManagerSoapStub(new java.net.URL(AuthorizationManagerSoap_address), this);
                _stub.setPortName(getAuthorizationManagerSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("AuthorizationManagerSoap".equals(inputPortName)) {
            return getAuthorizationManagerSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://portal.snamretegas.it/", "AuthorizationManagerLit");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://portal.snamretegas.it/", "AuthorizationManagerSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AuthorizationManagerSoap".equals(portName)) {
            setAuthorizationManagerSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
