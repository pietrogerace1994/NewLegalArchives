package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the RICORSO database table.
 * 
 */
@Entity
@NamedQuery(name="Ricorso.findAll", query="SELECT r FROM Ricorso r")
public class Ricorso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RICORSO_ID_GENERATOR", sequenceName="RICORSO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="RICORSO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang; 

	//bi-directional many-to-one association to RRicorsoOrganoGiudicante
	@OneToMany(mappedBy="ricorso")
	private Set<RRicorsoOrganoGiudicante> RRicorsoOrganoGiudicantes;

	public Ricorso() {
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

	public Set<RRicorsoOrganoGiudicante> getRRicorsoOrganoGiudicantes() {
		return this.RRicorsoOrganoGiudicantes;
	}

	public void setRRicorsoOrganoGiudicantes(Set<RRicorsoOrganoGiudicante> RRicorsoOrganoGiudicantes) {
		this.RRicorsoOrganoGiudicantes = RRicorsoOrganoGiudicantes;
	}

	public RRicorsoOrganoGiudicante addRRicorsoOrganoGiudicante(RRicorsoOrganoGiudicante RRicorsoOrganoGiudicante) {
		getRRicorsoOrganoGiudicantes().add(RRicorsoOrganoGiudicante);
		RRicorsoOrganoGiudicante.setRicorso(this);

		return RRicorsoOrganoGiudicante;
	}

	public RRicorsoOrganoGiudicante removeRRicorsoOrganoGiudicante(RRicorsoOrganoGiudicante RRicorsoOrganoGiudicante) {
		getRRicorsoOrganoGiudicantes().remove(RRicorsoOrganoGiudicante);
		RRicorsoOrganoGiudicante.setRicorso(null);

		return RRicorsoOrganoGiudicante;
	}

}