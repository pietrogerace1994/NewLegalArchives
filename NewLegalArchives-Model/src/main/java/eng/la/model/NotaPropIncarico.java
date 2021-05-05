package eng.la.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the NOTA_PROP_INCARICO database table.
 * 
 */
@Entity
@Table(name="NOTA_PROP_INCARICO")
@NamedQuery(name="NotaPropIncarico.findAll", query="SELECT n FROM NotaPropIncarico n")
public class NotaPropIncarico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NOTA_PROP_INCARICO_ID_GENERATOR", sequenceName="NOTA_PROP_INCARICO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="NOTA_PROP_INCARICO_ID_GENERATOR")
	private long id;
	
	@Lob
	@Column(name="LIFECYCLE_XML")
	private String lifecycleXml;
	
	@Column(name="INVIATA")
	private Integer inviata;
	
	@Column(name="DATA_INVIO")
	private Date dataInvio;
	
	@Column(name="RETURN_FILE")
	@Lob
	private Blob returnFile;
	
	@Column(name="RETURN_FILE_NAME")
	private String returnFileName;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "ID_DOCUMENTO_FIRMATO")
	private Documento documento_firmato;
	
	

	public Documento getDocumento_firmato() {
		return documento_firmato;
	}

	public void setDocumento_firmato(Documento documento_firmato) {
		this.documento_firmato = documento_firmato;
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

	public Blob getReturnFile() {
		return returnFile;
	}

	public void setReturnFile(Blob returnFile) {
		this.returnFile = returnFile;
	}

	public String getReturnFileName() {
		return returnFileName;
	}

	public void setReturnFileName(String returnFileName) {
		this.returnFileName = returnFileName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_PROP_INCARICO")
	private Date dataPropIncarico;
	
	/*
	@ManyToOne()
	@JoinColumn(name="DOCUMENTO_ID")
	private Documento documento;*/

	
	//private String allegato;
	
	@Lob
	@Column(name="DESCRIZIONE")
	private String descrizione;

	private String lang;

	@Column(name="OGGETTO")
	private String oggetto;

	@Column(name="PRATICA")
	private String pratica;

	@Column(name="PROPONENTE")
	private String proponente;

	@Column(name="PROPOSTA")
	private String proposta;
	
	@Column(name="APPROVATORE")
	public String Approvatore;
	
	@Column(name="AUTORIZZATORE")
	public String autorizzatore;

	@Column(name="RESPONSABILI")
	private String responsabili;
	
	@Lob
	@Column(name="INFO_COMPENSO_NP")
	private String infoCompensoNP;
	
	@Column(name="NOME_FORO")
	private String nomeForo;

	@Column(name="VALORE_INCARICO")
	private String valoreIncarico;
	
	@Column(name="INFO_CORRESPONSIONE")
	private String infoCorresponsione;
	
	@Column(name="SCELTA_INFO_HAND_BOOK")
	private Integer sceltaInfo;
	
	@Column(name="INFO_HAND_BOOK")
	private String infoHandBook;

	
	public Integer getSceltaInfo() {
		return sceltaInfo;
	}

	public void setSceltaInfo(Integer sceltaInfo) {
		this.sceltaInfo = sceltaInfo;
	}

	public String getAutorizzatore() {
		return autorizzatore;
	}

	public void setAutorizzatore(String autorizzatore) {
		this.autorizzatore = autorizzatore;
	}

	public String getApprovatore() {
		return Approvatore;
	}

	public void setApprovatore(String approvatore) {
		Approvatore = approvatore;
	}

	public String getNomeForo() {
		return nomeForo;
	}

	public void setNomeForo(String nomeForo) {
		this.nomeForo = nomeForo;
	}

	public String getResponsabili() {
		return responsabili;
	}

	public void setResponsabili(String responsabili) {
		this.responsabili = responsabili;
	}


	public String getInfoHandBook() {
		return infoHandBook;
	}

	public void setInfoHandBook(String infoHandBook) {
		this.infoHandBook = infoHandBook;
	}

	public String getInfoCorresponsione() {
		return infoCorresponsione;
	}

	public void setInfoCorresponsione(String infoCorresponsione) {
		this.infoCorresponsione = infoCorresponsione;
	}

	public String getInfoCompensoNP() {
		return infoCompensoNP;
	}

	public void setInfoCompensoNP(String infoCompensoNP) {
		this.infoCompensoNP = infoCompensoNP;
	}

	@ManyToOne
	@JoinColumn(name="PROCURATORE_ID")	
	private Procuratore procuratore;

	//bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy="notaPropIncarico")
	private Set<Incarico> incaricos;



	public NotaPropIncarico() {}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataPropIncarico() {
		return this.dataPropIncarico;
	}

	public void setDataPropIncarico(Date dataPropIncarico) {
		this.dataPropIncarico = dataPropIncarico;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getPratica() {
		return this.pratica;
	}

	public void setPratica(String pratica) {
		this.pratica = pratica;
	}

	public String getProponente() {
		return this.proponente;
	}

	public void setProponente(String proponente) {
		this.proponente = proponente;
	}

	public Procuratore getProcuratore() {
		return procuratore;
	}

	public void setProcuratore(Procuratore procuratore) {
		this.procuratore = procuratore;
	}

	public String getProposta() {
		return this.proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getValoreIncarico() {
		return this.valoreIncarico;
	}

	public void setValoreIncarico(String valoreIncarico) {
		this.valoreIncarico = valoreIncarico;
	}

	public Set<Incarico> getIncaricos() {
		return this.incaricos;
	}

	public void setIncaricos(Set<Incarico> incaricos) {
		this.incaricos = incaricos;
	}

	public Incarico addIncarico(Incarico incarico) {
		getIncaricos().add(incarico);
		incarico.setNotaPropIncarico(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setNotaPropIncarico(null);

		return incarico;
	}



}