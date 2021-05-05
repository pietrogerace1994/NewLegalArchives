package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RicercaGestioneUtenteRest {
	private long current;
	private long rowCount; 
	private List<GestioneUtenteRest> rows;
	private long total;
	
	
	public long getCurrent() {
		return current;
	}
	public void setCurrent(long current) {
		this.current = current;
	}
	public long getRowCount() {
		return rowCount;
	}
	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}
	public List<GestioneUtenteRest> getRows() {
		return rows;
	}
	public void setRows(List<GestioneUtenteRest> rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	
	
}
