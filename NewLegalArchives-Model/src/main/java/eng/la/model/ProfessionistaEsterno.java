package eng.la.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;


/**
 * The persistent class for the PROFESSIONISTA_ESTERNO database table.
 * 
 */
@Entity
@Table(name="PROFESSIONISTA_ESTERNO")
@NamedQuery(name="ProfessionistaEsterno.findAll", query="SELECT p FROM ProfessionistaEsterno p")
public class ProfessionistaEsterno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROFESSIONISTA_ESTERNO_ID_GENERATOR", sequenceName="PROFESSIONISTA_ESTERNO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PROFESSIONISTA_ESTERNO_ID_GENERATOR")
	private long id;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name="MOTIVAZIONE_RICHIESTA")
	private String motivazioneRichiesta;

	@Formula(value="cognome || ' ' || nome")
	private String cognomeNome;
	
	private String cognome;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICH_AUTORIZZAZIONE")
	private Date dataRichAutorizzazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ULTIMA_MODIFICA")
	private Date dataUltimaModifica;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	private String email;

	private String fax;

	private String nome;

	private String telefono;
	
	@Column(name="GIUDIZIO")
	private Integer giudizio;

	//bi-directional many-to-one association to TipoProfessionista
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_PROFESSIONISTA")
	private TipoProfessionista tipoProfessionista;

	//bi-directional many-to-one association to DueDiligence
	@OneToMany(mappedBy="professionistaEsterno")
	private Set<DueDiligence> dueDiligences;

	//bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy="professionistaEsterno")
	private Set<Incarico> incaricos;

	//bi-directional many-to-one association to StatoEsitoValutazioneProf
	@ManyToOne()
	@JoinColumn(name="ID_STATO_ESITO_VALUTAZIONE")
	private StatoEsitoValutazioneProf statoEsitoValutazioneProf;

	//bi-directional many-to-one association to StatoProfessionista
	@ManyToOne()
	@JoinColumn(name="ID_STATO_PROFESSIONISTA")
	private StatoProfessionista statoProfessionista;

	//bi-directional many-to-one association to StudioLegale
	@ManyToOne()
	@JoinColumn(name="ID_STUDIO_LEGALE")
	private StudioLegale studioLegale;

	//bi-directional many-to-one association to ProfessionistaEsternoWf
	@OneToMany(mappedBy="professionistaEsterno")
	private Set<ProfessionistaEsternoWf> professionistaEsternoWfs;

	//bi-directional many-to-one association to RProfessionistaNazione
	@OneToMany(mappedBy="professionistaEsterno")
	private Set<RProfessionistaNazione> RProfessionistaNaziones;

	//bi-directional many-to-one association to RProfEstSpec
	@OneToMany(mappedBy="professionistaEsterno")
	private Set<RProfEstSpec> RProfEstSpecs;

	//bi-directional many-to-one association to RProfDocumento
	@OneToMany(mappedBy="professionistaEsterno")
	private Set<RProfDocumento> RProfDocumentos;
	
	//bi-directional many-to-one association to StatoProfessionista
	@ManyToOne()
	@JoinColumn(name="ID_CATEGORIA_CONTEST")
	private CategoriaContest categoriaContest;
	
	public ProfessionistaEsterno() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getMotivazioneRichiesta() {
		return this.motivazioneRichiesta;
	}

	public void setMotivazioneRichiesta(String motivazioneRichiesta) {
		this.motivazioneRichiesta = motivazioneRichiesta;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
	public Date getDataUltimaModifica() {
		return this.dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	
	public Date getDataAutorizzazione() {
		return this.dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}
	
	public Date getDataRichAutorizzazione() {
		return this.dataRichAutorizzazione;
	}

	public void setDataRichAutorizzazione(Date dataRichAutorizzazione) {
		this.dataRichAutorizzazione = dataRichAutorizzazione;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<DueDiligence> getDueDiligences() {
		return this.dueDiligences;
	}

	public void setDueDiligences(Set<DueDiligence> dueDiligences) {
		this.dueDiligences = dueDiligences;
	}

	public DueDiligence addDueDiligence(DueDiligence dueDiligence) {
		getDueDiligences().add(dueDiligence);
		dueDiligence.setProfessionistaEsterno(this);

		return dueDiligence;
	}

	public DueDiligence removeDueDiligence(DueDiligence dueDiligence) {
		getDueDiligences().remove(dueDiligence);
		dueDiligence.setProfessionistaEsterno(null);

		return dueDiligence;
	}

	public Set<Incarico> getIncaricos() {
		return this.incaricos;
	}

	public void setIncaricos(Set<Incarico> incaricos) {
		this.incaricos = incaricos;
	}

	public Incarico addIncarico(Incarico incarico) {
		getIncaricos().add(incarico);
		incarico.setProfessionistaEsterno(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setProfessionistaEsterno(null);

		return incarico;
	}

	public StatoEsitoValutazioneProf getStatoEsitoValutazioneProf() {
		return this.statoEsitoValutazioneProf;
	}

	public void setStatoEsitoValutazioneProf(StatoEsitoValutazioneProf statoEsitoValutazioneProf) {
		this.statoEsitoValutazioneProf = statoEsitoValutazioneProf;
	}

	public StatoProfessionista getStatoProfessionista() {
		return this.statoProfessionista;
	}

	public void setStatoProfessionista(StatoProfessionista statoProfessionista) {
		this.statoProfessionista = statoProfessionista;
	}

	public StudioLegale getStudioLegale() {
		return this.studioLegale;
	}

	public void setStudioLegale(StudioLegale studioLegale) {
		this.studioLegale = studioLegale;
	}

	public Set<ProfessionistaEsternoWf> getProfessionistaEsternoWfs() {
		return this.professionistaEsternoWfs;
	}

	public void setProfessionistaEsternoWfs(Set<ProfessionistaEsternoWf> professionistaEsternoWfs) {
		this.professionistaEsternoWfs = professionistaEsternoWfs;
	}

	public ProfessionistaEsternoWf addProfessionistaEsternoWf(ProfessionistaEsternoWf professionistaEsternoWf) {
		getProfessionistaEsternoWfs().add(professionistaEsternoWf);
		professionistaEsternoWf.setProfessionistaEsterno(this);

		return professionistaEsternoWf;
	}

	public ProfessionistaEsternoWf removeProfessionistaEsternoWf(ProfessionistaEsternoWf professionistaEsternoWf) {
		getProfessionistaEsternoWfs().remove(professionistaEsternoWf);
		professionistaEsternoWf.setProfessionistaEsterno(null);

		return professionistaEsternoWf;
	}

	public Set<RProfessionistaNazione> getRProfessionistaNaziones() {
		return this.RProfessionistaNaziones;
	}

	public void setRProfessionistaNaziones(Set<RProfessionistaNazione> RProfessionistaNaziones) {
		this.RProfessionistaNaziones = RProfessionistaNaziones;
	}

	public RProfessionistaNazione addRProfessionistaNazione(RProfessionistaNazione RProfessionistaNazione) {
		getRProfessionistaNaziones().add(RProfessionistaNazione);
		RProfessionistaNazione.setProfessionistaEsterno(this);

		return RProfessionistaNazione;
	}

	public RProfessionistaNazione removeRProfessionistaNazione(RProfessionistaNazione RProfessionistaNazione) {
		getRProfessionistaNaziones().remove(RProfessionistaNazione);
		RProfessionistaNazione.setProfessionistaEsterno(null);

		return RProfessionistaNazione;
	}

	public Set<RProfEstSpec> getRProfEstSpecs() {
		return this.RProfEstSpecs;
	}

	public void setRProfEstSpecs(Set<RProfEstSpec> RProfEstSpecs) {
		this.RProfEstSpecs = RProfEstSpecs;
	}

	public RProfEstSpec addRProfEstSpec(RProfEstSpec RProfEstSpec) {
		getRProfEstSpecs().add(RProfEstSpec);
		RProfEstSpec.setProfessionistaEsterno(this);

		return RProfEstSpec;
	}

	public RProfEstSpec removeRProfEstSpec(RProfEstSpec RProfEstSpec) {
		getRProfEstSpecs().remove(RProfEstSpec);
		RProfEstSpec.setProfessionistaEsterno(null);

		return RProfEstSpec;
	}

	public TipoProfessionista getTipoProfessionista() {
		return tipoProfessionista;
	}

	public void setTipoProfessionista(TipoProfessionista tipoProfessionista) {
		this.tipoProfessionista = tipoProfessionista;
	}

	public Set<RProfDocumento> getRProfDocumentos() {
		return RProfDocumentos;
	}

	public void setRProfDocumentos(Set<RProfDocumento> rProfDocumentos) {
		RProfDocumentos = rProfDocumentos;
	}

	public RProfDocumento addRProfDocumento(RProfDocumento RProfDocumento) {
		getRProfDocumentos().add(RProfDocumento);
		RProfDocumento.setProfessionistaEsterno(this);

		return RProfDocumento;
	}

	public RProfDocumento removeRProfDocumento(RProfDocumento RProfDocumento) {
		getRProfDocumentos().remove(RProfDocumento);
		RProfDocumento.setProfessionistaEsterno(null);

		return RProfDocumento;
	}

	public Integer getGiudizio() {
		return giudizio;
	}

	public void setGiudizio(Integer giudizio) {
		this.giudizio = giudizio;
	}

	public CategoriaContest getCategoriaContest() {
		return categoriaContest;
	}

	public void setCategoriaContest(CategoriaContest categoriaContest) {
		this.categoriaContest = categoriaContest;
	}
	
}