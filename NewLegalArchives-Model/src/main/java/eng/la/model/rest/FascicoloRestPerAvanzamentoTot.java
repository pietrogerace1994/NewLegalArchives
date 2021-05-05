package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FascicoloRestPerAvanzamentoTot {
	private long total;
	private List<FascicoloRestPerAvanzamento> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<FascicoloRestPerAvanzamento> getRows() {
		return rows;
	}

	public void setRows(List<FascicoloRestPerAvanzamento> rows) {
		this.rows = rows;
	}

}
