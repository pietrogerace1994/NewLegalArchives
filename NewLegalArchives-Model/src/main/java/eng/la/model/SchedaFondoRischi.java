package eng.la.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eng.la.model.audit.AuditedAttribute;


/**
 * The persistent class for the SCHEDA_FONDO_RISCHI database table.
 * 
 */
@Entity
@Table(name="SCHEDA_FONDO_RISCHI")
@NamedQuery(name="SchedaFondoRischi.findAll", query="SELECT s FROM SchedaFondoRischi s")
public class SchedaFondoRischi extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SCHEDA_FONDO_RISCHI_ID_GENERATOR", sequenceName="SCHEDA_FONDO_RISCHI_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SCHEDA_FONDO_RISCHI_ID_GENERATOR")
	private long id;

	@Column(name="TESTO_ESPLICATIVO")
	private String testoEsplicativo;

	@Column(name="MOTIVAZIONE")
	private String motivazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICHIESTA_AUTOR_SCHEDA")
	private Date dataRichiestaAutorScheda;

	@Column(name="CONTROPARTE")
	private String controparte;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to TipologiaSchedaFr
	@ManyToOne()
	@JoinColumn(name="ID_TIPOLOGIA_SCHEDA")
	private TipologiaSchedaFr tipologiaSchedaFr;
	
	//bi-directional many-to-one association to RischioSoccombenza
	@ManyToOne()
	@JoinColumn(name="ID_RISCHIO_SOCCOMBENZA")
	private RischioSoccombenza rischioSoccombenza;

	//bi-directional many-to-one association to StatoIncarico
	@AuditedAttribute(classType=StatoSchedaFondoRischi.class)
	@ManyToOne()
	@JoinColumn(name="ID_STATO_SCHEDA_FONDO_RISCHI")
	private StatoSchedaFondoRischi statoSchedaFondoRischi;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="VALORE_DOMANDA")
	private BigDecimal valoreDomanda;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="COPERTURA_ASSICURATIVA")
	private BigDecimal coperturaAssicurativa;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="MANLEVA")
	private BigDecimal manleva;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="COMMESSA_DI_INVESTIMENTO")
	private BigDecimal commessaDiInvestimento;
	
	@AuditedAttribute(classType=BigDecimal.class)
	@Column(name="PASSIVITA_STIMATA")
	private BigDecimal passivitaStimata;

	//bi-directional many-to-one association to SchedaFondoRischiWf
	@OneToMany(mappedBy="schedaFondoRischi")
	private Set<SchedaFondoRischiWf> schedaFondoRischiWfs;
	
	//bi-directional many-to-one association to Fascicolo
	@OneToMany(mappedBy="schedaFondoRischi")
	private Set<Fascicolo> fascicolos;

	public SchedaFondoRischi() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTestoEsplicativo() {
		return this.testoEsplicativo;
	}

	public void setTestoEsplicativo(String commento) {
		this.testoEsplicativo = commento;
	}

	public Date getDataAutorizzazione() {
		return this.dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataRichiestaAutorScheda() {
		return this.dataRichiestaAutorScheda;
	}

	public void setDataRichiestaAutorScheda(Date dataRichiestaAutorScheda) {
		this.dataRichiestaAutorScheda = dataRichiestaAutorScheda;
	}


	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}
	
	public TipologiaSchedaFr getTipologiaSchedaFr() {
		return tipologiaSchedaFr;
	}

	public void setTipologiaSchedaFr(TipologiaSchedaFr tipologiaSchedaFr) {
		this.tipologiaSchedaFr = tipologiaSchedaFr;
	}

	public RischioSoccombenza getRischioSoccombenza() {
		return rischioSoccombenza;
	}

	public void setRischioSoccombenza(RischioSoccombenza rischioSoccombenza) {
		this.rischioSoccombenza = rischioSoccombenza;
	}

	public StatoSchedaFondoRischi getStatoSchedaFondoRischi() {
		return this.statoSchedaFondoRischi;
	}

	public void setStatoSchedaFondoRischi(StatoSchedaFondoRischi statoSchedaFondoRischi) {
		this.statoSchedaFondoRischi = statoSchedaFondoRischi;
	}
	
	public String getControparte() {
		return controparte;
	}

	public void setControparte(String controparte) {
		this.controparte = controparte;
	}

	public BigDecimal getValoreDomanda() {
		return valoreDomanda;
	}

	public void setValoreDomanda(BigDecimal valoreDomanda) {
		this.valoreDomanda = valoreDomanda;
	}

	public BigDecimal getCoperturaAssicurativa() {
		return coperturaAssicurativa;
	}

	public void setCoperturaAssicurativa(BigDecimal coperturaAssicurativa) {
		this.coperturaAssicurativa = coperturaAssicurativa;
	}

	public BigDecimal getManleva() {
		return manleva;
	}

	public void setManleva(BigDecimal manleva) {
		this.manleva = manleva;
	}

	public BigDecimal getCommessaDiInvestimento() {
		return commessaDiInvestimento;
	}

	public void setCommessaDiInvestimento(BigDecimal commessaDiInvestimento) {
		this.commessaDiInvestimento = commessaDiInvestimento;
	}

	public BigDecimal getPassivitaStimata() {
		return passivitaStimata;
	}

	public void setPassivitaStimata(BigDecimal passivitaStimata) {
		this.passivitaStimata = passivitaStimata;
	}

	public Set<SchedaFondoRischiWf> getSchedaFondoRischiWfs() {
		return this.schedaFondoRischiWfs;
	}

	public void setSchedaFondoRischiWfs(Set<SchedaFondoRischiWf> schedaFondoRischiWfs) {
		this.schedaFondoRischiWfs = schedaFondoRischiWfs;
	}

	public SchedaFondoRischiWf addSchedaFondoRischiWf(SchedaFondoRischiWf schedaFondoRischiWf) {
		getSchedaFondoRischiWfs().add(schedaFondoRischiWf);
		schedaFondoRischiWf.setSchedaFondoRischi(this);

		return schedaFondoRischiWf;
	}

	public SchedaFondoRischiWf removeSchedaFondoRischiWf(SchedaFondoRischiWf schedaFondoRischiWf) {
		getSchedaFondoRischiWfs().remove(schedaFondoRischiWf);
		schedaFondoRischiWf.setSchedaFondoRischi(null);

		return schedaFondoRischiWf;
	}

	public Set<Fascicolo> getFascicolos() {
		return this.fascicolos;
	}

	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}

	public Fascicolo addFascicolo(Fascicolo fascicolo) {
		getFascicolos().add(fascicolo);
		fascicolo.setSchedaFondoRischi(this);

		return fascicolo;
	}

	public Fascicolo removeFascicolo(Fascicolo fascicolo) {
		getFascicolos().remove(fascicolo);
		fascicolo.setSchedaFondoRischi(null);

		return fascicolo;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}	
}