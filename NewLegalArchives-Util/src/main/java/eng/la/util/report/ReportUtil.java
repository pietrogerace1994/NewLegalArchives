package eng.la.util.report;


//import com.eni.icteam.legalarchives.common.utils.URLEncodingUtils;
//import com.eni.icteam.legalarchives.model.Materia;
//import com.eni.icteam.legalarchives.model.TipoOperatoreValoreCausa;
//import com.eni.icteam.legalarchives.model.dto.FascicoloDynamicAggregatorRpt;
//import com.eni.icteam.legalarchives.model.dto.FascicoloDynamicSubAggregatorRpt;
//import com.eni.icteam.legalarchives.model.dto.FascicoloRpt;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Classe di utility per i report.
 * 
 * @author ACER
 */
public class ReportUtil {

    private static Logger logger = LoggerFactory.getLogger(ReportUtil.class);

	/** 
	 * Ritorna la label associata alla lingua corrente.
	 *
	 * @param language  codice della lingua "it" "en"  etc.
	 * @param list lista delle decodifiche in formato "it:etichetta","en:label",..
	 * @return label
	 */
	public static String getLabel(String language,List<String>list) {
		return getLabel(language,list,"");
	}
	
	/**
	 * Metodo che ritorna
	 * <p> 
	 * @param language la lingua
	 * @param list una lista
	 * @param defaultValue valore di default
	 * @return
	 */
	public static String getLabel(String language,List<String>list,String defaultValue) {
		if ( language==null ) {
			return "##Language not Defined##";
		}

		if ( list == null ) {
			return defaultValue;
		}

		try{
			for ( int i=0; i<list.size(); i++) {
				String element = list.get(i);
				if ( element!=null && element.length()>3 && element.substring(2,3).equals(":") && element.substring(0, 2).equals(language) ) {
					return element.substring(3);
				}
			}
			
			//default : ricerca in italiano
			for ( int i=0; i<list.size(); i++) {
				String element = list.get(i);
				if (  element!=null && element.length()>3 &&  element.substring(2,3).equals(":") && element.substring(0, 2).equals("it") ) {
					return element.substring(3);
				}
			}
			//ritorno tutti gli elementi
			String element =null;
			for ( int i=0; i<list.size(); i++) {
				if (element==null) {
					element = "";
				} else {
					element+=";";
				}
				element += list.get(i);
			}
			return element == null? "" : element;
		} catch (Exception e) {
			return "##List of Label malformed##";
		}
	}

	public static String printList(List<String>list,String separator ) {
		//ritorno tutti gli elementi
		StringBuilder result = new StringBuilder();

		for ( int i=0; i<list.size(); i++) {
			if (i>0) {
				result.append(separator);
			}
			result.append(list.get(i));
		}
		return result.toString();
	}

	public static String printList(List<String>list,String separator,String language ) {
		//ritorno tutti gli elementi
		StringBuilder result = new StringBuilder();
		boolean first=true;
		for ( int i=0; i<list.size(); i++) {
			String element = list.get(i);
			if ( element.length()> 3 && element.substring(2,3).equals(":") && element.substring(0, 2).equals(language) ) {
				if (!first) {
          result.append(separator);
        }
				result.append(element.substring(3));
				first=false;
			}
		}
		return result.toString();
	}

	/**
	 * Stampa il testo fieldValue .
	 * Se fieldValue e' null ritorna stringa vuota.
	 * 
	 * @param fieldValue 
	 */
	public static String printText(String fieldValue) {
		return fieldValue==null?"":fieldValue;
	}

	/**
	 *
	 * @param list  $P{JASPER_REPORT}.getFields()
	 * @param fieldName "nomeCampo"
	 * @param datasource $P{REPORT_DATA_SOURCE}
	 * @return object se questo viene trovato nel dataSource altrimenti null
	 */
	public static Object getJRFieldByName(JRField[] list,String fieldName,JRDataSource datasource)
	throws JRException {
		Object result =null;
		for ( int i=0;i<list.length ; i++ ) {
			if ( list[i].getName().equals(fieldName) ) {
				return datasource.getFieldValue(  list[i] );
			}
		}
		return result;
	}

	/**
	 * Stampa il testo delle stringhe concatenandole e separandole con carattere indicato. 
	 * Se fieldValue e' null ritorna stringa vuota.
	 * <p>
	 * @param lista oggetti/stringhe. 
	 */
    public static String printText(List<String> listValue, String separator) {
        if(listValue==null)
            return null;
        StringBuilder result = null;
        for (Object item : listValue) {
            if (item instanceof String) {
                if (result == null) {
                    result = new StringBuilder();
                } else {
                    result.append(separator);
                }
                result.append(item);
            }
        }
        return result == null ? "" : result.toString();
    }

