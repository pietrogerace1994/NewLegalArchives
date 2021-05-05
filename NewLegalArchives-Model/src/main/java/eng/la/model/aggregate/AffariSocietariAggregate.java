package eng.la.model.aggregate;

import java.util.List;

import eng.la.model.AffariSocietari;

public class AffariSocietariAggregate {

	private List<AffariSocietari> list;
	private Long numeroTotaleElementi;
	
	public AffariSocietariAggregate() {
	}

	public List<AffariSocietari> getList() {
		return list;
	}

	public void setList(List<AffariSocietari> list) {
		this.list = list;
	}

	public Long getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(Long numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}

	
}
