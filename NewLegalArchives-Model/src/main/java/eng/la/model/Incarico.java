package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedAttribute;
import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the INCARICO database table.
 * 
 */
@Entity
@NamedQuery(name="Incarico.findAll", query="SELECT i FROM Incarico i")
public class Incarico extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INCARICO_ID_GENERATOR", sequenceName="INCARICO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="INCARICO_ID_GENERATOR")
	private long id;

	@AuditedAttribute
	@Column(name="COLLEGIO_ARBITRALE")
	private String collegioArbitrale;

	@AuditedAttribute
	private String commento;

	@AuditedAttribute
	private String motivazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICHIESTA_AUTOR_INCARICO")
	private Date dataRichiestaAutorIncarico;
	
	@AuditedAttribute(classType=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RINVIO_VOTAZIONE")
	private Date dataRinvioVotazione;

	@AuditedAttribute
	@Column(name="DENOM_STUDIO_ARBITRO_PRESIDEN")
	private String denomStudioArbitroPresiden;

	@AuditedAttribute
	@Column(name="DENOMIN_STUDIO_ARBITRO_SEGRET")
	private String denominStudioArbitroSegret;

	@AuditedAttribute
	@Column(name="DENOMINAZ_STUDIO_CONTROPARTE")
	private String denominazStudioControparte;

	@AuditedAttribute
	@Column(name="INDIRIZZO_ARBITRO_CONTROPARTE")
	private String indirizzoArbitroControparte;

	@AuditedAttribute
	@Column(name="INDIRIZZO_ARBITRO_PRESIDENTE")
	private String indirizzoArbitroPresidente;

	@AuditedAttribute
	@Column(name="INDIRIZZO_ARBITRO_SEGRETARIO")
	private String indirizzoArbitroSegretario;

	private String lang;

	@AuditedAttribute
	@Column(name="NOME_COLLEGIO_ARBITRALE")
	private String nomeCollegioArbitrale;

	@AuditedAttribute
	@Column(name="NOME_INCARICO")
	private String nomeIncarico;

	@AuditedAttribute
	@Column(name="NOMINATIVO_ARBITRO_CONTROPARTE")
	private String nominativoArbitroControparte;

	@AuditedAttribute
	@Column(name="NOMINATIVO_PRESIDENTE")
	private String nominativoPresidente;

	@AuditedAttribute
	@Column(name="NOMINATIVO_SEGRETARIO")
	private String nominativoSegretario;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to LetteraIncarico
	@ManyToOne()
	@JoinColumn(name="ID_LETTERA")
	private LetteraIncarico letteraIncarico;

	//bi-directional many-to-one association to ListaRiferimento
	@ManyToOne()
	@JoinColumn(name="ID_LISTA_RIF")
	private ListaRiferimento listaRiferimento;

	//bi-directional many-to-one association to NotaPropIncarico
	@ManyToOne()
	@JoinColumn(name="ID_PROPOSTA")
	private NotaPropIncarico notaPropIncarico;

	//bi-directional many-to-one association to Procura
	@ManyToOne()
	@JoinColumn(name="ID_PROCURA")
	private Procura procura;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROFESSIONISTA_ESTERNO")
	private ProfessionistaEsterno professionistaEsterno;

	//bi-directional many-to-one association to StatoIncarico
	@AuditedAttribute(classType=StatoIncarico.class)
	@ManyToOne()
	@JoinColumn(name="ID_STATO_INCARICO")
	private StatoIncarico statoIncarico;

	//bi-directional many-to-one association to VerificaAnticorruzione
	@ManyToOne()
	@JoinColumn(name="ID_VER_ANTICOR")
	private VerificaAnticorruzione verificaAnticorruzione;

	//bi-directional many-to-one association to VerificaPartiCorrelate
	@ManyToOne()
	@JoinColumn(name="ID_VER_CORRELLATI")
	private VerificaPartiCorrelate verificaPartiCorrelate;

	//bi-directional many-to-one association to IncaricoWf
	@OneToMany(mappedBy="incarico")
	private Set<IncaricoWf> incaricoWfs;
	
	//bi-directional many-to-one association to Incarico
	@ManyToOne()
	@JoinColumn(name="ID_INCARICO_ARBITRALE")
	private Incarico incarico; 

	//bi-directional many-to-one association to RIncaricoProformaSocieta
	@OneToMany(mappedBy="incarico")
	private Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas;

	//bi-directional many-to-one association to VendorManagement
	@OneToMany(mappedBy="incarico")
	private Set<VendorManagement> vendorManagements;
	
	//bi-directional many-to-one association to Nazione
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione; 
	
	//bi-directional many-to-one association to Specializzazione
	@ManyToOne()
	@JoinColumn(name="ID_SPECIALIZZAZIONE")
	private Specializzazione specializzazione;

	public Incarico() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCollegioArbitrale() {
		return this.collegioArbitrale;
	}

	public void setCollegioArbitrale(String collegioArbitrale) {
		this.collegioArbitrale = collegioArbitrale;
	}

	public String getCommento() {
		return this.commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Date getDataAutorizzazione() {
		return this.dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataRichiestaAutorIncarico() {
		return this.dataRichiestaAutorIncarico;
	}

	public void setDataRichiestaAutorIncarico(Date dataRichiestaAutorIncarico) {
		this.dataRichiestaAutorIncarico = dataRichiestaAutorIncarico;
	}
	
	public Date getDataRinvioVotazione() {
		return this.dataRinvioVotazione;
	}

	public void setDataRinvioVotazione(Date dataRinvioVotazione) {
		this.dataRinvioVotazione = dataRinvioVotazione;
	}

	public String getDenomStudioArbitroPresiden() {
		return this.denomStudioArbitroPresiden;
	}

	public void setDenomStudioArbitroPresiden(String denomStudioArbitroPresiden) {
		this.denomStudioArbitroPresiden = denomStudioArbitroPresiden;
	}

	public String getDenominStudioArbitroSegret() {
		return this.denominStudioArbitroSegret;
	}

	public void setDenominStudioArbitroSegret(String denominStudioArbitroSegret) {
		this.denominStudioArbitroSegret = denominStudioArbitroSegret;
	}

	public String getDenominazStudioControparte() {
		return this.denominazStudioControparte;
	}

	public void setDenominazStudioControparte(String denominazStudioControparte) {
		this.denominazStudioControparte = denominazStudioControparte;
	}

	public String getIndirizzoArbitroControparte() {
		return this.indirizzoArbitroControparte;
	}

	public void setIndirizzoArbitroControparte(String indirizzoArbitroControparte) {
		this.indirizzoArbitroControparte = indirizzoArbitroControparte;
	}

	public String getIndirizzoArbitroPresidente() {
		return this.indirizzoArbitroPresidente;
	}

	public void setIndirizzoArbitroPresidente(String indirizzoArbitroPresidente) {
		this.indirizzoArbitroPresidente = indirizzoArbitroPresidente;
	}

	public String getIndirizzoArbitroSegretario() {
		return this.indirizzoArbitroSegretario;
	}

	public void setIndirizzoArbitroSegretario(String indirizzoArbitroSegretario) {
		this.indirizzoArbitroSegretario = indirizzoArbitroSegretario;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNomeCollegioArbitrale() {
		return this.nomeCollegioArbitrale;
	}

	public void setNomeCollegioArbitrale(String nomeCollegioArbitrale) {
		this.nomeCollegioArbitrale = nomeCollegioArbitrale;
	}

	public String getNomeIncarico() {
		return this.nomeIncarico;
	}

	public void setNomeIncarico(String nomeIncarico) {
		this.nomeIncarico = nomeIncarico;
	}

	public String getNominativoArbitroControparte() {
		return this.nominativoArbitroControparte;
	}

	public void setNominativoArbitroControparte(String nominativoArbitroControparte) {
		this.nominativoArbitroControparte = nominativoArbitroControparte;
	}

	public String getNominativoPresidente() {
		return this.nominativoPresidente;
	}

	public void setNominativoPresidente(String nominativoPresidente) {
		this.nominativoPresidente = nominativoPresidente;
	}

	public String getNominativoSegretario() {
		return this.nominativoSegretario;
	}

	public void setNominativoSegretario(String nominativoSegretario) {
		this.nominativoSegretario = nominativoSegretario;
	}

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public LetteraIncarico getLetteraIncarico() {
		return this.letteraIncarico;
	}

	public void setLetteraIncarico(LetteraIncarico letteraIncarico) {
		this.letteraIncarico = letteraIncarico;
	}

	public ListaRiferimento getListaRiferimento() {
		return this.listaRiferimento;
	}

	public void setListaRiferimento(ListaRiferimento listaRiferimento) {
		this.listaRiferimento = listaRiferimento;
	}

	public NotaPropIncarico getNotaPropIncarico() {
		return this.notaPropIncarico;
	}

	public void setNotaPropIncarico(NotaPropIncarico notaPropIncarico) {
		this.notaPropIncarico = notaPropIncarico;
	}

	public Procura getProcura() {
		return this.procura;
	}

	public void setProcura(Procura procura) {
		this.procura = procura;
	}

	public ProfessionistaEsterno getProfessionistaEsterno() {
		return this.professionistaEsterno;
	}

	public void setProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

	public StatoIncarico getStatoIncarico() {
		return this.statoIncarico;
	}

	public void setStatoIncarico(StatoIncarico statoIncarico) {
		this.statoIncarico = statoIncarico;
	}

	public VerificaAnticorruzione getVerificaAnticorruzione() {
		return this.verificaAnticorruzione;
	}

	public void setVerificaAnticorruzione(VerificaAnticorruzione verificaAnticorruzione) {
		this.verificaAnticorruzione = verificaAnticorruzione;
	}

	public VerificaPartiCorrelate getVerificaPartiCorrelate() {
		return this.verificaPartiCorrelate;
	}

	public void setVerificaPartiCorrelate(VerificaPartiCorrelate verificaPartiCorrelate) {
		this.verificaPartiCorrelate = verificaPartiCorrelate;
	}

	public Incarico getIncarico() {
		return this.incarico;
	}

	public void setIncarico(Incarico incarico) {
		this.incarico = incarico;
	}
	
	public Set<IncaricoWf> getIncaricoWfs() {
		return this.incaricoWfs;
	}

	public void setIncaricoWfs(Set<IncaricoWf> incaricoWfs) {
		this.incaricoWfs = incaricoWfs;
	}

	public IncaricoWf addIncaricoWf(IncaricoWf incaricoWf) {
		getIncaricoWfs().add(incaricoWf);
		incaricoWf.setIncarico(this);

		return incaricoWf;
	}

	public IncaricoWf removeIncaricoWf(IncaricoWf incaricoWf) {
		getIncaricoWfs().remove(incaricoWf);
		incaricoWf.setIncarico(null);

		return incaricoWf;
	}

	public Set<RIncaricoProformaSocieta> getRIncaricoProformaSocietas() {
		return this.RIncaricoProformaSocietas;
	}

	public void setRIncaricoProformaSocietas(Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas) {
		this.RIncaricoProformaSocietas = RIncaricoProformaSocietas;
	}

	public RIncaricoProformaSocieta addRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().add(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setIncarico(this);

		return RIncaricoProformaSocieta;
	}

	public RIncaricoProformaSocieta removeRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().remove(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setIncarico(null);

		return RIncaricoProformaSocieta;
	}

	public Set<VendorManagement> getVendorManagements() {
		return this.vendorManagements;
	}

	public void setVendorManagements(Set<VendorManagement> vendorManagements) {
		this.vendorManagements = vendorManagements;
	}

	public VendorManagement addVendorManagement(VendorManagement vendorManagement) {
		getVendorManagements().add(vendorManagement);
		vendorManagement.setIncarico(this);

		return vendorManagement;
	}

	public VendorManagement removeVendorManagement(VendorManagement vendorManagement) {
		getVendorManagements().remove(vendorManagement);
		vendorManagement.setIncarico(null);

		return vendorManagement;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	
	@AuditedObjectName
	public String getObjectName(){
		if( collegioArbitrale.equals("T")){
			return getNomeCollegioArbitrale();
		}else{
			return getNomeIncarico();
		}
	}
	
	public Nazione getNazione() {
		return nazione;
	}

	public void setNazione(Nazione nazione) {
		this.nazione = nazione;
	}

	public Specializzazione getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(Specializzazione specializzazione) {
		this.specializzazione = specializzazione;
	}

	
}