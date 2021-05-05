package eng.la.model.custom;

import java.io.Serializable;
import java.util.List;

import eng.la.model.Societa;

public class ParcellaRow implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = -9191849834729236840L;
public Societa societa;
public List<ProformaRow> proformasRow;

public Societa getSocieta() {
	return societa;
}
public void setSocieta(Societa societa) {
	this.societa = societa;
}
public List<ProformaRow> getProformasRow() {
	return proformasRow;
}
public void setProformasRow(List<ProformaRow> proformasRow) {
	this.proformasRow = proformasRow;
}


	
	
}
