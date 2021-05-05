package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_SETTORE_GIURIDICO_MATERIA database table.
 * 
 */
@Entity
@Table(name="R_SETTORE_GIURIDICO_MATERIA")
@NamedQuery(name="RSettoreGiuridicoMateria.findAll", query="SELECT r FROM RSettoreGiuridicoMateria r")
public class RSettoreGiuridicoMateria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="R_SETTORE_GIURIDICO_MATERIA_ID_GENERATOR", sequenceName="R_SETT_GIURIDICO_MATERIA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_SETTORE_GIURIDICO_MATERIA_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Materia
	@ManyToOne()
	@JoinColumn(name="ID_MATERIA")
	private Materia materia;

	//bi-directional many-to-one association to SettoreGiuridico
	@ManyToOne()
	@JoinColumn(name="ID_SETTORE_GIURIDICO")
	private SettoreGiuridico settoreGiuridico;

	public RSettoreGiuridicoMateria() {
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

	public Materia getMateria() {
		return this.materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public SettoreGiuridico getSettoreGiuridico() {
		return this.settoreGiuridico;
	}

	public void setSettoreGiuridico(SettoreGiuridico settoreGiuridico) {
		this.settoreGiuridico = settoreGiuridico;
	}

}