package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PROGETTO database table.
 */
@Entity
@Table(name="REPERTORIO_STANDARD")
public class RepertorioStandard extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REPERTORIO_STANDARD_ID_GENERATOR", sequenceName="REPERTORIO_STANDARD_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="REPERTORIO_STANDARD_ID_GENERATOR")
	private long id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="NOTE")
	private String nota;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;
	
	@Column(name="LANG")
	private String lingua;
	
	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;
	
	
	@ManyToOne()
	@JoinColumn(name="ID_UTENTE")
	private Utente utente;
	
	@Column(name="SOCIETA")
	private String societa;
	
	@ManyToOne()
	@JoinColumn(name="ID_LIVELLO_I")
	private PrimoLivelloAttribuzioni primoLivelloAttribuzioni;
	
	@ManyToOne()
	@JoinColumn(name="ID_LIVELLO_II")
	private SecondoLivelloAttribuzioni secondoLivelloAttribuzioni;
	
	@ManyToOne()
	@JoinColumn(name="ID_POSIZIONE_ORG")
	private PosizioneOrganizzativa posizioneOrganizzativa;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="repertorio")
	private DocumentoRepertorioStandard documentoRepertorioStandard;

	public long getId() {
		return id;
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

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}


	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public PrimoLivelloAttribuzioni getPrimoLivelloAttribuzioni() {
		return primoLivelloAttribuzioni;
	}

	public void setPrimoLivelloAttribuzioni(PrimoLivelloAttribuzioni primoLivelloAttribuzioni) {
		this.primoLivelloAttribuzioni = primoLivelloAttribuzioni;
	}

	public SecondoLivelloAttribuzioni getSecondoLivelloAttribuzioni() {
		return secondoLivelloAttribuzioni;
	}

	public void setSecondoLivelloAttribuzioni(SecondoLivelloAttribuzioni secondoLivelloAttribuzioni) {
		this.secondoLivelloAttribuzioni = secondoLivelloAttribuzioni;
	}

	public PosizioneOrganizzativa getPosizioneOrganizzativa() {
		return posizioneOrganizzativa;
	}

	public void setPosizioneOrganizzativa(PosizioneOrganizzativa posizioneOrganizzativa) {
		this.posizioneOrganizzativa = posizioneOrganizzativa;
	}
	
	

	

}