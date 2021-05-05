package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UtilRest {
	private long id;
	private String nome;
	private String azioni;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCommand() {
		return azioni;
	}
	
	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}

}
