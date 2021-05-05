package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the GIUDIZIO database table.
 * 
 */
@Entity
@NamedQuery(name="Giudizio.findAll", query="SELECT g FROM Giudizio g")
public class Giudizio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GIUDIZIO_ID_GENERATOR", sequenceName="GIUDIZIO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="GIUDIZIO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;
 
	//bi-directional many-to-one association to RGiudizioOrganoGiudicante
	@OneToMany(mappedBy="giudizio")
	private Set<RGiudizioOrganoGiudicante> RGiudizioOrganoGiudicantes;

	//bi-directional many-to-one association to RSettoreGiuridicoGiudizio
	@OneToMany(mappedBy="giudizio")
	private Set<RSettoreGiuridicoGiudizio> RSettoreGiuridicoGiudizios;

	public Giudizio() {
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
 
	public Set<RGiudizioOrganoGiudicante> getRGiudizioOrganoGiudicantes() {
		return this.RGiudizioOrganoGiudicantes;
	}

	public void setRGiudizioOrganoGiudicantes(Set<RGiudizioOrganoGiudicante> RGiudizioOrganoGiudicantes) {
		this.RGiudizioOrganoGiudicantes = RGiudizioOrganoGiudicantes;
	}

	public RGiudizioOrganoGiudicante addRGiudizioOrganoGiudicante(RGiudizioOrganoGiudicante RGiudizioOrganoGiudicante) {
		getRGiudizioOrganoGiudicantes().add(RGiudizioOrganoGiudicante);
		RGiudizioOrganoGiudicante.setGiudizio(this);

		return RGiudizioOrganoGiudicante;
	}

	public RGiudizioOrganoGiudicante removeRGiudizioOrganoGiudicante(RGiudizioOrganoGiudicante RGiudizioOrganoGiudicante) {
		getRGiudizioOrganoGiudicantes().remove(RGiudizioOrganoGiudicante);
		RGiudizioOrganoGiudicante.setGiudizio(null);

		return RGiudizioOrganoGiudicante;
	}

	public Set<RSettoreGiuridicoGiudizio> getRSettoreGiuridicoGiudizios() {
		return this.RSettoreGiuridicoGiudizios;
	}

	public void setRSettoreGiuridicoGiudizios(Set<RSettoreGiuridicoGiudizio> RSettoreGiuridicoGiudizios) {
		this.RSettoreGiuridicoGiudizios = RSettoreGiuridicoGiudizios;
	}

	public RSettoreGiuridicoGiudizio addRSettoreGiuridicoGiudizio(RSettoreGiuridicoGiudizio RSettoreGiuridicoGiudizio) {
		getRSettoreGiuridicoGiudizios().add(RSettoreGiuridicoGiudizio);
		RSettoreGiuridicoGiudizio.setGiudizio(this);

		return RSettoreGiuridicoGiudizio;
	}

	public RSettoreGiuridicoGiudizio removeRSettoreGiuridicoGiudizio(RSettoreGiuridicoGiudizio RSettoreGiuridicoGiudizio) {
		getRSettoreGiuridicoGiudizios().remove(RSettoreGiuridicoGiudizio);
		RSettoreGiuridicoGiudizio.setGiudizio(null);

		return RSettoreGiuridicoGiudizio;
	}

}