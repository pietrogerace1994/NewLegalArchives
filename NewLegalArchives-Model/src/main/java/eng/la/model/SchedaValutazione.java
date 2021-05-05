package eng.la.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SCHEDA_VALUTAZIONE database table.
 * 
 */
@Entity
@Table(name="SCHEDA_VALUTAZIONE")
@NamedQuery(name="SchedaValutazione.findAll", query="SELECT s FROM SchedaValutazione s")
public class SchedaValutazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SCHEDA_VALUTAZIONE_ID_GENERATOR", sequenceName="SCHEDA_VALUTAZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SCHEDA_VALUTAZIONE_ID_GENERATOR")
	private long id;

	@Column(name="CONGRUO_RISP_LEGGE")
	private String congruoRispLegge;

	@Column(name="CONGRUO_RISP_LETT_INC")
	private String congruoRispLettInc;

	@Column(name="CONGRUO_RISP_PAR_FOR")
	private String congruoRispParFor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	private String lang;

	@Column(name="LEGALE_INTERNO")
	private String legaleInterno;

	private String note;

	@Column(name="SOLO_OGG_LETT_INC")
	private String soloOggLettInc;

	@Column(name="UNITA_LEGALE")
	private String unitaLegale;

	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO")
	private Documento documento;
	
	@Column(name="INVIATA")
	private Integer inviata;
	
	@Column(name="DATA_INVIO")
	private Date dataInvio;
	
	@Lob
	@Column(name="LIFECYCLE_XML")
	private String lifecycleXml;
	
	@Column(name="RETURN_FILE")
	@Lob
	private Blob returnFile;
	
	@Column(name="RETURN_FILE_NAME")
	private String returnFileName;
	
	@Column(name="AUTORITA")
	private String autorita;
	
	@Column(name="VALORE_INCARICO")
	private String valoreIncarico;
	
	//bi-directional many-to-one association to Proforma
	@OneToMany(mappedBy="schedaValutazione")
	private Set<Proforma> proformas;

	public SchedaValutazione() {
	}

	
	
	public Blob getReturnFile() {
		return returnFile;
	}



	public void setReturnFile(Blob returnFile) {
		this.returnFile = returnFile;
	}



	public String getReturnFileName() {
		return returnFileName;
	}



	public void setReturnFileName(String returnFileName) {
		this.returnFileName = returnFileName;
	}



	public Integer getInviata() {
		return inviata;
	}



	public void setInviata(Integer inviata) {
		this.inviata = inviata;
	}



	public Date getDataInvio() {
		return dataInvio;
	}



	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}



	public String getLifecycleXml() {
		return lifecycleXml;
	}



	public void setLifecycleXml(String lifecycleXml) {
		this.lifecycleXml = lifecycleXml;
	}



	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getCongruoRispLegge() {
		return this.congruoRispLegge;
	}

	public void setCongruoRispLegge(String congruoRispLegge) {
		this.congruoRispLegge = congruoRispLegge;
	}

	public String getCongruoRispLettInc() {
		return this.congruoRispLettInc;
	}

	public void setCongruoRispLettInc(String congruoRispLettInc) {
		this.congruoRispLettInc = congruoRispLettInc;
	}

	public String getCongruoRispParFor() {
		return this.congruoRispParFor;
	}

	public void setCongruoRispParFor(String congruoRispParFor) {
		this.congruoRispParFor = congruoRispParFor;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLegaleInterno() {
		return this.legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSoloOggLettInc() {
		return this.soloOggLettInc;
	}

	public void setSoloOggLettInc(String soloOggLettInc) {
		this.soloOggLettInc = soloOggLettInc;
	}

	public String getUnitaLegale() {
		return this.unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}

	public Set<Proforma> getProformas() {
		return this.proformas;
	}

	public void setProformas(Set<Proforma> proformas) {
		this.proformas = proformas;
	}

	public Proforma addProforma(Proforma proforma) {
		getProformas().add(proforma);
		proforma.setSchedaValutazione(this);

		return proforma;
	}

	public Proforma removeProforma(Proforma proforma) {
		getProformas().remove(proforma);
		proforma.setSchedaValutazione(null);
		return proforma;
	}

	public String getAutorita() {
		return autorita;
	}

	public void setAutorita(String autorita) {
		this.autorita = autorita;
	}

	public String getValoreIncarico() {
		return valoreIncarico;
	}

	public void setValoreIncarico(String valoreIncarico) {
		this.valoreIncarico = valoreIncarico;
	}

}