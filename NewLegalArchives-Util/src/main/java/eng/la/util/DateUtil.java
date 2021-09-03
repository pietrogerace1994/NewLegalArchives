package eng.la.util; 

	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


	public class DateUtil {
	    private DateUtil() {
	    }

	    public static int getAnno(Date date) {
	        int d = 0;
	        if (date != null) {
	            SimpleDateFormat df = new SimpleDateFormat("yyyy");
	            d = Integer.parseInt(df.format(date));
	        }
	        return d;
	    }
	    
	    public static int getAnnoAbbr(Date date) {
	        int d = 0;
	        if (date != null) {
	            SimpleDateFormat df = new SimpleDateFormat("yy");
	            d = Integer.parseInt(df.format(date));
	        }
	        return d;
	    }


	    public static int getMese(Date date) {
	        int d = 0;
	        if (date != null) {
	            SimpleDateFormat df = new SimpleDateFormat("MM");
	            d = Integer.parseInt(df.format(date));
	        }
	        return d;
	    }
	    
	    public static String getMeseParlante(Date date, String lang) {
	        String d = "";
	        if (date != null) {
	        	SimpleDateFormat df = null;
	        	
	        	if(lang.equals("IT"))
	        		df = new SimpleDateFormat("MMMM", Locale.ITALY);
	        	if(lang.equals("EN"))
	        		df = new SimpleDateFormat("MMMM", Locale.ENGLISH);
	        	
	            d = df.format(date);
	        }
	        return d;
	    }
	    
	    public static int getGiorno(Date date) {
	        int d = 0;
	        if (date != null) {
	            SimpleDateFormat df = new SimpleDateFormat("dd");
	            d = Integer.parseInt(df.format(date));
	        }
	        return d;
	    }

	    
	    public static Date toDateYYYYMMDD(String date) {
	        Date d = null;
	        try {
	            d = _dataCompattaYYYYMMDD.parse(date);
	        } catch (Exception e) {
	        	d = null;
	        }
	        return d;
	    }
	    
	    public static Date toDate_ddMMyyyy(String date) {
	        Date d = null;
	        try {
	            d = _dataCompatta_ddMMyyyy.parse(date);
	        } catch (Exception e) {
	        	d = null;
	        }
	        return d;
	    }

	    public static Date toDate(String date) {
	        Date d = null;
	        try {
	            d = _data.parse(date);
	        } catch (Exception e) {
	        	d = null;
	        }
	        return d;
	    }

	    public static String toDateForDDS(String date) {
			try {
				Date dateConverted = _data.parse(date);
				String dateFormatted = _dataForDDS.format(dateConverted);
				String dateFormattedForDDS = dateFormatted + "Z";
				return dateFormattedForDDS;
			} catch (Exception e) {
				return null;
			}
		}

	    public static Date toDateFromDB(String date) {
	        Date d = null;
	        try {
	            d = _dataDB.parse(date);
	        } catch (Exception e) {
	        	d = null;
	        }
	        return d;
	    }
	    
	    public static Date toDate(String formatoData, String date) {
	        Date d = null;
	        try {
	            d = new SimpleDateFormat(formatoData).parse(date);
	        } catch (Exception e) {
	        	d = null;
	        }
	        return d;
	    }

	    public static Date toDateTime(String date) {
	        Date d = null;
	        try {
	            d = _dataOraNoTrattino.parse(date);
	        } catch (Exception e) {
	        	d = null;
	        }
	        return d;
	    }
	    
	    
	    public static boolean isData(long date) {
	        return !"".equals(formattaData(date));
	    }

	    public static boolean isData(String date) {
	        Date d = toDate(date);
	        return d != null && _data.format(d).equals(date);
	    }
	    
	    public static boolean isYear(String year) {
	        SimpleDateFormat df = new SimpleDateFormat("yyyy");
	        try {
	            df.parse(year);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	    }

	    /*
	     * Format a long into a Date using the pattern MM/dd/yyyy - HH:mm:ss return
	     * empty if an error occurs
	     */
	    public static String formattaDataOra(long date) {
	        try {
	            return _dataOra.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }
	    
	    /*
	     * Format a Date into a Date using the pattern MM/dd/yyyy - HH:mm:ss return
	     * empty if an error occurs
	     */
	    public static String formattaDataOraDate(Date date) {
	        try {
	            return _dataOra.format(date);
	        } catch (Exception e) {
	        	
	        }
	        return "";
	    }
	    
	    public static String formattaDataOraNoTrattino(long date) {
	        try {
	            return _dataOraNoTrattino.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }
	    
	    public static String formattaDataCompatta(long date) {
	        try {
	            return _dataCompatta.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }

	    /*
	     * dataOra di tipo "dd/MM/yyyy - HH:mm:ss"
	     */
	    public static Date getDataOra(String dataOra) {
	        try {
	            return _dataOra.parse(dataOra);
	        } catch (Exception e) {

	        }
	        return null;
	    }

	    public static String formattaData(long date) {
	        try {
	            return _data.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }

	    public static String getDataYYYYMMDD(long date) {
	        try {
	            return _data_yyyymmdd.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }
	    
	    
	    public static String getDatayyyymmddHHmmssdupunti(long date) {
	        try {
	            return _data_yyyymmddHHmmss_duepunti.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    } 
	    
	    public static String getDataDDMMYYYY(long date) {
	        try {
	            return _data_ddmmyyyy.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }
	    public static String getDataDDMMYYYYtrattino(long date) {
	        try {
	            return _data_trattino.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }
	    public static String getOraDataHHmm(long date) {
	        try {
	            return _ora_data_HHmm.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }
	    
	    public static String getDatayyyymmddHHmmss(long date) {
	        try {
	            return _data_yyyymmddHHmmss.format(new Date(date));
	        } catch (Exception e) {

	        }
	        return "";
	    }        
	    public static Date getDataFutura(long data, int anno) {
	        try {

	            return _dataCompatta.parse((String) String.valueOf(Integer
	                    .parseInt(_dataCompatta.format(new Date(data))))
	                    + anno);
	        } catch (Exception e) {
	            return null;
	        }
	    }
	    
	    public static String toString_ddMMyyyy(Date data) {
	    	return new SimpleDateFormat("dd/MM/yyyy").format(data);  
	    }
	    
	    public static String toString_ORACLE_yyyyMMdd(Date data) {
	    	return new SimpleDateFormat("yyyy/MM/dd").format(data)+" 00:00:00";  
	    }
	    
	    public static String toString_ORACLE_yyyyMMdd_HHmmss(Date data) {
	    	return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(data);  
	    }
	    
	    
	    private final static SimpleDateFormat _dataOra = new SimpleDateFormat(
	            "dd/MM/yyyy - HH:mm:ss");

	    private final static SimpleDateFormat _dataOraNoTrattino = new SimpleDateFormat(
	            "dd/MM/yyyy HH:mm:ss");

	    private final static SimpleDateFormat _data = new SimpleDateFormat(
	            "dd/MM/yyyy");

		private final static SimpleDateFormat _dataForDDS = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS");

		private final static SimpleDateFormat _data_trattino = new SimpleDateFormat(
	            "dd-MM-yyyy");
	    
	    private final static SimpleDateFormat _dataDB = new SimpleDateFormat(
	            "dd-MMM-yy", Locale.ITALY);
	    
	    private final static SimpleDateFormat _dataCompatta = new SimpleDateFormat(
	    "ddMMyyyy");

	    private final static SimpleDateFormat _dataCompattaYYYYMMDD = new SimpleDateFormat(
	    "yyyyMMdd");
	    
	    private final static SimpleDateFormat _dataCompatta_ddMMyyyy = new SimpleDateFormat(
	    	    "dd/MM/yyyy");

	    private final static SimpleDateFormat _data_yyyymmdd = new SimpleDateFormat(
	            "yyyy-MM-dd");
	    private final static SimpleDateFormat _data_ddmmyyyy = new SimpleDateFormat(
	    "dd/MM/yyyy");
	    private final static SimpleDateFormat _ora_data_HHmm = new SimpleDateFormat(
	    "HH:mm");
	    private final static SimpleDateFormat _data_yyyymmddHHmmss = new SimpleDateFormat(
	    "yyyy-MM-dd-HH-mm-ss");
	    private final static SimpleDateFormat _data_yyyymmddHHmmss_duepunti = new SimpleDateFormat(
	    "yyyy-MM-dd HH:mm:ss");
		// FUNZIONE PER CALCOLO DIFFERENZA TRA DUE DATE
		public static int giorniDifferenza(String sdate1, String sdate2, String fmt, TimeZone tz) {
			SimpleDateFormat df = new SimpleDateFormat(fmt);
			Date date1  = null;
			Date date2  = null;
			try {
				date1 = df.parse(sdate1); 
				date2 = df.parse(sdate2); 
			}catch (ParseException pe){
				pe.printStackTrace();
			}
			Calendar cal1 = null; 
			Calendar cal2 = null;
			if (tz == null){
				cal1=Calendar.getInstance(); 
				cal2=Calendar.getInstance(); 
			}else{
				cal1=Calendar.getInstance(tz); 
				cal2=Calendar.getInstance(tz); 
			}
			// different date might have different offset
			cal1.setTime(date1);          
			long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
			cal2.setTime(date2);
			long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
			// Use integer calculation, truncate the decimals
			int hr1   = (int)(ldate1/3600000); //60*60*1000
			int hr2   = (int)(ldate2/3600000);
			int days1 = (int)hr1/24;
			int days2 = (int)hr2/24;
			int dateDiff  = days2 - days1;
//			int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK))<0 ? 1 : 0;
//			int weekDiff  = dateDiff/7 + weekOffset; 
//			int yearDiff  = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR); 
//			int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

			// RITORNA DIFFERENZA DATE
			return dateDiff;
		}
 
		public static long calcDiffGiorni(Date d1, Date d2) {
		    long diff = d2.getTime() - d1.getTime();
		    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		}
		
}
