package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the VERIFICA_ANTICORRUZIONE database table.
 * 
 */
@Entity
@Table(name="VERIFICA_ANTICORRUZIONE")
@NamedQuery(name="VerificaAnticorruzione.findAll", query="SELECT v FROM VerificaAnticorruzione v")
public class VerificaAnticorruzione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VERIFICA_ANTICORRUZIONE_ID_GENERATOR", sequenceName="VERIFICA_ANTICORRUZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="VERIFICA_ANTICORRUZIONE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@ManyToOne()
	@JoinColumn(name="DOCUMENTO_ID")
	private Documento documento;
	
	//bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy="verificaAnticorruzione")
	private Set<Incarico> incaricos;

	public VerificaAnticorruzione() {
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

	public Set<Incarico> getIncaricos() {
		return this.incaricos;
	}

	public void setIncaricos(Set<Incarico> incaricos) {
		this.incaricos = incaricos;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Incarico addIncarico(Incarico incarico) {
		getIncaricos().add(incarico);
		incarico.setVerificaAnticorruzione(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setVerificaAnticorruzione(null);

		return incarico;
	}

}