package it.eng.la.ws.service;
import it.eng.la.ws.entity.Item;

import java.util.List;

import javax.ejb.Local;

@Local
public interface HrService {
	
	public String insert(String dataAggiornamento, List<Item> dati);
	
}
