package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eng.la.model.audit.AuditedAttribute;


/**
 * The persistent class for the PROGETTO database table.
 */
@Entity
@Table(name="PROGETTO")
public class Progetto extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROGETTO_ID_GENERATOR", sequenceName="PROGETTO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="PROGETTO_ID_GENERATOR")
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CHIUSURA")
	private Date dataChiusura;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@Column(name="DESCRIZIONE")
	private String descrizione;

	@Column(name="OGGETTO")
	private String oggetto;
	
	@Column(name="LANG")
	private String lang;

	@Column(name="NOME")
	private String nome;

	@AuditedAttribute
	@Column(name="LEGALE_INTERNO")
	private String legaleInterno;

	@OneToMany(mappedBy="progetto")
	private Set<Fascicolo> fascicolos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="progetto")
	private Set<DocumentoProgetto> documentoProgettoSet;
	

	public Progetto() {
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

	public Date getDataChiusura() {
		return this.dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
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

	public Set<Fascicolo> getFascicolos() {
		return this.fascicolos;
	}
	
	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}


	public void setFascicolos(Set<Fascicolo> fascicolos) {
		this.fascicolos = fascicolos;
	}

	public Fascicolo addFascicolo(Fascicolo fascicolo) {
		getFascicolos().add(fascicolo);
		fascicolo.setProgetto(this);

		return fascicolo;
	}

	public Fascicolo removeFascicolo(Fascicolo fascicolo) {
		getFascicolos().remove(fascicolo);
		fascicolo.setProgetto(null);

		return fascicolo;
	}

	public Set<DocumentoProgetto> getDocumentoProgettoSet() {
		return documentoProgettoSet;
	}

	public void setDocumentoProgettoSet(Set<DocumentoProgetto> documentoProgettoSet) {
		this.documentoProgettoSet = documentoProgettoSet;
	}
	
}