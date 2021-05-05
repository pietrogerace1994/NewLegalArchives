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
 * The persistent class for the TIPOLOGIA_FASCICOLO database table.
 * 
 */
@Entity
@Table(name="TIPOLOGIA_FASCICOLO")
@NamedQuery(name="TipologiaFascicolo.findAll", query="SELECT t FROM TipologiaFascicolo t")
public class TipologiaFascicolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOLOGIA_FASCICOLO_ID_GENERATOR", sequenceName="TIPOLOGIA_FASCICOLO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPOLOGIA_FASCICOLO_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to CentroDiCosto
	@OneToMany(mappedBy="tipologiaFascicolo")
	private Set<CentroDiCosto> centroDiCostos;

	//bi-directional many-to-one association to Fascicolo
	@OneToMany(mappedBy="tipologiaFascicolo")
	private Set<Fascicolo> fascicolos;

	//bi-directional many-to-one association to RTipFascicoloSettGiuridico
	@OneToMany(mappedBy="tipologiaFascicolo")
	private Set<RTipFascicoloSettGiuridico> RTipFascicoloSettGiuridicos;

	//bi-directional many-to-one association to VoceDiConto
	@OneToMany(mappedBy="tipologiaFascicolo")
	private Set<VoceDiConto> voceDiContos;

	public TipologiaFascicolo() {
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

	public Set<CentroDiCosto> getCentroDiCostos() {
		return this.centroDiCostos;
	}

	public void setCentroDiCostos(Set<CentroDiCosto> centroDiCostos) {
		this.centroDiCostos = centroDiCostos;
	}

	public CentroDiCosto addCentroDiCosto(CentroDiCosto centroDiCosto) {
		getCentroDiCostos().add(centroDiCosto);
		centroDiCosto.setTipologiaFascicolo(this);

		return centroDiCosto;
	}

	public CentroDiCosto removeCentroDiCosto(CentroDiCosto centroDiCosto) {
		getCentroDiCostos().remove(centroDiCosto);
		centroDiCosto.setTipologiaFascicolo(null);

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
		fascicolo.setTipologiaFascicolo(this);

		return fascicolo;
	}

	public Fascicolo removeFascicolo(Fascicolo fascicolo) {
		getFascicolos().remove(fascicolo);
		fascicolo.setTipologiaFascicolo(null);

		return fascicolo;
	}

	public Set<RTipFascicoloSettGiuridico> getRTipFascicoloSettGiuridicos() {
		return this.RTipFascicoloSettGiuridicos;
	}

	public void setRTipFascicoloSettGiuridicos(Set<RTipFascicoloSettGiuridico> RTipFascicoloSettGiuridicos) {
		this.RTipFascicoloSettGiuridicos = RTipFascicoloSettGiuridicos;
	}

	public RTipFascicoloSettGiuridico addRTipFascicoloSettGiuridico(RTipFascicoloSettGiuridico RTipFascicoloSettGiuridico) {
		getRTipFascicoloSettGiuridicos().add(RTipFascicoloSettGiuridico);
		RTipFascicoloSettGiuridico.setTipologiaFascicolo(this);

		return RTipFascicoloSettGiuridico;
	}

	public RTipFascicoloSettGiuridico removeRTipFascicoloSettGiuridico(RTipFascicoloSettGiuridico RTipFascicoloSettGiuridico) {
		getRTipFascicoloSettGiuridicos().remove(RTipFascicoloSettGiuridico);
		RTipFascicoloSettGiuridico.setTipologiaFascicolo(null);

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
		voceDiConto.setTipologiaFascicolo(this);

		return voceDiConto;
	}

	public VoceDiConto removeVoceDiConto(VoceDiConto voceDiConto) {
		getVoceDiContos().remove(voceDiConto);
		voceDiConto.setTipologiaFascicolo(null);

		return voceDiConto;
	}

}