package it.snamretegas.portal;

public class AuthorizationManagerSoapProxy implements it.snamretegas.portal.AuthorizationManagerSoap {
  private String _endpoint = null;
  private it.snamretegas.portal.AuthorizationManagerSoap authorizationManagerSoap = null;
  
  public AuthorizationManagerSoapProxy() {
    _initAuthorizationManagerSoapProxy();
  }
  
  public AuthorizationManagerSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initAuthorizationManagerSoapProxy();
  }
  
  private void _initAuthorizationManagerSoapProxy() {
    try {
      authorizationManagerSoap = (new it.snamretegas.portal.AuthorizationManagerLitLocator()).getAuthorizationManagerSoap();
      if (authorizationManagerSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)authorizationManagerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)authorizationManagerSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (authorizationManagerSoap != null)
      ((javax.xml.rpc.Stub)authorizationManagerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.snamretegas.portal.AuthorizationManagerSoap getAuthorizationManagerSoap() {
    if (authorizationManagerSoap == null)
      _initAuthorizationManagerSoapProxy();
    return authorizationManagerSoap;
  }
  
  public java.lang.String[] getUserRoles(java.lang.String applicationId, java.lang.String userId) throws java.rmi.RemoteException{
    if (authorizationManagerSoap == null)
      _initAuthorizationManagerSoapProxy();
    return authorizationManagerSoap.getUserRoles(applicationId, userId);
  }
  
  public boolean hasUserRole(java.lang.String applicationId, java.lang.String userId, java.lang.String roleId) throws java.rmi.RemoteException{
    if (authorizationManagerSoap == null)
      _initAuthorizationManagerSoapProxy();
    return authorizationManagerSoap.hasUserRole(applicationId, userId, roleId);
  }
  
  
}