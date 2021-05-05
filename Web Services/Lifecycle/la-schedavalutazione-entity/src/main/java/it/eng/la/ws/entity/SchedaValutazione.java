package it.eng.la.ws.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SCHEDA_VALUTAZIONE")
@NamedQueries({
		@NamedQuery(name = "SchedaValutazione.findInviata", query = "SELECT li FROM SchedaValutazione li WHERE li.inviata=0"),
		@NamedQuery(name = "SchedaValutazione.byid", query = "SELECT i FROM SchedaValutazione i WHERE i.id = :id") })
public class SchedaValutazione implements Serializable {
	private static final long serialVersionUID = 3955824676073922122L;

	@Id
	@SequenceGenerator(name = "SCHEDA_VALUTAZIONE_ID_GENERATOR", sequenceName = "SCHEDA_VALUTAZIONE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SCHEDA_VALUTAZIONE_ID_GENERATOR")
	private long id;

	@Column(name = "CONGRUO_RISP_LEGGE")
	private String congruoRispLegge;

	@Column(name = "CONGRUO_RISP_LETT_INC")
	private String congruoRispLettInc;

	@Column(name = "CONGRUO_RISP_PAR_FOR")
	private String congruoRispParFor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CREAZIONE")
	private Date dataCreazione;

	private String lang;

	@Column(name = "LEGALE_INTERNO")
	private String legaleInterno;

	private String note;

	@Column(name = "SOLO_OGG_LETT_INC")
	private String soloOggLettInc;

	@Column(name = "UNITA_LEGALE")
	private String unitaLegale;
	
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO")
	private Documento documento;

	@Column(name = "LIFECYCLE_XML")
	private String lifecycleXml;

	@Column(name = "INVIATA")
	private Integer inviata;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INVIO")
	private Date dataInvio;

	@Column(name = "RETURN_FILE")
	@Lob
	private byte[] returnFile;

	@Column(name = "RETURN_FILE_NAME")
	private String returnFileName;

	public SchedaValutazione() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getInviata() {
		return inviata;
	}

	public void setInviata(Integer inviata) {
		this.inviata = inviata;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getReturnFileName() {
		return returnFileName;
	}

	public void setReturnFileName(String returnFileName) {
		this.returnFileName = returnFileName;
	}

	public byte[] getReturnFile() {
		return returnFile;
	}

	public void setReturnFile(byte[] returnFile) {
		this.returnFile = returnFile;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCongruoRispLegge() {
		return congruoRispLegge;
	}

	public void setCongruoRispLegge(String congruoRispLegge) {
		this.congruoRispLegge = congruoRispLegge;
	}

	public String getCongruoRispLettInc() {
		return congruoRispLettInc;
	}

	public void setCongruoRispLettInc(String congruoRispLettInc) {
		this.congruoRispLettInc = congruoRispLettInc;
	}

	public String getCongruoRispParFor() {
		return congruoRispParFor;
	}

	public void setCongruoRispParFor(String congruoRispParFor) {
		this.congruoRispParFor = congruoRispParFor;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public String getSoloOggLettInc() {
		return soloOggLettInc;
	}

	public void setSoloOggLettInc(String soloOggLettInc) {
		this.soloOggLettInc = soloOggLettInc;
	}

	public String getUnitaLegale() {
		return unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

	public String getLifecycleXml() {
		return lifecycleXml;
	}

	public void setLifecycleXml(String lifecycleXml) {
		this.lifecycleXml = lifecycleXml;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}