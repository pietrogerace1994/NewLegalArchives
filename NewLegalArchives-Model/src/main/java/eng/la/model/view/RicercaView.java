package eng.la.model.view;

public class RicercaView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	private String jsonArrayFascicolo;
	private String jsonArrayIncarico; 
	private String jsonArrayCollegioArbitrale;
	private String jsonArrayAtto;
	private String jsonArrayCosti;
	private String jsonArrayFile;
	private Long numFascicoliTroviati;
	private Long numAttiTroviati;
	private Long numIncarichiTroviati;
	private Long numCollegioArbitraleTroviati;
	private Long numCostiTroviati;
	private Long numFileTroviati;
	private String oggetto;
	
	public RicercaView() {
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getJsonArrayFascicolo() {
		return jsonArrayFascicolo;
	}

	public void setJsonArrayFascicolo(String jsonArrayFascicolo) {
		this.jsonArrayFascicolo = jsonArrayFascicolo;
	}

	public String getJsonArrayIncarico() {
		return jsonArrayIncarico;
	}

	public void setJsonArrayIncarico(String jsonArrayIncarico) {
		this.jsonArrayIncarico = jsonArrayIncarico;
	}

	public String getJsonArrayCosti() {
		return jsonArrayCosti;
	}

	public void setJsonArrayCosti(String jsonArrayCosti) {
		this.jsonArrayCosti = jsonArrayCosti;
	}

	public String getJsonArrayAtto() {
		return jsonArrayAtto;
	}

	public void setJsonArrayAtto(String jsonArrayAtto) {
		this.jsonArrayAtto = jsonArrayAtto;
	}

	public Long getNumFascicoliTroviati() {
		return numFascicoliTroviati;
	}

	public void setNumFascicoliTroviati(Long numFascicoliTroviati) {
		this.numFascicoliTroviati = numFascicoliTroviati;
	}

	public Long getNumAttiTroviati() {
		return numAttiTroviati;
	}

	public void setNumAttiTroviati(Long numAttiTroviati) {
		this.numAttiTroviati = numAttiTroviati;
	}

	public Long getNumIncarichiTroviati() {
		return numIncarichiTroviati;
	}

	public void setNumIncarichiTroviati(Long numIncarichiTroviati) {
		this.numIncarichiTroviati = numIncarichiTroviati;
	}

	public Long getNumCostiTroviati() {
		return numCostiTroviati;
	}

	public void setNumCostiTroviati(Long numCostiTroviati) {
		this.numCostiTroviati = numCostiTroviati;
	}

	public String getJsonArrayCollegioArbitrale() {
		return jsonArrayCollegioArbitrale;
	}

	public void setJsonArrayCollegioArbitrale(String jsonArrayCollegioArbitrale) {
		this.jsonArrayCollegioArbitrale = jsonArrayCollegioArbitrale;
	}

	public Long getNumCollegioArbitraleTroviati() {
		return numCollegioArbitraleTroviati;
	}

	public void setNumCollegioArbitraleTroviati(Long numCollegioArbitraleTroviati) {
		this.numCollegioArbitraleTroviati = numCollegioArbitraleTroviati;
	}

	public Long getNumFileTroviati() {
		return numFileTroviati;
	}

	public void setNumFileTroviati(Long numFileTroviati) {
		this.numFileTroviati = numFileTroviati;
	}

	public String getJsonArrayFile() {
		return jsonArrayFile;
	}

	public void setJsonArrayFile(String jsonArrayFile) {
		this.jsonArrayFile = jsonArrayFile;
	}

	
}
