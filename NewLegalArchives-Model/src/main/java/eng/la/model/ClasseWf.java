package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the CLASSE_WF database table.
 * 
 */
@Entity
@Table(name="CLASSE_WF")
@NamedQuery(name="ClasseWf.findAll", query="SELECT c FROM ClasseWf c")
public class ClasseWf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CLASSE_WF_ID_GENERATOR", sequenceName="CLASSE_WF_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="CLASSE_WF_ID_GENERATOR")
	private long id;

	private String codice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	private String nome;

	//bi-directional many-to-one association to ConfigurazioneStepWf
	@OneToMany(mappedBy="classeWf")
	private Set<ConfigurazioneStepWf> configurazioneStepWfs;

	public ClasseWf() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<ConfigurazioneStepWf> getConfigurazioneStepWfs() {
		return this.configurazioneStepWfs;
	}

	public void setConfigurazioneStepWfs(Set<ConfigurazioneStepWf> configurazioneStepWfs) {
		this.configurazioneStepWfs = configurazioneStepWfs;
	}

	public ConfigurazioneStepWf addConfigurazioneStepWf(ConfigurazioneStepWf configurazioneStepWf) {
		getConfigurazioneStepWfs().add(configurazioneStepWf);
		configurazioneStepWf.setClasseWf(this);

		return configurazioneStepWf;
	}

	public ConfigurazioneStepWf removeConfigurazioneStepWf(ConfigurazioneStepWf configurazioneStepWf) {
		getConfigurazioneStepWfs().remove(configurazioneStepWf);
		configurazioneStepWf.setClasseWf(null);

		return configurazioneStepWf;
	}

}