package eng.la.util;

import java.util.List;

import eng.la.util.costants.Costanti;

public class CurrentSessionUtil {
	public CurrentSessionUtil() {

	}

	private String userId = Costanti.USERID_GUEST;
	private List<String> rolesCode;
	private List<String> collMatricole;
	private String clientIp;
	private String nominativo;
	private Throwable lastError;
	private String motivazione;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRolesCode() {
		return rolesCode;
	}

	public void setRolesCode(List<String> rolesCode) {
		this.rolesCode = rolesCode;
	}

	public List<String> getCollMatricole() {
		return collMatricole;
	}

	public void setCollMatricole(List<String> collMatricole) {
		this.collMatricole = collMatricole;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public String toString() {
		return "CurrentSessionUtil [userId=" + userId + ", rolesCode=" + rolesCode + "]";
	}

	public void setNominativo(String nominativoUtil) {
		this.nominativo = nominativoUtil;
	}

	public String getNominativo() {
		return nominativo;
	}

	public void setLastError(Throwable ex) {
		lastError = ex;
	}
	
	public Throwable getLastError() {
		return lastError;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	
	
}
