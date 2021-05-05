/**
 * ProfileManagerSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.snamretegas.portal;

public interface ProfileManagerSoap extends java.rmi.Remote {
    public it.snamretegas.portal.UserProfile getUserProfile(java.lang.String userId) throws java.rmi.RemoteException;
    public it.snamretegas.portal.ProfileAttribute[] getUserExtendedProfile(java.lang.String applicationId, java.lang.String userId) throws java.rmi.RemoteException;
}
