package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PROCURA database table.
 * 
 */
@Entity
@NamedQuery(name="Procura.findAll", query="SELECT p FROM Procura p")
public class Procura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROCURA_ID_GENERATOR", sequenceName="PROCURA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PROCURA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@ManyToOne()
	@JoinColumn(name="DOCUMENTO_ID")
	private Documento documento;
	
	//bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy="procura")
	private Set<Incarico> incaricos;

	public Procura() {
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
		incarico.setProcura(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setProcura(null);

		return incarico;
	}

}