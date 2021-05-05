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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SOCIETA database table.
 * 
 */
@Entity
@NamedQuery(name="Societa.findAll", query="SELECT s FROM Societa s")
public class Societa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOCIETA_ID_GENERATOR", sequenceName="SOCIETA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SOCIETA_ID_GENERATOR")
	private long id;

	@Column(name="CAP")
	private String cap;

	@Column(name="CITTA")
	private String citta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="EMAIL_AMMINISTRAZIONE")
	private String emailAmministrazione;

	@Column(name="INDIRIZZO")
	private String indirizzo;

	@Column(name="NOME")
	private String nome;

	@Column(name="RAGIONE_SOCIALE")
	private String ragioneSociale;
	
	@Column(name="CF")
	private String codiceFiscale;
	
	@Column(name="PIVA")
	private String partitaIva;
	
	@Column(name="SITO_WEB")
	private String sitoWeb;
	
	@Column(name="PIE_DI_PAGINA")
	private String pieDiPagina;

	//bi-directional many-to-one association to Atto
	@OneToMany(mappedBy="societa")
	private Set<Atto> attos;

	//bi-directional many-to-one association to CentroDiCosto
	@OneToMany(mappedBy="societa")
	private Set<CentroDiCosto> centroDiCostos;

	//bi-directional many-to-one association to Fascicolo
	@OneToMany(mappedBy="societa")
	private Set<Fascicolo> fascicolos;

	//bi-directional many-to-one association to RFascicoloSocieta
	@OneToMany(mappedBy="societa")
	private Set<RFascicoloSocieta> RFascicoloSocietas;

	//bi-directional many-to-one association to RIncaricoProformaSocieta
	@OneToMany(mappedBy="societa")
	private Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas;

	//bi-directional many-to-one association to Nazione
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione;

	//bi-directional many-to-one association to TipoSocieta
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_SOCIETA")
	private TipoSocieta tipoSocieta;

	public Societa() {
	}
	
	

	public String getSitoWeb() {
		return sitoWeb;
	}



	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}



	public String getPieDiPagina() {
		return pieDiPagina;
	}



	public void setPieDiPagina(String pieDiPagina) {
		this.pieDiPagina = pieDiPagina;
	}



	public String getCodiceFiscale() {
		return codiceFiscale;
	}



	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}



	public String getPartitaIva() {
		return partitaIva;
	}



	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}



	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return this.citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getEmailAmministrazione() {
		return this.emailAmministrazione;
	}

	public void setEmailAmministrazione(String emailAmministrazione) {
		this.emailAmministrazione = emailAmministrazione;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
 
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Set<Atto> getAttos() {
		return this.attos;
	}

	public void setAttos(Set<Atto> attos) {
		this.attos = attos;
	}

	public Atto addAtto(Atto atto) {
		getAttos().add(atto);
		atto.setSocieta(this);

		return atto;
	}

	public Atto removeAtto(Atto atto) {
		getAttos().remove(atto);
		atto.setSocieta(null);

		return atto;
	}

	public Set<CentroDiCosto> getCentroDiCostos() {
		return this.centroDiCostos;
	}

	public void setCentroDiCostos(Set<CentroDiCosto> centroDiCostos) {
		this.centroDiCostos = centroDiCostos;
	}

	public CentroDiCosto addCentroDiCosto(CentroDiCosto centroDiCosto) {
		getCentroDiCostos().add(centroDiCosto);
		centroDiCosto.setSocieta(this);

		return centroDiCosto;
	}

	public CentroDiCosto removeCentroDiCosto(CentroDiCosto centroDiCosto) {
		getCentroDiCostos().remove(centroDiCosto);
		centroDiCosto.setSocieta(null);

		return centroDiCosto;
	}

	public Set<Fascicolo> getFascicolos() {
		return this.fascicolos;
	}

	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}

	public Fascicolo addFascicolo(Fascicolo fascicolo) {
		getFascicolos().add(fascicolo);
		fascicolo.setSocieta(this);

		return fascicolo;
	}

	public Fascicolo removeFascicolo(Fascicolo fascicolo) {
		getFascicolos().remove(fascicolo);
		fascicolo.setSocieta(null);

		return fascicolo;
	}

	public Set<RFascicoloSocieta> getRFascicoloSocietas() {
		return this.RFascicoloSocietas;
	}

	public void setRFascicoloSocietas(Set<RFascicoloSocieta> RFascicoloSocietas) {
		this.RFascicoloSocietas = RFascicoloSocietas;
	}

	public RFascicoloSocieta addRFascicoloSocieta(RFascicoloSocieta RFascicoloSocieta) {
		getRFascicoloSocietas().add(RFascicoloSocieta);
		RFascicoloSocieta.setSocieta(this);

		return RFascicoloSocieta;
	}

	public RFascicoloSocieta removeRFascicoloSocieta(RFascicoloSocieta RFascicoloSocieta) {
		getRFascicoloSocietas().remove(RFascicoloSocieta);
		RFascicoloSocieta.setSocieta(null);

		return RFascicoloSocieta;
	}

	public Set<RIncaricoProformaSocieta> getRIncaricoProformaSocietas() {
		return this.RIncaricoProformaSocietas;
	}

	public void setRIncaricoProformaSocietas(Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas) {
		this.RIncaricoProformaSocietas = RIncaricoProformaSocietas;
	}

	public RIncaricoProformaSocieta addRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().add(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setSocieta(this);

		return RIncaricoProformaSocieta;
	}

	public RIncaricoProformaSocieta removeRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().remove(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setSocieta(null);

		return RIncaricoProformaSocieta;
	}

	public Nazione getNazione() {
		return this.nazione;
	}

	public void setNazione(Nazione nazione) {
		this.nazione = nazione;
	}

	public TipoSocieta getTipoSocieta() {
		return this.tipoSocieta;
	}

	public void setTipoSocieta(TipoSocieta tipoSocieta) {
		this.tipoSocieta = tipoSocieta;
	}

}