package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the POSIZIONE_ORGANIZZATIVA database table.
 * 
 */
@Entity
@Table(name="POSIZIONE_ORGANIZZATIVA")
@NamedQuery(name="PosizioneOrganizzativa.findAll", query="SELECT t FROM PosizioneOrganizzativa t")
public class PosizioneOrganizzativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="POSIZIONE_ORGANIZZATIVA_ID_GENERATOR", sequenceName="POSIZIONE_ORGANIZZATIVA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="POSIZIONE_ORGANIZZATIVA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to RepertorioStandard
	@OneToMany(mappedBy="posizioneOrganizzativa")
	private Set<RepertorioStandard> repertorioStandards;

	public PosizioneOrganizzativa() {
	}

	public PosizioneOrganizzativa(Long idPosizioneOrganizzativa) {
		setId(idPosizioneOrganizzativa);
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

	public Set<RepertorioStandard> getRepertorioStandards() {
		return this.repertorioStandards;
	}

	public void setRepertorioStandards(Set<RepertorioStandard> repertorioStandards) {
		this.repertorioStandards = repertorioStandards;
	}

	public RepertorioStandard addRepertorioStandard(RepertorioStandard repertorioStandard) {
		getRepertorioStandards().add(repertorioStandard);
		repertorioStandard.setPosizioneOrganizzativa(this);

		return repertorioStandard;
	}

	public RepertorioStandard removeRepertorioStandard(RepertorioStandard repertorioStandard) {
		getRepertorioStandards().remove(repertorioStandard);
		repertorioStandard.setPosizioneOrganizzativa(null);

		return repertorioStandard;
	}

}