package eng.la.model.aggregate;

import java.util.List;

import eng.la.model.OrganoSociale;

public class OrganoSocialeAggregate {

	private List<OrganoSociale> list;
	private Long numeroTotaleElementi;
	
	public OrganoSocialeAggregate() {
	}

	public List<OrganoSociale> getList() {
		return list;
	}

	public void setList(List<OrganoSociale> list) {
		this.list = list;
	}

	public Long getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(Long numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}

	
}
