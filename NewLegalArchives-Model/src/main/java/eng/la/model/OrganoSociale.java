package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PROGETTO database table.
 */
@Entity
@Table(name="ORGANO_SOCIALE")
public class OrganoSociale extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORGANO_SOCIALE_ID_GENERATOR", sequenceName="ORGANO_SOCIALE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="ORGANO_SOCIALE_ID_GENERATOR")
	private long id;

	
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA")
	private AffariSocietari societaAffari;
	
	@ManyToOne()
	@JoinColumn(name="TIPO_ORGANO_SOCIALE")
	private TipoOrganoSociale tipoOrganoSociale;
	
	@Column(name="COGNOME")
	private String cognome;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="CARICA")
	private String carica;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_NOMINA")
	private Date dataNomina;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CESSAZIONE")
	private Date dataCessazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_SCADENZA")
	private Date dataScadenza;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ACCETTAZIONE_CARICA")
	private Date dataAccettazioneCarica;	
	
	@Column(name="EMOLUMENTO")
	private Long emolumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;	
	
	@Column(name="LUOGO_NASCITA")
	private String luogoNascita;


	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name="NOTE")
	private String note;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AffariSocietari getSocietaAffari() {
		return societaAffari;
	}

	public void setSocietaAffari(AffariSocietari societaAffari) {
		this.societaAffari = societaAffari;
	}

	public TipoOrganoSociale getTipoOrganoSociale() {
		return tipoOrganoSociale;
	}

	public void setTipoOrganoSociale(TipoOrganoSociale tipoOrganoSociale) {
		this.tipoOrganoSociale = tipoOrganoSociale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCarica() {
		return carica;
	}

	public void setCarica(String carica) {
		this.carica = carica;
	}

	public Date getDataNomina() {
		return dataNomina;
	}

	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}

	public Date getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataAccettazioneCarica() {
		return dataAccettazioneCarica;
	}

	public void setDataAccettazioneCarica(Date dataAccettazioneCarica) {
		this.dataAccettazioneCarica = dataAccettazioneCarica;
	}

	public Long getEmolumento() {
		return emolumento;
	}

	public void setEmolumento(Long emolumento) {
		this.emolumento = emolumento;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	
	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}	
	
	
	

}