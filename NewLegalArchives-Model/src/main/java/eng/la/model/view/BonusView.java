package eng.la.model.view;

import java.math.BigDecimal;

public class BonusView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal importo;
	private String descrizione;
	
	
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
