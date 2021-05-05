package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the POSIZIONE_SOCIETA database table.
 * 
 */
@Entity
@Table(name="POSIZIONE_SOCIETA")
@NamedQuery(name="PosizioneSocieta.findAll", query="SELECT p FROM PosizioneSocieta p")
public class PosizioneSocieta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="POSIZIONE_SOCIETA_ID_GENERATOR", sequenceName="POSIZIONE_SOCIETA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="POSIZIONE_SOCIETA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to RFascicoloSocieta
	@OneToMany(mappedBy="posizioneSocieta")
	private Set<RFascicoloSocieta> RFascicoloSocietas;

	//bi-directional many-to-one association to RSettGiuridicoPosSocieta
	@OneToMany(mappedBy="posizioneSocieta")
	private Set<RSettGiuridicoPosSocieta> RSettGiuridicoPosSocietas;

	public PosizioneSocieta() {
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

	public Set<RFascicoloSocieta> getRFascicoloSocietas() {
		return this.RFascicoloSocietas;
	}

	public void setRFascicoloSocietas(Set<RFascicoloSocieta> RFascicoloSocietas) {
		this.RFascicoloSocietas = RFascicoloSocietas;
	}

	public RFascicoloSocieta addRFascicoloSocieta(RFascicoloSocieta RFascicoloSocieta) {
		getRFascicoloSocietas().add(RFascicoloSocieta);
		RFascicoloSocieta.setPosizioneSocieta(this);

		return RFascicoloSocieta;
	}

	public RFascicoloSocieta removeRFascicoloSocieta(RFascicoloSocieta RFascicoloSocieta) {
		getRFascicoloSocietas().remove(RFascicoloSocieta);
		RFascicoloSocieta.setPosizioneSocieta(null);

		return RFascicoloSocieta;
	}

	public Set<RSettGiuridicoPosSocieta> getRSettGiuridicoPosSocietas() {
		return this.RSettGiuridicoPosSocietas;
	}

	public void setRSettGiuridicoPosSocietas(Set<RSettGiuridicoPosSocieta> RSettGiuridicoPosSocietas) {
		this.RSettGiuridicoPosSocietas = RSettGiuridicoPosSocietas;
	}

	public RSettGiuridicoPosSocieta addRSettGiuridicoPosSocieta(RSettGiuridicoPosSocieta RSettGiuridicoPosSocieta) {
		getRSettGiuridicoPosSocietas().add(RSettGiuridicoPosSocieta);
		RSettGiuridicoPosSocieta.setPosizioneSocieta(this);

		return RSettGiuridicoPosSocieta;
	}

	public RSettGiuridicoPosSocieta removeRSettGiuridicoPosSocieta(RSettGiuridicoPosSocieta RSettGiuridicoPosSocieta) {
		getRSettGiuridicoPosSocietas().remove(RSettGiuridicoPosSocieta);
		RSettGiuridicoPosSocieta.setPosizioneSocieta(null);

		return RSettGiuridicoPosSocieta;
	}

}