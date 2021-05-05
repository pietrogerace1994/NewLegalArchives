package eng.la.model.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SemestreView extends BaseView{

	private static final long serialVersionUID = 1L;
	
	private String semestreStr; 
	private List<BigDecimal> capacita = new ArrayList<BigDecimal>();
	private List<BigDecimal> competenze = new ArrayList<BigDecimal>();
	private List<BigDecimal> costi = new ArrayList<BigDecimal>();
	private List<BigDecimal> flessibilita = new ArrayList<BigDecimal>();
	private List<BigDecimal> tempi = new ArrayList<BigDecimal>();
	private List<BigDecimal> autorevolezza = new ArrayList<BigDecimal>();
	private List<BigDecimal> totale = new ArrayList<BigDecimal>();
	private List<BigDecimal> reperibilita = new ArrayList<BigDecimal>();
	private List<VendorManagementView> votazioni = new ArrayList<VendorManagementView>();
	private String mediaCapacita;
	private String mediaCompetenze;
	private String mediaCosti;
	private String mediaFlessibilita;
	private String mediaTempi;
	private String mediaAutorevolezza;
	private String mediaReperibilita;
	private String mediaTotale;
	private String percentualeVotanti;
	private long id;
	
	/**
	 * @return the semestreStr
	 */
	public String getSemestreStr() {
		return semestreStr;
	}
	/**
	 * @param semestreStr the semestreStr to set
	 */
	public void setSemestreStr(String semestreStr) {
		this.semestreStr = semestreStr;
	}
	/**
	 * @return the capacita
	 */
	public List<BigDecimal> getCapacita() {
		return capacita;
	}
	/**
	 * @param capacita the capacita to set
	 */
	public void setCapacita(List<BigDecimal> capacita) {
		this.capacita = capacita;
	}
	/**
	 * @return the competenze
	 */
	public List<BigDecimal> getCompetenze() {
		return competenze;
	}
	/**
	 * @param competenze the competenze to set
	 */
	public void setCompetenze(List<BigDecimal> competenze) {
		this.competenze = competenze;
	}
	/**
	 * @return the costi
	 */
	public List<BigDecimal> getCosti() {
		return costi;
	}
	/**
	 * @param costi the costi to set
	 */
	public void setCosti(List<BigDecimal> costi) {
		this.costi = costi;
	}
	/**
	 * @return the flessibilita
	 */
	public List<BigDecimal> getFlessibilita() {
		return flessibilita;
	}
	/**
	 * @param flessibilita the flessibilita to set
	 */
	public void setFlessibilita(List<BigDecimal> flessibilita) {
		this.flessibilita = flessibilita;
	}
	/**
	 * @return the tempi
	 */
	public List<BigDecimal> getTempi() {
		return tempi;
	}
	/**
	 * @param tempi the tempi to set
	 */
	public void setTempi(List<BigDecimal> tempi) {
		this.tempi = tempi;
	}
	/**
	 * @return the autorevolezza
	 */
	public List<BigDecimal> getAutorevolezza() {
		return autorevolezza;
	}
	/**
	 * @param autorevolezza the autorevolezza to set
	 */
	public void setAutorevolezza(List<BigDecimal> autorevolezza) {
		this.autorevolezza = autorevolezza;
	}
	/**
	 * @return the reperibilita
	 */
	public List<BigDecimal> getReperibilita() {
		return reperibilita;
	}
	/**
	 * @param reperibilita the reperibilita to set
	 */
	public void setReperibilita(List<BigDecimal> reperibilita) {
		this.reperibilita = reperibilita;
	}
	/**
	 * @return the totale
	 */
	public List<BigDecimal> getTotale() {
		return totale;
	}
	/**
	 * @param totale the totale to set
	 */
	public void setTotale(List<BigDecimal> totale) {
		this.totale = totale;
	}
	/**
	 * @return the mediaCapacita
	 */
	public String getMediaCapacita() {
		return mediaCapacita;
	}
	/**
	 * @param mediaCapacita the mediaCapacita to set
	 */
	public void setMediaCapacita(String mediaCapacita) {
		this.mediaCapacita = mediaCapacita;
	}
	/**
	 * @return the mediaCompetenze
	 */
	public String getMediaCompetenze() {
		return mediaCompetenze;
	}
	/**
	 * @param mediaCompetenze the mediaCompetenze to set
	 */
	public void setMediaCompetenze(String mediaCompetenze) {
		this.mediaCompetenze = mediaCompetenze;
	}
	/**
	 * @return the mediaCosti
	 */
	public String getMediaCosti() {
		return mediaCosti;
	}
	/**
	 * @param mediaCosti the mediaCosti to set
	 */
	public void setMediaCosti(String mediaCosti) {
		this.mediaCosti = mediaCosti;
	}
	/**
	 * @return the mediaFlessibilita
	 */
	public String getMediaFlessibilita() {
		return mediaFlessibilita;
	}
	/**
	 * @param mediaFlessibilita the mediaFlessibilita to set
	 */
	public void setMediaFlessibilita(String mediaFlessibilita) {
		this.mediaFlessibilita = mediaFlessibilita;
	}
	/**
	 * @return the mediaTempi
	 */
	public String getMediaTempi() {
		return mediaTempi;
	}
	/**
	 * @param mediaTempi the mediaTempi to set
	 */
	public void setMediaTempi(String mediaTempi) {
		this.mediaTempi = mediaTempi;
	}
	/**
	 * @return the mediaAutorevolezza
	 */
	public String getMediaAutorevolezza() {
		return mediaAutorevolezza;
	}
	/**
	 * @param mediaAutorevolezza the mediaAutorevolezza to set
	 */
	public void setMediaAutorevolezza(String mediaAutorevolezza) {
		this.mediaAutorevolezza = mediaAutorevolezza;
	}
	/**
	 * @return the mediaReperibilita
	 */
	public String getMediaReperibilita() {
		return mediaReperibilita;
	}
	/**
	 * @param mediaReperibilita the mediaReperibilita to set
	 */
	public void setMediaReperibilita(String mediaReperibilita) {
		this.mediaReperibilita = mediaReperibilita;
	}
	/**
	 * @return the mediaTotale
	 */
	public String getMediaTotale() {
		return mediaTotale;
	}
	/**
	 * @param mediaTotale the mediaTotale to set
	 */
	public void setMediaTotale(String mediaTotale) {
		this.mediaTotale = mediaTotale;
	}
	
	/**
	 * @return the votazioni
	 */
	public List<VendorManagementView> getVotazioni() {
		return votazioni;
	}
	/**
	 * @param votazioni the votazioni to set
	 */
	public void setVotazioni(List<VendorManagementView> votazioni) {
		this.votazioni = votazioni;
	}
	
	/**
	 * @return the percentualeVotanti
	 */
	public String getPercentualeVotanti() {
		return percentualeVotanti;
	}
	/**
	 * @param percentualeVotanti the percentualeVotanti to set
	 */
	public void setPercentualeVotanti(String percentualeVotanti) {
		this.percentualeVotanti = percentualeVotanti;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	
	public BigDecimal getMediaVal(List<BigDecimal> listaVal){
		
		BigDecimal media = new BigDecimal(0);
		
		if(listaVal.size() > 0){
			
			for(BigDecimal val : listaVal){
				media = media.add(val);
			}
			media = media.divide(new BigDecimal(listaVal.size()), RoundingMode.HALF_UP);
		}
		return media;
	}
	
	public void addVotazione(VendorManagementView vendorManagementView){
		this.votazioni.add(vendorManagementView);
	}
	
	public void calcolaPercentuale(Long votanti, int voti){
		
		String resultStr = "0 %";
		
		if(votanti > 0 && voti > 0){
			
			double result = (((double)voti * 100) / (double)votanti);
			if(result > 0){
				NumberFormat formatter = new DecimalFormat("#0.00");
				resultStr = formatter.format(result) + "%";
			}
		}
		this.percentualeVotanti = resultStr;
	}
}
