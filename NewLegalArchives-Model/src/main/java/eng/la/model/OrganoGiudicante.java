package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the ORGANO_GIUDICANTE database table.
 * 
 */
@Entity
@Table(name="ORGANO_GIUDICANTE")
@NamedQuery(name="OrganoGiudicante.findAll", query="SELECT o FROM OrganoGiudicante o")
public class OrganoGiudicante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORGANO_GIUDICANTE_ID_GENERATOR", sequenceName="ORGANO_GIUDICANTE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="ORGANO_GIUDICANTE_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;
 
	//bi-directional many-to-one association to RGiudizioOrganoGiudicante
	@OneToMany(mappedBy="organoGiudicante")
	private Set<RGiudizioOrganoGiudicante> RGiudizioOrganoGiudicantes;

	//bi-directional many-to-one association to RRicorsoOrganoGiudicante
	@OneToMany(mappedBy="organoGiudicante")
	private Set<RRicorsoOrganoGiudicante> RRicorsoOrganoGiudicantes;

	public OrganoGiudicante() {
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

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<RGiudizioOrganoGiudicante> getRGiudizioOrganoGiudicantes() {
		return this.RGiudizioOrganoGiudicantes;
	}

	public void setRGiudizioOrganoGiudicantes(Set<RGiudizioOrganoGiudicante> RGiudizioOrganoGiudicantes) {
		this.RGiudizioOrganoGiudicantes = RGiudizioOrganoGiudicantes;
	}

	public Set<RRicorsoOrganoGiudicante> getRRicorsoOrganoGiudicantes() {
		return this.RRicorsoOrganoGiudicantes;
	}

	public void setRRicorsoOrganoGiudicantes(Set<RRicorsoOrganoGiudicante> RRicorsoOrganoGiudicantes) {
		this.RRicorsoOrganoGiudicantes = RRicorsoOrganoGiudicantes;
	}



}