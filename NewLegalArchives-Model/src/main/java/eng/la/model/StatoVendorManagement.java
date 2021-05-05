package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_VENDOR_MANAGEMENT database table.
 * 
 */
@Entity
@Table(name="STATO_VENDOR_MANAGEMENT")
@NamedQuery(name="StatoVendorManagement.findAll", query="SELECT s FROM StatoVendorManagement s")
public class StatoVendorManagement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_VENDOR_MANAGEMENT_ID_GENERATOR", sequenceName="STATO_VENDOR_MANAGEMENT_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_VENDOR_MANAGEMENT_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to SchedaFondoRischi
	@OneToMany(mappedBy="statoVendorManagement")
	private Set<VendorManagement> votazioni;

	public StatoVendorManagement() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodGruppoLingua() {
		return this.codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	@AuditedObjectName
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Set<VendorManagement> getVotazioni() {
		return this.votazioni;
	}

	public void setVotazioni(Set<VendorManagement> votazioni) {
		this.votazioni = votazioni;
	}

	public VendorManagement addVendorManagement(VendorManagement vendorManagement) {
		getVotazioni().add(vendorManagement);
		vendorManagement.setStatoVendorManagement(this);

		return vendorManagement;
	}

	public VendorManagement removeVendorManagement(VendorManagement vendorManagement) {
		getVotazioni().remove(vendorManagement);
		vendorManagement.setStatoVendorManagement(null);

		return vendorManagement;
	}

}