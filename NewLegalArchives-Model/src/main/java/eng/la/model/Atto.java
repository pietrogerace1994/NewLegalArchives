package eng.la.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eng.la.model.audit.AuditedAttribute;
import eng.la.model.audit.AuditedObjectName;


/**
 * The persistent class for the ATTO database table.
 * 
 */
@Entity
@NamedQuery(name="Atto.findAll", query="SELECT a FROM Atto a")
public class Atto extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ATTO_ID_GENERATOR", sequenceName="ATTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="ATTO_ID_GENERATOR")
	private long id;
	
	@AuditedAttribute
	@Column(name="CREATO_DA")
	private String creatoDa;

	@AuditedAttribute
	@Column(name="OWNER")
	private String owner;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_REGISTRAZIONE")
	private Date dataRegistrazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_NOTIFICA")
	private Date dataNotifica;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_UDIENZA")
	private Date dataUdienza;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ULTIMA_MODIFICA")
	private Date dataUltimaModifica;

	@AuditedAttribute
	@Column(name="DESTINATARIO")
	private String destinatario;

	@AuditedAttribute
	@Column(name="EMAIL_INVIO_ALTRI_UFFICI")
	private String emailInvioAltriUffici;

	@AuditedAttribute
	@Column(name="FORO_COMPETENTE")
	private String foroCompetente;

	private String lang;

	@AuditedAttribute
	@Column(name="NOTE")
	private String note;

	@AuditedAttribute
	@Column(name="NUMERO_PROTOCOLLO")
	private String numeroProtocollo;

	@AuditedAttribute
	@Column(name="PARTE_NOTIFICANTE")
	private String parteNotificante;

	@AuditedAttribute
	private String rilevante;

	@AuditedAttribute
	@Column(name="TIPO_ATTO")
	private String tipoAtto;

	@AuditedAttribute
	@Column(name="UNITA_LEG_INVIO_ALTRI_UFFICI")
	private String unitaLegInvioAltriUffici;

	@AuditedAttribute
	@Column(name="UTENTE_INVIO_ALTRI_UFFICI")
	private String utenteInvioAltriUffici;
  
	//bi-directional many-to-one association to CategoriaAtto
	@ManyToOne()
	@JoinColumn(name="ID_CATEGORIA_ATTO")
	private CategoriaAtto categoriaAtto;
	
	//bi-directional many-to-one association to CategoriaAtto
	@ManyToOne()
	@JoinColumn(name="ID_ESITO_ATTO")
	private EsitoAtto esitoAtto;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="PAGAMENTO_DOVUTO")
	private BigDecimal pagamentoDovuto;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="SPESE_CARICO")
	private BigDecimal speseCarico;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="SPESE_FAVORE")
	private BigDecimal speseFavore;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to Societa
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA")
	private Societa societa;

	//bi-directional many-to-one association to StatoAtto
	@AuditedAttribute(classType=StatoAtto.class)
	@ManyToOne()
	@JoinColumn(name="ID_STATO_ATTO")
	private StatoAtto statoAtto;

	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO")
	private Documento documento;
	
	//bi-directional many-to-one association to AttoWf
	@OneToMany(mappedBy="atto")
	private Set<AttoWf> attoWfs;

	public Atto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatoDa() {
		return this.creatoDa;
	}

	public void setCreatoDa(String creatoDa) {
		this.creatoDa = creatoDa;
	}
	
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	} 

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
	public Date getDataRegistrazione() {
		return this.dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataNotifica() {
		return this.dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public Date getDataUdienza() {
		return this.dataUdienza;
	}

	public void setDataUdienza(Date dataUdienza) {
		this.dataUdienza = dataUdienza;
	}

	public Date getDataUltimaModifica() {
		return this.dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getEmailInvioAltriUffici() {
		return this.emailInvioAltriUffici;
	}

	public void setEmailInvioAltriUffici(String emailInvioAltriUffici) {
		this.emailInvioAltriUffici = emailInvioAltriUffici;
	}

	public String getForoCompetente() {
		return this.foroCompetente;
	}

	public void setForoCompetente(String foroCompetente) {
		this.foroCompetente = foroCompetente;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@AuditedObjectName
	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getParteNotificante() {
		return this.parteNotificante;
	}

	public void setParteNotificante(String parteNotificante) {
		this.parteNotificante = parteNotificante;
	}

	public String getRilevante() {
		return this.rilevante;
	}

	public void setRilevante(String rilevante) {
		this.rilevante = rilevante;
	}

	public String getTipoAtto() {
		return this.tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public String getUnitaLegInvioAltriUffici() {
		return this.unitaLegInvioAltriUffici;
	}

	public void setUnitaLegInvioAltriUffici(String unitaLegInvioAltriUffici) {
		this.unitaLegInvioAltriUffici = unitaLegInvioAltriUffici;
	}

	public String getUtenteInvioAltriUffici() {
		return this.utenteInvioAltriUffici;
	}

	public void setUtenteInvioAltriUffici(String utenteInvioAltriUffici) {
		this.utenteInvioAltriUffici = utenteInvioAltriUffici;
	}

	public CategoriaAtto getCategoriaAtto() {
		return this.categoriaAtto;
	}

	public void setCategoriaAtto(CategoriaAtto categoriaAtto) {
		this.categoriaAtto = categoriaAtto;
	}

	public EsitoAtto getEsitoAtto() {
		return esitoAtto;
	}

	public void setEsitoAtto(EsitoAtto esitoAtto) {
		this.esitoAtto = esitoAtto;
	}

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public Societa getSocieta() {
		return this.societa;
	}

	public void setSocieta(Societa societa) {
		this.societa = societa;
	}

	public StatoAtto getStatoAtto() {
		return this.statoAtto;
	}

	public void setStatoAtto(StatoAtto statoAtto) {
		this.statoAtto = statoAtto;
	}
	
	public Documento getDocumento() {
	return documento;
	}
	
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	
	public Set<AttoWf> getAttoWfs() {
		return this.attoWfs;
	}

	public void setAttoWfs(Set<AttoWf> attoWfs) {
		this.attoWfs = attoWfs;
	}

	public AttoWf addAttoWf(AttoWf attoWf) {
		getAttoWfs().add(attoWf);
		attoWf.setAtto(this);

		return attoWf;
	}

	public AttoWf removeAttoWf(AttoWf attoWf) {
		getAttoWfs().remove(attoWf);
		attoWf.setAtto(null);

		return attoWf;
	}

	public BigDecimal getPagamentoDovuto() {
		return pagamentoDovuto;
	}

	public void setPagamentoDovuto(BigDecimal pagamentoDovuto) {
		this.pagamentoDovuto = pagamentoDovuto;
	}

	public BigDecimal getSpeseCarico() {
		return speseCarico;
	}

	public void setSpeseCarico(BigDecimal speseCarico) {
		this.speseCarico = speseCarico;
	}

	public BigDecimal getSpeseFavore() {
		return speseFavore;
	}

	public void setSpeseFavore(BigDecimal speseFavore) {
		this.speseFavore = speseFavore;
	}
	
	

}