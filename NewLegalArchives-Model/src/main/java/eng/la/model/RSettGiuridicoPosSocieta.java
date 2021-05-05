package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_SETT_GIURIDICO_POS_SOCIETA database table.
 * 
 */
@Entity
@Table(name="R_SETT_GIURIDICO_POS_SOCIETA")
@NamedQuery(name="RSettGiuridicoPosSocieta.findAll", query="SELECT r FROM RSettGiuridicoPosSocieta r")
public class RSettGiuridicoPosSocieta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_SETT_GIURIDICO_POS_SOCIETA_ID_GENERATOR", sequenceName="R_SETT_GIURIDICO_POS_SOCIETA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_SETT_GIURIDICO_POS_SOCIETA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to PosizioneSocieta
	@ManyToOne()
	@JoinColumn(name="ID_POSIZIONE_SOCIETA")
	private PosizioneSocieta posizioneSocieta;

	//bi-directional many-to-one association to SettoreGiuridico
	@ManyToOne()
	@JoinColumn(name="ID_SETTORE_GIURIDICO")
	private SettoreGiuridico settoreGiuridico;

	public RSettGiuridicoPosSocieta() {
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

	public PosizioneSocieta getPosizioneSocieta() {
		return this.posizioneSocieta;
	}

	public void setPosizioneSocieta(PosizioneSocieta posizioneSocieta) {
		this.posizioneSocieta = posizioneSocieta;
	}

	public SettoreGiuridico getSettoreGiuridico() {
		return this.settoreGiuridico;
	}

	public void setSettoreGiuridico(SettoreGiuridico settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

}