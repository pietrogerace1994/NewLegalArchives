package eng.la.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the LIVELLO_ATTRIBUZIONI_II database table.
 * 
 */
@Entity
@Table(name="LIVELLO_ATTRIBUZIONI_II")
@NamedQuery(name="LivelloAttribuzioniII.findAll", query="SELECT r FROM LivelloAttribuzioniII r")
public class LivelloAttribuzioniII implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LIVELLO_ATTRIBUZIONI_II_ID_GENERATOR", sequenceName="LIVELLO_ATTRIBUZIONI_II_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="LIVELLO_ATTRIBUZIONI_II_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to SchedaFondoRischi
	@OneToMany(mappedBy="livelloAttribuzioniII")
	private Set<Procure> procures;


	public LivelloAttribuzioniII() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodGruppoLingua() {
		return this.codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	 
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Set<Procure> getProcures() {
		return this.procures;
	}

	public void setProcures(Set<Procure> procures) {
		this.procures = procures;
	}

	public Procure addProcure(Procure procure) {
		getProcures().add(procure);
		procure.setLivelloAttribuzioniII(this);
		return procure;
	}

	public Procure removeProcure(Procure procure) {
		getProcures().remove(procure);
		procure.setLivelloAttribuzioniII(null);
		return procure;
	}
}