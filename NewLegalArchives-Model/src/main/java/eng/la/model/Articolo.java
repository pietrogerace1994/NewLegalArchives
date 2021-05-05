package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the EMAIL database table.
 * 
 */
@Entity
@NamedQuery(name = "Articolo.findAll", query = "SELECT e FROM Articolo e")
public class Articolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ARTICOLO_ID_GENERATOR", sequenceName = "ARTICOLO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ARTICOLO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CREAZIONE")
	private Date dataCreazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_EMAIL")
	private Date dataEmail;

	@Lob
	private String contenuto;

	@Column(name = "TITOLO")
	private String oggetto;

	@Column(name = "abstract")
	private String contenutoBreve;

	@ManyToOne
	@JoinColumn(name = "ID_CATEGORIA")
	private CategoriaMailinglist categoria;

	@ManyToOne
	@JoinColumn(name = "ID_SOTTOCATEGORIA")
	private CategoriaMailinglist sottoCategoria;
	
	//bi-directional many-to-one association to RProfDocumento
	@OneToMany(mappedBy="articolo")
	private Set<DocumentoArticolo> documentoArticolo;
	
	public Set<DocumentoArticolo> getDocumentoArticolo() {
		return documentoArticolo;
	}

	public void setDocumentoArticolo(Set<DocumentoArticolo> documentoArticolo) {
		this.documentoArticolo = documentoArticolo;
	}

	public Articolo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Date getDataEmail() {
		return dataEmail;
	}

	public void setDataEmail(Date dataEmail) {
		this.dataEmail = dataEmail;
	}

	public CategoriaMailinglist getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaMailinglist categoria) {
		this.categoria = categoria;
	}

	public CategoriaMailinglist getSottoCategoria() {
		return sottoCategoria;
	}

	public void setSottoCategoria(CategoriaMailinglist sottoCategoria) {
		this.sottoCategoria = sottoCategoria;
	}

	public String getContenutoBreve() {
		return contenutoBreve;
	}

	public void setContenutoBreve(String contenutoBreve) {
		this.contenutoBreve = contenutoBreve;
	}

	@Override
	public String toString() {
		return "Articolo [id=" + id + ", oggetto=" + oggetto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Articolo other = (Articolo) obj;
		if (id != other.id)
			return false;
		return true;
	}


	
	

}