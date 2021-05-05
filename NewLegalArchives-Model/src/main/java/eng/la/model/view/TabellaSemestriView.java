package eng.la.model.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class TabellaSemestriView extends BaseView{

	private static final long serialVersionUID = 1L;
	
	private List<SemestreView> semestre = new ArrayList<SemestreView>();
	private String totale;
	private String nazioneDesc;
	private String specializzazioneDesc;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getNazioneDesc() {
		return nazioneDesc;
	}
	public void setNazioneDesc(String nazioneDesc) {
		this.nazioneDesc = nazioneDesc;
	}
	public String getSpecializzazioneDesc() {
		return specializzazioneDesc;
	}
	public void setSpecializzazioneDesc(String specializzazioneDesc) {
		this.specializzazioneDesc = specializzazioneDesc;
	}
	public String getTotale() {
		return totale;
	}
	public void setTotale(String totale) {
		this.totale = totale;
	}
	/**
	 * @return the semestre
	 */
	public List<SemestreView> getSemestre() {
		return semestre;
	}
	/**
	 * @param semestre the semestre to set
	 */
	public void setSemestre(List<SemestreView> semestre) {
		this.semestre = semestre;
	}
	
	
	/** Per ogni semestre della tabella calcola la media di tutte le votazioni  **/
	public void calcolaMediaSemestri(){
		
		if(this.getSemestre().size() > 0){
			
			for(SemestreView semestreView : this.getSemestre()){
				
				semestreView.setMediaAutorevolezza(semestreView.getMediaVal(semestreView.getAutorevolezza()).toString());
				semestreView.setMediaCapacita(semestreView.getMediaVal(semestreView.getCapacita()).toString());
				semestreView.setMediaCompetenze(semestreView.getMediaVal(semestreView.getCompetenze()).toString());
				semestreView.setMediaCosti(semestreView.getMediaVal(semestreView.getCosti()).toString());
				semestreView.setMediaFlessibilita(semestreView.getMediaVal(semestreView.getFlessibilita()).toString());
				semestreView.setMediaTempi(semestreView.getMediaVal(semestreView.getTempi()).toString());
				semestreView.setMediaReperibilita(semestreView.getMediaVal(semestreView.getReperibilita()).toString());
				semestreView.setMediaTotale(semestreView.getMediaVal(semestreView.getTotale()).toString());
			}
		}
	}
	
	public void calcolaTotale(){
		
		if(this.getSemestre().size() > 0){
			
			BigDecimal tot = new BigDecimal(0);
			
			for(SemestreView semestreView : this.getSemestre()){
				
				if(semestreView.getMediaTotale() != null && !semestreView.getMediaTotale().isEmpty()){
					
					tot = tot.add(new BigDecimal(semestreView.getMediaTotale()));
				}
			}
			if(!tot.equals(new BigDecimal(0))){
				tot = tot.divide(new BigDecimal(this.getSemestre().size()), RoundingMode.HALF_UP);
			}
			this.totale = tot.toString();
		}
	}
}
