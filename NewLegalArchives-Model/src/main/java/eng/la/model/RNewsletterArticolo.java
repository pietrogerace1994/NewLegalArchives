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
 * The persistent class for the R_PROF_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="R_NEWSLETTER_ARTICOLO")
@NamedQuery(name="RNewsletterArticolo.findAll", query="SELECT d FROM RNewsletterArticolo d")
public class RNewsletterArticolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_NEWSLETTER_ARTICOLO_ID_GENERATOR", sequenceName="R_NEWSLETTER_ARTICOLO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="R_NEWSLETTER_ARTICOLO_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;
 
	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_NEWSLETTER")
	private Newsletter newsletter;

	@ManyToOne()
	@JoinColumn(name="ID_ARTICOLO")
	private Articolo articolo;
	
	public RNewsletterArticolo() {
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

	public Newsletter getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	

}