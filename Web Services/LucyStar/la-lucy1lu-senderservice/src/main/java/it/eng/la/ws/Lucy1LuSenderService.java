package it.eng.la.ws.senderservice;

import javax.ejb.Local;
import javax.ejb.Timer;

@Local
public interface Lucy1LuSenderService {
	
	public void init();
	
	public void doTask(Timer timer);

}
