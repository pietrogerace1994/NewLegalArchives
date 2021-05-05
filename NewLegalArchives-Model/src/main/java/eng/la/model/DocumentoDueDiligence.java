package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the DOCUMENTO_DUE_DILIGENCE database table.
 */
@Entity
@Table(name="DOCUMENTO_DUE_DILIGENCE")
public class DocumentoDueDiligence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "ID")
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="ID_DUE_DILIGENCE")
	private DueDiligence dueDiligence;
		
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO")
	private Documento documento;
	
	@Column(name = "DATA_CANCELLAZIONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancellazione;

	public DocumentoDueDiligence() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DueDiligence getDueDiligence() {
		return dueDiligence;
	}

	public void setDueDiligence(DueDiligence dueDiligence) {
		this.dueDiligence = dueDiligence;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	
}