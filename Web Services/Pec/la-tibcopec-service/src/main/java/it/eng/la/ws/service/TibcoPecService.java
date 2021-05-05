package it.eng.la.ws.service;
import it.eng.laws.entity.TibcoPecEntity;

import javax.ejb.Local;

@Local
public interface TibcoPecService 
{
	public String insert(TibcoPecEntity c);
}
