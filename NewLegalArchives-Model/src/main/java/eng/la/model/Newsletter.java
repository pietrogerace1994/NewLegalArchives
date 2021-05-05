package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the EMAIL database table.
 * 
 */
/**
 * @author Andrea
 *
 */
@Entity
@NamedQuery(name = "Newsletter.findAll", query = "SELECT e FROM Newsletter e")
public class Newsletter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NEWSLETTER_ID_GENERATOR", sequenceName = "NEWSLETTER_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NEWSLETTER_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CREAZIONE")
	private Date dataCreazione;

	private String titolo;

	@OneToOne
	@JoinColumn(name = "ID_COPERTINA")
	private Documento copertina;
	
	@ManyToOne()
	@JoinColumn(name="ID_STATO")
	private StatoNewsletter stato;
	
	//bi-directional many-to-one association to RProfDocumento
	@OneToMany(mappedBy="newsletter")
	private List<RNewsletterArticolo> rNewsletterArticolos;
	
	//bi-directional many-to-one association to RProfDocumento
	@OneToMany(mappedBy="newsletter")
	private List<NewsletterEmail> newsletterEmails;

	private String numero;
	
	@JoinColumn(name = "HIGHLIGHTS")
	private Long highLights;
	
	public Newsletter() {
	}
	
	
	
	




	public Long getHighLights() {
		return highLights;
	}








	public void setHighLights(Long highLights) {
		this.highLights = highLights;
	}








	public List<RNewsletterArticolo> getrNewsletterArticolos() {
		return rNewsletterArticolos;
	}






	public void setrNewsletterArticolos(List<RNewsletterArticolo> rNewsletterArticolos) {
		this.rNewsletterArticolos = rNewsletterArticolos;
	}






	public List<NewsletterEmail> getNewsletterEmails() {
		return newsletterEmails;
	}






	public void setNewsletterEmails(List<NewsletterEmail> newsletterEmails) {
		this.newsletterEmails = newsletterEmails;
	}






	public String getNumero() {
		return numero;
	}






	public void setNumero(String numero) {
		this.numero = numero;
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

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Documento getCopertina() {
		return copertina;
	}

	public void setCopertina(Documento copertina) {
		this.copertina = copertina;
	}

	public StatoNewsletter getStato() {
		return stato;
	}

	public void setStato(StatoNewsletter stato) {
		this.stato = stato;
	}
	
	public List<RNewsletterArticolo> getRNewsletterArticolos() {
		return rNewsletterArticolos;
	}

	public void setRNewsletterArticolos(List<RNewsletterArticolo> RNewsletterArticolos) {
		rNewsletterArticolos = RNewsletterArticolos;
	}


	@Override
	public String toString() {
		return "Newsletter [id=" + id + ", dataCancellazione=" + dataCancellazione + ", dataCreazione=" + dataCreazione
				+ ", titolo=" + titolo + ", copertina=" + copertina + ", stato=" + stato +", numero=" + numero
				+ ", highLights=" + highLights + "]";
	}
	
	

}