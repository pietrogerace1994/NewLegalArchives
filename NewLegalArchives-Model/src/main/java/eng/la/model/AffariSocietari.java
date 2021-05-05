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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PROGETTO database table.
 */
@Entity
@Table(name="SOCIETA_AFFARI")
public class AffariSocietari extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOCIETA_AFFARI_ID_GENERATOR", sequenceName="SOCIETA_AFFARI_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SOCIETA_AFFARI_ID_GENERATOR")
	private long id;
	
	@Column(name="CODICE_SOCIETA")
	private Long codiceSocieta;
	
	@Column(name="DENOMINAZIONE")   
	private String denominazione;
	
	@Column(name="CAPITALE_SOTTOSCRITTO")   
	private Long capitaleSottoscritto;
	
	@Column(name="CAPITALE_VERSATO")   
	private Long capitaleSociale;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_COSTITUZIONE")   
	private Date dataCostituzione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_USCITA")   
	private Date dataUscita;

	@Column(name="DENOMINAZIONE_BREVE")
	private String denominazioneBreve;
	
	@Column(name="SIGLA_FORMA_GIURIDICA")   
	private String siglaFormaGiuridica;

	@Column(name="FORMA_GIURIDICA")   
	private String formaGiuridica;
	
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione;

	@Column(name="SIGLA_STATO_PROVINCIA")
	private String siglaStatoProvincia;
	
	@Column(name="STATO_PROVINCIA")   
	private String siglaProvincia;
	
	@Column(name="ITALIA_ESTERO")   
	private String italiaEstero;
	
	@Column(name="UE_EXTRAUE")   
	private String ueExstraue;
	
	@Column(name="SEDE_LEGALE")   
	private String sedeLegale;
	
	@Column(name="INDIRIZZO")   
	private String indirizzo;
	
	@Column(name="CAP")   
	private Long cap;
	
	@Column(name="CODICE_FISCALE")   
	private String codiceFiscale;
	
	@Column(name="PARTITA_IVA")   
	private String partitaIva;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_REA")   
	private Date dataRea;
	
	@Column(name="NUMERO_REA")   
	private Long numeroRea;

	@Column(name="COMUNE_REA")   
	private String comuneRea;
	
	@Column(name="SIGLA_PROVINCIA_REA")   
	private String siglaProvinciaRea;
	
	@Column(name="PROVINCIA_REA")   
	private String provinciaRea;
	
	@Column(name="QUOTAZIONE")   
	private String quotazione;
	
	@Column(name="IN_LIQUIDAZIONE")   
	private String inLiquidazione;
	
	@Column(name="MODELLO_DI_GOVERNANCE")   
	private String modelloDiGovernance;
	
	@Column(name="N_COMPONENTI_CDA")   
	private Long nComponentiCDA;
	
	@Column(name="N_COMPONENTI_CS")   
	private String nComponentiCollegioSindacale;
	
	@Column(name="N_COMPONENTI_ODV")   
	private String nComponentiOdv;
	
	@Column(name="CODICE_NAZIONE")   
	private String codiceNazione;
	
	@Column(name="SOCIETA_DI_REVISIONE")   
	private String societaDiRevisione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INCARICO")
	private Date dataIncarico;
	
	@Column(name="ID_CONTROLLANTE")
	private Long idControllante;
	
	@Column(name="PERCENTUALE_CONTROLLANTE")
	private Long percentualeControllante;
	
	@Column(name="PERCENTUALE_TERZI")
	private Long percentualeTerzi;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	//bi-directional many-to-one association to RSocietaAffari
	@OneToMany(mappedBy="societaAffari")
	private Set<RSocietaAffari> RSocietaAffaris;


	public AffariSocietari(long idSocieta) {
		setId(idSocieta);
	}
	
	public AffariSocietari() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCodiceSocieta() {
		return codiceSocieta;
	}

	public void setCodiceSocieta(Long codiceSocieta) {
		this.codiceSocieta = codiceSocieta;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Long getCapitaleSottoscritto() {
		return capitaleSottoscritto;
	}

	public void setCapitaleSottoscritto(Long capitaleSottoscritto) {
		this.capitaleSottoscritto = capitaleSottoscritto;
	}

	public Long getCapitaleSociale() {
		return capitaleSociale;
	}

	public void setCapitaleSociale(Long capitaleSociale) {
		this.capitaleSociale = capitaleSociale;
	}

	public Date getDataCostituzione() {
		return dataCostituzione;
	}

	public void setDataCostituzione(Date dataCostituzione) {
		this.dataCostituzione = dataCostituzione;
	}

	public Date getDataUscita() {
		return dataUscita;
	}

	public void setDataUscita(Date dataUscita) {
		this.dataUscita = dataUscita;
	}

	public String getDenominazioneBreve() {
		return denominazioneBreve;
	}

	public void setDenominazioneBreve(String denominazioneBreve) {
		this.denominazioneBreve = denominazioneBreve;
	}

	public String getSiglaFormaGiuridica() {
		return siglaFormaGiuridica;
	}

	public void setSiglaFormaGiuridica(String siglaFormaGiuridica) {
		this.siglaFormaGiuridica = siglaFormaGiuridica;
	}

	public String getFormaGiuridica() {
		return formaGiuridica;
	}

	public void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public Nazione getNazione() {
		return nazione;
	}

	public void setNazione(Nazione nazione) {
		this.nazione = nazione;
	}

	public String getSiglaStatoProvincia() {
		return siglaStatoProvincia;
	}

	public void setSiglaStatoProvincia(String siglaStatoProvincia) {
		this.siglaStatoProvincia = siglaStatoProvincia;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public String getItaliaEstero() {
		return italiaEstero;
	}

	public void setItaliaEstero(String italiaEstero) {
		this.italiaEstero = italiaEstero;
	}

	public String getUeExstraue() {
		return ueExstraue;
	}

	public void setUeExstraue(String ueExstraue) {
		this.ueExstraue = ueExstraue;
	}

	public String getSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Long getCap() {
		return cap;
	}

	public void setCap(Long cap) {
		this.cap = cap;
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

	public Date getDataRea() {
		return dataRea;
	}

	public void setDataRea(Date dataRea) {
		this.dataRea = dataRea;
	}

	public Long getNumeroRea() {
		return numeroRea;
	}

	public void setNumeroRea(Long numeroRea) {
		this.numeroRea = numeroRea;
	}

	public String getComuneRea() {
		return comuneRea;
	}

	public void setComuneRea(String comuneRea) {
		this.comuneRea = comuneRea;
	}

	public String getSiglaProvinciaRea() {
		return siglaProvinciaRea;
	}

	public void setSiglaProvinciaRea(String siglaProvinciaRea) {
		this.siglaProvinciaRea = siglaProvinciaRea;
	}

	public String getProvinciaRea() {
		return provinciaRea;
	}

	public void setProvinciaRea(String provinciaRea) {
		this.provinciaRea = provinciaRea;
	}

	public String getQuotazione() {
		return quotazione;
	}

	public void setQuotazione(String quotazione) {
		this.quotazione = quotazione;
	}

	public String getInLiquidazione() {
		return inLiquidazione;
	}

	public void setInLiquidazione(String inLiquidazione) {
		this.inLiquidazione = inLiquidazione;
	}

	public String getModelloDiGovernance() {
		return modelloDiGovernance;
	}

	public void setModelloDiGovernance(String modelloDiGovernance) {
		this.modelloDiGovernance = modelloDiGovernance;
	}

	public Long getnComponentiCDA() {
		return nComponentiCDA;
	}

	public void setnComponentiCDA(Long nComponentiCDA) {
		this.nComponentiCDA = nComponentiCDA;
	}

	public String getnComponentiCollegioSindacale() {
		return nComponentiCollegioSindacale;
	}

	public void setnComponentiCollegioSindacale(String nComponentiCollegioSindacale) {
		this.nComponentiCollegioSindacale = nComponentiCollegioSindacale;
	}

	public String getnComponentiOdv() {
		return nComponentiOdv;
	}

	public void setnComponentiOdv(String nComponentiOdv) {
		this.nComponentiOdv = nComponentiOdv;
	}

	public String getCodiceNazione() {
		return codiceNazione;
	}

	public void setCodiceNazione(String codiceNazione) {
		this.codiceNazione = codiceNazione;
	}

	public String getSocietaDiRevisione() {
		return societaDiRevisione;
	}

	public void setSocietaDiRevisione(String societaDiRevisione) {
		this.societaDiRevisione = societaDiRevisione;
	}

	public Date getDataIncarico() {
		return dataIncarico;
	}

	public void setDataIncarico(Date dataIncarico) {
		this.dataIncarico = dataIncarico;
	}

	public Long getIdControllante() {
		return idControllante;
	}

	public void setIdControllante(Long idControllante) {
		this.idControllante = idControllante;
	}

	public Long getPercentualeControllante() {
		return percentualeControllante;
	}

	public void setPercentualeControllante(Long percentualeControllante) {
		this.percentualeControllante = percentualeControllante;
	}

	public Long getPercentualeTerzi() {
		return percentualeTerzi;
	}

	public void setPercentualeTerzi(Long percentualeTerzi) {
		this.percentualeTerzi = percentualeTerzi;
	}
	
	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Set<RSocietaAffari> getRSocietaAffaris() {
		return RSocietaAffaris;
	}

	public void setRSocietaAffaris(Set<RSocietaAffari> rSocietaAffaris) {
		RSocietaAffaris = rSocietaAffaris;
	}

	
}