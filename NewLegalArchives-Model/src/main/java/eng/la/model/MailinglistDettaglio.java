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
@Table(name = "MAILINGLIST_DETTAGLIO")
public class MailinglistDettaglio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MAILINGLIST_DETTAGLIO_ID_GENERATOR", sequenceName = "MAILINGLIST_DETTAGLIO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "MAILINGLIST_DETTAGLIO_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name = "ID_MAILINGLIST")
	private Mailinglist mailinglist;

	@ManyToOne
	@JoinColumn(name = "ID_RUBRICA")
	private Rubrica rubrica;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Mailinglist getMailinglist() {
		return mailinglist;
	}

	public void setMailinglist(Mailinglist mailinglist) {
		this.mailinglist = mailinglist;
	}

	public Rubrica getRubrica() {
		return rubrica;
	}

	public void setRubrica(Rubrica rubrica) {
		this.rubrica = rubrica;
	}

}
