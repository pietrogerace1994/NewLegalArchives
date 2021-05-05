package it.eng.la.ws.entity;

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
 * The persistent class for the STUDIO_LEGALE database table.
 * 
 */
@Entity
@Table(name = "STUDIO_LEGALE")
@NamedQuery(name = "StudioLegale.findAll", query = "SELECT s FROM StudioLegale s")
public class StudioLegale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "STUDIO_LEGALE_ID_GENERATOR", sequenceName = "STUDIO_LEGALE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "STUDIO_LEGALE_ID_GENERATOR")
	private long id;

	private String cap;

	private String citta;

	@Column(name = "CODICE_SAP")
	private String codiceSap;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String denominazione;

	private String email;

	private String fax;

	private String indirizzo;

	@Column(name = "PARTITA_IVA")
	private String partitaIva;

	private String telefono;

	// bi-directional many-to-one association to ProfessionistaEsterno
	@OneToMany(mappedBy = "studioLegale")
	private Set<ProfessionistaEsterno> professionistaEsternos;

	public StudioLegale() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return this.citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCodiceSap() {
		return this.codiceSap;
	}

	public void setCodiceSap(String codiceSap) {
		this.codiceSap = codiceSap;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<ProfessionistaEsterno> getProfessionistaEsternos() {
		return this.professionistaEsternos;
	}

	public void setProfessionistaEsternos(
			Set<ProfessionistaEsterno> professionistaEsternos) {
		this.professionistaEsternos = professionistaEsternos;
	}

	public ProfessionistaEsterno addProfessionistaEsterno(
			ProfessionistaEsterno professionistaEsterno) {
		getProfessionistaEsternos().add(professionistaEsterno);
		professionistaEsterno.setStudioLegale(this);

		return professionistaEsterno;
	}

	public ProfessionistaEsterno removeProfessionistaEsterno(
			ProfessionistaEsterno professionistaEsterno) {
		getProfessionistaEsternos().remove(professionistaEsterno);
		professionistaEsterno.setStudioLegale(null);

		return professionistaEsterno;
	}

}