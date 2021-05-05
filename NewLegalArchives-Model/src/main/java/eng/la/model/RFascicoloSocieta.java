package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_FASCICOLO_SOCIETA database table.
 * 
 */
@Entity
@Table(name="R_FASCICOLO_SOCIETA")
@NamedQuery(name="RFascicoloSocieta.findAll", query="SELECT r FROM RFascicoloSocieta r")
public class RFascicoloSocieta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_FASCICOLO_SOCIETA_ID_GENERATOR", sequenceName="R_FASCICOLO_SOCIETA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_FASCICOLO_SOCIETA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="TIPOLOGIA_SOCIETA")
	private String tipologiaSocieta;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to PosizioneSocieta
	@ManyToOne()
	@JoinColumn(name="ID_POSIZIONE_SOCIETA")
	private PosizioneSocieta posizioneSocieta;

	//bi-directional many-to-one association to Societa
	@ManyToOne()
	@JoinColumn(name="ID_SOCIETA")
	private Societa societa;

	public RFascicoloSocieta() {
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

	public String getTipologiaSocieta() {
		return this.tipologiaSocieta;
	}

	public void setTipologiaSocieta(String tipologiaSocieta) {
		this.tipologiaSocieta = tipologiaSocieta;
	}

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public PosizioneSocieta getPosizioneSocieta() {
		return this.posizioneSocieta;
	}

	public void setPosizioneSocieta(PosizioneSocieta posizioneSocieta) {
		this.posizioneSocieta = posizioneSocieta;
	}

	public Societa getSocieta() {
		return this.societa;
	}

	public void setSocieta(Societa societa) {
		this.societa = societa;
	}

}