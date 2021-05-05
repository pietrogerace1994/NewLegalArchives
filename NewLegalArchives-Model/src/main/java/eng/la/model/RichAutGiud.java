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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the RICH_AUT_GIUD database table.
 * 
 */
@Entity
@Table(name="RICH_AUT_GIUD")
@NamedQuery(name="RichAutGiud.findAll", query="SELECT r FROM RichAutGiud r")
public class RichAutGiud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RICH_AUT_GIUD_ID_GENERATOR", sequenceName="RICH_AUT_GIUD_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="RICH_AUT_GIUD_ID_GENERATOR")
	private long id;

	@Column(name="AUTORITA_GIUDIZIARIA")
	private String autoritaGiudiziaria;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICEZIONE")
	private Date dataRicezione;

	@Column(name="OGGETTO")
	private String oggetto;

	@Column(name="FORNITORE")
	private String fornitore;
	
	@ManyToOne()
	@JoinColumn(name="ID_TIPOLOGIA_RICHIESTA")
	private StatoRichAutGiud statoRichAutGiud;

	@ManyToOne()
	@JoinColumn(name="ID_STATO_RICH_AUT_GIUD")
	private TipologiaRichiesta tipologiaRichiesta;
	
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO_STEP1")
	private Documento documentoStep1;
	
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO_STEP2")
	private Documento documentoStep2;
	
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO_STEP3")
	private Documento documentoStep3;
	
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA")
	private Societa societa;

	public RichAutGiud() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAutoritaGiudiziaria() {
		return this.autoritaGiudiziaria;
	}

	public void setAutoritaGiudiziaria(String autoritaGiudiziaria) {
		this.autoritaGiudiziaria = autoritaGiudiziaria;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataInserimento() {
		return this.dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataRicezione() {
		return this.dataRicezione;
	}

	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public StatoRichAutGiud getStatoRichAutGiud() {
		return this.statoRichAutGiud;
	}

	public void setStatoRichAutGiud(StatoRichAutGiud statoRichAutGiud) {
		this.statoRichAutGiud = statoRichAutGiud;
	}

	public TipologiaRichiesta getTipologiaRichiesta() {
		return this.tipologiaRichiesta;
	}

	public void setTipologiaRichiesta(TipologiaRichiesta tipologiaRichiesta) {
		this.tipologiaRichiesta = tipologiaRichiesta;
	}

	public Documento getDocumentoStep1() {
		return documentoStep1;
	}

	public void setDocumentoStep1(Documento documentoStep1) {
		this.documentoStep1 = documentoStep1;
	}

	public Documento getDocumentoStep2() {
		return documentoStep2;
	}

	public void setDocumentoStep2(Documento documentoStep2) {
		this.documentoStep2 = documentoStep2;
	}

	public Documento getDocumentoStep3() {
		return documentoStep3;
	}

	public void setDocumentoStep3(Documento documentoStep3) {
		this.documentoStep3 = documentoStep3;
	}

	public Societa getSocieta() {
		return societa;
	}

	public void setSocieta(Societa societa) {
		this.societa = societa;
	}

	public String getFornitore() {
		return fornitore;
	}

	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}
	
	
	
}