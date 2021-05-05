package eng.la.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NEWSLETTER_EMAIL")
public class NewsletterEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NEWSLETTER_EMAIL_ID_GENERATOR", sequenceName = "NEWSLETTER_EMAIL_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NEWSLETTER_EMAIL_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name = "ID_NEWSLETTER")
	private Newsletter newsletter;
	
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public Newsletter getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
