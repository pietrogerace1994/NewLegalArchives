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
 * The persistent class for the FASCICOLO database table.
 * 
 */ 
@Entity 
@NamedQuery(name="Fascicolo.findAll", query="SELECT f FROM Fascicolo f")
public class Fascicolo extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="FASCICOLO_ID_GENERATOR", sequenceName="FASCICOLO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="FASCICOLO_ID_GENERATOR")
	private long id;

	@AuditedAttribute
	@Column(name="AUTORITA_EMANANTE")
	private String autoritaEmanante;

	@AuditedAttribute
	@Column(name="AUTORITA_GIUDIZIARIA")
	private String autoritaGiudiziaria;

	@AuditedAttribute
	private String controinteressato;
	
	@AuditedAttribute
	private String resistente;
	
	@AuditedAttribute
	private String ricorrente;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CHIUSURA")
	private Date dataChiusura;
 
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ULTIMA_MODIFICA")
	private Date dataUltimaModifica;

	@AuditedAttribute
	private String descrizione; 

	@AuditedAttribute
	@Column(name="JOINT_VENTURE")
	private String jointVenture;

	private String lang;

	@AuditedAttribute
	@Column(name="LEGALE_INTERNO")
	private String legaleInterno;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="N_ARCHIVIO_CONTENITORE")
	private BigDecimal nArchivioContenitore;

	private String nome;

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="NUMERO_ARCHIVIO")
	private BigDecimal numeroArchivio;

	@AuditedAttribute
	@Column(name="OGGETTO_SINTETICO")
	private String oggettoSintetico;

	@AuditedAttribute
	private String rilevante;

	@AuditedAttribute
	@Column(name="SIGLA_CLIENTE")
	private String siglaCliente;
 
	private String target; 

	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="VALORE_CAUSA_PRATICA")
	private BigDecimal valoreCausaPratica;
	
	@AuditedAttribute
	@Column(name="TITOLO")
	private String titolo;
	
	@AuditedAttribute
	@Column(name="CENTRO_DI_COSTO")
	private String centroDiCosto;
	
	@AuditedAttribute
	@Column(name="VOCE_DI_CONTO")
	private String voceDiConto;

	//bi-directional many-to-one association to Atto
	@OneToMany(mappedBy="fascicolo")
	private Set<Atto> attos;
 
	//bi-directional many-to-one association to Controparte
	@OneToMany(mappedBy="fascicolo")
	private Set<Controparte> contropartes;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_PADRE")
	private Fascicolo fascicolo; 

	//bi-directional many-to-one association to Nazione
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione; 
	
	//bi-directional many-to-one association to Progetto
	@ManyToOne()
	@JoinColumn(name="ID_PROGETTO")
	private Progetto progetto;
 
	//bi-directional many-to-one association to SettoreGiuridico 
	@ManyToOne()
	@JoinColumn(name="ID_SETTORE_GIURIDICO")
	private SettoreGiuridico settoreGiuridico;

	//bi-directional many-to-one association to Societa 
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA_PARTNER")
	private Societa societa;

	//bi-directional many-to-one association to StatoFascicolo
	@AuditedAttribute(classType=StatoFascicolo.class)
	@ManyToOne()
	@JoinColumn(name="ID_STATO_FASCICOLO")
	private StatoFascicolo statoFascicolo;

	//bi-directional many-to-one association to TipoContenzioso
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_CONTENZIOSO")
	private TipoContenzioso tipoContenzioso;

	//bi-directional many-to-one association to TipologiaFascicolo 
	@ManyToOne()
	@JoinColumn(name="ID_TIPOLOGIA_FASCICOLO")
	private TipologiaFascicolo tipologiaFascicolo;
    
	//bi-directional many-to-one association to RFascPrestNotar
	@OneToMany(mappedBy="fascicolo") 
	private Set<RFascPrestNotar> RFascPrestNotars;

	//bi-directional many-to-one association to ValoreCausa
	@ManyToOne()
	@JoinColumn(name="ID_VALORE_CAUSA")
	private ValoreCausa valoreCausa;
	
	//bi-directional many-to-one association to SchedaFondoRischi
	@ManyToOne()
	@JoinColumn(name="ID_SCHEDA_FR")
	private SchedaFondoRischi schedaFondoRischi;

	//bi-directional many-to-one association to FascicoloWf
	@OneToMany(mappedBy="fascicolo")
	private Set<FascicoloWf> fascicoloWfs;

	//bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy="fascicolo")
	private Set<Incarico> incaricos; 
 
	//bi-directional many-to-one association to ParteCivile
	@OneToMany(mappedBy="fascicolo")
	private Set<ParteCivile> parteCiviles;

	//bi-directional many-to-one association to PersonaOffesa
	@OneToMany(mappedBy="fascicolo")
	private Set<PersonaOffesa> personaOffesas;

	//bi-directional many-to-one association to Pfr
	@OneToMany(mappedBy="fascicolo")
	private Set<Pfr> pfrs;

	//bi-directional many-to-one association to ResponsabileCivile
	@OneToMany(mappedBy="fascicolo")
	private Set<ResponsabileCivile> responsabileCiviles;
 
	//bi-directional many-to-one association to RCorrelazioneFascicoli
	@OneToMany(mappedBy="fascicolo1")
	private Set<RCorrelazioneFascicoli> RCorrelazioneFascicolis;

	//bi-directional many-to-one association to RFascicoloMateria
	@OneToMany(mappedBy="fascicolo")
	private Set<RFascicoloMateria> RFascicoloMaterias;

	//bi-directional many-to-one association to RFascicoloSocieta
	@OneToMany(mappedBy="fascicolo")
	private Set<RFascicoloSocieta> RFascicoloSocietas;

	//bi-directional many-to-one association to RUtenteFascicolo
	@OneToMany(mappedBy="fascicolo")
	private Set<RUtenteFascicolo> RUtenteFascicolos;

	//bi-directional many-to-one association to SoggettoIndagato
	@OneToMany(mappedBy="fascicolo")
	private Set<SoggettoIndagato> soggettoIndagatos; 

	//bi-directional many-to-one association to RFascicoloGiudizio
	@OneToMany(mappedBy="fascicolo")
	private Set<RFascicoloGiudizio> RFascicoloGiudizios;

	//bi-directional many-to-one association to RFascicoloGiudizio
	@OneToMany(mappedBy="fascicolo")
	private Set<RFascicoloRicorso> RFascicoloRicorsos;

	//bi-directional many-to-one association to TerzoChiamatoCausa
	@OneToMany(mappedBy="fascicolo")
	private Set<TerzoChiamatoCausa> terzoChiamatoCausas;
	

	public Fascicolo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAutoritaEmanante() {
		return this.autoritaEmanante;
	}

	public void setAutoritaEmanante(String autoritaEmanante) {
		this.autoritaEmanante = autoritaEmanante;
	}

	public Set<RFascicoloGiudizio> getRFascicoloGiudizios() {
		return RFascicoloGiudizios;
	}

	public void setRFascicoloGiudizios(Set<RFascicoloGiudizio> rFascicoloGiudizios) {
		RFascicoloGiudizios = rFascicoloGiudizios;
	}

	public Set<RFascicoloRicorso> getRFascicoloRicorsos() {
		return RFascicoloRicorsos;
	}

	public void setRFascicoloRicorsos(Set<RFascicoloRicorso> rFascicoloRicorsos) {
		RFascicoloRicorsos = rFascicoloRicorsos;
	}

	public String getAutoritaGiudiziaria() {
		return this.autoritaGiudiziaria;
	}

	public void setAutoritaGiudiziaria(String autoritaGiudiziaria) {
		this.autoritaGiudiziaria = autoritaGiudiziaria;
	}

	public String getControinteressato() {
		return this.controinteressato;
	}

	public void setControinteressato(String controinteressato) {
		this.controinteressato = controinteressato;
	}
	
	

	public String getResistente() {
		return resistente;
	}

	public void setResistente(String resistente) {
		this.resistente = resistente;
	}

	public String getRicorrente() {
		return ricorrente;
	}

	public void setRicorrente(String ricorrente) {
		this.ricorrente = ricorrente;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
	public Date getDataChiusura() {
		return this.dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataUltimaModifica() {
		return this.dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
 
	public String getJointVenture() {
		return this.jointVenture;
	}

	public void setJointVenture(String jointVenture) {
		this.jointVenture = jointVenture;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLegaleInterno() {
		return this.legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public BigDecimal getNArchivioContenitore() {
		return this.nArchivioContenitore;
	}

	public void setNArchivioContenitore(BigDecimal nArchivioContenitore) {
		this.nArchivioContenitore = nArchivioContenitore;
	}
 
	@AuditedObjectName
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
 
	public BigDecimal getNumeroArchivio() {
		return this.numeroArchivio;
	}

	public void setNumeroArchivio(BigDecimal numeroArchivio) {
		this.numeroArchivio = numeroArchivio;
	}

	public String getOggettoSintetico() {
		return this.oggettoSintetico;
	}

	public void setOggettoSintetico(String oggettoSintetico) {
		this.oggettoSintetico = oggettoSintetico;
	}

	public String getRilevante() {
		return this.rilevante;
	}

	public void setRilevante(String rilevante) {
		this.rilevante = rilevante;
	}

	public String getSiglaCliente() {
		return this.siglaCliente;
	}

	public void setSiglaCliente(String siglaCliente) {
		this.siglaCliente = siglaCliente;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	} 
	
	public BigDecimal getValoreCausaPratica() {
		return this.valoreCausaPratica;
	}

	public void setValoreCausaPratica(BigDecimal valoreCausaPratica) {
		this.valoreCausaPratica = valoreCausaPratica;
	}

	public Set<Atto> getAttos() {
		return this.attos;
	}

	public void setAttos(Set<Atto> attos) {
		this.attos = attos;
	}

	public Atto addAtto(Atto atto) {
		getAttos().add(atto);
		atto.setFascicolo(this);

		return atto;
	}

	public Atto removeAtto(Atto atto) {
		getAttos().remove(atto);
		atto.setFascicolo(null);

		return atto;
	}

	public Set<RFascPrestNotar> getRFascPrestNotars() {
		return RFascPrestNotars;
	}

	public void setRFascPrestNotars(Set<RFascPrestNotar> rFascPrestNotars) {
		RFascPrestNotars = rFascPrestNotars;
	}

	public Set<Controparte> getContropartes() {
		return this.contropartes;
	}

	public void setContropartes(Set<Controparte> contropartes) {
		this.contropartes = contropartes;
	}

	public Controparte addControparte(Controparte controparte) {
		getContropartes().add(controparte);
		controparte.setFascicolo(this);

		return controparte;
	}

	public Controparte removeControparte(Controparte controparte) {
		getContropartes().remove(controparte);
		controparte.setFascicolo(null);

		return controparte;
	}

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}
    
	public Nazione getNazione() {
		return this.nazione;
	}

	public void setNazione(Nazione nazione) {
		this.nazione = nazione;
	}
 
	public Progetto getProgetto() {
		return this.progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}
 
	public SettoreGiuridico getSettoreGiuridico() {
		return this.settoreGiuridico;
	}

	public void setSettoreGiuridico(SettoreGiuridico settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

	public Societa getSocieta() {
		return this.societa;
	}

	public void setSocieta(Societa societa) {
		this.societa = societa;
	}

	public StatoFascicolo getStatoFascicolo() {
		return this.statoFascicolo;
	}

	public void setStatoFascicolo(StatoFascicolo statoFascicolo) {
		this.statoFascicolo = statoFascicolo;
	}

	public TipoContenzioso getTipoContenzioso() {
		return this.tipoContenzioso;
	}

	public void setTipoContenzioso(TipoContenzioso tipoContenzioso) {
		this.tipoContenzioso = tipoContenzioso;
	}

	public TipologiaFascicolo getTipologiaFascicolo() {
		return this.tipologiaFascicolo;
	}

	public void setTipologiaFascicolo(TipologiaFascicolo tipologiaFascicolo) {
		this.tipologiaFascicolo = tipologiaFascicolo;
	}
 
	public ValoreCausa getValoreCausa() {
		return this.valoreCausa;
	}

	public void setValoreCausa(ValoreCausa valoreCausa) {
		this.valoreCausa = valoreCausa;
	}
	
	public SchedaFondoRischi getSchedaFondoRischi() {
		return this.schedaFondoRischi;
	}

	public void setSchedaFondoRischi(SchedaFondoRischi schedaFondoRischi) {
		this.schedaFondoRischi = schedaFondoRischi;
	}

	public Set<FascicoloWf> getFascicoloWfs() {
		return this.fascicoloWfs; 
	}

	public void setFascicoloWfs(Set<FascicoloWf> fascicoloWfs) {
		this.fascicoloWfs = fascicoloWfs;
	}

	public FascicoloWf addFascicoloWf(FascicoloWf fascicoloWf) {
		getFascicoloWfs().add(fascicoloWf);
		fascicoloWf.setFascicolo(this);

		return fascicoloWf;
	}

	public FascicoloWf removeFascicoloWf(FascicoloWf fascicoloWf) {
		getFascicoloWfs().remove(fascicoloWf);
		fascicoloWf.setFascicolo(null);

		return fascicoloWf;
	}

	public Set<Incarico> getIncaricos() {
		return this.incaricos;
	}

	public void setIncaricos(Set<Incarico> incaricos) {
		this.incaricos = incaricos;
	}

	public Incarico addIncarico(Incarico incarico) {
		getIncaricos().add(incarico);
		incarico.setFascicolo(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setFascicolo(null);

		return incarico;
	}

	public Set<ParteCivile> getParteCiviles() {
		return this.parteCiviles;
	}

	public void setParteCiviles(Set<ParteCivile> parteCiviles) {
		this.parteCiviles = parteCiviles;
	}

	public ParteCivile addParteCivile(ParteCivile parteCivile) {
		getParteCiviles().add(parteCivile);
		parteCivile.setFascicolo(this);

		return parteCivile;
	}

	public ParteCivile removeParteCivile(ParteCivile parteCivile) {
		getParteCiviles().remove(parteCivile);
		parteCivile.setFascicolo(null);

		return parteCivile;
	}

	public Set<PersonaOffesa> getPersonaOffesas() {
		return this.personaOffesas;
	}

	public void setPersonaOffesas(Set<PersonaOffesa> personaOffesas) {
		this.personaOffesas = personaOffesas;
	}

	public PersonaOffesa addPersonaOffesa(PersonaOffesa personaOffesa) {
		getPersonaOffesas().add(personaOffesa);
		personaOffesa.setFascicolo(this);

		return personaOffesa;
	}

	public PersonaOffesa removePersonaOffesa(PersonaOffesa personaOffesa) {
		getPersonaOffesas().remove(personaOffesa);
		personaOffesa.setFascicolo(null);

		return personaOffesa;
	}

	public Set<Pfr> getPfrs() {
		return this.pfrs;
	}

	public void setPfrs(Set<Pfr> pfrs) {
		this.pfrs = pfrs;
	}

	public Pfr addPfr(Pfr pfr) {
		getPfrs().add(pfr);
		pfr.setFascicolo(this);

		return pfr;
	}

	public Pfr removePfr(Pfr pfr) {
		getPfrs().remove(pfr);
		pfr.setFascicolo(null);

		return pfr;
	}

	public Set<ResponsabileCivile> getResponsabileCiviles() {
		return this.responsabileCiviles;
	}

	public void setResponsabileCiviles(Set<ResponsabileCivile> responsabileCiviles) {
		this.responsabileCiviles = responsabileCiviles;
	}

	public ResponsabileCivile addResponsabileCivile(ResponsabileCivile responsabileCivile) {
		getResponsabileCiviles().add(responsabileCivile);
		responsabileCivile.setFascicolo(this);

		return responsabileCivile;
	}

	public ResponsabileCivile removeResponsabileCivile(ResponsabileCivile responsabileCivile) {
		getResponsabileCiviles().remove(responsabileCivile);
		responsabileCivile.setFascicolo(null);

		return responsabileCivile;
	} 
	
	public Set<RCorrelazioneFascicoli> getRCorrelazioneFascicolis() {
		return RCorrelazioneFascicolis;
	}

	public void setRCorrelazioneFascicolis(Set<RCorrelazioneFascicoli> rCorrelazioneFascicolis) {
		RCorrelazioneFascicolis = rCorrelazioneFascicolis;
	}

	public Set<RFascicoloMateria> getRFascicoloMaterias() {
		return this.RFascicoloMaterias;
	}

	public void setRFascicoloMaterias(Set<RFascicoloMateria> RFascicoloMaterias) {
		this.RFascicoloMaterias = RFascicoloMaterias;
	}

	public RFascicoloMateria addRFascicoloMateria(RFascicoloMateria RFascicoloMateria) {
		getRFascicoloMaterias().add(RFascicoloMateria);
		RFascicoloMateria.setFascicolo(this);

		return RFascicoloMateria;
	}

	public RFascicoloMateria removeRFascicoloMateria(RFascicoloMateria RFascicoloMateria) {
		getRFascicoloMaterias().remove(RFascicoloMateria);
		RFascicoloMateria.setFascicolo(null);

		return RFascicoloMateria;
	}

	public Set<RFascicoloSocieta> getRFascicoloSocietas() {
		return this.RFascicoloSocietas;
	}

	public void setRFascicoloSocietas(Set<RFascicoloSocieta> RFascicoloSocietas) {
		this.RFascicoloSocietas = RFascicoloSocietas;
	}

	public RFascicoloSocieta addRFascicoloSocieta(RFascicoloSocieta RFascicoloSocieta) {
		getRFascicoloSocietas().add(RFascicoloSocieta);
		RFascicoloSocieta.setFascicolo(this);

		return RFascicoloSocieta;
	}

	public RFascicoloSocieta removeRFascicoloSocieta(RFascicoloSocieta RFascicoloSocieta) {
		getRFascicoloSocietas().remove(RFascicoloSocieta);
		RFascicoloSocieta.setFascicolo(null);

		return RFascicoloSocieta;
	}

	public Set<RUtenteFascicolo> getRUtenteFascicolos() {
		return this.RUtenteFascicolos;
	}

	public void setRUtenteFascicolos(Set<RUtenteFascicolo> RUtenteFascicolos) {
		this.RUtenteFascicolos = RUtenteFascicolos;
	}

	public RUtenteFascicolo addRUtenteFascicolo(RUtenteFascicolo RUtenteFascicolo) {
		getRUtenteFascicolos().add(RUtenteFascicolo);
		RUtenteFascicolo.setFascicolo(this);

		return RUtenteFascicolo;
	}

	public RUtenteFascicolo removeRUtenteFascicolo(RUtenteFascicolo RUtenteFascicolo) {
		getRUtenteFascicolos().remove(RUtenteFascicolo);
		RUtenteFascicolo.setFascicolo(null);

		return RUtenteFascicolo;
	}

	public Set<SoggettoIndagato> getSoggettoIndagatos() {
		return this.soggettoIndagatos;
	}

	public void setSoggettoIndagatos(Set<SoggettoIndagato> soggettoIndagatos) {
		this.soggettoIndagatos = soggettoIndagatos;
	}

	public SoggettoIndagato addSoggettoIndagato(SoggettoIndagato soggettoIndagato) {
		getSoggettoIndagatos().add(soggettoIndagato);
		soggettoIndagato.setFascicolo(this);

		return soggettoIndagato;
	}

	public SoggettoIndagato removeSoggettoIndagato(SoggettoIndagato soggettoIndagato) {
		getSoggettoIndagatos().remove(soggettoIndagato);
		soggettoIndagato.setFascicolo(null);

		return soggettoIndagato;
	}

	public Set<TerzoChiamatoCausa> getTerzoChiamatoCausas() {
		return this.terzoChiamatoCausas;
	}

	public void setTerzoChiamatoCausas(Set<TerzoChiamatoCausa> terzoChiamatoCausas) {
		this.terzoChiamatoCausas = terzoChiamatoCausas;
	}

	public TerzoChiamatoCausa addTerzoChiamatoCausa(TerzoChiamatoCausa terzoChiamatoCausa) {
		getTerzoChiamatoCausas().add(terzoChiamatoCausa);
		terzoChiamatoCausa.setFascicolo(this);

		return terzoChiamatoCausa;
	}

	public TerzoChiamatoCausa removeTerzoChiamatoCausa(TerzoChiamatoCausa terzoChiamatoCausa) {
		getTerzoChiamatoCausas().remove(terzoChiamatoCausa);
		terzoChiamatoCausa.setFascicolo(null);

		return terzoChiamatoCausa;
	}	

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
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
	
}