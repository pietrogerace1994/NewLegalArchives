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

import eng.la.model.audit.AuditedAttribute;


/**
 * The persistent class for the BEAUTY_CONTEST database table.
 * 
 */
@Entity
@Table(name="BEAUTY_CONTEST")
@NamedQuery(name="BeautyContest.findAll", query="SELECT bc FROM BeautyContest bc")
public class BeautyContest extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BEAUTY_CONTEST_ID_GENERATOR", sequenceName="BEAUTY_CONTEST_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="BEAUTY_CONTEST_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_EMISSIONE")
	private Date dataEmissione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CHIUSURA")
	private Date dataChiusura;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICHIESTA_AUTOR_BC")
	private Date dataRichiestaAutorBc;

	@Column(name="TITOLO")
	private String titolo;
	
	@Column(name="LEGALE_INTERNO")
	private String legaleInterno;

	//bi-directional many-to-one association to NAZIONE
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione;
	
	//bi-directional many-to-one association to PROFESSIONISTA_ESTERNO
	@ManyToOne()
	@JoinColumn(name="ID_VINCITORE")
	private ProfessionistaEsterno vincitore;

	//bi-directional many-to-one association to StatoBeautyContest
	@AuditedAttribute(classType=StatoBeautyContest.class)
	@ManyToOne()
	@JoinColumn(name="ID_STATO_BEAUTY_CONTEST")
	private StatoBeautyContest statoBeautyContest;
	
	@Column(name="CENTRO_DI_COSTO")
	private String centroDiCosto;
	
	@Column(name="INCARICO_ACCORDO_QUADRO")
	private String incarico_accordoQuadro;
	
	@Column(name="DESCRIZIONE_SOW")
	private String descrizione_sow;

	//bi-directional many-to-one association to BeautyContestWf
	@OneToMany(mappedBy="beautyContest")
	private Set<BeautyContestWf> beautyContestWfs;
	
	//bi-directional many-to-one association to RBeautyContestMateria
	@OneToMany(mappedBy="beautyContest")
	private Set<RBeautyContestMateria> RBeautyContestMaterias;
	
	//bi-directional many-to-one association to RBeautyContestMateria
	@OneToMany(mappedBy="beautyContest")
	private Set<RBeautyContestProfessionistaEsterno> RBeautyContestProfessionistaEsternos;

	public BeautyContest() {
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

	public Date getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public Date getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public Date getDataRichiestaAutorBc() {
		return dataRichiestaAutorBc;
	}

	public void setDataRichiestaAutorBc(Date dataRichiestaAutorBc) {
		this.dataRichiestaAutorBc = dataRichiestaAutorBc;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public Nazione getNazione() {
		return nazione;
	}

	public void setNazione(Nazione nazione) {
		this.nazione = nazione;
	}

	public ProfessionistaEsterno getVincitore() {
		return vincitore;
	}

	public void setVincitore(ProfessionistaEsterno vincitore) {
		this.vincitore = vincitore;
	}

	public StatoBeautyContest getStatoBeautyContest() {
		return statoBeautyContest;
	}

	public void setStatoBeautyContest(StatoBeautyContest statoBeautyContest) {
		this.statoBeautyContest = statoBeautyContest;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getIncarico_accordoQuadro() {
		return incarico_accordoQuadro;
	}

	public void setIncarico_accordoQuadro(String incarico_accordoQuadro) {
		this.incarico_accordoQuadro = incarico_accordoQuadro;
	}

	public String getDescrizione_sow() {
		return descrizione_sow;
	}

	public void setDescrizione_sow(String descrizione_sow) {
		this.descrizione_sow = descrizione_sow;
	}

	public Set<BeautyContestWf> getBeautyContestWfs() {
		return this.beautyContestWfs;
	}

	public void setBeautyContestWfs(Set<BeautyContestWf> beautyContestWfs) {
		this.beautyContestWfs = beautyContestWfs;
	}

	public BeautyContestWf addBeautyContestWf(BeautyContestWf beautyContestWf) {
		getBeautyContestWfs().add(beautyContestWf);
		beautyContestWf.setBeautyContest(this);
		return beautyContestWf;
	}

	public BeautyContestWf removeBeautyContestWf(BeautyContestWf beautyContestWf) {
		getBeautyContestWfs().remove(beautyContestWf);
		beautyContestWf.setBeautyContest(null);
		return beautyContestWf;
	}
	
	public Set<RBeautyContestMateria> getRBeautyContestMaterias() {
		return this.RBeautyContestMaterias;
	}

	public void setRBeautyContestMaterias(Set<RBeautyContestMateria> RBeautyContestMaterias) {
		this.RBeautyContestMaterias = RBeautyContestMaterias;
	}

	public RBeautyContestMateria addRBeautyContestMateria(RBeautyContestMateria RBeautyContestMateria) {
		getRBeautyContestMaterias().add(RBeautyContestMateria);
		RBeautyContestMateria.setBeautyContest(this);
		return RBeautyContestMateria;
	}

	public RBeautyContestMateria removeRBeautyContestMateria(RBeautyContestMateria RBeautyContestMateria) {
		getRBeautyContestMaterias().remove(RBeautyContestMateria);
		RBeautyContestMateria.setBeautyContest(null);

		return RBeautyContestMateria;
	}
	
	public Set<RBeautyContestProfessionistaEsterno> getRBeautyContestProfessionistaEsternos() {
		return this.RBeautyContestProfessionistaEsternos;
	}

	public void setRBeautyContestProfessionistaEsternos(Set<RBeautyContestProfessionistaEsterno> RBeautyContestProfessionistaEsternos) {
		this.RBeautyContestProfessionistaEsternos = RBeautyContestProfessionistaEsternos;
	}

	public RBeautyContestProfessionistaEsterno addRBeautyContestProfessionistaEsterno(RBeautyContestProfessionistaEsterno RBeautyContestProfessionistaEsterno) {
		getRBeautyContestProfessionistaEsternos().add(RBeautyContestProfessionistaEsterno);
		RBeautyContestProfessionistaEsterno.setBeautyContest(this);
		return RBeautyContestProfessionistaEsterno;
	}

	public RBeautyContestProfessionistaEsterno removeRBeautyContestProfessionistaEsterno(RBeautyContestProfessionistaEsterno RBeautyContestProfessionistaEsterno) {
		getRBeautyContestProfessionistaEsternos().remove(RBeautyContestProfessionistaEsterno);
		RBeautyContestProfessionistaEsterno.setBeautyContest(null);
		return RBeautyContestProfessionistaEsterno;
	}

}