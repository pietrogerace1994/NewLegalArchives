package eng.la.model.view;

import java.math.BigDecimal;

public class AccontoView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal importo;
	private String anno;
	
	
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}

}
