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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "NOTIFICA_PEC")
public class NotificaPec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NOTIFICA_PEC_ID_GENERATOR", sequenceName = "NOTIFICA_PEC_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTIFICA_PEC_ID_GENERATOR")
	private long id;

	@ManyToOne()
	@JoinColumn(name = "MATRICOLA_UTENTE")
	private Utente utente;

	@ManyToOne()
	@JoinColumn(name = "ID_UTENTE_PEC")
	private UtentePec utentePec;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_NOTIFICA")
	private Date dataNotifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_LETTURA")
	private Date dataLettura;

	@Column(name = "STATO")
	private int stato;

	@Column(name = "CANCELLATO")
	private int cancellato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@Column(name="MOTIVO_RIFIUTO")
	private String motivoRifiuto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public UtentePec getUtentePec() {
		return utentePec;
	}

	public void setUtentePec(UtentePec utentePec) {
		this.utentePec = utentePec;
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

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public int getCancellato() {
		return cancellato;
	}

	public void setCancellato(int cancellato) {
		this.cancellato = cancellato;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getMotivoRifiuto() {
		return motivoRifiuto;
	}

	public void setMotivoRifiuto(String motivoRifiuto) {
		this.motivoRifiuto = motivoRifiuto;
	}

}