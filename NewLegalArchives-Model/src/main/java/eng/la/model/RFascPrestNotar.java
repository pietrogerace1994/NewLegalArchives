package eng.la.model;
 
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_FASC_PREST_NOTAR database table.
 * 
 */
@Entity
@Table(name="R_FASC_PREST_NOTAR") 
public class RFascPrestNotar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_FASC_PREST_NOTAR_ID_GENERATOR", sequenceName="R_FASC_PREST_NOTAR_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_FASC_PREST_NOTAR_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to TipoPrestNotarile
	@ManyToOne()
	@JoinColumn(name="ID_TIPO_PRESTAZIONE_NOTARILE")
	private TipoPrestNotarile tipoPrestNotarile;

	public RFascPrestNotar() {
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

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public TipoPrestNotarile getTipoPrestNotarile() {
		return tipoPrestNotarile;
	}

	public void setTipoPrestNotarile(TipoPrestNotarile tipoPrestNotarile) {
		this.tipoPrestNotarile = tipoPrestNotarile;
	}
 

}