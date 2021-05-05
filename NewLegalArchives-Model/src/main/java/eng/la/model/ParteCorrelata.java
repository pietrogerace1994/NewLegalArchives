package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the PARTE_CORRELATA database table.
 * 
 */
@Entity
@Table(name="PARTE_CORRELATA")
@NamedQuery(name="ParteCorrelata.findAll", query="SELECT p FROM ParteCorrelata p")
public class ParteCorrelata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PARTE_CORRELATA_ID_GENERATOR", sequenceName="PARTE_CORRELATA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PARTE_CORRELATA_ID_GENERATOR")
	private long id;

	@Column(name="PARTITA_IVA")
	private String partitaIva;
	
	@Column(name="COD_FISCALE")
	private String codFiscale;

	@Column(name="CONSIGLIERI_SINDACI")
	private String consiglieriSindaci;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;

	private String denominazione;

	private String familiare;

	private String lang;

	private String rapporto;

	//bi-directional many-to-one association to Nazione
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione;

	//bi-directional many-to-one association to TipoCorrelazione
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_CORRELAZIONE")
	private TipoCorrelazione tipoCorrelazione;

	public ParteCorrelata() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConsiglieriSindaci() {
		return this.consiglieriSindaci;
	}

	public void setConsiglieriSindaci(String consiglieriSindaci) {
		this.consiglieriSindaci = consiglieriSindaci;
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

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getFamiliare() {
		return this.familiare;
	}

	public void setFamiliare(String familiare) {
		this.familiare = familiare;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getRapporto() {
		return this.rapporto;
	}

	public void setRapporto(String rapporto) {
		this.rapporto = rapporto;
	}

	public Nazione getNazione() {
		return this.nazione;
	}

	public void setNazione(Nazione nazione) {
		this.nazione = nazione;
	}

	public TipoCorrelazione getTipoCorrelazione() {
		return this.tipoCorrelazione;
	}

	public void setTipoCorrelazione(TipoCorrelazione tipoCorrelazione) {
		this.tipoCorrelazione = tipoCorrelazione;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

}