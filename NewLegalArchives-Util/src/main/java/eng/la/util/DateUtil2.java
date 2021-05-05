package eng.la.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil2 {
	
	public static String PATTERN_ddMMyyyy = "dd/MM/yyyy";
	
	public static Calendar toCalendar(Date date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public static Date zeroTimePart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }
	
	public static Date addWeeks(final Date dataInput, int numeroSettimane) {
		Calendar calInput = Calendar.getInstance();
		calInput.setTime(dataInput);
		calInput.add(Calendar.WEEK_OF_MONTH, numeroSettimane);
		Date dataOutput = calInput.getTime();
		return dataOutput;
	}	
	
	public static Date addGiorni(final Date dataInput, int numeroGiorni) {
		Calendar calInput = Calendar.getInstance();
		calInput.setTime(dataInput);
		calInput.add(Calendar.DAY_OF_MONTH, numeroGiorni);
		Date dataOutput = calInput.getTime();
		return dataOutput;
	}
	
	public static Date subtractGiorni(final Date dataInput, int numeroGiorni) {
		Calendar calInput = Calendar.getInstance();
		calInput.setTime(dataInput);
		calInput.add(Calendar.DAY_OF_MONTH, -numeroGiorni);
		Date dataOutput = calInput.getTime();
		return dataOutput;
	}
	
	public static int compareWithoutTimePart(final Date date1, final Date date2) {
		Date date11 = DateUtil2.zeroTimePart(date1);
		Date date22 = DateUtil2.zeroTimePart(date2);
		int comp = date11.compareTo(date22);
		return comp;
	}
	
	public static String convert_DateToString(String formatoData,Date date) {
		if(formatoData==null)
			formatoData = PATTERN_ddMMyyyy;
		String dateOut = new SimpleDateFormat(formatoData).format(date);
		return dateOut;
	}
	
	public static Date convert_StringToDate(String formatoData,String dateStr) {
		if(formatoData==null)
			formatoData = PATTERN_ddMMyyyy;
		Date dateOut=null;
		try {
			dateOut = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return dateOut;
	}
	
	public static List<Date> estraiDateSemestre(String semestre){
		
		List<Date> inizioFine = new ArrayList<Date>();
		
		Date dataInizio = null;
		Date dataFine = null;
		
		String anno = semestre.substring(semestre.lastIndexOf(" ") + 1);
		
		if(semestre.indexOf("II") > -1){
			dataInizio = DateUtil.toDate("01/07/" + anno);
			dataFine = DateUtil.toDate("31/12/" + anno);
		}else{
			dataInizio = DateUtil.toDate("01/01/" + anno);
			dataFine = DateUtil.toDate("30/06/" + anno);
		}
		
		if(dataInizio != null){
			inizioFine.add(dataInizio);
			inizioFine.add(dataFine);
		}
		return inizioFine;
	}
	
	public static List<Date> estraiDateSemestreSuccessivo(String semestre){
		
		List<Date> inizioFine = new ArrayList<Date>();
		
		Date dataInizio = null;
		Date dataFine = null;
		
		String anno = semestre.substring(semestre.lastIndexOf(" ") + 1);
		int annoInt = Integer.parseInt(anno);
		
		if(semestre.indexOf("II") > -1){
			annoInt = annoInt + 1;
			dataInizio = DateUtil.toDate("01/01/" + annoInt);
			dataFine = DateUtil.toDate("30/06/" + annoInt);
		}else{
			dataInizio = DateUtil.toDate("01/07/" + anno);
			dataFine = DateUtil.toDate("31/12/" + anno);
		}
		if(dataInizio != null){
			inizioFine.add(dataInizio);
			inizioFine.add(dataFine);
		}
		return inizioFine;
	}
	
	/** Estrae il semestre in base alla data attuale **/
	public static List<String> estraiSemestre(){
		List<String> semestre = new ArrayList<String>();
		Date now = new Date();
		
		int anno = DateUtil.getAnno(now);
		int mese = DateUtil.getMese(now);
		
		if( mese>=1 && mese<=6){
			anno = anno - 1;
			semestre.add("01/07/" + anno);
			semestre.add("31/12/" + anno);
		}
		else{
			semestre.add("01/01/" + anno) ;
			semestre.add("30/06/" + anno);
		}
		return semestre;
	}
	
	/** Estrae il semestre precedente in base alla data attuale **/
	public static List<Date> estraiDateSemestrePrecedente(){
		List<Date> semestre = new ArrayList<Date>();
		Date now = new Date();
		
		int anno = DateUtil.getAnno(now);
		int mese = DateUtil.getMese(now);
		
		if( mese>=1 && mese<=6){
			anno = anno - 1;
			semestre.add(DateUtil.toDate("01/07/" + anno));
			semestre.add(DateUtil.toDate("31/12/" + anno));
		}
		else{
			semestre.add(DateUtil.toDate("01/01/" + anno)) ;
			semestre.add(DateUtil.toDate("30/06/" + anno));
		}
		return semestre;
	}
	
	/** Estrae il semestre in base alla data attuale **/
	public static List<Date> estraiDateSemestre(){
		List<Date> semestre = new ArrayList<Date>();
		Date now = new Date();
		
		int anno = DateUtil.getAnno(now);
		int mese = DateUtil.getMese(now);
		
		if( mese>=1 && mese<=6){
			semestre.add(DateUtil.toDate("01/01/" + anno)) ;
			semestre.add(DateUtil.toDate("30/06/" + anno));
		}
		else{
			semestre.add(DateUtil.toDate("01/07/" + anno));
			semestre.add(DateUtil.toDate("31/12/" + anno));
		}
		return semestre;
	}
	
	/** Estrae la stringa rappresentante il semestre in base alla data in input **/
	public static String estraiSemestreDaData(Date dataValutazione) throws Throwable{
		String semestreRiferimento = null;	
		int anno = DateUtil.getAnno(dataValutazione);
		int mese = DateUtil.getMese(dataValutazione);

		if( mese>=1 && mese<=6){
			
			anno = anno - 1;

			semestreRiferimento = "II - "+anno;
		}
		else{
			semestreRiferimento = "I - "+anno;
		}

		return semestreRiferimento;
	}
	
	public static String estraiTrimestreDaData(Date data){
		String trimestre = null;
		int anno = DateUtil.getAnno(data);
		int mese = DateUtil.getMese(data);
		
		if(mese >= 1 && mese <= 3){
			trimestre = "I - "+ anno;
		}
		else if(mese >= 4 && mese <= 6){
			trimestre = "II - "+ anno;
		}
		else if(mese >= 7 && mese <= 9){
			trimestre = "III - "+ anno;
		}
		else if(mese >= 10 && mese <= 12){
			trimestre = "IV - "+ anno;
		}
		
		return trimestre;
	}
	
	public static String estraiTrimestreDelayedDaData(Date data, int delay){
		String trimestre = null;
		
		Date dataDelayed = addGiorni(data, -delay);
		
		int anno = DateUtil.getAnno(dataDelayed);
		int mese = DateUtil.getMese(dataDelayed);
		
		if(mese >= 1 && mese <= 3){
			trimestre = "I - "+ anno;
		}
		else if(mese >= 4 && mese <= 6){
			trimestre = "II - "+ anno;
		}
		else if(mese >= 7 && mese <= 9){
			trimestre = "III - "+ anno;
		}
		else if(mese >= 10 && mese <= 12){
			trimestre = "IV - "+ anno;
		}
		
		return trimestre;
	}
	
	public static boolean stessoTrimestre(Date dataModifica, Date dataAttuale){
		boolean result = false;
		
		if(dataModifica != null && dataAttuale != null){
			
			String trimestreDataModifica = estraiTrimestreDaData(dataModifica);
			String trimestreDataAttuale = estraiTrimestreDaData(dataAttuale);
			
			if(trimestreDataModifica.equals(trimestreDataAttuale)){
				result = true;
			}
		}
		
		return result;
	}
	
	public static List<Date> estraiIntervalloDateTrimestre(String trimestre){
		
		List<Date> inizioFine = new ArrayList<Date>();
		
		Date dataInizio = null;
		Date dataFine = null;
		
		String anno = trimestre.substring(trimestre.lastIndexOf(" ") + 1);
		
		if(trimestre.indexOf("IV - ") > -1){
			dataInizio = DateUtil.toDate("01/10/" + anno);
			dataFine = DateUtil.toDate("31/12/" + anno);
		}
		else if(trimestre.indexOf("III - ") > -1){
			dataInizio = DateUtil.toDate("01/07/" + anno);
			dataFine = DateUtil.toDate("30/09/" + anno);
		}
		else if(trimestre.indexOf("II - ") > -1){
			dataInizio = DateUtil.toDate("01/04/" + anno);
			dataFine = DateUtil.toDate("30/06/" + anno);
		}
		else if(trimestre.indexOf("I - ") > -1){
			dataInizio = DateUtil.toDate("01/01/" + anno);
			dataFine = DateUtil.toDate("31/03/" + anno);
		}
		
		if(dataInizio != null && dataFine != null){
			inizioFine.add(dataInizio);
			inizioFine.add(dataFine);
		}
		return inizioFine;
	}
	
	public static String calcolaTrimestrePrecedente(String trimestre){
		
		String trimestrePrecedente = "";
		
		if(trimestre != null && !trimestre.isEmpty()){
			
			int anno = Integer.parseInt(trimestre.substring(trimestre.lastIndexOf(" ") + 1));
			
			if(trimestre.indexOf("IV - ") > -1){
				trimestrePrecedente = "III - " + anno;
			}
			else if(trimestre.indexOf("III - ") > -1){
				trimestrePrecedente = "II - " + anno;
			}
			else if(trimestre.indexOf("II - ") > -1){
				trimestrePrecedente = "I - " + anno;
			}
			else if(trimestre.indexOf("I - ") > -1){
				trimestrePrecedente = "IV - " + (anno - 1);
			}
		}
		return trimestrePrecedente;
	}
}
