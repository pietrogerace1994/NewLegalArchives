package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the CRITERIO database table.
 * 
 */
@Entity
@NamedQuery(name="Criterio.findAll", query="SELECT c FROM Criterio c")
public class Criterio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CRITERIO_ID_GENERATOR", sequenceName="CRITERIO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CRITERIO_ID_GENERATOR")
	private long id;

	private String attivo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="PERC_AUTOREVOLEZZA")
	private BigDecimal percAutorevolezza;

	@Column(name="PERC_CAPACITA")
	private BigDecimal percCapacita;

	@Column(name="PERC_COMPETENZE")
	private BigDecimal percCompetenze;

	@Column(name="PERC_COSTI")
	private BigDecimal percCosti;

	@Column(name="PERC_FLESSIBILITA")
	private BigDecimal percFlessibilita;

	@Column(name="PERC_TEMPI")
	private BigDecimal percTempi;
	
	@Column(name="PERC_REPERIBILITA")
	private BigDecimal percReperibilita;

	//bi-directional many-to-one association to VendorManagement
	@OneToMany(mappedBy="criterio")
	private Set<VendorManagement> vendorManagements;

	public Criterio() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAttivo() {
		return this.attivo;
	}

	public void setAttivo(String attivo) {
		this.attivo = attivo;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public BigDecimal getPercAutorevolezza() {
		return this.percAutorevolezza;
	}

	public void setPercAutorevolezza(BigDecimal percAutorevolezza) {
		this.percAutorevolezza = percAutorevolezza;
	}

	public BigDecimal getPercCapacita() {
		return this.percCapacita;
	}

	public void setPercCapacita(BigDecimal percCapacita) {
		this.percCapacita = percCapacita;
	}

	public BigDecimal getPercCompetenze() {
		return this.percCompetenze;
	}

	public void setPercCompetenze(BigDecimal percCompetenze) {
		this.percCompetenze = percCompetenze;
	}

	public BigDecimal getPercCosti() {
		return this.percCosti;
	}

	public void setPercCosti(BigDecimal percCosti) {
		this.percCosti = percCosti;
	}

	public BigDecimal getPercFlessibilita() {
		return this.percFlessibilita;
	}

	public void setPercFlessibilita(BigDecimal percFlessibilita) {
		this.percFlessibilita = percFlessibilita;
	}

	public BigDecimal getPercTempi() {
		return this.percTempi;
	}

	public void setPercTempi(BigDecimal percTempi) {
		this.percTempi = percTempi;
	}
	
	public BigDecimal getPercReperibilita() {
		return this.percReperibilita;
	}

	public void setPercReperibilita(BigDecimal percReperibilita) {
		this.percReperibilita = percReperibilita;
	}

	public Set<VendorManagement> getVendorManagements() {
		return this.vendorManagements;
	}

	public void setVendorManagements(Set<VendorManagement> vendorManagements) {
		this.vendorManagements = vendorManagements;
	}

	public VendorManagement addVendorManagement(VendorManagement vendorManagement) {
		getVendorManagements().add(vendorManagement);
		vendorManagement.setCriterio(this);

		return vendorManagement;
	}

	public VendorManagement removeVendorManagement(VendorManagement vendorManagement) {
		getVendorManagements().remove(vendorManagement);
		vendorManagement.setCriterio(null);

		return vendorManagement;
	}

	
	
	
}