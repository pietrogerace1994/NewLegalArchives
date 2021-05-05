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
@Table(name="DOCUMENTO_PROGETTO")
public class DocumentoProgetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "ID")
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="ID_PROGETTO")
	private Progetto progetto;
		
	@ManyToOne()
	@JoinColumn(name="ID_DOCUMENTO")
	private Documento documento;
	
	@Column(name = "DATA_CANCELLAZIONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCancellazione;

	public DocumentoProgetto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
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