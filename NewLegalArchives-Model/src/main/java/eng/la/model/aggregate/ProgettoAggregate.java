package eng.la.model.aggregate;

import java.io.Serializable;
import java.util.List;

import eng.la.model.Progetto;

public class ProgettoAggregate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1280468124208687L;
	private List<Progetto> list;
	private Long numeroTotaleElementi;
	
	public ProgettoAggregate() {
	}

	public List<Progetto> getList() {
		return list;
	}

	public void setList(List<Progetto> list) {
		this.list = list;
	}

	public Long getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(Long numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}
	
}