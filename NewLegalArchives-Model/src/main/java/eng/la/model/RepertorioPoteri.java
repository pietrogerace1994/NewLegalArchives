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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the PROGETTO database table.
 */
@Entity
@Table(name="REPERTORIO_POTERI")
public class RepertorioPoteri extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REPERTORIO_POTERI_ID_GENERATOR", sequenceName="REPERTORIO_POTERI_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="REPERTORIO_POTERI_ID_GENERATOR")
	private long id;
	
	@Column(name="CODICE")
	private String codice;
	
	@Column(name="DESCRIZIONE")
	private String descrizione;
	
	@Column(name="TESTO")
	private String testo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@Column(name="LANG")
	private String lingua;
	
	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;
	
	
	@ManyToOne()
	@JoinColumn(name="ID_CATEGORIA")
	private CategoriaTessere categoria;
	
	@ManyToOne()
	@JoinColumn(name="ID_SUBCATEGORIA")
	private SubCategoriaTessere subcategoria;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public CategoriaTessere getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaTessere categoria) {
		this.categoria = categoria;
	}

	public SubCategoriaTessere getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(SubCategoriaTessere subcategoria) {
		this.subcategoria = subcategoria;
	}
	
	

}