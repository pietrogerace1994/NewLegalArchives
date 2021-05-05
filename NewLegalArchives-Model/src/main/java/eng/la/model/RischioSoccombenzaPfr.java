package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the RISCHIO_SOCCOMBENZA_PFR database table.
 * 
 */
@Entity
@Table(name="RISCHIO_SOCCOMBENZA_PFR")
@NamedQuery(name="RischioSoccombenzaPfr.findAll", query="SELECT r FROM RischioSoccombenzaPfr r")
public class RischioSoccombenzaPfr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RISCHIO_SOCCOMBENZA_PFR_ID_GENERATOR", sequenceName="RISCHIO_SOCCOMBENZA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="RISCHIO_SOCCOMBENZA_PFR_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to Pfr
	@OneToMany(mappedBy="rischioSoccombenzaPfr")
	private Set<Pfr> pfrs;

	public RischioSoccombenzaPfr() {
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

	public Set<Pfr> getPfrs() {
		return this.pfrs;
	}

	public void setPfrs(Set<Pfr> pfrs) {
		this.pfrs = pfrs;
	}

	public Pfr addPfr(Pfr pfr) {
		getPfrs().add(pfr);
		pfr.setRischioSoccombenzaPfr(this);

		return pfr;
	}

	public Pfr removePfr(Pfr pfr) {
		getPfrs().remove(pfr);
		pfr.setRischioSoccombenzaPfr(null);

		return pfr;
	}

}