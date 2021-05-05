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


/**
 * The persistent class for the DUE_DILIGENCE database table.
 * 
 */
@Entity
@Table(name="DUE_DILIGENCE")
public class DueDiligence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DUE_DILIGENCE_ID_GENERATOR", sequenceName="DUE_DILIGENCE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="DUE_DILIGENCE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_APERTURA")
	private Date dataApertura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CHIUSURA")
	private Date dataChiusura;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROFESSIONISTA")
	private ProfessionistaEsterno professionistaEsterno;

	//bi-directional many-to-one association to StatoDueDiligence
	@ManyToOne()
	@JoinColumn(name="ID_STATO_DUE_DILIGENCE")
	private StatoDueDiligence statoDueDiligence;
	
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO_STEP1")
	private Documento documentoStep1;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="dueDiligence")
	private Set<DocumentoDueDiligence> documentoDueDiligences;
	
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO_STEP3")
	private Documento documentoStep3;

	public DueDiligence() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataApertura() {
		return this.dataApertura;
	}

	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataChiusura() {
		return this.dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public ProfessionistaEsterno getProfessionistaEsterno() {
		return this.professionistaEsterno;
	}

	public void setProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

	public StatoDueDiligence getStatoDueDiligence() {
		return this.statoDueDiligence;
	}

	public void setStatoDueDiligence(StatoDueDiligence statoDueDiligence) {
		this.statoDueDiligence = statoDueDiligence;
	}

	public Documento getDocumentoStep1() {
		return documentoStep1;
	}

	public void setDocumentoStep1(Documento documentoStep1) {
		this.documentoStep1 = documentoStep1;
	}

	public Set<DocumentoDueDiligence> getDocumentoDueDiligences() {
		return documentoDueDiligences;
	}

	public void setDocumentoDueDiligences(Set<DocumentoDueDiligence> documentoDueDiligences) {
		this.documentoDueDiligences = documentoDueDiligences;
	}

	public Documento getDocumentoStep3() {
		return documentoStep3;
	}

	public void setDocumentoStep3(Documento documentoStep3) {
		this.documentoStep3 = documentoStep3;
	}
	
}