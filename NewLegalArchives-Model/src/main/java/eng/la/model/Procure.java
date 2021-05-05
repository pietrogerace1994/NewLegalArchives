package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eng.la.model.audit.AuditedAttribute;


/**
 * The persistent class for the PROGETTO database table.
 */
@Entity
@Table(name="PROCURE")
public class Procure extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROCURE_ID_GENERATOR", sequenceName="PROCURE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PROCURE_ID_GENERATOR")
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CONFERIMENTO")
	private Date dataConferimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_REVOCA")
	private Date dataRevoca;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="NOME_PROCURATORE")
	private String nomeProcuratore;
	
	@Column(name="NUMERO_REPERTORIO")
	private String numeroRepertorio;

	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA_APPARTENENZA")
	private Societa societa;
	
	@ManyToOne()
	@JoinColumn(name="ID_NOTAIO")
	private ProfessionistaEsterno notaio;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="procure")
	private Set<DocumentoProcure> documentoProcureSet;
	
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;
	
	//bi-directional many-to-one association to TipoSocieta
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_PROCURE")
	private TipoProcure tipoProcure;
	
	@AuditedAttribute
	@Column(name="LEGALE_INTERNO")
	private String legaleInterno;
	
	@Column(name="UTENTE")
	private String utente;
	
	//bi-directional many-to-one association to TipoSocieta
	@ManyToOne()
	@JoinColumn(name="ID_POSIZIONE_ORGANIZZATIVA")
	private PosizioneOrganizzativa posizioneOrganizzativa;
	
	//bi-directional many-to-one association to TipoSocieta
	@ManyToOne()
	@JoinColumn(name="ID_LIVELLO_ATTRIBUZIONI_I")
	private LivelloAttribuzioniI livelloAttribuzioniI;
	
	//bi-directional many-to-one association to TipoSocieta
	@ManyToOne()
	@JoinColumn(name="ID_LIVELLO_ATTRIBUZIONI_II")
	private LivelloAttribuzioniII livelloAttribuzioniII;


	public Procure() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataConferimento() {
		return dataConferimento;
	}

	public void setDataConferimento(Date dataConferimento) {
		this.dataConferimento = dataConferimento;
	}

	public Date getDataRevoca() {
		return dataRevoca;
	}

	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}

	public String getNomeProcuratore() {
		return nomeProcuratore;
	}

	public void setNomeProcuratore(String nomeProcuratore) {
		this.nomeProcuratore = nomeProcuratore;
	}

	public String getNumeroRepertorio() {
		return numeroRepertorio;
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = numeroRepertorio;
	}

	public Societa getSocieta() {
		return societa;
	}

	public void setSocieta(Societa societa) {
		this.societa = societa;
	}

	public ProfessionistaEsterno getNotaio() {
		return notaio;
	}

	public void setNotaio(ProfessionistaEsterno notaio) {
		this.notaio = notaio;
	}

	public Set<DocumentoProcure> getDocumentoProcureSet() {
		return documentoProcureSet;
	}

	public void setDocumentoProcureSet(Set<DocumentoProcure> documentoProcureSet) {
		this.documentoProcureSet = documentoProcureSet;
	}
	

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Fascicolo getFascicolo() {
		return fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public TipoProcure getTipoProcure() {
		return tipoProcure;
	}

	public void setTipoProcure(TipoProcure tipoProcure) {
		this.tipoProcure = tipoProcure;
	}
	
	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}
	
	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public PosizioneOrganizzativa getPosizioneOrganizzativa() {
		return posizioneOrganizzativa;
	}

	public void setPosizioneOrganizzativa(PosizioneOrganizzativa posizioneOrganizzativa) {
		this.posizioneOrganizzativa = posizioneOrganizzativa;
	}

	public LivelloAttribuzioniI getLivelloAttribuzioniI() {
		return livelloAttribuzioniI;
	}

	public void setLivelloAttribuzioniI(LivelloAttribuzioniI livelloAttribuzioniI) {
		this.livelloAttribuzioniI = livelloAttribuzioniI;
	}

	public LivelloAttribuzioniII getLivelloAttribuzioniII() {
		return livelloAttribuzioniII;
	}

	public void setLivelloAttribuzioniII(LivelloAttribuzioniII livelloAttribuzioniII) {
		this.livelloAttribuzioniII = livelloAttribuzioniII;
	}
}