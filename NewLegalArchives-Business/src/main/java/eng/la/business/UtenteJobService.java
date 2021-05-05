package eng.la.business;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import eng.la.model.Utente;

public interface UtenteJobService {
	public void aggiornaUtenteRuolo() throws Throwable;
	
	public void inviaMailProtocollo() throws Throwable;
}
