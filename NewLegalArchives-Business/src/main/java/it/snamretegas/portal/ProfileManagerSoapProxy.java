package it.snamretegas.portal;

public class ProfileManagerSoapProxy implements it.snamretegas.portal.ProfileManagerSoap {
  private String _endpoint = null;
  private it.snamretegas.portal.ProfileManagerSoap profileManagerSoap = null;
  
  public ProfileManagerSoapProxy() {
    _initProfileManagerSoapProxy();
  }
  
  public ProfileManagerSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initProfileManagerSoapProxy();
  }
  
  private void _initProfileManagerSoapProxy() {
    try {
      profileManagerSoap = (new it.snamretegas.portal.ProfileManagerLitLocator()).getProfileManagerSoap();
      if (profileManagerSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)profileManagerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)profileManagerSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (profileManagerSoap != null)
      ((javax.xml.rpc.Stub)profileManagerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.snamretegas.portal.ProfileManagerSoap getProfileManagerSoap() {
    if (profileManagerSoap == null)
      _initProfileManagerSoapProxy();
    return profileManagerSoap;
  }
  
  public it.snamretegas.portal.UserProfile getUserProfile(java.lang.String userId) throws java.rmi.RemoteException{
    if (profileManagerSoap == null)
      _initProfileManagerSoapProxy();
    return profileManagerSoap.getUserProfile(userId);
  }
  
  public it.snamretegas.portal.ProfileAttribute[] getUserExtendedProfile(java.lang.String applicationId, java.lang.String userId) throws java.rmi.RemoteException{
    if (profileManagerSoap == null)
      _initProfileManagerSoapProxy();
    return profileManagerSoap.getUserExtendedProfile(applicationId, userId);
  }
  
  
}