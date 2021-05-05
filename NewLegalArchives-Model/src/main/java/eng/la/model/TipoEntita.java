package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the TIPO_ENTITA database table.
 * 
 */
@Entity
@Table(name="TIPO_ENTITA")
@NamedQuery(name="TipoEntita.findAll", query="SELECT t FROM TipoEntita t")
public class TipoEntita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_ENTITA_ID_GENERATOR", sequenceName="TIPO_ENTITA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_ENTITA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to Controparte
	@OneToMany(mappedBy="tipoEntita")
	private Set<Controparte> contropartes;

	//bi-directional many-to-one association to ParteCivile
	@OneToMany(mappedBy="tipoEntita")
	private Set<ParteCivile> parteCiviles;

	//bi-directional many-to-one association to PersonaOffesa
	@OneToMany(mappedBy="tipoEntita")
	private Set<PersonaOffesa> personaOffesas;

	//bi-directional many-to-one association to ResponsabileCivile
	@OneToMany(mappedBy="tipoEntita")
	private Set<ResponsabileCivile> responsabileCiviles;

	//bi-directional many-to-one association to TerzoChiamatoCausa
	@OneToMany(mappedBy="tipoEntita")
	private Set<TerzoChiamatoCausa> terzoChiamatoCausas;

	public TipoEntita() {
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

	public Set<Controparte> getContropartes() {
		return this.contropartes;
	}

	public void setContropartes(Set<Controparte> contropartes) {
		this.contropartes = contropartes;
	}

	public Controparte addControparte(Controparte controparte) {
		getContropartes().add(controparte);
		controparte.setTipoEntita(this);

		return controparte;
	}

	public Controparte removeControparte(Controparte controparte) {
		getContropartes().remove(controparte);
		controparte.setTipoEntita(null);

		return controparte;
	}

	public Set<ParteCivile> getParteCiviles() {
		return this.parteCiviles;
	}

	public void setParteCiviles(Set<ParteCivile> parteCiviles) {
		this.parteCiviles = parteCiviles;
	}

	public ParteCivile addParteCivile(ParteCivile parteCivile) {
		getParteCiviles().add(parteCivile);
		parteCivile.setTipoEntita(this);

		return parteCivile;
	}

	public ParteCivile removeParteCivile(ParteCivile parteCivile) {
		getParteCiviles().remove(parteCivile);
		parteCivile.setTipoEntita(null);

		return parteCivile;
	}

	public Set<PersonaOffesa> getPersonaOffesas() {
		return this.personaOffesas;
	}

	public void setPersonaOffesas(Set<PersonaOffesa> personaOffesas) {
		this.personaOffesas = personaOffesas;
	}

	public PersonaOffesa addPersonaOffesa(PersonaOffesa personaOffesa) {
		getPersonaOffesas().add(personaOffesa);
		personaOffesa.setTipoEntita(this);

		return personaOffesa;
	}

	public PersonaOffesa removePersonaOffesa(PersonaOffesa personaOffesa) {
		getPersonaOffesas().remove(personaOffesa);
		personaOffesa.setTipoEntita(null);

		return personaOffesa;
	}

	public Set<ResponsabileCivile> getResponsabileCiviles() {
		return this.responsabileCiviles;
	}

	public void setResponsabileCiviles(Set<ResponsabileCivile> responsabileCiviles) {
		this.responsabileCiviles = responsabileCiviles;
	}

	public ResponsabileCivile addResponsabileCivile(ResponsabileCivile responsabileCivile) {
		getResponsabileCiviles().add(responsabileCivile);
		responsabileCivile.setTipoEntita(this);

		return responsabileCivile;
	}

	public ResponsabileCivile removeResponsabileCivile(ResponsabileCivile responsabileCivile) {
		getResponsabileCiviles().remove(responsabileCivile);
		responsabileCivile.setTipoEntita(null);

		return responsabileCivile;
	}

	public Set<TerzoChiamatoCausa> getTerzoChiamatoCausas() {
		return this.terzoChiamatoCausas;
	}

	public void setTerzoChiamatoCausas(Set<TerzoChiamatoCausa> terzoChiamatoCausas) {
		this.terzoChiamatoCausas = terzoChiamatoCausas;
	}

	public TerzoChiamatoCausa addTerzoChiamatoCausa(TerzoChiamatoCausa terzoChiamatoCausa) {
		getTerzoChiamatoCausas().add(terzoChiamatoCausa);
		terzoChiamatoCausa.setTipoEntita(this);

		return terzoChiamatoCausa;
	}

	public TerzoChiamatoCausa removeTerzoChiamatoCausa(TerzoChiamatoCausa terzoChiamatoCausa) {
		getTerzoChiamatoCausas().remove(terzoChiamatoCausa);
		terzoChiamatoCausa.setTipoEntita(null);

		return terzoChiamatoCausa;
	}

}