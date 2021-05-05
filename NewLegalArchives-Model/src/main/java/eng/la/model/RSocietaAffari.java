package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_SOCIETA_AFFARI database table.
 * 
 */
@Entity
@Table(name="R_SOCIETA_AFFARI")
@NamedQuery(name="RSocietaAffari.findAll", query="SELECT r FROM RSocietaAffari r")
public class RSocietaAffari implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_SOCIETA_AFFARI_ID_GENERATOR", sequenceName="R_SOCIETA_AFFARI_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_SOCIETA_AFFARI_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Societa
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA_AFFARI")
	private AffariSocietari societaAffari;

	//bi-directional many-to-one association to Societa
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA_SOCIO")
	private AffariSocietari societaSocio;
	
	@Column(name="PERCENTUALE_SOCIO")
	private Long percentualeSocio;

	public RSocietaAffari() {
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

	public AffariSocietari getSocietaAffari() {
		return societaAffari;
	}

	public void setSocietaAffari(AffariSocietari societaAffari) {
		this.societaAffari = societaAffari;
	}

	public AffariSocietari getSocietaSocio() {
		return societaSocio;
	}

	public void setSocietaSocio(AffariSocietari societaSocio) {
		this.societaSocio = societaSocio;
	}

	public Long getPercentualeSocio() {
		return percentualeSocio;
	}

	public void setPercentualeSocio(Long percentualeSocio) {
		this.percentualeSocio = percentualeSocio;
	}



}