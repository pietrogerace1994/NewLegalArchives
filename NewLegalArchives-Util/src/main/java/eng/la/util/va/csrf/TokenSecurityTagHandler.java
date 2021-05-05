package eng.la.util.va.csrf;
//engsecurity VA

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class TokenSecurityTagHandler extends TagSupport {
    
	private static final long serialVersionUID = 3833336416221885315L;
	
	private Boolean regenerate = Boolean.TRUE;
    private Boolean input = Boolean.TRUE;
    public final static String TOKEN_NAME = "CSRFToken";
    
    private static final Logger log = Logger.getLogger(TokenSecurityTagHandler.class);
    
    public TokenSecurityTagHandler() {
    }


    public int doStartTag() throws JspException {
        int output = super.doStartTag(); 
        
        if (log.isDebugEnabled()){
            log.debug("*** " + this.getClass().getCanonicalName() + " START SECURITY TOKEN TAG" + " ***");
        }
        
        HttpSession session = (HttpSession) pageContext.getSession(); pageContext.getSession();
        
        String token = null;
        try {
                // genera il token se richiesto
                if( this.getRegenerate().booleanValue() ){
                
                    token = GenerateCSRFToken.instance().generate();
                    if (log.isDebugEnabled()){
                        log.debug("*** " + this.getClass().getCanonicalName() + " TOKEN creato :" + token + " ***");
                    }
                    session.removeAttribute(TOKEN_NAME);
                    session.setAttribute(TOKEN_NAME, token);
                
                } else { // altrimenti recupera il token precedente dalla sessione, se invalido lo ricrea
                    
                    token = (String) session.getAttribute(TOKEN_NAME);
                    if (log.isDebugEnabled()){
                        log.debug("*** " + this.getClass().getCanonicalName() + " TOKEN non rigenerato:" + token + " ***");
                        log.debug("*** " + this.getClass().getCanonicalName() + " TOKEN in sessione:" + token + " ***");
                    }
                    if( null == token || !(token.length()>0) ){
                        // ricrea il token e lo stora in sessione
                        token = GenerateCSRFToken.instance().generate(); 
                        if (log.isDebugEnabled()){
                            log.debug("*** " + this.getClass().getCanonicalName() + " TOKEN null rigenerato :" + token +" ***");
                        }
                        session.removeAttribute(TOKEN_NAME);
                        session.setAttribute(TOKEN_NAME, token);
                    } else {
                        // in token
                    }
                }

        } catch (Exception e) {
             throw new JspException(e);
        }
        Writer writer = pageContext.getOut();
        
        try {
        if( this.input.booleanValue() ){

            //ServletContext servletContext = pageContext.getServletContext();
            // ottiene il nome del token dal ServletContext (web.xml)
            //String tokenParameter servletContext.getInitParameter(this.TOKEN_NAME);
            
            writer.write("<input name=\"" + this.TOKEN_NAME + "\" type=\"hidden\" value=\"" + token + "\" />"); 
        
        } else {
            writer.write(token);
        }
        
            
        } catch (IOException e) {
            throw new JspException(e);
        }   

        return output;
    }


    public void setRegenerate(Boolean regenerate) {
        this.regenerate = regenerate;
    }

    public Boolean getRegenerate() {
        return regenerate;
    }

    public void setInput(Boolean input) {
        this.input = input;
    }

    public Boolean getInput() {
        return input;
    }
    
}