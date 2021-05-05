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

@Entity(name = "ARCHIVIO_PROTOCOLLO")
public class ArchivioProtocollo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ARCHIVIO_PROTOCOLLO_ID_GENERATOR", sequenceName = "ARCHIVIO_PROTOCOLLO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ARCHIVIO_PROTOCOLLO_ID_GENERATOR")
	private long id;
	
	@Column(name = "NUM_PROTOCOLLO")
	private String numProtocollo;
	
	private Integer progressivo;

	@ManyToOne()
	@JoinColumn(name = "MATRICOLA_MITTENTE")
	private Utente mittenteMat;
	
	@ManyToOne()
	@JoinColumn(name = "MATRICOLA_DESTINATARIO")
	private Utente destinatarioMat;
	
	private String mittente;
	
	private String destinatario;

	@Column(name = "UNITA_APPART")
	private String unitaAppart;
	
	@Column(name = "DATA_INSERIMENTO")
	private Date dataInserimento;
	
	private String oggetto;
	
	private String tipo;
	
	private String stato;
	
	private String commento;
	
	@ManyToOne()
	@JoinColumn(name = "FASCICOLO_ASSOCIATO")
	private Fascicolo fascicoloAssociato;
	
	@ManyToOne()
	@JoinColumn(name = "ID_STATO")
	private StatoProtocollo statoProtocollo;
	
	@ManyToOne()
	@JoinColumn(name = "ID_DOCUMENTO")
	private Documento documento;
	
	@ManyToOne()
	@JoinColumn(name = "OWNER")
	private Utente owner;
	
	@ManyToOne()
	@JoinColumn(name = "ASSEGNATARIO")
	private Utente assegnatario;
	
	

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Utente getOwner() {
		return owner;
	}

	public void setOwner(Utente owner) {
		this.owner = owner;
	}

	public Utente getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(Utente assegnatario) {
		this.assegnatario = assegnatario;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumProtocollo() {
		return numProtocollo;
	}

	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}

	public Integer getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	public String getUnitaAppart() {
		return unitaAppart;
	}

	public void setUnitaAppart(String unitaAppart) {
		this.unitaAppart = unitaAppart;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Fascicolo getFascicoloAssociato() {
		return fascicoloAssociato;
	}

	public void setFascicoloAssociato(Fascicolo fascicoloAssociato) {
		this.fascicoloAssociato = fascicoloAssociato;
	}

	public Utente getMittenteMat() {
		return mittenteMat;
	}

	public void setMittenteMat(Utente mittenteMat) {
		this.mittenteMat = mittenteMat;
	}

	public Utente getDestinatarioMat() {
		return destinatarioMat;
	}

	public void setDestinatarioMat(Utente destinatarioMat) {
		this.destinatarioMat = destinatarioMat;
	}

	public StatoProtocollo getStatoProtocollo() {
		return statoProtocollo;
	}

	public void setStatoProtocollo(StatoProtocollo statoProtocollo) {
		this.statoProtocollo = statoProtocollo;
	}

	

}
