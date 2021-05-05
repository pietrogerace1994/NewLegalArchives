package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "NOTIFICA_WEB")
public class NotificaWeb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NOTIFICA_WEB_ID_GENERATOR", sequenceName = "NOTIFICA_WEB_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTIFICA_WEB_ID_GENERATOR")
	private long id;

	@ManyToOne()
	@JoinColumn(name = "MATRICOLA_MITT")
	private Utente matricolaMitt;

	@ManyToOne()
	@JoinColumn(name = "MATRICOLA_DEST")
	private Utente matricolaDest;

	@Column(name = "KEY_MESSAGE")
	private String keyMessage;

	@Column(name = "DATA_NOTIFICA")
	private Date dataNotifica;

	@Column(name = "DATA_LETTURA")
	private Date dataLettura;

	@Column(name = "DATA_INVIO_MAIL")
	private Date dataInvioMail;

	@Column(name = "JSON_PARAM")
	private String jsonParam;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Utente getMatricolaMitt() {
		return matricolaMitt;
	}

	public void setMatricolaMitt(Utente matricolaMitt) {
		this.matricolaMitt = matricolaMitt;
	}

	public Utente getMatricolaDest() {
		return matricolaDest;
	}

	public void setMatricolaDest(Utente matricolaDest) {
		this.matricolaDest = matricolaDest;
	}

	public String getKeyMessage() {
		return keyMessage;
	}

	public void setKeyMessage(String keyMessage) {
		this.keyMessage = keyMessage;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public Date getDataLettura() {
		return dataLettura;
	}

	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	public Date getDataInvioMail() {
		return dataInvioMail;
	}

	public void setDataInvioMail(Date dataInvioMail) {
		this.dataInvioMail = dataInvioMail;
	}

	public String getJsonParam() {
		return jsonParam;
	}

	public void setJsonParam(String jsonParam) {
		this.jsonParam = jsonParam;
	}

}
