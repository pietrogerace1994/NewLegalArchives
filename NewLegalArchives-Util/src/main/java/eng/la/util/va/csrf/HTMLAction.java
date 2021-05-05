package eng.la.util.va.csrf;
//engsecurity VA

/*import it.enidata.gme.framework.j2ee.event.Event;
import it.enidata.gme.framework.j2ee.event.EventResponse;
import it.enidata.gme.ejb.exception.MyQueryException;
*/
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.Serializable;

public interface HTMLAction extends Serializable {
	
    public void setServletContext(ServletContext context);

    public void doStart(HttpServletRequest request);

    //public Event perform(HttpServletRequest request) throws HTMLActionException, MyQueryException;

    public void doEnd(HttpServletRequest request, HttpServletResponse eventResponse);
    

}