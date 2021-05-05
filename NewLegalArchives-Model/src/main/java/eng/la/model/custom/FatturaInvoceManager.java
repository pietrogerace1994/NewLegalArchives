package eng.la.model.custom;

public class FatturaInvoceManager {

	String numFattura;
	String dataFattura;
	String protIn;
	String codFornitore;
	String numFatturaLegal;
	
	public String getNumFattura() {
		return numFattura;
	}
	public void setNumFattura(String numFattura) {
		this.numFattura = numFattura;
	}
	public String getDataFattura() {
		return dataFattura;
	}
	public void setDataFattura(String dataFattura) {
		this.dataFattura = dataFattura;
	}
	public String getProtIn() {
		return protIn;
	}
	public void setProtIn(String protIn) {
		this.protIn = protIn;
	}
	public String getCodFornitore() {
		return codFornitore;
	}
	public void setCodFornitore(String codFornitore) {
		this.codFornitore = codFornitore;
	}
	public String getNumFatturaLegal() {
		return numFatturaLegal;
	}
	public void setNumFatturaLegal(String numFatturaLegal) {
		this.numFatturaLegal = numFatturaLegal;
	}
	@Override
	public String toString() {
		return "FatturaInvoceManager [numFattura=" + numFattura
				+ ", dataFattura=" + dataFattura + ", protIn=" + protIn
				+ ", codFornitore=" + codFornitore + ", numFatturaLegal="
				+ numFatturaLegal + "]";
	}
	
	
}
