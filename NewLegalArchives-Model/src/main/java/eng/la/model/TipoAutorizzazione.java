package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TIPO_AUTORIZZAZIONE database table.
 * 
 */
@Entity
@Table(name="TIPO_AUTORIZZAZIONE")
@NamedQuery(name="TipoAutorizzazione.findAll", query="SELECT t FROM TipoAutorizzazione t")
public class TipoAutorizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_AUTORIZZAZIONE_ID_GENERATOR", sequenceName="TIPO_AUTORIZZAZIONE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="TIPO_AUTORIZZAZIONE_ID_GENERATOR")
	private long id;
	
	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String descrizione;
	
	private String lang;

	public TipoAutorizzazione() {
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

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getLang() {
		return lang;
	}
}