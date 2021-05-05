package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the MAILINGLIST database table.
 * 
 */
@Entity
@NamedQuery(name = "Mailinglist.findAll", query = "SELECT m FROM Mailinglist m")
public class Mailinglist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MAILINGLIST_ID_GENERATOR", sequenceName = "MAILINGLIST_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "MAILINGLIST_ID_GENERATOR")
	private long id;

	// bi-directional many-to-one association to CategoriaMailinglist
	@ManyToOne
	@JoinColumn(name = "ID_CATEGORIA_MAILINGLIST")
	private CategoriaMailinglist categoriaMailinglist;

	
	@OneToMany(mappedBy="mailinglist") 
	private Set<MailinglistDettaglio> mailinglistDettaglio;	

	private String nome;

	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
	
	
	public Mailinglist() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CategoriaMailinglist getCategoriaMailinglist() {
		return this.categoriaMailinglist;
	}

	public void setCategoriaMailinglist(CategoriaMailinglist categoriaMailinglist) {
		this.categoriaMailinglist = categoriaMailinglist;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<MailinglistDettaglio> getMailinglistDettaglio() {
		return mailinglistDettaglio;
	}

	public void setMailinglistDettaglio(Set<MailinglistDettaglio> mailinglistDettaglio) {
		this.mailinglistDettaglio = mailinglistDettaglio;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	@Override
	public String toString() {
		return "Mailinglist [id=" + id + ", categoriaMailinglist=" + categoriaMailinglist + ", mailinglistDettaglio="
				+ mailinglistDettaglio + ", nome=" + nome + ", dataCancellazione=" + dataCancellazione + "]";
	}
	
	

}