package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_TIP_FASCICOLO_SETT_GIURIDICO database table.
 * 
 */
@Entity
@Table(name="R_TIP_FASCICOLO_SETT_GIURIDICO")
@NamedQuery(name="RTipFascicoloSettGiuridico.findAll", query="SELECT r FROM RTipFascicoloSettGiuridico r")
public class RTipFascicoloSettGiuridico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_TIP_FASCICOLO_SETT_GIURIDICO_ID_GENERATOR", sequenceName="R_TIP_FASCICOLO_SETT_GIURIDICO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_TIP_FASCICOLO_SETT_GIURIDICO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to SettoreGiuridico
	@ManyToOne()
	@JoinColumn(name="ID_SETTORE_GIURIDICO")
	private SettoreGiuridico settoreGiuridico;

	//bi-directional many-to-one association to TipologiaFascicolo
	@ManyToOne()
	@JoinColumn(name="ID_TIPOLOGIA_FASCICOLO")
	private TipologiaFascicolo tipologiaFascicolo;

	public RTipFascicoloSettGiuridico() {
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

	public SettoreGiuridico getSettoreGiuridico() {
		return this.settoreGiuridico;
	}

	public void setSettoreGiuridico(SettoreGiuridico settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

	public TipologiaFascicolo getTipologiaFascicolo() {
		return this.tipologiaFascicolo;
	}

	public void setTipologiaFascicolo(TipologiaFascicolo tipologiaFascicolo) {
		this.tipologiaFascicolo = tipologiaFascicolo;
	}

}