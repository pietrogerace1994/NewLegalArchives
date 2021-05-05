package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CONFIGURAZIONE database table.
 * 
 */
@Entity
@NamedQuery(name="Configurazione.findAll", query="SELECT c FROM Configurazione c")
public class Configurazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONFIGURAZIONE_ID_GENERATOR", sequenceName="CONFIGURAZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CONFIGURAZIONE_ID_GENERATOR")
	private long id;

	@Column(name="CD_KEY")
	private String cdKey;

	@Column(name="CD_VALUE")
	private String cdValue;

	public Configurazione() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCdKey() {
		return this.cdKey;
	}

	public void setCdKey(String cdKey) {
		this.cdKey = cdKey;
	}

	public String getCdValue() {
		return this.cdValue;
	}

	public void setCdValue(String cdValue) {
		this.cdValue = cdValue;
	}

}