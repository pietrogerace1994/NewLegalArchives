package eng.la.model.aggregate;

import java.util.List;

import eng.la.model.RepertorioPoteri;

public class RepertorioPoteriAggregate {

	private List<RepertorioPoteri> list;
	private Long numeroTotaleElementi;
	
	public RepertorioPoteriAggregate() {
	}

	public List<RepertorioPoteri> getList() {
		return list;
	}

	public void setList(List<RepertorioPoteri> list) {
		this.list = list;
	}

	public Long getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(Long numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}

	
}
