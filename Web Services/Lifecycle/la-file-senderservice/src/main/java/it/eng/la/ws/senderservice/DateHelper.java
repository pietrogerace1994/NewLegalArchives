package it.eng.la.ws.senderservice;

//import java.sql.Date;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * This is an helper class to manage date and time objects.
 */
public class DateHelper {
	
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy.MM.dd", Locale.ITALY);
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy - kk:mm:ss", Locale.ITALY);
    public static SimpleDateFormat dateFormatForFilename = new SimpleDateFormat("ddMMyyyy", Locale.ITALY);
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");
    public static SimpleDateFormat timeNowFormat = new SimpleDateFormat("kk:mm:ss");
    public static SimpleDateFormat timeFormatForFilename = new SimpleDateFormat("kkmmss", Locale.ITALY);
    public static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM", Locale.ITALY);
    public static SimpleDateFormat dateFormatDay = new SimpleDateFormat("EEE", Locale.ITALY);
    public static SimpleDateFormat dateFormatYear = new SimpleDateFormat("yy");
    public static SimpleDateFormat dateFormatNumberDay = new SimpleDateFormat("dd");
    public static SimpleDateFormat dateFormatMonthAsString = new SimpleDateFormat("MMMM", Locale.ITALY);

    protected static long oneDayMillis = 86400000;
    protected static int giorniCommerciali = 30;

