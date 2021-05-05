package eng.la.model.rest;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import eng.la.model.Materia;
import eng.la.model.view.SettoreGiuridicoView;

@XmlRootElement
public class MateriaRest {
	private long id;
	private String nome;
	private String lang;
	private String codGruppoLingua;
	private Long idSottoMateria;
	private String sottoMateriaNome; 
	private String jsonAlberaturaMaterie;
	private List<Materia> vos;
	private List<SettoreGiuridicoView> listaSettoreGiuridico;
	
	
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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Long getIdSottoMateria() {
		return idSottoMateria;
	}

	public void setIdSottoMateria(Long idSottoMateria) {
		this.idSottoMateria = idSottoMateria;
	}

	public void setSottoMateriaNome(String sottoMateriaNome) {
		this.sottoMateriaNome = sottoMateriaNome;
	}
	
	public String getSottoMateriaNome() {
		return this.sottoMateriaNome;
	}

	public List<SettoreGiuridicoView> getListaSettoreGiuridico() {
		return listaSettoreGiuridico;
	}

	public void setListaSettoreGiuridico(List<SettoreGiuridicoView> listaSettoreGiuridico) {
		this.listaSettoreGiuridico = listaSettoreGiuridico;
	}

	public String getJsonAlberaturaMaterie() {
		return jsonAlberaturaMaterie;
	}

	public void setJsonAlberaturaMaterie(String jsonAlberaturaMaterie) {
		this.jsonAlberaturaMaterie = jsonAlberaturaMaterie;
	}

	public List<Materia> getVos() {
		return vos;
	}

	public void setVos(List<Materia> vos) {
		this.vos = vos;
	}

	
}
