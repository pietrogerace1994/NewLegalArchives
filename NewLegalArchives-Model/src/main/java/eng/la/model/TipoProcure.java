package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_PROCURE database table.
 * 
 */
@Entity
@Table(name="TIPO_PROCURE")
@NamedQuery(name="TipoProcure.findAll", query="SELECT t FROM TipoProcure t")
public class TipoProcure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_PROCURE_ID_GENERATOR", sequenceName="TIPO_PROCURE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_PROCURE_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to Procure
	@OneToMany(mappedBy="tipoProcure")
	private Set<Procure> procuras;

	public TipoProcure() {
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

	public Set<Procure> getProcures() {
		return this.procuras;
	}

	public void setProcures(Set<Procure> procuras) {
		this.procuras = procuras;
	}

	public Procure addProcure(Procure procura) {
		getProcures().add(procura);
		procura.setTipoProcure(this);

		return procura;
	}

	public Procure removeProcure(Procure procura) {
		getProcures().remove(procura);
		procura.setTipoProcure(null);

		return procura;
	}

}