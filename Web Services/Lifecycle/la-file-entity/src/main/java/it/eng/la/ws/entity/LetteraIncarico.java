package it.eng.la.ws.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="LETTERA_INCARICO")
@NamedQueries({
@NamedQuery(name = "LetteraIncarico.findInviata", query = "SELECT li FROM LetteraIncarico li WHERE li.inviata=0"),
@NamedQuery(name="LetteraIncarico.byid", query="SELECT i FROM LetteraIncarico i WHERE i.id = :id")
})

public class LetteraIncarico implements Serializable 
{
	private static final long serialVersionUID = 3955824676073922122L;
	
	@Id
	@SequenceGenerator(name="LETTERA_INCARICO_GENERATOR", sequenceName="LETTERA_INCARICO_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="LETTERA_INCARICO_GENERATOR")
	private long id;

	@Column(name = "OGGETTO")
	private String oggetto;

	@Column(name = "PROTOCOLLO")
	private String protocollo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_PROTOCOLLO")
	private Date dataProtocollo;

	@Column(name = "MESI_COMPENSO_STRAGIUDIZIALE")
	private long mesiCompensoStragiudiziale;

	@Column(name = "BONUS_CIVILE_01")
	private long bonusCivile01;

	@Column(name = "BONUS_CIVILE_02")
	private long bonusCivile02;

	@Column(name = "PERC_ESITO_FAV_BONUS_CIVILE_02")
	private long percEsitoFavBonusCivile02;

	@Column(name = "BONUS_CIVILE_03")
	private long bonusCivile03;
	
	@Column(name = "PERC_ESITO_FAV_BONUS_CIVILE_03")
	private long percEsitoFavBonusCivile03;
	
	@Column(name = "BONUS_AMMINISTRATIVO_01")
	private long bonusAmministrativo01;
	
	@Column(name = "BONUS_AMMINISTRATIVO_02")
	private long bonusAmministrativo02;
	
	@Column(name = "BONUS_ARBITRALE_01")
	private long bonusArbitrale01;
	
	@Column(name = "BONUS_ARBITRALE_02")
	private long bonusArbitrale02;
	
	@Column(name = "PERC_ESITO_FAV_BONUS_ARBITR_02")
	private long percEsitoFavBonusArbritr02;

	@Column(name = "BONUS_ARBITRALE_03")
	private long bonusArbitrale03;

	@Column(name = "PERC_ESITO_FAV_BONUS_ARBITR_03")
	private long percEsitoFavBonusArbritr03;

	@Column(name = "LANG")
	private String lang;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name = "Compenso")
	private long compenso;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;

	@Column(name = "TIPO_VALUTA_ID")
	private long tipoValutaId;
	
	@Column(name="IS_QUADRO")
	private int isQuadro;
	
	@Column(name="ATTIVITA_STRAGIUDIZIALE_PENALE")
	private String attivitaStragiudizialePenale;
	
	@Column(name="INFO_COMPENSO")
	private String infoCompenso;
	
	@Column(name="LIFECYCLE_XML")
	private String lifecycleXml;
	
	@Column(name="QUALIFICA")
	private String qualifica;
	
	@Column(name="LEGALE_INTERNO")
	private String legaleInterno;
	
	@Column(name="INVIATA")
	private Integer inviata;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_INVIO")
	private Date dataInvio;
	
	@Column(name="RETURN_FILE")
	@Lob
	private byte[] returnFile;
	
	@Column(name="RETURN_FILE_NAME")
	private String returnFileName;

	public LetteraIncarico() {}

	public long getId() 
	{
		return this.id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}
	
	public String getOggetto() 
	{
		return oggetto;
	}

	public void setOggetto(String oggetto) 
	{
		this.oggetto = oggetto;
	}

	public String getProtocollo() 
	{
		return protocollo;
	}

	public void setProtocollo(String protocollo) 
	{
		this.protocollo = protocollo;
	}

	public Date getDataProtocollo() 
	{
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) 
	{
		this.dataProtocollo = dataProtocollo;
	}

	public long getMesiCompensoStragiudiziale() 
	{
		return mesiCompensoStragiudiziale;
	}

	public void setMesiCompensoStragiudiziale(long mesiCompensoStragiudiziale) 
	{
		this.mesiCompensoStragiudiziale = mesiCompensoStragiudiziale;
	}

	public long getBonusCivile01() 
	{
		return bonusCivile01;
	}

	public void setBonusCivile01(long bonusCivile01) 
	{
		this.bonusCivile01 = bonusCivile01;
	}

	public long getBonusCivile02() 
	{
		return bonusCivile02;
	}

	public void setBonusCivile02(long bonusCivile02) 
	{
		this.bonusCivile02 = bonusCivile02;
	}

	public long getPercEsitoFavBonusCivile02() 
	{
		return percEsitoFavBonusCivile02;
	}

