package eng.la.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The persistent class for the EMAIL database table.
 * 
 */
@Entity
@NamedQuery(name = "Acconti.findAll", query = "SELECT e FROM Acconti e")
public class Acconti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ACCONTI_ID_GENERATOR", sequenceName = "ACCONTI_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ACCONTI_ID_GENERATOR")
	private long id;
	
	

	@Column(name = "IMPORTO")
	private BigDecimal importo;

	@Column(name = "ANNO")
	private String anno;

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

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public LetteraIncarico getLetteraIncarico() {
		return letteraIncarico;
	}

	public void setLetteraIncarico(LetteraIncarico letteraIncarico) {
		this.letteraIncarico = letteraIncarico;
	}
	
	


	
	

}