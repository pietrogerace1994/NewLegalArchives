package eng.la.util.va.xss;
//engsecurity VA

//import org.springframework..GeneralFailureException;
import eng.la.util.exceptions.LAException;



import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

public class ValidatingHttpRequest extends HttpServletRequestWrapper{

	protected Logger log = Logger.getLogger("gme.j2ee.web");
	
    public ValidatingHttpRequest(HttpServletRequest request) {
            super(request);
    }
    public String getParameter(String name) {
            HttpServletRequest req = (HttpServletRequest) super.getRequest();
			try {
				try {
					return validate( name, req.getParameter( name ) );
				} catch (LAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return name;
    }
   
    public String getHeader(String name) {
        String value = super.getHeader(name);
        try {
			return validate( name, value );
		} catch (LAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
    }
    
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            try {
				encodedValues[i] = validate(parameter, values[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return encodedValues;
    }
    
    /*// Danger - you can optionally allow getting the raw parameter
    public String getRawParameter( String name ) {
            HttpServletRequest req = (HttpServletRequest) super.getRequest();
            return req.getParameter( name );
    }*/
    
    /* private Pattern pattern = Pattern.compile("[a-zA-Z" +
	"\u00C0-\u00F6\u00F8-\u00FF0-9" + // lettere accentate
	"\u0020" + // spazio
	"\u2044" + // lo slash (/)
	"\u2212" + // il meno (-)
	"\u002E" + // il punto(.)
	"\u002C" + // la virgola (,)
	"\u003A" + // due punti(:)
	"\u003B" + // il punto e virgola(;)
	"\u0028" + // la parentesi tonda aperta
	"\u0029" + // la parentesi tonda chiusa
	"\u2038" + // il cappelletto (^)
	"\u0040" + // la chiocciola (@)
	"\u005F" + // l'underscore (_)
	"\u20AC" + // ?
	"\u003F" + // ?
	"\u0021" + // !
	"\u0026" + // &
	"\u002B" + // +
	"\u0027" + // l'apostrofo (')
	"\u0025" + // il %
	"]+");

	private Pattern pattern2 = Pattern.compile("[a-zA-Z" +
		"\u00C0-\u00F6\u00F8-\u00FF0-9" + // lettere accentate
		"\u0020" + // spazio
		"\u2044" + // lo slash (/)
		"\u2212" + // il meno (-)
		"\u002E" + // il punto(.)
		"\u002C" + // la virgola (,)
		"\u003A" + // due punti(:)
		"\u003B" + // il punto e virgola(;)
		"\u0028" + // la parentesi tonda aperta
		"\u0029" + // la parentesi tonda chiusa
		"\u2038" + // il cappelletto (^)
		"\u0040" + // la chiocciola (@)
		"\u005F" + // l'underscore (_)
		"\u20AC" + // ?
		"\u003D" + // =
		"\u003F" + // ?
		"\u0021" + // !
		"\u0026" + // &
		"\u002B" + // +
		"\u0027" + // l'apostrofo (')
		"\u0025" + // il %
		"]+");*/
	
	private Pattern pattern = Pattern.compile("[a-zA-Z\u00C0-\u00F6\u00F8-\u00FF0-9\\s/\\-.,:;()\\^@_\u20AC\u003F\u0021\u0026\u002B\u0027\u0025]+");    
	private Pattern pattern2 = Pattern.compile("[a-zA-Z\u00C0-\u00F6\u00F8-\u00FF0-9\\s/\\-.,:;()\\^@_\u20AC=\u003F\u0021\u0026\u002B\u0027\u0025]+");
   
    private String validate( String name, String input ) throws Exception {
            // important - always canonicalize before validating
            //String canonical = canonicalize( input );
    		String canonical =  input ;
            log.debug("CANONICAL: "+canonical);
            
            if(canonical!=null && !canonical.trim().equalsIgnoreCase("")){
            	
                boolean convert = false;
                //String unreadable = "?????????????????????????? ????????????????";
                /*String[] unreadable = {"??","??","??","?","?","?","?","??","??","??","??","??","?","?","?","?","?","? ","??","??","??","??","?","?","?","?","?","??","?"};
        		
        		for(int i =0; i < unreadable.length; i++){
        	        if(canonical.contains(unreadable[i])){
        	        	convert = true;
        	            break;
        	        }
        	    }*/
                
                String unreadable = "?";
                if(canonical.contains(unreadable) || canonical.contains("?")){
    	        	convert = true;
    	        }
                
                if(convert){
                	try {
                		// readable -> ?????????????????????????????
                		canonical = new String(canonical.getBytes("ISO-8859-1"), "UTF-8");
                	} catch (UnsupportedEncodingException e) {
                		log.debug("UnsupportedEncodingException "+canonical);
                	}
                }
            	
                log.debug("AFTER - nome: "+name+" canonical: "+canonical );
                
            	//aggiungere contestualizzazione dei caratteri
            	
            	
	            // check to see if input matches whitelist character set
	            if(!name.equalsIgnoreCase("Content-Type")){
	                if ( !pattern.matcher( canonical ).matches() ) {
		                    //throw new ValidationException( "Improper format in " + name + " field");
		                    throw new LAException("Improper format in " + name + " field");
		            }
	            } else {
	                if ( !pattern2.matcher( canonical ).matches() ) {
	                    //throw new ValidationException( "Improper format in " + name + " field");
	                    throw new LAException("Improper format in " + name + " field");
	                }
	            }
	            // you could html entity encode input, but it's probably better to do this before output
	            //canonical = HTMLEntityEncode( canonical );
            }
            return canonical;
    }
   
    // Simplifies input to its simplest form to make encoding tricks more difficult
    private String canonicalize( String input ) {
        //String canonical = sun.text.Normalizer.normalize( input, Normalizer. DECOMP, 0 );
    	String canonical = null;
    	if(input!=null){
    		canonical = Normalizer.normalize(input, Normalizer.Form.NFD);
    	}
        return canonical;
}
   
    // Return HTML Entity code equivalents for any special characters
    public static String HTMLEntityEncode( String input ) {
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < input.length(); ++i ) {
                char ch = input.charAt( i );
                if ( ch>='a' && ch<='z' || ch>='A' && ch<='Z' || ch>='0' && ch<='9' ) {
                        sb.append( ch );
                } else {
                        sb.append( "&#" + (int)ch + ";" );
                }
        }
        return sb.toString();
    }
   
}