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
 * The persistent class for the R_PROF_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="R_PROF_DOCUMENTO")
@NamedQuery(name="RProfDocumento.findAll", query="SELECT d FROM RProfDocumento d")
public class RProfDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_PROF_DOCUMENTO_ID_GENERATOR", sequenceName="R_PROF_DOCUMENTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_PROF_DOCUMENTO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
 
	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROFESSIONISTA_ESTERNO")
	private ProfessionistaEsterno professionistaEsterno;

	@ManyToOne()
	@JoinColumn(name="ID_TIPO_CATEG_DOCUMENTALE")
	private TipoCategDocumentale tipoCategDocumentale;
	
	@ManyToOne()
	@JoinColumn(name="DOCUMENTO_ID")
	private Documento documento;
	
	public RProfDocumento() {
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

	public TipoCategDocumentale getTipoCategDocumentale() {
		return tipoCategDocumentale;
	}

	public void setTipoCategDocumentale(TipoCategDocumentale tipoCategDocumentale) {
		this.tipoCategDocumentale = tipoCategDocumentale;
	}

	public ProfessionistaEsterno getProfessionistaEsterno() {
		return professionistaEsterno;
	}

	public void setProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

 

}