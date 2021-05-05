package eng.la.model.rest;

public class ProformaRest {
	private long id;
	private String nomeProforma;
	private String fattura;
	private String contabilizzata;
	private String annoEsercizioFinanziario;
	private String dataInserimento;
	private String nomeIncarico;
	private String nomeSocieta;
	private String stato;
	private String azioni;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomeProforma() {
		return nomeProforma;
	}

	public void setNomeProforma(String nomeProforma) {
		this.nomeProforma = nomeProforma;
	}

	public String getFattura() {
		return fattura;
	}

	public void setFattura(String fattura) {
		this.fattura = fattura;
	}

	public String getContabilizzata() {
		return contabilizzata;
	}
	
	public void setContabilizzata(String contabilizzata) {
		this.contabilizzata = contabilizzata;
	}
	
	public String getAnnoEsercizioFinanziario() {
		return annoEsercizioFinanziario;
	}

	public void setAnnoEsercizioFinanziario(String annoEsercizioFinanziario) {
		this.annoEsercizioFinanziario = annoEsercizioFinanziario;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getNomeIncarico() {
		return nomeIncarico;
	}

	public void setNomeIncarico(String nomeIncarico) {
		this.nomeIncarico = nomeIncarico;
	}

	public String getNomeSocieta() {
		return nomeSocieta;
	}

	public void setNomeSocieta(String nomeSocieta) {
		this.nomeSocieta = nomeSocieta;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getAzioni() {
		return azioni;
	}

	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}

}
