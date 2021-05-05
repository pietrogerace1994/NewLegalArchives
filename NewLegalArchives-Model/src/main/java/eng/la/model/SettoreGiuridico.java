package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the SETTORE_GIURIDICO database table.
 * 
 */
@Entity
@Table(name="SETTORE_GIURIDICO")
@NamedQuery(name="SettoreGiuridico.findAll", query="SELECT s FROM SettoreGiuridico s")
public class SettoreGiuridico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SETTORE_GIURIDICO_ID_GENERATOR", sequenceName="SETTORE_GIURIDICO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SETTORE_GIURIDICO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to CentroDiCosto
	@OneToMany(mappedBy="settoreGiuridico")
	private Set<CentroDiCosto> centroDiCostos;

	//bi-directional many-to-one association to Fascicolo
	@OneToMany(mappedBy="settoreGiuridico")
	private Set<Fascicolo> fascicolos;

	//bi-directional many-to-one association to RSettoreGiuridicoGiudizio
	@OneToMany(mappedBy="settoreGiuridico")
	private Set<RSettoreGiuridicoGiudizio> RSettoreGiuridicoGiudizios;

	//bi-directional many-to-one association to RSettoreGiuridicoMateria
	@OneToMany(mappedBy="settoreGiuridico")
	private Set<RSettoreGiuridicoMateria> RSettoreGiuridicoMaterias;

	//bi-directional many-to-one association to RSettGiuridicoPosSocieta
	@OneToMany(mappedBy="settoreGiuridico")
	private Set<RSettGiuridicoPosSocieta> RSettGiuridicoPosSocietas;

	//bi-directional many-to-one association to RTipFascicoloSettGiuridico
	@OneToMany(mappedBy="settoreGiuridico")
	private Set<RTipFascicoloSettGiuridico> RTipFascicoloSettGiuridicos;

	//bi-directional many-to-one association to VoceDiConto
	@OneToMany(mappedBy="settoreGiuridico")
	private Set<VoceDiConto> voceDiContos;

	public SettoreGiuridico() {
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

	public Set<CentroDiCosto> getCentroDiCostos() {
		return this.centroDiCostos;
	}

	public void setCentroDiCostos(Set<CentroDiCosto> centroDiCostos) {
		this.centroDiCostos = centroDiCostos;
	}

	public CentroDiCosto addCentroDiCosto(CentroDiCosto centroDiCosto) {
		getCentroDiCostos().add(centroDiCosto);
		centroDiCosto.setSettoreGiuridico(this);

		return centroDiCosto;
	}

	public CentroDiCosto removeCentroDiCosto(CentroDiCosto centroDiCosto) {
		getCentroDiCostos().remove(centroDiCosto);
		centroDiCosto.setSettoreGiuridico(null);

		return centroDiCosto;
	}

	public Set<Fascicolo> getFascicolos() {
		return this.fascicolos;
	}

	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}

	public Fascicolo addFascicolo(Fascicolo fascicolo) {
		getFascicolos().add(fascicolo);
		fascicolo.setSettoreGiuridico(this);

		return fascicolo;
	}

	public Fascicolo removeFascicolo(Fascicolo fascicolo) {
		getFascicolos().remove(fascicolo);
		fascicolo.setSettoreGiuridico(null);

		return fascicolo;
	}

	public Set<RSettoreGiuridicoGiudizio> getRSettoreGiuridicoGiudizios() {
		return this.RSettoreGiuridicoGiudizios;
	}

	public void setRSettoreGiuridicoGiudizios(Set<RSettoreGiuridicoGiudizio> RSettoreGiuridicoGiudizios) {
		this.RSettoreGiuridicoGiudizios = RSettoreGiuridicoGiudizios;
	}

	public RSettoreGiuridicoGiudizio addRSettoreGiuridicoGiudizio(RSettoreGiuridicoGiudizio RSettoreGiuridicoGiudizio) {
		getRSettoreGiuridicoGiudizios().add(RSettoreGiuridicoGiudizio);
		RSettoreGiuridicoGiudizio.setSettoreGiuridico(this);

		return RSettoreGiuridicoGiudizio;
	}

	public RSettoreGiuridicoGiudizio removeRSettoreGiuridicoGiudizio(RSettoreGiuridicoGiudizio RSettoreGiuridicoGiudizio) {
		getRSettoreGiuridicoGiudizios().remove(RSettoreGiuridicoGiudizio);
		RSettoreGiuridicoGiudizio.setSettoreGiuridico(null);

		return RSettoreGiuridicoGiudizio;
	}

	public Set<RSettoreGiuridicoMateria> getRSettoreGiuridicoMaterias() {
		return this.RSettoreGiuridicoMaterias;
	}

	public void setRSettoreGiuridicoMaterias(Set<RSettoreGiuridicoMateria> RSettoreGiuridicoMaterias) {
		this.RSettoreGiuridicoMaterias = RSettoreGiuridicoMaterias;
	}

	public RSettoreGiuridicoMateria addRSettoreGiuridicoMateria(RSettoreGiuridicoMateria RSettoreGiuridicoMateria) {
		getRSettoreGiuridicoMaterias().add(RSettoreGiuridicoMateria);
		RSettoreGiuridicoMateria.setSettoreGiuridico(this);

		return RSettoreGiuridicoMateria;
	}

	public RSettoreGiuridicoMateria removeRSettoreGiuridicoMateria(RSettoreGiuridicoMateria RSettoreGiuridicoMateria) {
		getRSettoreGiuridicoMaterias().remove(RSettoreGiuridicoMateria);
		RSettoreGiuridicoMateria.setSettoreGiuridico(null);

		return RSettoreGiuridicoMateria;
	}

	public Set<RSettGiuridicoPosSocieta> getRSettGiuridicoPosSocietas() {
		return this.RSettGiuridicoPosSocietas;
	}

	public void setRSettGiuridicoPosSocietas(Set<RSettGiuridicoPosSocieta> RSettGiuridicoPosSocietas) {
		this.RSettGiuridicoPosSocietas = RSettGiuridicoPosSocietas;
	}

	public RSettGiuridicoPosSocieta addRSettGiuridicoPosSocieta(RSettGiuridicoPosSocieta RSettGiuridicoPosSocieta) {
		getRSettGiuridicoPosSocietas().add(RSettGiuridicoPosSocieta);
		RSettGiuridicoPosSocieta.setSettoreGiuridico(this);

		return RSettGiuridicoPosSocieta;
	}

	public RSettGiuridicoPosSocieta removeRSettGiuridicoPosSocieta(RSettGiuridicoPosSocieta RSettGiuridicoPosSocieta) {
		getRSettGiuridicoPosSocietas().remove(RSettGiuridicoPosSocieta);
		RSettGiuridicoPosSocieta.setSettoreGiuridico(null);

		return RSettGiuridicoPosSocieta;
	}

	public Set<RTipFascicoloSettGiuridico> getRTipFascicoloSettGiuridicos() {
		return this.RTipFascicoloSettGiuridicos;
	}

	public void setRTipFascicoloSettGiuridicos(Set<RTipFascicoloSettGiuridico> RTipFascicoloSettGiuridicos) {
		this.RTipFascicoloSettGiuridicos = RTipFascicoloSettGiuridicos;
	}

	public RTipFascicoloSettGiuridico addRTipFascicoloSettGiuridico(RTipFascicoloSettGiuridico RTipFascicoloSettGiuridico) {
		getRTipFascicoloSettGiuridicos().add(RTipFascicoloSettGiuridico);
		RTipFascicoloSettGiuridico.setSettoreGiuridico(this);

		return RTipFascicoloSettGiuridico;
	}

	public RTipFascicoloSettGiuridico removeRTipFascicoloSettGiuridico(RTipFascicoloSettGiuridico RTipFascicoloSettGiuridico) {
		getRTipFascicoloSettGiuridicos().remove(RTipFascicoloSettGiuridico);
		RTipFascicoloSettGiuridico.setSettoreGiuridico(null);

		return RTipFascicoloSettGiuridico;
	}

	public Set<VoceDiConto> getVoceDiContos() {
		return this.voceDiContos;
	}

	public void setVoceDiContos(Set<VoceDiConto> voceDiContos) {
		this.voceDiContos = voceDiContos;
	}

	public VoceDiConto addVoceDiConto(VoceDiConto voceDiConto) {
		getVoceDiContos().add(voceDiConto);
		voceDiConto.setSettoreGiuridico(this);

		return voceDiConto;
	}

	public VoceDiConto removeVoceDiConto(VoceDiConto voceDiConto) {
		getVoceDiContos().remove(voceDiConto);
		voceDiConto.setSettoreGiuridico(null);

		return voceDiConto;
	}

}