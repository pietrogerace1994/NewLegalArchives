package eng.la.util; 


public class StringUtil {
	
	private final static String HTML_APOSTROFO = "&#39;";
	
	private StringUtil() {
	}

	public static String escapeJsonApostrofo(String jsonStr) {
		if(jsonStr==null) return null;
		StringBuffer newJsonStr= new StringBuffer();
		for(int i=0; i<jsonStr.length(); i++) {
			if( jsonStr.charAt(i) == '\'' ) 
				newJsonStr.append(HTML_APOSTROFO);
			else
				newJsonStr.append(jsonStr.charAt(i));
		}
			
		return newJsonStr.toString();
	}  
	
	public static String escapeJson(String str) {
		str=org.apache.commons.lang3.StringEscapeUtils.escapeJson(str);
		str=eng.la.util.StringUtil.escapeJsonApostrofo(str);
		return str;
	}
	
	
	public static String escapeMsWord(String str){
		
		str = str.replace( (char)145, (char)'\'');

		str = str.replace( (char)8216, (char)'\''); // left single quote

		str = str.replace( (char)146, (char)'\'');

		str = str.replace( (char)8217, (char)'\''); // right single quote
		
		str = str.replace( (char)147, (char)'\"');

		str = str.replace( (char)148, (char)'\"');

		str = str.replace( (char)8220, (char)'\"'); // left double

		str = str.replace( (char)8221, (char)'\"'); // right double

		str = str.replace( (char)8211, (char)'-' ); // em dash??    

		str = str.replace( (char)150, (char)'-' );
		
		return str;
	}
	
}
