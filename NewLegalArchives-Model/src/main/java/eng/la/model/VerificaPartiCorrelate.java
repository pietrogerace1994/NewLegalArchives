package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the VERIFICA_PARTI_CORRELATE database table.
 * 
 */
@Entity
@Table(name="VERIFICA_PARTI_CORRELATE")
@NamedQuery(name="VerificaPartiCorrelate.findAll", query="SELECT v FROM VerificaPartiCorrelate v")
public class VerificaPartiCorrelate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="VERIFICA_PARTI_CORRELATE_ID_GENERATOR", sequenceName="VERIFICA_PARTI_CORRELATE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="VERIFICA_PARTI_CORRELATE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@ManyToOne()
	@JoinColumn(name="DOCUMENTO_ID")
	private Documento documento;
	
	//bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy="verificaPartiCorrelate")
	private Set<Incarico> incaricos;

	public VerificaPartiCorrelate() {
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

	public Set<Incarico> getIncaricos() {
		return this.incaricos;
	}

	public void setIncaricos(Set<Incarico> incaricos) {
		this.incaricos = incaricos;
	}

	public Incarico addIncarico(Incarico incarico) {
		getIncaricos().add(incarico);
		incarico.setVerificaPartiCorrelate(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setVerificaPartiCorrelate(null);

		return incarico;
	}

}