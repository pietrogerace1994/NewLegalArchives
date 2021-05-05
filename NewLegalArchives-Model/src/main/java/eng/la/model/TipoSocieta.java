package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_SOCIETA database table.
 * 
 */
@Entity
@Table(name="TIPO_SOCIETA")
@NamedQuery(name="TipoSocieta.findAll", query="SELECT t FROM TipoSocieta t")
public class TipoSocieta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_SOCIETA_ID_GENERATOR", sequenceName="TIPO_SOCIETA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_SOCIETA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to Societa
	@OneToMany(mappedBy="tipoSocieta")
	private Set<Societa> societas;

	public TipoSocieta() {
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

	public Set<Societa> getSocietas() {
		return this.societas;
	}

	public void setSocietas(Set<Societa> societas) {
		this.societas = societas;
	}

	public Societa addSocieta(Societa societa) {
		getSocietas().add(societa);
		societa.setTipoSocieta(this);

		return societa;
	}

	public Societa removeSocieta(Societa societa) {
		getSocietas().remove(societa);
		societa.setTipoSocieta(null);

		return societa;
	}

}