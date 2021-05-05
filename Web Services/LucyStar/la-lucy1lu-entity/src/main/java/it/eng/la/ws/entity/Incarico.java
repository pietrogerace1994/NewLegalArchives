package it.eng.la.ws.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the INCARICO database table.
 * 
 */
@Entity
@NamedQuery(name="Incarico.findAll", query="SELECT i FROM Incarico i")
public class Incarico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INCARICO_ID_GENERATOR", sequenceName="INCARICO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="INCARICO_ID_GENERATOR")
	private long id;

	@Column(name="COLLEGIO_ARBITRALE")
	private String collegioArbitrale;

	private String commento;

	private String motivazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_AUTORIZZAZIONE")
	private Date dataAutorizzazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_RICHIESTA_AUTOR_INCARICO")
	private Date dataRichiestaAutorIncarico;

	@Column(name="DENOM_STUDIO_ARBITRO_PRESIDEN")
	private String denomStudioArbitroPresiden;

	@Column(name="DENOMIN_STUDIO_ARBITRO_SEGRET")
	private String denominStudioArbitroSegret;

	@Column(name="DENOMINAZ_STUDIO_CONTROPARTE")
	private String denominazStudioControparte;

	@Column(name="INDIRIZZO_ARBITRO_CONTROPARTE")
	private String indirizzoArbitroControparte;

	@Column(name="INDIRIZZO_ARBITRO_PRESIDENTE")
	private String indirizzoArbitroPresidente;

	@Column(name="INDIRIZZO_ARBITRO_SEGRETARIO")
	private String indirizzoArbitroSegretario;

	private String lang;

	@Column(name="NOME_COLLEGIO_ARBITRALE")
	private String nomeCollegioArbitrale;

	@Column(name="NOME_INCARICO")
	private String nomeIncarico;

	@Column(name="NOMINATIVO_ARBITRO_CONTROPARTE")
	private String nominativoArbitroControparte;

	@Column(name="NOMINATIVO_PRESIDENTE")
	private String nominativoPresidente;

	@Column(name="NOMINATIVO_SEGRETARIO")
	private String nominativoSegretario;

	//bi-directional many-to-one association to ProfessionistaEsterno
	@ManyToOne()
	@JoinColumn(name="ID_PROFESSIONISTA_ESTERNO")
	private ProfessionistaEsterno professionistaEsterno;

	//bi-directional many-to-one association to Incarico
	@ManyToOne()
	@JoinColumn(name="ID_INCARICO_ARBITRALE")
	private Incarico incarico; 
	
	//bi-directional many-to-one association to RIncaricoProformaSocieta
	@OneToMany(mappedBy="incarico")
	private Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas;

	public Incarico() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCollegioArbitrale() {
		return this.collegioArbitrale;
	}

	public void setCollegioArbitrale(String collegioArbitrale) {
		this.collegioArbitrale = collegioArbitrale;
	}

	public String getCommento() {
		return this.commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Date getDataAutorizzazione() {
		return this.dataAutorizzazione;
	}

	public void setDataAutorizzazione(Date dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataRichiestaAutorIncarico() {
		return this.dataRichiestaAutorIncarico;
	}

	public void setDataRichiestaAutorIncarico(Date dataRichiestaAutorIncarico) {
		this.dataRichiestaAutorIncarico = dataRichiestaAutorIncarico;
	}

	public String getDenomStudioArbitroPresiden() {
		return this.denomStudioArbitroPresiden;
	}

	public void setDenomStudioArbitroPresiden(String denomStudioArbitroPresiden) {
		this.denomStudioArbitroPresiden = denomStudioArbitroPresiden;
	}

	public String getDenominStudioArbitroSegret() {
		return this.denominStudioArbitroSegret;
	}

	public void setDenominStudioArbitroSegret(String denominStudioArbitroSegret) {
		this.denominStudioArbitroSegret = denominStudioArbitroSegret;
	}

	public String getDenominazStudioControparte() {
		return this.denominazStudioControparte;
	}

	public void setDenominazStudioControparte(String denominazStudioControparte) {
		this.denominazStudioControparte = denominazStudioControparte;
	}

	public String getIndirizzoArbitroControparte() {
		return this.indirizzoArbitroControparte;
	}

	public void setIndirizzoArbitroControparte(String indirizzoArbitroControparte) {
		this.indirizzoArbitroControparte = indirizzoArbitroControparte;
	}

	public String getIndirizzoArbitroPresidente() {
		return this.indirizzoArbitroPresidente;
	}

	public void setIndirizzoArbitroPresidente(String indirizzoArbitroPresidente) {
		this.indirizzoArbitroPresidente = indirizzoArbitroPresidente;
	}

	public String getIndirizzoArbitroSegretario() {
		return this.indirizzoArbitroSegretario;
	}

	public void setIndirizzoArbitroSegretario(String indirizzoArbitroSegretario) {
		this.indirizzoArbitroSegretario = indirizzoArbitroSegretario;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNomeCollegioArbitrale() {
		return this.nomeCollegioArbitrale;
	}

	public void setNomeCollegioArbitrale(String nomeCollegioArbitrale) {
		this.nomeCollegioArbitrale = nomeCollegioArbitrale;
	}

	public String getNomeIncarico() {
		return this.nomeIncarico;
	}

	public void setNomeIncarico(String nomeIncarico) {
		this.nomeIncarico = nomeIncarico;
	}

	public String getNominativoArbitroControparte() {
		return this.nominativoArbitroControparte;
	}

	public void setNominativoArbitroControparte(String nominativoArbitroControparte) {
		this.nominativoArbitroControparte = nominativoArbitroControparte;
	}

	public String getNominativoPresidente() {
		return this.nominativoPresidente;
	}

	public void setNominativoPresidente(String nominativoPresidente) {
		this.nominativoPresidente = nominativoPresidente;
	}

	public String getNominativoSegretario() {
		return this.nominativoSegretario;
	}

	public void setNominativoSegretario(String nominativoSegretario) {
		this.nominativoSegretario = nominativoSegretario;
	}

	public ProfessionistaEsterno getProfessionistaEsterno() {
		return this.professionistaEsterno;
	}

	public void setProfessionistaEsterno(ProfessionistaEsterno professionistaEsterno) {
		this.professionistaEsterno = professionistaEsterno;
	}

	public Incarico getIncarico() {
		return this.incarico;
	}

	public void setIncarico(Incarico incarico) {
		this.incarico = incarico;
	}
	
	public Set<RIncaricoProformaSocieta> getRIncaricoProformaSocietas() {
		return this.RIncaricoProformaSocietas;
	}

	public void setRIncaricoProformaSocietas(Set<RIncaricoProformaSocieta> RIncaricoProformaSocietas) {
		this.RIncaricoProformaSocietas = RIncaricoProformaSocietas;
	}

	public RIncaricoProformaSocieta addRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().add(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setIncarico(this);

		return RIncaricoProformaSocieta;
	}

	public RIncaricoProformaSocieta removeRIncaricoProformaSocieta(RIncaricoProformaSocieta RIncaricoProformaSocieta) {
		getRIncaricoProformaSocietas().remove(RIncaricoProformaSocieta);
		RIncaricoProformaSocieta.setIncarico(null);

		return RIncaricoProformaSocieta;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	
	public String getObjectName(){
		if( collegioArbitrale.equals("T")){
			return getNomeCollegioArbitrale();
		}else{
			return getNomeIncarico();
		}
	}

	
}