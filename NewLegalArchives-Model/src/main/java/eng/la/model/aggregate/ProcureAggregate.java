package eng.la.model.aggregate;

import java.util.List;

import eng.la.model.Procure;

public class ProcureAggregate {

	private List<Procure> list;
	private Long numeroTotaleElementi;
	
	public ProcureAggregate() {
	}

	public List<Procure> getList() {
		return list;
	}

	public void setList(List<Procure> list) {
		this.list = list;
	}

	public Long getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(Long numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}

	
}
