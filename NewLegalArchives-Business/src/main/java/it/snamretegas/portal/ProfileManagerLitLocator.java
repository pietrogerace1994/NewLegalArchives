/**
 * ProfileManagerLitLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.snamretegas.portal;

public class ProfileManagerLitLocator extends org.apache.axis.client.Service implements it.snamretegas.portal.ProfileManagerLit {

    public ProfileManagerLitLocator() {
    }


    public ProfileManagerLitLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ProfileManagerLitLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ProfileManagerSoap
    private java.lang.String ProfileManagerSoap_address = "http://ssows.snam.it:80/srgportalservices/ProfileManagerLit";

    public java.lang.String getProfileManagerSoapAddress() {
        return ProfileManagerSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ProfileManagerSoapWSDDServiceName = "ProfileManagerSoap";

    public java.lang.String getProfileManagerSoapWSDDServiceName() {
        return ProfileManagerSoapWSDDServiceName;
    }

    public void setProfileManagerSoapWSDDServiceName(java.lang.String name) {
        ProfileManagerSoapWSDDServiceName = name;
    }

    public it.snamretegas.portal.ProfileManagerSoap getProfileManagerSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ProfileManagerSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getProfileManagerSoap(endpoint);
    }

    public it.snamretegas.portal.ProfileManagerSoap getProfileManagerSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.snamretegas.portal.ProfileManagerSoapStub _stub = new it.snamretegas.portal.ProfileManagerSoapStub(portAddress, this);
            _stub.setPortName(getProfileManagerSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setProfileManagerSoapEndpointAddress(java.lang.String address) {
        ProfileManagerSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.snamretegas.portal.ProfileManagerSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                it.snamretegas.portal.ProfileManagerSoapStub _stub = new it.snamretegas.portal.ProfileManagerSoapStub(new java.net.URL(ProfileManagerSoap_address), this);
                _stub.setPortName(getProfileManagerSoapWSDDServiceName());
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
        if ("ProfileManagerSoap".equals(inputPortName)) {
            return getProfileManagerSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://portal.snamretegas.it/", "ProfileManagerLit");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://portal.snamretegas.it/", "ProfileManagerSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ProfileManagerSoap".equals(portName)) {
            setProfileManagerSoapEndpointAddress(address);
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