    //U224-LGA.LGA.2014.65998.Reportistica
    public static String printObjectList(List listValue, String separator) {
        StringBuilder result = null;
        for (Object item : listValue) {
            if (result == null) {
                result = new StringBuilder();
            } else {
                result.append(separator);
            }
            result.append(item);
        }

        return result == null ? "" : result.toString();
    }

    /**Stampa il testo delle stringhe concatenandole e separandole con carattere indicato .Se fieldValue e' null ritorna stringa vuota*/
	/*public static String printIndentedText(String value,int level) {
		StringBuilder result = new StringBuilder(value);
		for ( int i=0 ; i<level ; i++ )
			result.insert(0,"          ");
		return result.toString();
	}*/

	public static Boolean isLevel(Boolean valid,Integer level ,int leveltocompare) {
		return (valid && (level == leveltocompare) );
	}
	public static Boolean isLevel(Boolean valid,Integer level ,int rangeStart,int rangeEnd) {
		return (valid && level >= rangeStart && level <=rangeEnd );
	}

	public static Boolean isEmpty(String value) {
		return ( value==null || value.length()==0 );
	}


	private static Locale _locale;
	
	public static String printDate(String language,String country,Date date) {
		return printDate( new Locale(language, country),date);
	}
	public static String printDate(Locale locale,Date date) {
		return printDate(locale,null,date);
	}
	public static String printDate(Locale locale,TimeZone timezone,Date dateIn) {
		if ( dateIn==null ) {
			return "";
    }
		else {
			return getFormattedDate(dateIn,locale,timezone,"dd/MM/yyyy");
		}
	}
	
	public static String printDateTime(String language,String country,Date date) {
		return printDateTime( new Locale(language, country),date);
	}
	
	public static String printDateTime(Locale locale,Date date) {
		return printDateTime(locale,null,date);
	}
	
	public static String printDateTime(Locale locale,TimeZone timezone,Date dateIn) {
		if ( dateIn==null ) {
			return "";
		} else {
			return getFormattedDate(dateIn,locale,timezone,"dd/MM/yyyy HH:mm:ss");
		}
	}

	private static String getFormattedDate(Date dateIn ,Locale locale,TimeZone timezone, String format) {
		DateFormatSymbols d = new DateFormatSymbols(locale);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		//if (timezone!=null )
		//	formatter.setTimeZone(timezone);
		//long newTime = dateIn.getTime() - UTCDateUtils.getDaylightGMTOffset(dateIn);
		//Date date = new Date(newTime);
		formatter.setDateFormatSymbols(d);
		return formatter.format(dateIn);
	}

	public static String printValue(String language,String country,BigDecimal number) {
		return printValue(  new Locale(language, country) ,number);
	}
	
	public static String printValue(Locale locale,BigDecimal number) {
		BigDecimal bd = number;
		if ( bd==null ) {
			bd = BigDecimal.ZERO;
		}
		return NumberFormat.getNumberInstance(locale == null?Locale.ITALY:locale).format(bd);
	}
    
	public static String printLongCurrency(String language,String country,BigDecimal number) {
		return printLongCurrency(  new Locale(language, country) ,number);
	}
	
	public static String printLongCurrency(Locale locale,BigDecimal number) {
		return printCurrency(locale,number,"#,##0.0############");
	}
	
	public static String printCurrency(String language,String country,BigDecimal number) {
		return printCurrency(  new Locale(language, country) ,number);
	}

    public static String printCurrency(Locale locale,BigDecimal number) {
		return printCurrency(locale,number,"#,##0.00");
	}

    public static String printCurrency(Locale locale,String bigDecimalStringRappresentation) {
        if(bigDecimalStringRappresentation==null || "".equals(bigDecimalStringRappresentation))
            return "";
		return printCurrency(locale,new BigDecimal(bigDecimalStringRappresentation),"#,##0.00");
	}

    public static String printCurrency(Locale locale,BigDecimal number,String mask) {
    	BigDecimal bd = number;

		if ( bd==null ) {
			bd = BigDecimal.ZERO;
		}

		DecimalFormatSymbols d = new DecimalFormatSymbols(locale);
		DecimalFormat df = new DecimalFormat(mask);
		df.setDecimalFormatSymbols( d );
		return df.format(bd);
	}
}