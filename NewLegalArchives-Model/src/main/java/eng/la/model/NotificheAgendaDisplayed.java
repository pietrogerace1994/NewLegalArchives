package eng.la.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity(name = "NOTIFICHE_AGENDA_DISPLAYED")
public class NotificheAgendaDisplayed implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="NOTIFICHE_AGENDA_ID_GENERATOR", sequenceName="NOTIFICHE_AGENDA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="NOTIFICHE_AGENDA_ID_GENERATOR")
	private Long id;
	
	@Column(name="MATRICOLA_UTENTE", nullable=false)
	private String matricolaUtente;
	
	@Column(name="ID_EVENTO", nullable=false)
	private Long idEvento;
	
	@Column(name="ID_SCADENZA", nullable=false)
	private Long idScadenza;

	@Column(name="TIPO", nullable=false)
	private String tipo;
	
	@Column(name="DATA_DISPLAYED", nullable=false)
	private Date dataDisplayed;
	
	public String getMatricolaUtente() {
		return matricolaUtente;
	}

	public void setMatricolaUtente(String matricolaUtente) {
		this.matricolaUtente = matricolaUtente;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getIdScadenza() {
		return idScadenza;
	}

	public void setIdScadenza(Long idScadenza) {
		this.idScadenza = idScadenza;
	}



	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataDisplayed() {
		return dataDisplayed;
	}

	public void setDataDisplayed(Date dataDisplayed) {
		this.dataDisplayed = dataDisplayed;
	}
	
}