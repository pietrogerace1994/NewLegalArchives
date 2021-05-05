package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SUBCATEGORIA_TESSERE database table.
 * 
 */
@Entity
@Table(name="SUBCATEGORIA_TESSERE")
@NamedQuery(name="SubCategoriaTessere.findAll", query="SELECT t FROM SubCategoriaTessere t")
public class SubCategoriaTessere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SUBCATEGORIA_TESSERE_ID_GENERATOR", sequenceName="SUBCATEGORIA_TESSERE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SUBCATEGORIA_TESSERE_ID_GENERATOR")
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
	@OneToMany(mappedBy="subcategoria")
	private Set<RepertorioPoteri> repertorioPoteris;

	public SubCategoriaTessere() {
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

	public Set<RepertorioPoteri> getRepertorioPoteris() {
		return this.repertorioPoteris;
	}

	public void setRepertorioPoteris(Set<RepertorioPoteri> repertorioPoteris) {
		this.repertorioPoteris = repertorioPoteris;
	}

	public RepertorioPoteri addRepertorioPoteri(RepertorioPoteri repertorioPoteri) {
		getRepertorioPoteris().add(repertorioPoteri);
		repertorioPoteri.setSubcategoria(this);

		return repertorioPoteri;
	}

	public RepertorioPoteri removeRepertorioPoteri(RepertorioPoteri repertorioPoteri) {
		getRepertorioPoteris().remove(repertorioPoteri);
		repertorioPoteri.setSubcategoria(null);

		return repertorioPoteri;
	}

}