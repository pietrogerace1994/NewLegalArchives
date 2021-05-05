package eng.la.presentation.listener;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import eng.la.model.AbstractEntity;
import eng.la.model.view.UtenteView;
import eng.la.persistence.audit.AuditInterceptor;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
		UtenteView utenteView = (UtenteView) se.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		if( utenteView != null ){
			utenteView.getVo().setOperation(AbstractEntity.LOGOUT_OPERATION);
			utenteView.getVo().setOperationTimestamp(new Date());			
			auditInterceptor.auditLoginLogout( utenteView.getVo() );
		}
    }
	
}
