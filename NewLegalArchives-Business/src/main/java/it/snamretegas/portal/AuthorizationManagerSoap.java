/**
 * AuthorizationManagerSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.snamretegas.portal;

public interface AuthorizationManagerSoap extends java.rmi.Remote {
    public java.lang.String[] getUserRoles(java.lang.String applicationId, java.lang.String userId) throws java.rmi.RemoteException;
    public boolean hasUserRole(java.lang.String applicationId, java.lang.String userId, java.lang.String roleId) throws java.rmi.RemoteException;
}
