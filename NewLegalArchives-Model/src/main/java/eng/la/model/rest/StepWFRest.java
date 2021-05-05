package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@XmlRootElement(namespace = AbstractRest.NAMESPACE, name="StepWFRest")
@JsonDeserialize(as=StepWFRest.class)
public class StepWFRest extends AbstractRest {

	private String nome;
	private String cognome;
	private long idFascicolo;
	private String dataCreazione;
	private String noteSpecifiche;
	private String fascicoloConValore;
	private String codice;
	private long idFascicoloWf;
	private long id;
	private String descLinguaCorrente;
	private long idIncarico;
	private long idArbitrato;
	private long idProforma;
	private long idProfessionista;
	private long idAtto;
	private long idIncaricoWf;
	private String motivoRifiutoStepPrecedente;
	private int numeroStep;
	private long idProformaWf;
	private long idProfessionistaEsternoWf;
	private long idAttoWf;
	
	public StepWFRest() {
		super.setType(StepWFRest.class);
	}

	public long getIdIncaricoWf() {
		return idIncaricoWf;
	}

	public void setIdIncaricoWf(long idIncaricoWf) {
		this.idIncaricoWf = idIncaricoWf;
	}

	public long getIdProformaWf() {
		return idProformaWf;
	}

	public void setIdProformaWf(long idProformaWf) {
		this.idProformaWf = idProformaWf;
	}

	public long getIdProfessionistaEsternoWf() {
		return idProfessionistaEsternoWf;
	}

	public void setIdProfessionistaEsternoWf(long idProfessionistaEsternoWf) {
		this.idProfessionistaEsternoWf = idProfessionistaEsternoWf;
	}

	public long getIdIncarico() {
		return idIncarico;
	}

	public void setIdIncarico(long idIncarico) {
		this.idIncarico = idIncarico;
	}

	public long getIdArbitrato() {
		return idArbitrato;
	}

	public void setIdArbitrato(long idArbitrato) {
		this.idArbitrato = idArbitrato;
	}

	public long getIdProforma() {
		return idProforma;
	}

	public void setIdProforma(long idProforma) {
		this.idProforma = idProforma;
	}

	public long getIdProfessionista() {
		return idProfessionista;
	}

	public void setIdProfessionista(long idProfessionista) {
		this.idProfessionista = idProfessionista;
	}

	public long getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(long idAtto) {
		this.idAtto = idAtto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getNoteSpecifiche() {
		return noteSpecifiche;
	}

	public void setNoteSpecifiche(String noteSpecifiche) {
		this.noteSpecifiche = noteSpecifiche;
	}

	public String getFascicoloConValore() {
		return fascicoloConValore;
	}

	public void setFascicoloConValore(String fascicoloConValore) {
		this.fascicoloConValore = fascicoloConValore;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public long getIdFascicoloWf() {
		return idFascicoloWf;
	}

	public void setIdFascicoloWf(long idFascicoloWf) {
		this.idFascicoloWf = idFascicoloWf;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescLinguaCorrente() {
		return descLinguaCorrente;
	}

	public void setDescLinguaCorrente(String descLinguaCorrente) {
		this.descLinguaCorrente = descLinguaCorrente;
	}

	public String getMotivoRifiutoStepPrecedente() {
		return motivoRifiutoStepPrecedente;
	}

	public void setMotivoRifiutoStepPrecedente(String motivoRifiutoStepPrecedente) {
		this.motivoRifiutoStepPrecedente = motivoRifiutoStepPrecedente;
	}

	public int getNumeroStep() {
		return numeroStep;
	}

	public void setNumeroStep(int numeroStep) {
		this.numeroStep = numeroStep;
	}

	public long getIdAttoWf() {
		return idAttoWf;
	}

	public void setIdAttoWf(long idAttoWf) {
		this.idAttoWf = idAttoWf;
	}

}
