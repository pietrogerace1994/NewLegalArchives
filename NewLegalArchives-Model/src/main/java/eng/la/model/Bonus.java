package eng.la.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The persistent class for the EMAIL database table.
 * 
 */
@Entity
@NamedQuery(name = "Bonus.findAll", query = "SELECT e FROM Bonus e")
public class Bonus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "BONUS_ID_GENERATOR", sequenceName = "BONUS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BONUS_ID_GENERATOR")
	private long id;
	
	

	@Column(name = "IMPORTO")
	private BigDecimal importo;

	@Column(name = "DESCRIZIONE")
	private String descrizione;

	@ManyToOne()
	@JoinColumn(name="ID_LETTERA_INCARICO")
	private LetteraIncarico letteraIncarico;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LetteraIncarico getLetteraIncarico() {
		return letteraIncarico;
	}

	public void setLetteraIncarico(LetteraIncarico letteraIncarico) {
		this.letteraIncarico = letteraIncarico;
	}
	
	


	
	

}