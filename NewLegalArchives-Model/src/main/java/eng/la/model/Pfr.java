package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PFR database table.
 * 
 */
@Entity
@NamedQuery(name="Pfr.findAll", query="SELECT p FROM Pfr p")
public class Pfr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PFR_ID_GENERATOR", sequenceName="PFR_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PFR_ID_GENERATOR")
	private long id;

	private BigDecimal anno;

	@Column(name="COMMESSA_INVESTIMENTO")
	private BigDecimal commessaInvestimento;

	@Column(name="COPERTURA_ASSICURATIVA")
	private BigDecimal coperturaAssicurativa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String lang;

	private BigDecimal manleva;

	private BigDecimal mese;

	private String motivazione;

	@Column(name="PASSIVITA_STIMATA")
	private BigDecimal passivitaStimata;

	@Column(name="TESTO_ESPLICATIVO")
	private String testoEsplicativo;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to RischioSoccombenzaPfr
	@ManyToOne()
	@JoinColumn(name="ID_RISCHIO_SOCCOMBENZA_PFR")
	private RischioSoccombenzaPfr rischioSoccombenzaPfr;

	//bi-directional many-to-one association to StatoPfr
	@ManyToOne()
	@JoinColumn(name="ID_STATO_PFR")
	private StatoPfr statoPfr;

	//bi-directional many-to-one association to TipologiaPfr
	@ManyToOne()
	@JoinColumn(name="ID_TIPOLOGIA_PFR")
	private TipologiaPfr tipologiaPfr;

	//bi-directional many-to-one association to PfrWf
	@OneToMany(mappedBy="pfr")
	private Set<PfrWf> pfrWfs;

	public Pfr() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public BigDecimal getCommessaInvestimento() {
		return this.commessaInvestimento;
	}

	public void setCommessaInvestimento(BigDecimal commessaInvestimento) {
		this.commessaInvestimento = commessaInvestimento;
	}

	public BigDecimal getCoperturaAssicurativa() {
		return this.coperturaAssicurativa;
	}

	public void setCoperturaAssicurativa(BigDecimal coperturaAssicurativa) {
		this.coperturaAssicurativa = coperturaAssicurativa;
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

	public BigDecimal getManleva() {
		return this.manleva;
	}

	public void setManleva(BigDecimal manleva) {
		this.manleva = manleva;
	}

	public BigDecimal getMese() {
		return this.mese;
	}

	public void setMese(BigDecimal mese) {
		this.mese = mese;
	}

	public String getMotivazione() {
		return this.motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public BigDecimal getPassivitaStimata() {
		return this.passivitaStimata;
	}

	public void setPassivitaStimata(BigDecimal passivitaStimata) {
		this.passivitaStimata = passivitaStimata;
	}

	public String getTestoEsplicativo() {
		return this.testoEsplicativo;
	}

	public void setTestoEsplicativo(String testoEsplicativo) {
		this.testoEsplicativo = testoEsplicativo;
	}

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public RischioSoccombenzaPfr getRischioSoccombenzaPfr() {
		return this.rischioSoccombenzaPfr;
	}

	public void setRischioSoccombenzaPfr(RischioSoccombenzaPfr rischioSoccombenzaPfr) {
		this.rischioSoccombenzaPfr = rischioSoccombenzaPfr;
	}

	public StatoPfr getStatoPfr() {
		return this.statoPfr;
	}

	public void setStatoPfr(StatoPfr statoPfr) {
		this.statoPfr = statoPfr;
	}

	public TipologiaPfr getTipologiaPfr() {
		return this.tipologiaPfr;
	}

	public void setTipologiaPfr(TipologiaPfr tipologiaPfr) {
		this.tipologiaPfr = tipologiaPfr;
	}

	public Set<PfrWf> getPfrWfs() {
		return this.pfrWfs;
	}

	public void setPfrWfs(Set<PfrWf> pfrWfs) {
		this.pfrWfs = pfrWfs;
	}

	public PfrWf addPfrWf(PfrWf pfrWf) {
		getPfrWfs().add(pfrWf);
		pfrWf.setPfr(this);

		return pfrWf;
	}

	public PfrWf removePfrWf(PfrWf pfrWf) {
		getPfrWfs().remove(pfrWf);
		pfrWf.setPfr(null);

		return pfrWf;
	}

}