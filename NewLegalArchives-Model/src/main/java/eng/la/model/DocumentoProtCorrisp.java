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
 * The persistent class for the DOCUMENTO_PROT_CORRISP database table.
 * 
 */
@Entity
@Table(name="DOCUMENTO_PROT_CORRISP")
@NamedQuery(name="DocumentoProtCorrisp.findAll", query="SELECT d FROM DocumentoProtCorrisp d")
public class DocumentoProtCorrisp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOCUMENTO_PROT_CORRISP_ID_GENERATOR", sequenceName="DOCUMENTO_PROT_CORRISP_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="DOCUMENTO_PROT_CORRISP_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
 
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	@ManyToOne()
	@JoinColumn(name="ID_TIPO_CATEG_DOCUMENTALE")
	private TipoCategDocumentale tipoCategDocumentale;
	
	@ManyToOne()
	@JoinColumn(name="DOCUMENTO_ID")
	private Documento documento;
	
	public DocumentoProtCorrisp() {
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

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Fascicolo getFascicolo() {
		return fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public TipoCategDocumentale getTipoCategDocumentale() {
		return tipoCategDocumentale;
	}

	public void setTipoCategDocumentale(TipoCategDocumentale tipoCategDocumentale) {
		this.tipoCategDocumentale = tipoCategDocumentale;
	}

 

}