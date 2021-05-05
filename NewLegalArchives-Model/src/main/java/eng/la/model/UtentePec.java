package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the UTENTE_PEC database table.
 * 
 */
@Entity(name = "UTENTE_PEC")
public class UtentePec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "UTENTE_PEC_GENERATOR", sequenceName = "UTENTE_PEC_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UTENTE_PEC_GENERATOR")
	private long id;

	@Column(name = "UUID")
	private String UUId;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "PEC_DESTINATARIO")
	private String pecDestinatario;

	@Column(name = "PEC_MITTENTE")
	private String pecMittente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA")
	private Date data;

	@Column(name = "CANCELLATO")
	private int cancellato;

	@Column(name = "PEC_LAID")
	private String pecLaId;

	@Column(name = "PEC_OGGETTO")
	private String pecOggetto;

	@Column(name = "PEC_DATA_RICEZIONE")
	private String pecDataRicezione;

	@Column(name = "STATO")
	private int stato;
	
	@Column(name="UTENTE_INVIO_ALTRI_UFFICI")
	private String utenteInvioAltriUffici;
	
	@Column(name="EMAIL_INVIO_ALTRI_UFFICI")
	private String emailInvioAltriUffici;

	public UtentePec() {
	}

	public String getUUId() {
		return UUId;
	}

	public void setUUId(String uUId) {
		UUId = uUId;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCancellato() {
		return cancellato;
	}

	public void setCancellato(int cancellato) {
		this.cancellato = cancellato;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPecMittente() {
		return pecMittente;
	}

	public void setPecMittente(String pecMittente) {
		this.pecMittente = pecMittente;
	}

	public String getPecDestinatario() {
		return pecDestinatario;
	}

	public void setPecDestinatario(String pecDestinatario) {
		this.pecDestinatario = pecDestinatario;
	}

	public String getPecLaId() {
		return pecLaId;
	}

	public void setPecLaId(String pecLaId) {
		this.pecLaId = pecLaId;
	}

	public String getPecOggetto() {
		return pecOggetto;
	}

	public void setPecOggetto(String pecOggetto) {
		this.pecOggetto = pecOggetto;
	}

	public String getPecDataRicezione() {
		return pecDataRicezione;
	}

	public void setPecDataRicezione(String pecDataRicezione) {
		this.pecDataRicezione = pecDataRicezione;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public String getUtenteInvioAltriUffici() {
		return utenteInvioAltriUffici;
	}

	public void setUtenteInvioAltriUffici(String utenteInvioAltriUffici) {
		this.utenteInvioAltriUffici = utenteInvioAltriUffici;
	}

	public String getEmailInvioAltriUffici() {
		return emailInvioAltriUffici;
	}

	public void setEmailInvioAltriUffici(String emailInvioAltriUffici) {
		this.emailInvioAltriUffici = emailInvioAltriUffici;
	}

}