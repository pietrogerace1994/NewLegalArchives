package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the NAZIONE database table.
 * 
 */
@Entity
@NamedQuery(name="Nazione.findAll", query="SELECT n FROM Nazione n")
public class Nazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NAZIONE_ID_GENERATOR", sequenceName="NAZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="NAZIONE_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	@Column(name="SOLO_PARTE_CORRELATA")
	private String soloParteCorrelata;

	//bi-directional many-to-one association to Fascicolo
	@OneToMany(mappedBy="nazione")
	private Set<Fascicolo> fascicolos;

	//bi-directional many-to-one association to ParteCorrelata
	@OneToMany(mappedBy="nazione")
	private Set<ParteCorrelata> parteCorrelatas;

	//bi-directional many-to-one association to RProfessionistaNazione
	@OneToMany(mappedBy="nazione")
	private Set<RProfessionistaNazione> RProfessionistaNaziones;

	//bi-directional many-to-one association to Societa
	@OneToMany(mappedBy="nazione")
	private Set<Societa> societas;

	//bi-directional many-to-one association to StudioLegale
	@OneToMany(mappedBy="nazione")
	private Set<StudioLegale> studioLegales;

	public Nazione() {
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

	public String getSoloParteCorrelata() {
		return this.soloParteCorrelata;
	}

	public void setSoloParteCorrelata(String soloParteCorrelata) {
		this.soloParteCorrelata = soloParteCorrelata;
	}

	public Set<Fascicolo> getFascicolos() {
		return this.fascicolos;
	}

	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}

	public Fascicolo addFascicolo(Fascicolo fascicolo) {
		getFascicolos().add(fascicolo);
		fascicolo.setNazione(this);

		return fascicolo;
	}

	public Fascicolo removeFascicolo(Fascicolo fascicolo) {
		getFascicolos().remove(fascicolo);
		fascicolo.setNazione(null);

		return fascicolo;
	}

	public Set<ParteCorrelata> getParteCorrelatas() {
		return this.parteCorrelatas;
	}

	public void setParteCorrelatas(Set<ParteCorrelata> parteCorrelatas) {
		this.parteCorrelatas = parteCorrelatas;
	}

	public ParteCorrelata addParteCorrelata(ParteCorrelata parteCorrelata) {
		getParteCorrelatas().add(parteCorrelata);
		parteCorrelata.setNazione(this);

		return parteCorrelata;
	}

	public ParteCorrelata removeParteCorrelata(ParteCorrelata parteCorrelata) {
		getParteCorrelatas().remove(parteCorrelata);
		parteCorrelata.setNazione(null);

		return parteCorrelata;
	}

	public Set<RProfessionistaNazione> getRProfessionistaNaziones() {
		return this.RProfessionistaNaziones;
	}

	public void setRProfessionistaNaziones(Set<RProfessionistaNazione> RProfessionistaNaziones) {
		this.RProfessionistaNaziones = RProfessionistaNaziones;
	}

	public RProfessionistaNazione addRProfessionistaNazione(RProfessionistaNazione RProfessionistaNazione) {
		getRProfessionistaNaziones().add(RProfessionistaNazione);
		RProfessionistaNazione.setNazione(this);

		return RProfessionistaNazione;
	}

	public RProfessionistaNazione removeRProfessionistaNazione(RProfessionistaNazione RProfessionistaNazione) {
		getRProfessionistaNaziones().remove(RProfessionistaNazione);
		RProfessionistaNazione.setNazione(null);

		return RProfessionistaNazione;
	}

	public Set<Societa> getSocietas() {
		return this.societas;
	}

	public void setSocietas(Set<Societa> societas) {
		this.societas = societas;
	}

	public Societa addSocieta(Societa societa) {
		getSocietas().add(societa);
		societa.setNazione(this);

		return societa;
	}

	public Societa removeSocieta(Societa societa) {
		getSocietas().remove(societa);
		societa.setNazione(null);

		return societa;
	}

	public Set<StudioLegale> getStudioLegales() {
		return this.studioLegales;
	}

	public void setStudioLegales(Set<StudioLegale> studioLegales) {
		this.studioLegales = studioLegales;
	}

	public StudioLegale addStudioLegale(StudioLegale studioLegale) {
		getStudioLegales().add(studioLegale);
		studioLegale.setNazione(this);

		return studioLegale;
	}

	public StudioLegale removeStudioLegale(StudioLegale studioLegale) {
		getStudioLegales().remove(studioLegale);
		studioLegale.setNazione(null);

		return studioLegale;
	}

}