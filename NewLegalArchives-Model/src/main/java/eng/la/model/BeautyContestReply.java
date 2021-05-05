package eng.la.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the BEAUTY_CONTEST database table.
 * 
 */
@Entity
@Table(name="BEAUTY_CONTEST_REPLY", schema = "LEG_ARC")
@NamedQuery(name="BeautyContestReply.findAll", query="SELECT bc FROM BeautyContestReply bc")
public class BeautyContestReply extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BEAUTY_CONTEST_REPLY_ID_GENERATOR", sequenceName = "LEG_ARC.BEAUTY_CONTEST_REPLY_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="BEAUTY_CONTEST_REPLY_ID_GENERATOR")
	private long id;

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
	@Column(name="DATA_INVIO")
	private Date dataInvio;
	
	//bi-directional many-to-one association to PROFESSIONISTA_ESTERNO
	@ManyToOne()
	@JoinColumn(name="ID_PROF_ESTERNO")
	private ProfessionistaEsterno professionista;

	//bi-directional many-to-one association to StatoBeautyContest
	@ManyToOne()
	@JoinColumn(name="ID_STATO_BCR")
	private StatoBCR statoBCR;
	
	@Column(name="DESCRIZIONE")
	private String descrizione;
	
	//bi-directional many-to-one association to RBeautyContestMateria
	@ManyToOne()
	@JoinColumn(name="ID_BEAUTY_CONTEST")
	private BeautyContest  beautyContest;
	
	private BigDecimal offerta_economica;
	
	
	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public BigDecimal getOfferta_economica() {
		return offerta_economica;
	}

	public void setOfferta_economica(BigDecimal offerta_economica) {
		this.offerta_economica = offerta_economica;
	}

	public BeautyContestReply() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public ProfessionistaEsterno getProfessionista() {
		return professionista;
	}

	public void setProfessionista(ProfessionistaEsterno professionista) {
		this.professionista = professionista;
	}

	public StatoBCR getStatoBCR() {
		return statoBCR;
	}

	public void setStatoBCR(StatoBCR statoBCR) {
		this.statoBCR = statoBCR;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BeautyContest getBeautyContest() {
		return beautyContest;
	}

	public void setBeautyContest(BeautyContest beautyContest) {
		this.beautyContest = beautyContest;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	

}