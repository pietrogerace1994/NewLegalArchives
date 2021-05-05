package eng.la.model.aggregate;

import java.util.List;

import eng.la.model.RepertorioStandard;

public class RepertorioStandardAggregate {

	private List<RepertorioStandard> list;
	private Long numeroTotaleElementi;
	
	public RepertorioStandardAggregate() {
	}

	public List<RepertorioStandard> getList() {
		return list;
	}

	public void setList(List<RepertorioStandard> list) {
		this.list = list;
	}

	public Long getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(Long numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}

	
}
