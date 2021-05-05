package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CATEGORIA_MAILINGLIST database table.
 * 
 */
@Entity
@Table(name = "CATEGORIA_MAILINGLIST")
@NamedQuery(name = "CategoriaMailinglist.findAll", query = "SELECT c FROM CategoriaMailinglist c")
public class CategoriaMailinglist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CATEGORIA_MAILINGLIST_ID_GENERATOR", sequenceName = "CATEGORIA_MAILINGLIST_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CATEGORIA_MAILINGLIST_ID_GENERATOR")
	private long id;

	@Column(name = "COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@ManyToOne()
	@JoinColumn(name = "ID_CATEGORIA_PADRE")
	private CategoriaMailinglist categoriaPadre;

	private String lang;

	@Column(name = "NOME_CATEGORIA")
	private String nomeCategoria;
	
	private String colore;
	
	@Column(name = "ICON")
	private String icon;
	
	@Column(name = "ORD")
	private long ord;
	
	@Column(name = "CSS")
	private String css;
	
	
	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public CategoriaMailinglist() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodGruppoLingua() {
		return this.codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public CategoriaMailinglist getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(CategoriaMailinglist categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNomeCategoria() {
		return this.nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIcon() {
		return icon;
	}
	
	public void setOrd(long ord) {
		this.ord = ord;
	}
	
	public long getOrd() {
		return ord;
	}

	public void setCss(String css) {
		this.css = css;
	}
	
	public String getCss() {
		return css;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CategoriaMailinglist){
			CategoriaMailinglist toCompare = (CategoriaMailinglist) obj;
		    return (this.id==toCompare.getId());
		  }
		  return false;
	}

	@Override
	public String toString() {
		return "CategoriaMailinglist [id=" + id + ", codGruppoLingua=" + codGruppoLingua + ", dataCancellazione="
				+ dataCancellazione + ", categoriaPadre=" + categoriaPadre + ", lang=" + lang + ", nomeCategoria="
				+ nomeCategoria + ", colore=" + colore + ", icon=" + icon + ", ord=" + ord + ", css=" + css + "]";
	}
	
	


	
	
}