package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IndexRest {
	private long id;
	private String nome;
	private String descrizione;
	private String jsonArrayUltimiFascicoli;
	
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getJsonArrayUltimiFascicoli() {
		return jsonArrayUltimiFascicoli;
	}

	public void setJsonArrayUltimiFascicoli(String jsonArrayUltimiFascicoli) {
		this.jsonArrayUltimiFascicoli = jsonArrayUltimiFascicoli;
	}


	
}
