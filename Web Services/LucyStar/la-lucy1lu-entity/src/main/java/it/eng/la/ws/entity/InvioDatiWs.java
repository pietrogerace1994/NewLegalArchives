package it.eng.la.ws.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the INVIO_DATI_WS database table.
 * 
 */
@Entity
@Table(name="INVIO_DATI_WS")
@NamedQueries({
@NamedQuery(name = "InvioDatiWs.findAll", query = "SELECT i FROM InvioDatiWs i where (i.stateid = 0 or i.stateid = -1) and i.progresent < 5"),
@NamedQuery(name="InvioDatiWs.byidProforma", query="SELECT i FROM InvioDatiWs i where i.idProforma = :idProforma")
}) 
public class InvioDatiWs implements Serializable {

	private static final long serialVersionUID = 5532250035924329255L;

	@Id
	private long id;

	@Column(name = "ID_PROFORMA")
	private BigDecimal idProforma;

	@Column(name = "CODICE_FORNITORE")
	private String codiceFornitore;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_DOCUMENTO")
	private Date dataDocumento;

	@Column(name = "NUMERO_DOCUMENTO")
	private String numero;

	private BigDecimal esercizio;

	@Column(name = "PIVACLI")
	private String partitaIva;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATAAUT")
	private Date dataAutorizzazione;

	private String autorizzatore;

	@Column(name = "IMPORTOAUT")
	private BigDecimal importoAutorizzato;

	private String note;

	@Column(name = "CENTRO_DI_COSTO")
	private String centroDiCosto;

	@Column(name = "VOCE_DI_CONTO")
	private String voceDiConto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INVIO")
	private Date sentdate;

	@Column(name = "ID_STATO")
	private BigDecimal stateid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_REINVIO")
	private Date resentdate;

	@Column(name = "INVIO_PROGRESSIVO")
	private BigDecimal progresent;

	@Column(name = "MESSAGGIO_RITORNO")
	private String returnmessage;

	public InvioDatiWs() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAutorizzatore() {
		return this.autorizzatore;
	}

	public void setAutorizzatore(String autorizzatore) {
		this.autorizzatore = autorizzatore;
	}

	public Date getDataAutorizzazione() {
		return this.dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getVoceDiConto() {
		return voceDiConto;
	}

	public void setVoceDiConto(String voceDiConto) {
		this.voceDiConto = voceDiConto;
	}

	public BigDecimal getIdProforma() {
		return idProforma;
	}

	public void setIdProforma(BigDecimal idProforma) {
		this.idProforma = idProforma;
	}

	public String getCodiceFornitore() {
		return codiceFornitore;
	}

	public void setCodiceFornitore(String codiceFornitore) {
		this.codiceFornitore = codiceFornitore;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public BigDecimal getEsercizio() {
		return esercizio;
	}

	public void setEsercizio(BigDecimal esercizio) {
		this.esercizio = esercizio;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public BigDecimal getImportoAutorizzato() {
		return importoAutorizzato;
	}

	public void setImportoAutorizzato(BigDecimal importoAutorizzato) {
		this.importoAutorizzato = importoAutorizzato;
	}

	public Date getSentdate() {
		return sentdate;
	}

	public void setSentdate(Date sentdate) {
		this.sentdate = sentdate;
	}

	public BigDecimal getStateid() {
		return stateid;
	}

	public void setStateid(BigDecimal stateid) {
		this.stateid = stateid;
	}

	public Date getResentdate() {
		return resentdate;
	}

	public void setResentdate(Date resentdate) {
		this.resentdate = resentdate;
	}

	public BigDecimal getProgresent() {
		return progresent;
	}

	public void setProgresent(BigDecimal progresent) {
		this.progresent = progresent;
	}

	public String getReturnmessage() {
		return returnmessage;
	}

	public void setReturnmessage(String returnmessage) {
		this.returnmessage = returnmessage;
	}

}