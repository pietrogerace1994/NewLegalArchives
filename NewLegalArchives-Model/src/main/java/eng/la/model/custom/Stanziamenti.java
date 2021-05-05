package eng.la.model.custom;

import java.io.Serializable;

public class Stanziamenti implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 436632926867953017L;
	private int annoEsercizioFinanziario;
	private String nomeFascicolo;
	private String nomeIncarico;
	private Double importo;
	private String nomeProforma;
	private String statoProforma;
	private String fatturaContabilizzata;
	private String idStanziamento;
	private Double importoProforma;
	
	public Stanziamenti() {
		 
	}
	
	public int getAnnoEsercizioFinanziario() {
		return annoEsercizioFinanziario;
	}
	public void setAnnoEsercizioFinanziario(int annoEsercizioFinanziario) {
		this.annoEsercizioFinanziario = annoEsercizioFinanziario;
	}
	public String getNomeFascicolo() {
		return nomeFascicolo;
	}
	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}
	public String getNomeIncarico() {
		return nomeIncarico;
	}
	public void setNomeIncarico(String nomeIncarico) {
		this.nomeIncarico = nomeIncarico;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public String getNomeProforma() {
		return nomeProforma;
	}
	public void setNomeProforma(String nomeProforma) {
		this.nomeProforma = nomeProforma;
	}
	public String getStatoProforma() {
		return statoProforma;
	}
	public void setStatoProforma(String statoProforma) {
		this.statoProforma = statoProforma;
	}
	public String getFatturaContabilizzata() {
		return fatturaContabilizzata;
	}
	public void setFatturaContabilizzata(String fatturaContabilizzata) {
		this.fatturaContabilizzata = fatturaContabilizzata;
	}
	public String getIdStanziamento() {
		return idStanziamento;
	}
	public void setIdStanziamento(String idStanziamento) {
		this.idStanziamento = idStanziamento;
	}

	public Double getImportoProforma() {
		return importoProforma;
	}

	public void setImportoProforma(Double importoProforma) {
		this.importoProforma = importoProforma;
	}
	
	
}
