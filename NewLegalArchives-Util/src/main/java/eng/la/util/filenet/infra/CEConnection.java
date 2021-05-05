package eng.la.util.filenet.infra;

import java.util.Iterator;
import java.util.Vector;

import javax.security.auth.Subject;

/*import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;*/

/**
 * Classe per la gestione della connessione a FileNet
 * @author 
 *
 */
public class CEConnection {
	/*private Connection con;
	private Domain dom;
	private String domainName;
	private ObjectStoreSet ost;
	private Vector<String> osnames;
	private boolean isConnected;
	private UserContext uc;


	public CEConnection()
	{
		con = null;
		uc = UserContext.get();
		dom = null;
		domainName = null;
		ost = null;
		osnames = new Vector<String>();
		isConnected = false;
	}	
	



	public void establishConnection(String userName, String password, String stanza, String uri)
    {
		con = Factory.Connection.getConnection(uri);
		Subject sub = UserContext.createSubject(con, userName, password, stanza);
		uc = UserContext.get();
		uc.pushSubject(sub);
        dom = fetchDomain();
        domainName = dom.get_Name();
        ost = getOSSet();
        isConnected = true;
    }
	

	public Domain fetchDomain()
    {
        dom = Factory.Domain.fetchInstance(con, null, null);
        return dom;
    }
	

	public ObjectStoreSet getOSSet()
    {
        ost = dom.get_ObjectStores();
        return ost;
    }
	

	public Vector<String> getOSNames()
    {
    	if(osnames.isEmpty())
    	{
    		@SuppressWarnings("unchecked")
			Iterator<ObjectStore> it = ost.iterator();
    		while(it.hasNext())
    		{
    			ObjectStore os = it.next();
    			osnames.add(os.get_DisplayName());
    		}
    	}
        return osnames;
    }
	

	public boolean isConnected() 
	{
		return isConnected;
	}
	

	public ObjectStore fetchOS(String name)
    {
        ObjectStore os = Factory.ObjectStore.fetchInstance(dom, name, null);
        return os;
    }
	

	public String getDomainName()
    {
        return domainName;
    }
	public Connection getCon() {
		return con;
	}*/
}
