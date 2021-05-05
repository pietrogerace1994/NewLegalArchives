package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
	/**
	 * 
	 */
	public static final String UPDATE_OPERATION = "UPDATE";
	public static final String INSERT_OPERATION = "INSERT";
	public static final String DELETE_OPERATION = "DELETE";
	public static final String READ_OPERATION = "READ";

	public static final String LOGIN_OPERATION = "LOGIN";
	public static final String LOGOUT_OPERATION = "LOGOUT";
	
	private static final long serialVersionUID = 1L;

	@Transient
	private String operation;
	@Transient
	private Date operationTimestamp;

	public void setOperation(String operation) {
		this.operation = operation;
	}
 
	public String getOperation() {
		return operation;
	}
 

	public Date getOperationTimestamp() {
		return operationTimestamp;
	}

	public void setOperationTimestamp(Date operationTimestamp) {
		this.operationTimestamp = operationTimestamp;
	}


	@OneToMany(mappedBy = "idEntita")
	private Set<Autorizzazione> autorizzazioni;

	public void setAutorizzazioni(Set<Autorizzazione> autorizzazioni) {
		this.autorizzazioni = autorizzazioni;
	}

	public Set<Autorizzazione> getAutorizzazioni() {
		return autorizzazioni;
	}
}