	public void setPercEsitoFavBonusCivile02(long percEsitoFavBonusCivile02) 
	{
		this.percEsitoFavBonusCivile02 = percEsitoFavBonusCivile02;
	}

	public long getBonusCivile03() 
	{
		return bonusCivile03;
	}

	public void setBonusCivile03(long bonusCivile03) 
	{
		this.bonusCivile03 = bonusCivile03;
	}

	public long getPercEsitoFavBonusCivile03() 
	{
		return percEsitoFavBonusCivile03;
	}

	public void setPercEsitoFavBonusCivile03(long percEsitoFavBonusCivile03) 
	{
		this.percEsitoFavBonusCivile03 = percEsitoFavBonusCivile03;
	}

	public long getBonusAmministrativo01() 
	{
		return bonusAmministrativo01;
	}

	public void setBonusAmministrativo01(long bonusAmministrativo01) 
	{
		this.bonusAmministrativo01 = bonusAmministrativo01;
	}

	public long getBonusAmministrativo02() 
	{
		return bonusAmministrativo02;
	}

	public void setBonusAmministrativo02(long bonusAmministrativo02) 
	{
		this.bonusAmministrativo02 = bonusAmministrativo02;
	}

	public long getBonusArbitrale01() 
	{
		return bonusArbitrale01;
	}

	public void setBonusArbitrale01(long bonusArbitrale01) 
	{
		this.bonusArbitrale01 = bonusArbitrale01;
	}

	public long getBonusArbitrale02() 
	{
		return bonusArbitrale02;
	}

	public void setBonusArbitrale02(long bonusArbitrale02) 
	{
		this.bonusArbitrale02 = bonusArbitrale02;
	}

	public long getPercEsitoFavBonusArbritr02() 
	{
		return percEsitoFavBonusArbritr02;
	}

	public void setPercEsitoFavBonusArbritr02(long percEsitoFavBonusArbritr02) 
	{
		this.percEsitoFavBonusArbritr02 = percEsitoFavBonusArbritr02;
	}

	public long getBonusArbitrale03() 
	{
		return bonusArbitrale03;
	}

	public void setBonusArbitrale03(long bonusArbitrale03) 
	{
		this.bonusArbitrale03 = bonusArbitrale03;
	}

	public long getPercEsitoFavBonusArbritr03() 
	{
		return percEsitoFavBonusArbritr03;
	}

	public void setPercEsitoFavBonusArbritr03(long percEsitoFavBonusArbritr03) 
	{
		this.percEsitoFavBonusArbritr03 = percEsitoFavBonusArbritr03;
	}

	public String getLang() 
	{
		return lang;
	}

	public void setLang(String lang) 
	{
		this.lang = lang;
	}

	public Date getDataCancellazione() 
	{
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) 
	{
		this.dataCancellazione = dataCancellazione;
	}

	public long getCompenso() 
	{
		return compenso;
	}

	public void setCompenso(long compenso) 
	{
		this.compenso = compenso;
	}

	public String getDescrizione() 
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione) 
	{
		this.descrizione = descrizione;
	}

	public long getTipoValutaId() 
	{
		return tipoValutaId;
	}

	public void setTipoValutaId(long tipoValutaId) 
	{
		this.tipoValutaId = tipoValutaId;
	}

	public long getIsQuadro() 
	{
		return isQuadro;
	}

	public void setIsQuadro(int isQuadro) 
	{
		this.isQuadro = isQuadro;
	}

	public String getAttivitaStragiudizialePenale() 
	{
		return attivitaStragiudizialePenale;
	}

	public void setAttivitaStragiudizialePenale(String attivitaStragiudizialePenale) 
	{
		this.attivitaStragiudizialePenale = attivitaStragiudizialePenale;
	}

	public String getInfoCompenso() 
	{
		return infoCompenso;
	}

	public void setInfoCompenso(String infoCompenso) 
	{
		this.infoCompenso = infoCompenso;
	}

	public String getLifecycleXml() 
	{
		return lifecycleXml;
	}

	public void setLifecycleXml(String lifecycleXml) 
	{
		this.lifecycleXml = lifecycleXml;
	}

	public String getQualifica() 
	{
		return qualifica;
	}

	public void setQualifica(String qualifica) 
	{
		this.qualifica = qualifica;
	}

	public String getLegaleInterno() 
	{
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) 
	{
		this.legaleInterno = legaleInterno;
	}

	public Integer getInviata() {
		return inviata;
	}

	public void setInviata(Integer inviata) {
		this.inviata = inviata;
	}

	public Date getDataInvio() 
	{
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) 
	{
		this.dataInvio = dataInvio;
	}

	public String getReturnFileName() 
	{
		return returnFileName;
	}

	public void setReturnFileName(String returnFileName) 
	{
		this.returnFileName = returnFileName;
	}

	public byte[] getReturnFile() {
		return returnFile;
	}

	public void setReturnFile(byte[] returnFile) {
		this.returnFile = returnFile;
	}
}