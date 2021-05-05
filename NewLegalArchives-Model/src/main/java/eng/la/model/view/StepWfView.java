package eng.la.model.view;

import java.util.List;

import eng.la.model.StepWf;

public class StepWfView extends BaseView {
	private static final long serialVersionUID = 1L;
	private StepWf vo;
	
	// DATI PER LA PARTE DI PRESENTATION
	private String descLinguaCorrente;
	private String faseAttivita;
	private String noteSpecifiche;
	private String fascicoloConValore;
	private long idWorkflow;
	private long idStep;
	private String codiceClasse;
	private String note;
	private boolean avanzato;
	private long idObject;
	private long idFascicolo;
	private long idIncarico;
	private long idArbitrato;
	private long idProforma;
	private long idProfessionista;
	private long idAtto;
	private long idSchedaFr;
	private long idBeautyContest;
	private boolean rifiutato;
	private boolean isForResponsabileFoglia;
	private List<UtenteView> collaboratoriResponsabili;
	private List<UtenteView> legaliInterni;
	private List<UtenteView> GC;
	private boolean isForLegaleInterno;
	private String assegnatario;
	private boolean verificatore;
	private boolean storico;
	
	// Step Wf.
	public void setVo(StepWf vo){
		this.vo = vo;
	}
	
	public StepWf getVo() {
		return this.vo;
	}

	public String getDescLinguaCorrente() {
		return descLinguaCorrente;
	}

	public void setDescLinguaCorrente(String descLinguaCorrente) {
		this.descLinguaCorrente = descLinguaCorrente;
	}

	public String getFaseAttivita() {
		return faseAttivita;
	}

	public void setFaseAttivita(String faseAttivita) {
		this.faseAttivita = faseAttivita;
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

	public long getIdWorkflow() {
		return idWorkflow;
	}

	public void setIdWorkflow(long idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	public String getCodiceClasse() {
		return codiceClasse;
	}

	public void setCodiceClasse(String codiceClasse) {
		this.codiceClasse = codiceClasse;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isAvanzato() {
		return avanzato;
	}

	public void setAvanzato(boolean avanzato) {
		this.avanzato = avanzato;
	}

	public long getIdObject() {
		return idObject;
	}

	public void setIdObject(long idObject) {
		this.idObject = idObject;
	}

	public long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public long getIdIncarico() {
		return idIncarico;
	}

	public void setIdIncarico(long idIncarico) {
		this.idIncarico = idIncarico;
	}

	public long getIdStep() {
		return idStep;
	}

	public void setIdStep(long idStep) {
		this.idStep = idStep;
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

	public boolean isRifiutato() {
		return rifiutato;
	}

	public void setRifiutato(boolean rifiutato) {
		this.rifiutato = rifiutato;
	}

	public boolean isForResponsabileFoglia() {
		return isForResponsabileFoglia;
	}

	public void setForResponsabileFoglia(boolean isForResponsabileFoglia) {
		this.isForResponsabileFoglia = isForResponsabileFoglia;
	}

	public List<UtenteView> getCollaboratoriResponsabili() {
		return collaboratoriResponsabili;
	}

	public void setCollaboratoriResponsabili(List<UtenteView> collaboratoriResponsabili) {
		this.collaboratoriResponsabili = collaboratoriResponsabili;
	}

	public List<UtenteView> getLegaliInterni() {
		return legaliInterni;
	}

	public void setLegaliInterni(List<UtenteView> legaliInterni) {
		this.legaliInterni = legaliInterni;
	}

	public boolean isForLegaleInterno() {
		return isForLegaleInterno;
	}

	public void setForLegaleInterno(boolean isForLegaleInterno) {
		this.isForLegaleInterno = isForLegaleInterno;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public List<UtenteView> getGC() {
		return GC;
	}

	public void setGC(List<UtenteView> gC) {
		GC = gC;
	}

	public long getIdArbitrato() {
		return idArbitrato;
	}

	public void setIdArbitrato(long idArbitrato) {
		this.idArbitrato = idArbitrato;
	}

	public long getIdSchedaFr() {
		return idSchedaFr;
	}

	public void setIdSchedaFr(long idSchedaFr) {
		this.idSchedaFr = idSchedaFr;
	}

	public boolean getVerificatore() {
		return verificatore;
	}

	public void setVerificatore(boolean isVerificatore) {
		this.verificatore = isVerificatore;
	}

	public long getIdBeautyContest() {
		return idBeautyContest;
	}

	public void setIdBeautyContest(long idBeautyContest) {
		this.idBeautyContest = idBeautyContest;
	}
	
	public boolean getStorico() {
		return storico;
	}

	public void setStorico(boolean isStorico) {
		this.storico = isStorico;
	}
	
	@Override
	public String toString(){
		return "StepWFView{" +
				"idWorkflow=" + idWorkflow +
				"idFascicolo=" + idFascicolo + 
				"idIncarico=" + idIncarico + 
				"idProforma=" + idProforma + 
				"idSchedaFR=" + idSchedaFr + 
				"idProfessionistaEsterno" + idProfessionista + 
				"idAtto=" + idAtto 
				+ "}";
	}
}