    // date format used to extract day, month or year information
    public static SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.ITALY);
    public static SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.ITALY);
    public static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ITALY);
    public static SimpleDateFormat hourFormat = new SimpleDateFormat("kk", Locale.ITALY);
    public static SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.ITALY);
    public static SimpleDateFormat secondFormat = new SimpleDateFormat("ss", Locale.ITALY);
    
    protected DateHelper() {
    }

    /**
     * It gets the current date
     *
     * @return a java.sql.Date object
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * It gets the current time
     *
     * @return a java.sql.Time object
     */
    public static Time getCurrentTime() {
        return new Time(System.currentTimeMillis());
    }

    /**
     * It converts a time into a string
     *
     * @param a java.sql.Time object
     */
    public static String getTimeAsString(Time time) {
        return timeFormat.format(time);
    }

    /**
     * It converts a date into a string
     *
     * @param a java.sql.Date object
     */
    public static String getDateAsString(Date date) {
        return dateFormat.format(date);
    }
    
	public static String getDateUtilAsString(java.util.Date date) {
		return dateFormat.format(date);
	}
    
    /**
     * It converts a date into a string
     *
     * @param a java.sql.Date object
     */
    public static String getDateAsString2(Date date) {
        return dateFormat2.format(date);
    }
    /**
     * It converts a date into a string
     *
     * @param a java.sql.Date object
     */
    public static String getDateAsString3(Date date) {
        return dateFormat3.format(date);
    }
    /**
     * It converts a date into a string (taking into account also time values).
     *
     * @param a java.sql.Date object
     */
    public static String getDateTimeAsString(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * It converts a date into a string
     *
     * @param a java.sql.Date object
     */
    public static String getDateFormatForFilename(Date date) {
        return dateFormatForFilename.format(date);
    }
    
    /**
     * It converts a string into a date object
     *
     * @param date is a String
     * @return a java.sql.Date object
     */
    public static Date getDate(String date) throws ParseException {
        return new Date(dateFormat.parse(date).getTime());
    }
    
    /**
     * It converts a string into a date object
     *
     * @param date is a String
     * @return a java.sql.Date object
     */
    public static Date getDate2(String date) throws ParseException {
        return new Date(dateFormat2.parse(date).getTime());
    }

    /**
     * It converts a string into a date object
     *
     * @param date is a String
     * @return a java.sql.Date object
     */
    public static Date getDate3(String date) throws ParseException {
        return new Date(dateFormat3.parse(date).getTime());
    }
    
    /**
     * It converts a string into a time object
     *
     * @param time is a String
     * @return a java.sql.Time object
     */
    public static Time getTime(String time) throws ParseException {
        return new Time(timeFormat.parse(time).getTime());
    }

    public static Time getTimeNow() throws ParseException {
		Calendar c=new GregorianCalendar();
		int ore=c.get(Calendar.HOUR_OF_DAY);
		int minuti=c.get(Calendar.MINUTE);
		int secondi=c.get(Calendar.SECOND);
		Time t = new Time(timeNowFormat.parse(ore+":"+minuti+":"+secondi).getTime());
		return t;
    }
    /**
     * It gets the date of today adding the specified days.
     */
    public static Date addDays(int day) {
        long currentMillisec = System.currentTimeMillis();
        long dayToAdd = (oneDayMillis * (long) day) + currentMillisec;
        return new Date(dayToAdd);
    }

    public static Date addDays(int dayAmount, Date startDate) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(startDate);
    	calendar.add(Calendar.DAY_OF_MONTH, dayAmount);
    	return new Date(calendar.getTime().getTime());
    }
    
    public static long addMinutes(int minutes, long startDate) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(startDate);
    	calendar.add(Calendar.MINUTE, minutes);
    	return calendar.getTime().getTime();
    }

    public static Date backDays(int dayAmount) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_MONTH, -dayAmount);
    	return new Date(calendar.getTime().getTime());
    }
    
    public static Date backDays(int dayAmount, Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_MONTH, -dayAmount);
    	return new Date(calendar.getTime().getTime());
    }
    
	public static Date backWeek(int weekAmount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -weekAmount);
		return new Date(calendar.getTime().getTime());
	}
	
    public static Date backWeek(int weekAmount, Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.WEEK_OF_YEAR, -weekAmount);
    	return new Date(calendar.getTime().getTime());
    }
    
    public static String getMonth() {
        return dateFormatMonth.format(new Date(System.currentTimeMillis()));
    }

    public static String getMonth(Date data) {
        return dateFormatMonth.format(data);
    }
    
    public static String getDay() {
        return dateFormatDay.format(new Date(System.currentTimeMillis()));
    }  

    public static String getDay(Date data) {
        return dateFormatDay.format(data);
    }

    public static String getNumberDay(Date data) {
        return dateFormatNumberDay.format(data);
    }

    public static String getMonthAsString(Date date) {
        return dateFormatMonthAsString.format(date);
    }

    // add per visualizzare il nome del mese con l'iniziale in maiuscolo.
    public static String getMonthAsStringUpperCase(Date date) {
        String appMonth = dateFormatMonthAsString.format(date);
        String month = "";
        if (appMonth.trim().equals("gennaio")) {
            month = "Gennaio";
        }
        if (appMonth.trim().equals("febbraio")) {
            month = "Febbraio";
        }
        if (appMonth.trim().equals("marzo")) {
            month = "Marzo";
        }
        if (appMonth.trim().equals("aprile")) {
            month = "Aprile";
        }
        if (appMonth.trim().equals("maggio")) {
            month = "Maggio";
        }
        if (appMonth.trim().equals("giugno")) {
            month = "Giugno";
        }
        if (appMonth.trim().equals("luglio")) {
            month = "Luglio";
        }
        if (appMonth.trim().equals("agosto")) {
            month = "Agosto";
        }
        if (appMonth.trim().equals("settembre")) {
            month = "Settembre";
        }
        if (appMonth.trim().equals("ottobre")) {
            month = "Ottobre";
        }
        if (appMonth.trim().equals("novembre")) {
            month = "Novembre";
        }
        if (appMonth.trim().equals("dicembre")) {
            month = "Dicembre";
        }
        return month;
    }

    public static String getYear() {
        return dateFormatYear.format(new Date(System.currentTimeMillis()));
    }

    public static String getCompleteYear() {
        return yearFormat.format(new Date(System.currentTimeMillis()));
    }

 

    /**
     * It gets the year from the specified Date.
     */
    public static int extractYear(Date date) {
        return new Integer(yearFormat.format(date)).intValue();
    }

    /**
     * It gets the month from the specified Date.
     */
    public static int extractMonth(Date date) {
        return new Integer(monthFormat.format(date)).intValue();
    }

    /**
     * It gets the day from the specified Date.
     */
    public static int extractDay(Date date) {
        return new Integer(dayFormat.format(date)).intValue();
    }

    /**
     * It gets the hour from the specified Date.
     */
    public static int extractHour(Date date) {
        return new Integer(hourFormat.format(date)).intValue();
    }
    
    /**
     * It gets the minute from the specified Date.
     */
    public static int extractMinute(Date date) {
        return new Integer(minuteFormat.format(date)).intValue();
    }
    
    /**
     * It gets the second from the specified Date.
     */
    public static int extractSecond(Date date) {
        return new Integer(secondFormat.format(date)).intValue();
    }
    
    
    public static long calcDays(Date d1, Date d2) {
        long millis1 = d1.getTime();
        long millis2 = d2.getTime();
        long ris = millis2 - millis1;
        long nGiorni = ris / oneDayMillis;
        return nGiorni;
    }

    public static long calcMonths(Date d1, Date d2) {
        long nGiorni = calcDays(d1, d2);
        long nMesi = nGiorni / giorniCommerciali;
        return nMesi;
    }
    /**
     * It converts a date into a string ().
     *
     * @param a java.sql.Date object
     * @param a String format
     */
    public static String getDateFormatForDb(Date date,String format){
    	 SimpleDateFormat dateFormatForDb = new SimpleDateFormat(format, Locale.ITALY);
    	  return dateFormatForDb.format(date);
    }
    
    public static String getStringMonth(int meseInt){ 
    	/*
    	 * Restituisce il nome del mese
    	 * 
    	 * meseInt :  0-11
    	 * 
    	 * */
    	String mese="";
    	String mesi[] = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
    	mese = mesi[meseInt];
    	return mese;
    }
    public static String printDate(int dayInt,int monthInt,int yearInt,String sTime)
    { 
    	/*
    	 * Restituisce la stringa nel formato gg/mm/aaaa ora
    	 * 
    	 * monthInt :  0-12
    	 * 
    	 * */
    	String tmp="";
    	if(dayInt<10)
			{tmp += "0"+dayInt;}
		else
			{tmp += dayInt;}
		if(monthInt<10)
			{tmp += "/0"+monthInt;}
		else
			{tmp += "/"+monthInt;
		}
		
		tmp += "/"+yearInt;
		
		if(sTime!=null){
			tmp += " "+sTime;}
	    
		return tmp;
    }
    public static Date createDate(String date) throws ParseException{ 
    	
    	StringTokenizer st = new StringTokenizer(date, "/"); 
    	int day = 0;
		int month = 0;
		int year = 0;
    	
    	while (st.hasMoreTokens()) {
    		  day = Integer.valueOf(st.nextToken()).intValue();
    		  month = Integer.valueOf(st.nextToken()).intValue();
    		  year = Integer.valueOf(st.nextToken()).intValue();
    	}
    	Calendar c = Calendar.getInstance();
    	c.set(year, month-1, day);
		return getDate(getDateAsString(new Date(c.getTime().getTime())));
    }
    
}

