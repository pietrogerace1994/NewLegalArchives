package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_PROFORMA_FATTURA database table.
 * 
 */
@Entity
@Table(name="R_PROFORMA_FATTURA")
@NamedQuery(name="RProformaFattura.findAll", query="SELECT r FROM RProformaFattura r")
public class RProformaFattura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_PROFORMA_FATTURA_ID_GENERATOR", sequenceName="R_PROFORMA_FATTURA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_PROFORMA_FATTURA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Fattura
	@ManyToOne()
	@JoinColumn(name="ID_FATTURA")
	private Fattura fattura;

	//bi-directional many-to-one association to Proforma
	@ManyToOne()
	@JoinColumn(name="ID_PROFORMA")
	private Proforma proforma;

	public RProformaFattura() {
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

	public Fattura getFattura() {
		return this.fattura;
	}

	public void setFattura(Fattura fattura) {
		this.fattura = fattura;
	}

	public Proforma getProforma() {
		return this.proforma;
	}

	public void setProforma(Proforma proforma) {
		this.proforma = proforma;
	}

}