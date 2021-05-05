package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentoRest {
	private String nomefile;
	private String uuid;
	private String size;
	
	
	public String getNomefile() {
		return nomefile;
	}
	public void setNomefile(String nomefile) {
		this.nomefile = nomefile;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	

}
