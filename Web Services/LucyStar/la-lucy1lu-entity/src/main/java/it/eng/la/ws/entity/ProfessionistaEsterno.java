package it.eng.la.ws.entity;

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
 * The persistent class for the PROFESSIONISTA_ESTERNO database table.
 * 
 */
@Entity
@Table(name = "PROFESSIONISTA_ESTERNO")
@NamedQuery(name = "ProfessionistaEsterno.findAll", query = "SELECT p FROM ProfessionistaEsterno p")
public class ProfessionistaEsterno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PROFESSIONISTA_ESTERNO_ID_GENERATOR", sequenceName = "PROFESSIONISTA_ESTERNO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PROFESSIONISTA_ESTERNO_ID_GENERATOR")
	private long id;

	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name = "MOTIVAZIONE_RICHIESTA")
	private String motivazioneRichiesta;

	private String cognome;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_RICH_AUTORIZZAZIONE")
	private Date dataRichAutorizzazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ULTIMA_MODIFICA")
	private Date dataUltimaModifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	private String email;

	private String fax;

	private String nome;

	private String telefono;

	// bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy = "professionistaEsterno")
	private Set<Incarico> incaricos;

	// bi-directional many-to-one association to StudioLegale
	@ManyToOne()
	@JoinColumn(name = "ID_STUDIO_LEGALE")
	private StudioLegale studioLegale;

	public ProfessionistaEsterno() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getMotivazioneRichiesta() {
		return this.motivazioneRichiesta;
	}

	public void setMotivazioneRichiesta(String motivazioneRichiesta) {
		this.motivazioneRichiesta = motivazioneRichiesta;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataUltimaModifica() {
		return this.dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public Date getDataAutorizzazione() {
		return this.dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public Date getDataRichAutorizzazione() {
		return this.dataRichAutorizzazione;
	}

	public void setDataRichAutorizzazione(Date dataRichAutorizzazione) {
		this.dataRichAutorizzazione = dataRichAutorizzazione;
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<Incarico> getIncaricos() {
		return this.incaricos;
	}

	public void setIncaricos(Set<Incarico> incaricos) {
		this.incaricos = incaricos;
	}

	public Incarico addIncarico(Incarico incarico) {
		getIncaricos().add(incarico);
		incarico.setProfessionistaEsterno(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setProfessionistaEsterno(null);

		return incarico;
	}

	public StudioLegale getStudioLegale() {
		return this.studioLegale;
	}

	public void setStudioLegale(StudioLegale studioLegale) {
		this.studioLegale = studioLegale;
	}

}