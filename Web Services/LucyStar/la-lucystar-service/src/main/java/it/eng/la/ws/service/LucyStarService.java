package it.eng.la.ws.service;
import it.eng.laws.entity.ContabilizzazioneEntity;

import javax.ejb.Local;

@Local
public interface LucyStarService {
	
	public String insert(ContabilizzazioneEntity c);
	
}
