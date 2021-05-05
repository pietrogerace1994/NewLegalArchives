package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedAttribute;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the VENDOR_MANAGEMENT database table.
 * 
 */
@Entity
@Table(name="VENDOR_MANAGEMENT")
@NamedQuery(name="VendorManagement.findAll", query="SELECT v FROM VendorManagement v")
public class VendorManagement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VENDOR_MANAGEMENT_ID_GENERATOR", sequenceName="VENDOR_MANAGEMENT_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="VENDOR_MANAGEMENT_ID_GENERATOR")
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_VALUTAZIONE")
	private Date dataValutazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="INATTIVITA_PROFESSIONISTA")
	private String inattivitaProfessionista;

	private String lang;

	private String note;

	@Column(name="VALUTAZIONE_AUTOREVOLEZZA")
	private BigDecimal valutazioneAutorevolezza;

	@Column(name="VALUTAZIONE_CAPACITA")
	private BigDecimal valutazioneCapacita;

	@Column(name="VALUTAZIONE_COMPETENZE")
	private BigDecimal valutazioneCompetenze;

	@Column(name="VALUTAZIONE_COSTI")
	private BigDecimal valutazioneCosti;

	@Column(name="VALUTAZIONE_FLESSIBILITA")
	private BigDecimal valutazioneFlessibilita;

	@Column(name="VALUTAZIONE_TEMPI")
	private BigDecimal valutazioneTempi;
	
	@Column(name="VALUTAZIONE_REPERIBILITA")
	private BigDecimal valutazioneReperibilita;
	
	@Column(name="VALUTAZIONE")
	private BigDecimal valutazione;

	//bi-directional many-to-one association to Criterio
	@ManyToOne()
	@JoinColumn(name="ID_CRITERIO")
	private Criterio criterio;

	//bi-directional many-to-one association to Incarico
	@ManyToOne()
	@JoinColumn(name="ID_INCARICO")
	private Incarico incarico;
	
	//bi-directional many-to-one association to Nazione
	@ManyToOne()
	@JoinColumn(name="ID_NAZIONE")
	private Nazione nazione; 
	
	//bi-directional many-to-one association to Specializzazione
	@ManyToOne()
	@JoinColumn(name="ID_SPECIALIZZAZIONE")
	private Specializzazione specializzazione;
	
	//bi-directional many-to-one association to StatoVendorManagement
	@AuditedAttribute(classType=StatoVendorManagement.class)
	@ManyToOne()
	@JoinColumn(name="ID_STATO_VENDOR_MANAGEMENT")
	private StatoVendorManagement statoVendorManagement;

	public VendorManagement() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getInattivitaProfessionista() {
		return this.inattivitaProfessionista;
	}

	public void setInattivitaProfessionista(String inattivitaProfessionista) {
		this.inattivitaProfessionista = inattivitaProfessionista;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getValutazioneAutorevolezza() {
		return this.valutazioneAutorevolezza;
	}

	public void setValutazioneAutorevolezza(BigDecimal valutazioneAutorevolezza) {
		this.valutazioneAutorevolezza = valutazioneAutorevolezza;
	}

	public BigDecimal getValutazioneCapacita() {
		return this.valutazioneCapacita;
	}

	public void setValutazioneCapacita(BigDecimal valutazioneCapacita) {
		this.valutazioneCapacita = valutazioneCapacita;
	}

	public BigDecimal getValutazioneCompetenze() {
		return this.valutazioneCompetenze;
	}

	public void setValutazioneCompetenze(BigDecimal valutazioneCompetenze) {
		this.valutazioneCompetenze = valutazioneCompetenze;
	}

	public BigDecimal getValutazioneCosti() {
		return this.valutazioneCosti;
	}

	public void setValutazioneCosti(BigDecimal valutazioneCosti) {
		this.valutazioneCosti = valutazioneCosti;
	}

	public BigDecimal getValutazioneFlessibilita() {
		return this.valutazioneFlessibilita;
	}

	public void setValutazioneFlessibilita(BigDecimal valutazioneFlessibilita) {
		this.valutazioneFlessibilita = valutazioneFlessibilita;
	}

	public BigDecimal getValutazioneTempi() {
		return this.valutazioneTempi;
	}

	public void setValutazioneTempi(BigDecimal valutazioneTempi) {
		this.valutazioneTempi = valutazioneTempi;
	}
	
	public BigDecimal getValutazioneReperibilita() {
		return this.valutazioneReperibilita;
	}

	public void setValutazioneReperibilita(BigDecimal valutazioneReperibilita) {
		this.valutazioneReperibilita = valutazioneReperibilita;
	}
	
	public Criterio getCriterio() {
		return this.criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

	public Incarico getIncarico() {
		return this.incarico;
	}

	public void setIncarico(Incarico incarico) {
		this.incarico = incarico;
	}

	public BigDecimal getValutazione() {
		return valutazione;
	}

	public void setValutazione(BigDecimal valutazione) {
		this.valutazione = valutazione;
	}

	public Date getDataValutazione() {
		return dataValutazione;
	}

	public void setDataValutazione(Date dataValutazione) {
		this.dataValutazione = dataValutazione;
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

	public StatoVendorManagement getStatoVendorManagement() {
		return statoVendorManagement;
	}

	public void setStatoVendorManagement(StatoVendorManagement statoVendorManagement) {
		this.statoVendorManagement = statoVendorManagement;
	}
	
	

}