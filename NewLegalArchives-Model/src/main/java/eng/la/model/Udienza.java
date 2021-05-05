/*
 * @author Benedetto Giordano
 */
package eng.la.model;

import java.io.Serializable;
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


/**
 * The persistent class for the SCHEDA_FONDO_RISCHI database table.
 * 
 */
@Entity
@Table(name="UDIENZA")
@NamedQuery(name="Udienza.findAll", query="SELECT u FROM Udienza u")
public class Udienza extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="UDIENZA_ID_GENERATOR", sequenceName="UDIENZA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="UDIENZA_ID_GENERATOR")
	private long id;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_UDIENZA")
	private Date dataUdienza;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	//bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name="ID_FASCICOLO")
	private Fascicolo fascicolo;

	//bi-directional many-to-one association to Fascicolo
	@OneToMany(mappedBy="schedaFondoRischi")
	private Set<Fascicolo> fascicolos;

	public Udienza() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataUdienza() {
		return dataUdienza;
	}

	public void setDataUdienza(Date dataUdienza) {
		this.dataUdienza = dataUdienza;
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

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public Set<Fascicolo> getFascicolos() {
		return fascicolos;
	}

	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}
}