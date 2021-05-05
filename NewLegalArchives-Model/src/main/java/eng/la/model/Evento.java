package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the EVENTO database table.
 * 
 */
@Entity
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e")
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EVENTO_ID_GENERATOR", sequenceName="EVENTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="EVENTO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_EVENTO")
	private Date dataEvento;

	@Column(name="DELAY_AVVISO")
	private BigDecimal delayAvviso;

	private String descrizione;

	@Column(name="FREQUENZA_AVVISO")
	private BigDecimal frequenzaAvviso;

	private String lang;

	private String oggetto;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to Evento
	@OneToMany(mappedBy="fascicolo")
	private Set<Fascicolo> fascicolos;

	public Evento() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataEvento() {
		return this.dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public BigDecimal getDelayAvviso() {
		return this.delayAvviso;
	}

	public void setDelayAvviso(BigDecimal delayAvviso) {
		this.delayAvviso = delayAvviso;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getFrequenzaAvviso() {
		return this.frequenzaAvviso;
	}

	public void setFrequenzaAvviso(BigDecimal frequenzaAvviso) {
		this.frequenzaAvviso = frequenzaAvviso;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Fascicolo getFascicolo() {
		return fascicolo;
	}

	public Set<Fascicolo> getFascicolos() {
		return fascicolos;
	}

	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

}