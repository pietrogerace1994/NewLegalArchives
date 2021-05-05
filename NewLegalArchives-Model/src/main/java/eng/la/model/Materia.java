package eng.la.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the MATERIA database table.
 * 
 */
@Entity
@NamedQuery(name="Materia.findAll", query="SELECT m FROM Materia m")
public class Materia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MATERIA_ID_GENERATOR", sequenceName="MATERIA_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="MATERIA_ID_GENERATOR")
	private long id;

	@Column(name="COD_GRUPPO_LINGUA")
	private String codGruppoLingua;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name="ID_MATERIA")
	private String idMateria;

	@Column(name="ID_MATERIA_PADRE")
	private String idMateriaPadre;

	private String lang;

	private String nome;

	//bi-directional many-to-one association to Materia
	@ManyToOne()
	@JoinColumn(name="ID_PADRE")
	private Materia materiaPadre;
 
	//bi-directional many-to-one association to RFascicoloMateria
	@OneToMany(mappedBy="materia")
	private Set<RFascicoloMateria> RFascicoloMaterias;

	//bi-directional many-to-one association to RSettoreGiuridicoMateria
	@OneToMany(mappedBy="materia")
	private Set<RSettoreGiuridicoMateria> RSettoreGiuridicoMaterias;
	
	//bi-directional many-to-one association to RBeautyContestMateria
	@OneToMany(mappedBy="materia")
	private Set<RBeautyContestMateria> RBeautyContestMaterias;

	public Materia() {
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

	public String getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}

	public String getIdMateriaPadre() {
		return this.idMateriaPadre;
	}

	public void setIdMateriaPadre(String idMateriaPadre) {
		this.idMateriaPadre = idMateriaPadre;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
   
	public Materia getMateriaPadre() {
		return materiaPadre;
	}

	public void setMateriaPadre(Materia materiaPadre) {
		this.materiaPadre = materiaPadre;
	}
 
	public Set<RFascicoloMateria> getRFascicoloMaterias() {
		return this.RFascicoloMaterias;
	}

	public void setRFascicoloMaterias(Set<RFascicoloMateria> RFascicoloMaterias) {
		this.RFascicoloMaterias = RFascicoloMaterias;
	}

	public RFascicoloMateria addRFascicoloMateria(RFascicoloMateria RFascicoloMateria) {
		getRFascicoloMaterias().add(RFascicoloMateria);
		RFascicoloMateria.setMateria(this);

		return RFascicoloMateria;
	}

	public RFascicoloMateria removeRFascicoloMateria(RFascicoloMateria RFascicoloMateria) {
		getRFascicoloMaterias().remove(RFascicoloMateria);
		RFascicoloMateria.setMateria(null);

		return RFascicoloMateria;
	}

	public Set<RSettoreGiuridicoMateria> getRSettoreGiuridicoMaterias() {
		return this.RSettoreGiuridicoMaterias;
	}

	public void setRSettoreGiuridicoMaterias(Set<RSettoreGiuridicoMateria> RSettoreGiuridicoMaterias) {
		this.RSettoreGiuridicoMaterias = RSettoreGiuridicoMaterias;
	}

	public RSettoreGiuridicoMateria addRSettoreGiuridicoMateria(RSettoreGiuridicoMateria RSettoreGiuridicoMateria) {
		getRSettoreGiuridicoMaterias().add(RSettoreGiuridicoMateria);
		RSettoreGiuridicoMateria.setMateria(this);

		return RSettoreGiuridicoMateria;
	}

	public RSettoreGiuridicoMateria removeRSettoreGiuridicoMateria(RSettoreGiuridicoMateria RSettoreGiuridicoMateria) {
		getRSettoreGiuridicoMaterias().remove(RSettoreGiuridicoMateria);
		RSettoreGiuridicoMateria.setMateria(null);

		return RSettoreGiuridicoMateria;
	}
	
	public Set<RBeautyContestMateria> getRBeautyContestMaterias() {
		return this.RBeautyContestMaterias;
	}

	public void setRBeautyContestMaterias(Set<RBeautyContestMateria> RBeautyContestMaterias) {
		this.RBeautyContestMaterias = RBeautyContestMaterias;
	}

}