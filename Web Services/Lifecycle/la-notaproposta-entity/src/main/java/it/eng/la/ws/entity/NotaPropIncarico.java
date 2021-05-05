package it.eng.la.ws.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "NOTA_PROP_INCARICO")
@NamedQueries({
		@NamedQuery(name = "NotaPropIncarico.findInviata", query = "SELECT li FROM NotaPropIncarico li WHERE li.inviata=0"),
		@NamedQuery(name = "NotaPropIncarico.byid", query = "SELECT i FROM NotaPropIncarico i WHERE i.id = :id") })
public class NotaPropIncarico implements Serializable {
	private static final long serialVersionUID = 3955824676073922122L;

	@Id
	@SequenceGenerator(name = "NOTA_PROP_INCARICO_ID_GENERATOR", sequenceName = "NOTA_PROP_INCARICO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTA_PROP_INCARICO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_PROP_INCARICO")
	private Date dataPropIncarico;

	@Lob
	@Column(name = "DESCRIZIONE")
	private String descrizione;

	private String lang;

	@Column(name = "OGGETTO")
	private String oggetto;

	@Column(name = "PRATICA")
	private String pratica;

	@Column(name = "PROPONENTE")
	private String proponente;

	@Column(name = "PROPOSTA")
	private String proposta;

	@Column(name = "APPROVATORE")
	public String Approvatore;

	@Column(name = "AUTORIZZATORE")
	public String autorizzatore;

	@Column(name = "RESPONSABILI")
	private String responsabili;

	@Lob
	@Column(name = "INFO_COMPENSO_NP")
	private String infoCompensoNP;

	@Column(name = "NOME_FORO")
	private String nomeForo;

	@Column(name = "VALORE_INCARICO")
	private String valoreIncarico;

	@Column(name = "INFO_CORRESPONSIONE")
	private String infoCorresponsione;

	@Column(name = "SCELTA_INFO_HAND_BOOK")
	private Integer sceltaInfo;

	@Column(name = "INFO_HAND_BOOK")
	private String infoHandBook;

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

	public NotaPropIncarico() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataPropIncarico() {
		return dataPropIncarico;
	}

	public void setDataPropIncarico(Date dataPropIncarico) {
		this.dataPropIncarico = dataPropIncarico;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getPratica() {
		return pratica;
	}

	public void setPratica(String pratica) {
		this.pratica = pratica;
	}

	public String getProponente() {
		return proponente;
	}

	public void setProponente(String proponente) {
		this.proponente = proponente;
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getApprovatore() {
		return Approvatore;
	}

	public void setApprovatore(String approvatore) {
		Approvatore = approvatore;
	}

	public String getAutorizzatore() {
		return autorizzatore;
	}

	public void setAutorizzatore(String autorizzatore) {
		this.autorizzatore = autorizzatore;
	}

	public String getResponsabili() {
		return responsabili;
	}

	public void setResponsabili(String responsabili) {
		this.responsabili = responsabili;
	}

	public String getInfoCompensoNP() {
		return infoCompensoNP;
	}

	public void setInfoCompensoNP(String infoCompensoNP) {
		this.infoCompensoNP = infoCompensoNP;
	}

	public String getNomeForo() {
		return nomeForo;
	}

	public void setNomeForo(String nomeForo) {
		this.nomeForo = nomeForo;
	}

	public String getValoreIncarico() {
		return valoreIncarico;
	}

	public void setValoreIncarico(String valoreIncarico) {
		this.valoreIncarico = valoreIncarico;
	}

	public String getInfoCorresponsione() {
		return infoCorresponsione;
	}

	public void setInfoCorresponsione(String infoCorresponsione) {
		this.infoCorresponsione = infoCorresponsione;
	}

	public Integer getSceltaInfo() {
		return sceltaInfo;
	}

	public void setSceltaInfo(Integer sceltaInfo) {
		this.sceltaInfo = sceltaInfo;
	}

	public String getInfoHandBook() {
		return infoHandBook;
	}

	public void setInfoHandBook(String infoHandBook) {
		this.infoHandBook = infoHandBook;
	}

	public String getLifecycleXml() {
		return lifecycleXml;
	}

	public void setLifecycleXml(String lifecycleXml) {
		this.lifecycleXml = lifecycleXml;
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

	public byte[] getReturnFile() {
		return returnFile;
	}

	public void setReturnFile(byte[] returnFile) {
		this.returnFile = returnFile;
	}

	public String getReturnFileName() {
		return returnFileName;
	}

	public void setReturnFileName(String returnFileName) {
		this.returnFileName = returnFileName;
	}

}