package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MailingListEditRest {
	
	private long id;
	private String nome;
	private List<Long> rubricaDesc;
	private String categoria;
	private String sottoCategoria;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	

	public String getSottoCategoria() {
		return sottoCategoria;
	}

	public void setSottoCategoria(String sottoCategoria) {
		this.sottoCategoria = sottoCategoria;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Long> getRubricaDesc() {
		return rubricaDesc;
	}

	public void setRubricaDesc(List<Long> rubricaDesc) {
		this.rubricaDesc = rubricaDesc;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
}
