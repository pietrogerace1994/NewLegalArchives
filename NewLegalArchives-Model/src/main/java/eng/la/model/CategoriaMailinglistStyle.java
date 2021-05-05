package eng.la.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CATEGORIA_MAILINGLIST database table.
 * 
 */
@Entity
@Table(name = "CATEGORIA_MAILINGLIST_STYLE")
@NamedQuery(name = "CategoriaMailinglistStyle.findAll", query = "SELECT c FROM CategoriaMailinglistStyle c")
public class CategoriaMailinglistStyle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CATEGORIA_MAILINGLIST_STYLE_ID_GENERATOR", sequenceName = "CATEGORIA_MAILINGLIST_STYLE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CATEGORIA_MAILINGLIST_STYLE_ID_GENERATOR")
	private long id;
	
	private String colore;
	
	private String css;
	
	
	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public CategoriaMailinglistStyle() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCss(String css) {
		this.css = css;
	}
	
	public String getCss() {
		return css;
	}
	
}