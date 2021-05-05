package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_SETTORE_GIURIDICO_GIUDIZIO database table.
 * 
 */
@Entity
@Table(name="R_SETTORE_GIURIDICO_GIUDIZIO")
@NamedQuery(name="RSettoreGiuridicoGiudizio.findAll", query="SELECT r FROM RSettoreGiuridicoGiudizio r")
public class RSettoreGiuridicoGiudizio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_SETTORE_GIURIDICO_GIUDIZIO_ID_GENERATOR", sequenceName="R_SETTORE_GIURIDICO_GIUDIZIO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_SETTORE_GIURIDICO_GIUDIZIO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Giudizio
	@ManyToOne()
	@JoinColumn(name="ID_GIUDIZIO")
	private Giudizio giudizio;

	//bi-directional many-to-one association to SettoreGiuridico
	@ManyToOne()
	@JoinColumn(name="ID_SETTORE_GIURIDICO")
	private SettoreGiuridico settoreGiuridico;

	public RSettoreGiuridicoGiudizio() {
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

	public Giudizio getGiudizio() {
		return this.giudizio;
	}

	public void setGiudizio(Giudizio giudizio) {
		this.giudizio = giudizio;
	}

	public SettoreGiuridico getSettoreGiuridico() {
		return this.settoreGiuridico;
	}

	public void setSettoreGiuridico(SettoreGiuridico settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

}