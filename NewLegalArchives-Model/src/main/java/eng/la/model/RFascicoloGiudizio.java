package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the R_FASCICOLO_RICORSO database table.
 * 
 */
@Entity
@Table(name = "R_FASCICOLO_GIUDIZIO")
public class RFascicoloGiudizio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "R_FASCICOLO_GIUDIZIO_ID_GENERATOR", sequenceName = "R_FASCICOLO_GIUDIZIO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "R_FASCICOLO_GIUDIZIO_ID_GENERATOR")
	private long id;

	private String foro;

	private String note;

	@Column(name = "NUM_REGISTRO_CAUSA")
	private String numeroRegistroCausa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	// bi-directional many-to-one association to Fascicolo
	@ManyToOne()
	@JoinColumn(name = "ID_FASCICOLO")
	private Fascicolo fascicolo;

	// bi-directional many-to-one association to Ricorso
	@ManyToOne()
	@JoinColumn(name = "ID_GIUDIZIO")
	private Giudizio giudizio;

	// bi-directional many-to-one association to OrganoGiudicante
	@ManyToOne()
	@JoinColumn(name = "ID_ORGANO_GIUDICANTE")
	private OrganoGiudicante organoGiudicante;

	public RFascicoloGiudizio() {
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

	public Fascicolo getFascicolo() {
		return this.fascicolo;
	}

	public void setFascicolo(Fascicolo fascicolo) {
		this.fascicolo = fascicolo;
	}

	public String getForo() {
		return foro;
	}

	public void setForo(String foro) {
		this.foro = foro;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumeroRegistroCausa() {
		return numeroRegistroCausa;
	}

	public void setNumeroRegistroCausa(String numeroRegistroCausa) {
		this.numeroRegistroCausa = numeroRegistroCausa;
	}

	public Giudizio getGiudizio() {
		return giudizio;
	}

	public void setGiudizio(Giudizio giudizio) {
		this.giudizio = giudizio;
	}

	public OrganoGiudicante getOrganoGiudicante() {
		return organoGiudicante;
	}

	public void setOrganoGiudicante(OrganoGiudicante organoGiudicante) {
		this.organoGiudicante = organoGiudicante;
	}

}