package it.eng.la.ws.senderservice;

import java.io.Serializable;
import java.math.BigDecimal;

public class Esito implements Serializable {

	private static final long serialVersionUID = -5458561215882278499L;

	public Esito() {
	}

	private BigDecimal idProforma;
	private String returnMessage;
	private BigDecimal stateId;

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public BigDecimal getIdProforma() {
		return idProforma;
	}

	public void setIdProforma(BigDecimal idProforma) {
		this.idProforma = idProforma;
	}

}
