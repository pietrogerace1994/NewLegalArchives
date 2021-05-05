package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_ORGANO_SOCIALE database table.
 * 
 */
@Entity
@Table(name="TIPO_ORGANO_SOCIALE")
@NamedQuery(name="TipoOrganoSociale.findAll", query="SELECT t FROM TipoOrganoSociale t")
public class TipoOrganoSociale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_ORGANO_SOCIALE_ID_GENERATOR", sequenceName="TIPO_ORGANO_SOCIALE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_ORGANO_SOCIALE_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to RepertorioPoteri
	@OneToMany(mappedBy="tipoOrganoSociale")
	private Set<OrganoSociale> tipoOrganoSociale;

	public TipoOrganoSociale() {
	}

	public TipoOrganoSociale(Long id) {
		setId(id);
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
	
	public Set<OrganoSociale> getTipoOrganoSociale() {
		return tipoOrganoSociale;
	}

	public void setTipoOrganoSociale(Set<OrganoSociale> tipoOrganoSociale) {
		this.tipoOrganoSociale = tipoOrganoSociale;
	}

}