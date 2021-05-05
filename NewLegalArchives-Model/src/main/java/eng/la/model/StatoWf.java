package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_WF database table.
 * 
 */
@Entity
@Table(name="STATO_WF")
@NamedQuery(name="StatoWf.findAll", query="SELECT s FROM StatoWf s")
public class StatoWf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_WF_ID_GENERATOR", sequenceName="STATO_WF_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_WF_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to AttoWf
	@OneToMany(mappedBy="statoWf")
	private Set<AttoWf> attoWfs;

	//bi-directional many-to-one association to FascicoloWf
	@OneToMany(mappedBy="statoWf")
	private Set<FascicoloWf> fascicoloWfs;

	//bi-directional many-to-one association to IncaricoWf
	@OneToMany(mappedBy="statoWf")
	private Set<IncaricoWf> incaricoWfs;

	//bi-directional many-to-one association to PfrWf
	@OneToMany(mappedBy="statoWf")
	private Set<PfrWf> pfrWfs;

	//bi-directional many-to-one association to ProfessionistaEsternoWf
	@OneToMany(mappedBy="statoWf")
	private Set<ProfessionistaEsternoWf> professionistaEsternoWfs;

	//bi-directional many-to-one association to ProformaWf
	@OneToMany(mappedBy="statoWf")
	private Set<ProformaWf> proformaWfs;
	
	//bi-directional many-to-one association to ProformaWf
	@OneToMany(mappedBy="statoWf")
	private Set<SchedaFondoRischiWf> schedaFondoRischiWfs;

	public StatoWf() {
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

	public Set<AttoWf> getAttoWfs() {
		return this.attoWfs;
	}

	public void setAttoWfs(Set<AttoWf> attoWfs) {
		this.attoWfs = attoWfs;
	}

	public AttoWf addAttoWf(AttoWf attoWf) {
		getAttoWfs().add(attoWf);
		attoWf.setStatoWf(this);

		return attoWf;
	}

	public AttoWf removeAttoWf(AttoWf attoWf) {
		getAttoWfs().remove(attoWf);
		attoWf.setStatoWf(null);

		return attoWf;
	}

	public Set<FascicoloWf> getFascicoloWfs() {
		return this.fascicoloWfs;
	}

	public void setFascicoloWfs(Set<FascicoloWf> fascicoloWfs) {
		this.fascicoloWfs = fascicoloWfs;
	}

	public FascicoloWf addFascicoloWf(FascicoloWf fascicoloWf) {
		getFascicoloWfs().add(fascicoloWf);
		fascicoloWf.setStatoWf(this);

		return fascicoloWf;
	}

	public FascicoloWf removeFascicoloWf(FascicoloWf fascicoloWf) {
		getFascicoloWfs().remove(fascicoloWf);
		fascicoloWf.setStatoWf(null);

		return fascicoloWf;
	}

	public Set<IncaricoWf> getIncaricoWfs() {
		return this.incaricoWfs;
	}

	public void setIncaricoWfs(Set<IncaricoWf> incaricoWfs) {
		this.incaricoWfs = incaricoWfs;
	}

	public IncaricoWf addIncaricoWf(IncaricoWf incaricoWf) {
		getIncaricoWfs().add(incaricoWf);
		incaricoWf.setStatoWf(this);

		return incaricoWf;
	}

	public IncaricoWf removeIncaricoWf(IncaricoWf incaricoWf) {
		getIncaricoWfs().remove(incaricoWf);
		incaricoWf.setStatoWf(null);

		return incaricoWf;
	}

	public Set<PfrWf> getPfrWfs() {
		return this.pfrWfs;
	}

	public void setPfrWfs(Set<PfrWf> pfrWfs) {
		this.pfrWfs = pfrWfs;
	}

	public PfrWf addPfrWf(PfrWf pfrWf) {
		getPfrWfs().add(pfrWf);
		pfrWf.setStatoWf(this);

		return pfrWf;
	}

	public PfrWf removePfrWf(PfrWf pfrWf) {
		getPfrWfs().remove(pfrWf);
		pfrWf.setStatoWf(null);

		return pfrWf;
	}

	public Set<ProfessionistaEsternoWf> getProfessionistaEsternoWfs() {
		return this.professionistaEsternoWfs;
	}

	public void setProfessionistaEsternoWfs(Set<ProfessionistaEsternoWf> professionistaEsternoWfs) {
		this.professionistaEsternoWfs = professionistaEsternoWfs;
	}

	public ProfessionistaEsternoWf addProfessionistaEsternoWf(ProfessionistaEsternoWf professionistaEsternoWf) {
		getProfessionistaEsternoWfs().add(professionistaEsternoWf);
		professionistaEsternoWf.setStatoWf(this);

		return professionistaEsternoWf;
	}

	public ProfessionistaEsternoWf removeProfessionistaEsternoWf(ProfessionistaEsternoWf professionistaEsternoWf) {
		getProfessionistaEsternoWfs().remove(professionistaEsternoWf);
		professionistaEsternoWf.setStatoWf(null);

		return professionistaEsternoWf;
	}

	public Set<ProformaWf> getProformaWfs() {
		return this.proformaWfs;
	}

	public void setProformaWfs(Set<ProformaWf> proformaWfs) {
		this.proformaWfs = proformaWfs;
	}

	public ProformaWf addProformaWf(ProformaWf proformaWf) {
		getProformaWfs().add(proformaWf);
		proformaWf.setStatoWf(this);

		return proformaWf;
	}

	public ProformaWf removeProformaWf(ProformaWf proformaWf) {
		getProformaWfs().remove(proformaWf);
		proformaWf.setStatoWf(null);

		return proformaWf;
	}
	
	public Set<SchedaFondoRischiWf> getSchedaFondoRischiWfs() {
		return this.schedaFondoRischiWfs;
	}

	public void setSchedaFondoRischiWfs(Set<SchedaFondoRischiWf> schedaFondoRischiWfs) {
		this.schedaFondoRischiWfs = schedaFondoRischiWfs;
	}

	public SchedaFondoRischiWf addSchedaFondoRischiWf(SchedaFondoRischiWf schedaFondoRischiWf) {
		getSchedaFondoRischiWfs().add(schedaFondoRischiWf);
		schedaFondoRischiWf.setStatoWf(this);

		return schedaFondoRischiWf;
	}

	public SchedaFondoRischiWf removeSchedaFondoRischiWf(SchedaFondoRischiWf schedaFondoRischiWf) {
		getSchedaFondoRischiWfs().remove(schedaFondoRischiWf);
		schedaFondoRischiWf.setStatoWf(null);

		return schedaFondoRischiWf;
	}

}