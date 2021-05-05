package eng.la.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the LETTERA_INCARICO database table.
 * 
 */
/**
 * @author Andrea
 *
 */
@Entity
@Table(name="LETTERA_INCARICO")
@NamedQuery(name="LetteraIncarico.findAll", query="SELECT l FROM LetteraIncarico l")
public class LetteraIncarico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LETTERA_INCARICO_ID_GENERATOR", sequenceName="LETTERA_INCARICO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="LETTERA_INCARICO_ID_GENERATOR")
	private long id;

	@Column(name="BONUS_AMMINISTRATIVO_01")
	private BigDecimal bonusAmministrativo01;

	@Column(name="BONUS_AMMINISTRATIVO_02")
	private BigDecimal bonusAmministrativo02;

	@Column(name="BONUS_ARBITRALE_01")
	private BigDecimal bonusArbitrale01;

	@Column(name="BONUS_ARBITRALE_02")
	private BigDecimal bonusArbitrale02;

	@Column(name="BONUS_ARBITRALE_03")
	private BigDecimal bonusArbitrale03;

	@Column(name="BONUS_CIVILE_01")
	private BigDecimal bonusCivile01;

	@Column(name="BONUS_CIVILE_02")
	private BigDecimal bonusCivile02;

	@Column(name="BONUS_CIVILE_03")
	private BigDecimal bonusCivile03;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_PROTOCOLLO")
	private Date dataProtocollo;

	private String descrizione;

	private String lang;

	@Column(name="MESI_COMPENSO_STRAGIUDIZIALE")
	private BigDecimal mesiCompensoStragiudiziale;

	private String oggetto;

	@Column(name="PERC_ESITO_FAV_BONUS_ARBITR_02")
	private BigDecimal percEsitoFavBonusArbitr02;

	@Column(name="PERC_ESITO_FAV_BONUS_ARBITR_03")
	private BigDecimal percEsitoFavBonusArbitr03;

	@Column(name="PERC_ESITO_FAV_BONUS_CIVILE_02")
	private BigDecimal percEsitoFavBonusCivile02;

	@Column(name="PERC_ESITO_FAV_BONUS_CIVILE_03")
	private BigDecimal percEsitoFavBonusCivile03;

	private String protocollo;

	//bi-directional many-to-one association to Incarico
	@OneToMany(mappedBy="letteraIncarico")
	private Set<Incarico> incaricos;
	
	//bi-directional many-to-one association to TipoValuta
	@ManyToOne()
	@JoinColumn(name="TIPO_VALUTA_ID")
	private TipoValuta tipoValuta;
	
	
	@OneToMany(cascade = CascadeType.ALL, 
		        mappedBy = "letteraIncarico", orphanRemoval = true, fetch=FetchType.EAGER)
	@OrderBy("id ASC")
	private Set<Bonus> bonuss = new LinkedHashSet<Bonus>();
	
	
	@OneToMany(cascade = CascadeType.ALL, 
	        mappedBy = "letteraIncarico", orphanRemoval = true, fetch=FetchType.EAGER)
	@OrderBy("anno ASC")
	private Set<Acconti> accontis = new LinkedHashSet<Acconti>();
	
	@Column(name="IS_QUADRO")
	private boolean isQuadro;
	
	@Column(name="N_ACCORDO_QUADRO")
	private String nAccordoQuadro;
	
	@Lob
	@Column(name="ATTIVITA_STRAGIUDIZIALE_PENALE")
	private String attivitaStragiudizialePenale;
	
	@Column(name="LEGALE_INTERNO")
	private String legaleInterno;
	
	@Lob
	@Column(name="INFO_COMPENSO")
	private String infoCompenso;
	
	@Lob
	@Column(name="LIFECYCLE_XML")
	private String lifecycleXml;
	
	@Column(name="QUALIFICA")
	private String qualifica;
	
	@Column(name="COMPENSO")
	private BigDecimal compenso;
	
	@Column(name="INVIATA")
	private Integer inviata;
	
	@Column(name="DATA_INVIO")
	private Date dataInvio;
	
	@Column(name="RETURN_FILE")
	@Lob
	private Blob returnFile;
	
	@Column(name="RETURN_FILE_NAME")
	private String returnFileName;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "ID_DOCUMENTO_FIRMATO")
	private Documento documento_firmato;
	

	public LetteraIncarico() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

	

	public Documento getDocumento_firmato() {
		return documento_firmato;
	}

	public void setDocumento_firmato(Documento documento_firmato) {
		this.documento_firmato = documento_firmato;
	}

	public String getReturnFileName() {
		return returnFileName;
	}

	public void setReturnFileName(String returnFileName) {
		this.returnFileName = returnFileName;
	}

	public Blob getReturnFile() {
		return returnFile;
	}

	public void setReturnFile(Blob returnFile) {
		this.returnFile = returnFile;
	}

	public String getnAccordoQuadro() {
		return nAccordoQuadro;
	}

	public void setnAccordoQuadro(String nAccordoQuadro) {
		this.nAccordoQuadro = nAccordoQuadro;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Integer getInviata() {
		return inviata;
	}

	public void setInviata(Integer inviata) {
		this.inviata = inviata;
	}

	

	public BigDecimal getCompenso() {
		return compenso;
	}

	public void setCompenso(BigDecimal compenso) {
		this.compenso = compenso;
	}

	public boolean isQuadro() {
		return isQuadro;
	}

	public void setQuadro(boolean isQuadro) {
		this.isQuadro = isQuadro;
	}

	
	
	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public String getAttivitaStragiudizialePenale() {
		return attivitaStragiudizialePenale;
	}

	public void setAttivitaStragiudizialePenale(String attivitaStragiudizialePenale) {
		this.attivitaStragiudizialePenale = attivitaStragiudizialePenale;
	}

	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public String getInfoCompenso() {
		return infoCompenso;
	}

	public void setInfoCompenso(String infoCompenso) {
		this.infoCompenso = infoCompenso;
	}

	public String getLifecycleXml() {
		return lifecycleXml;
	}

	public void setLifecycleXml(String lifecycleXml) {
		this.lifecycleXml = lifecycleXml;
	}

	public BigDecimal getBonusAmministrativo01() {
		return this.bonusAmministrativo01;
	}

	public void setBonusAmministrativo01(BigDecimal bonusAmministrativo01) {
		this.bonusAmministrativo01 = bonusAmministrativo01;
	}

	public BigDecimal getBonusAmministrativo02() {
		return this.bonusAmministrativo02;
	}

	public void setBonusAmministrativo02(BigDecimal bonusAmministrativo02) {
		this.bonusAmministrativo02 = bonusAmministrativo02;
	}

	public BigDecimal getBonusArbitrale01() {
		return this.bonusArbitrale01;
	}

	public void setBonusArbitrale01(BigDecimal bonusArbitrale01) {
		this.bonusArbitrale01 = bonusArbitrale01;
	}

	public BigDecimal getBonusArbitrale02() {
		return this.bonusArbitrale02;
	}

	public void setBonusArbitrale02(BigDecimal bonusArbitrale02) {
		this.bonusArbitrale02 = bonusArbitrale02;
	}

	public BigDecimal getBonusArbitrale03() {
		return this.bonusArbitrale03;
	}

	public void setBonusArbitrale03(BigDecimal bonusArbitrale03) {
		this.bonusArbitrale03 = bonusArbitrale03;
	}

	public BigDecimal getBonusCivile01() {
		return this.bonusCivile01;
	}

	public void setBonusCivile01(BigDecimal bonusCivile01) {
		this.bonusCivile01 = bonusCivile01;
	}

	public BigDecimal getBonusCivile02() {
		return this.bonusCivile02;
	}

	public void setBonusCivile02(BigDecimal bonusCivile02) {
		this.bonusCivile02 = bonusCivile02;
	}

	public BigDecimal getBonusCivile03() {
		return this.bonusCivile03;
	}

	public void setBonusCivile03(BigDecimal bonusCivile03) {
		this.bonusCivile03 = bonusCivile03;
	}

	public Date getDataCancellazione() {
		return this.dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Date getDataProtocollo() {
		return this.dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
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

	public BigDecimal getMesiCompensoStragiudiziale() {
		return this.mesiCompensoStragiudiziale;
	}

	public void setMesiCompensoStragiudiziale(BigDecimal mesiCompensoStragiudiziale) {
		this.mesiCompensoStragiudiziale = mesiCompensoStragiudiziale;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public BigDecimal getPercEsitoFavBonusArbitr02() {
		return this.percEsitoFavBonusArbitr02;
	}

	public void setPercEsitoFavBonusArbitr02(BigDecimal percEsitoFavBonusArbitr02) {
		this.percEsitoFavBonusArbitr02 = percEsitoFavBonusArbitr02;
	}

	public BigDecimal getPercEsitoFavBonusArbitr03() {
		return this.percEsitoFavBonusArbitr03;
	}

	public void setPercEsitoFavBonusArbitr03(BigDecimal percEsitoFavBonusArbitr03) {
		this.percEsitoFavBonusArbitr03 = percEsitoFavBonusArbitr03;
	}

	public BigDecimal getPercEsitoFavBonusCivile02() {
		return this.percEsitoFavBonusCivile02;
	}

	public void setPercEsitoFavBonusCivile02(BigDecimal percEsitoFavBonusCivile02) {
		this.percEsitoFavBonusCivile02 = percEsitoFavBonusCivile02;
	}

	public BigDecimal getPercEsitoFavBonusCivile03() {
		return this.percEsitoFavBonusCivile03;
	}

	public void setPercEsitoFavBonusCivile03(BigDecimal percEsitoFavBonusCivile03) {
		this.percEsitoFavBonusCivile03 = percEsitoFavBonusCivile03;
	}

	public String getProtocollo() {
		return this.protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Set<Incarico> getIncaricos() {
		return this.incaricos;
	}

	public void setIncaricos(Set<Incarico> incaricos) {
		this.incaricos = incaricos;
	}

	public Incarico addIncarico(Incarico incarico) {
		getIncaricos().add(incarico);
		incarico.setLetteraIncarico(this);

		return incarico;
	}

	public Incarico removeIncarico(Incarico incarico) {
		getIncaricos().remove(incarico);
		incarico.setLetteraIncarico(null);

		return incarico;
	}

	public TipoValuta getTipoValuta() {
		return tipoValuta;
	}

	public void setTipoValuta(TipoValuta tipoValuta) {
		this.tipoValuta = tipoValuta;
	}

	public Set<Bonus> getBonus() {
		return bonuss;
	}
	
	public void addBonus(Bonus bonus) {
		bonuss.add(bonus);
		bonus.setLetteraIncarico(this);;
    }
 
    public void removeBonus(Bonus bonus) {
        bonus.setLetteraIncarico(null);
        this.bonuss.remove(bonus);
    }

	public Set<Acconti> getAcconti() {
		return accontis;
	}
	
	public void addAcconti(Acconti acconti) {
		accontis.add(acconti);
		acconti.setLetteraIncarico(this);;
    }
 
    public void removeAcconti(Acconti acconti) {
        acconti.setLetteraIncarico(null);
        this.accontis.remove(acconti);
    }
	
	
	

}