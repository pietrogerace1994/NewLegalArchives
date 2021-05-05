package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_VALUTA database table.
 * 
 */
@Entity
@Table(name="TIPO_VALUTA")
@NamedQuery(name="TipoValuta.findAll", query="SELECT t FROM TipoValuta t")
public class TipoValuta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_VALUTA_ID_GENERATOR", sequenceName="TIPO_VALUTA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_VALUTA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String nome;

	//bi-directional many-to-one association to Proforma
	@OneToMany(mappedBy="tipoValuta")
	private Set<Proforma> proformas;

	public TipoValuta() {
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Proforma> getProformas() {
		return this.proformas;
	}

	public void setProformas(Set<Proforma> proformas) {
		this.proformas = proformas;
	}

	public Proforma addProforma(Proforma proforma) {
		getProformas().add(proforma);
		proforma.setTipoValuta(this);

		return proforma;
	}

	public Proforma removeProforma(Proforma proforma) {
		getProformas().remove(proforma);
		proforma.setTipoValuta(null);

		return proforma;
	}

}