package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;

import eng.la.model.audit.AuditedObjectName;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the STATO_BEAUTY_CONTEST database table.
 * 
 */
@Entity
@Table(name="STATO_BCR", schema = "LEG_ARC")
@NamedQuery(name="StatoBCR.findAll", query="SELECT s FROM StatoBCR s")
public class StatoBCR implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATO_BCR_ID_GENERATOR", sequenceName="LEG_ARC.STATO_BCR_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="STATO_BCR_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;

	private String lang;

	//bi-directional many-to-one association to BeautyContest
	@OneToMany(mappedBy="statoBCR")
	private Set<BeautyContestReply> beautyContestReplys;
	
	


	public StatoBCR() {
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

	@AuditedObjectName
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

	public Set<BeautyContestReply> getBeautyContestReplys() {
		return beautyContestReplys;
	}

	public void setBeautyContestReplys(Set<BeautyContestReply> beautyContestReplys) {
		this.beautyContestReplys = beautyContestReplys;
	}

	

	

}