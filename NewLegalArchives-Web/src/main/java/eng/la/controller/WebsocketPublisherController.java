package eng.la.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eng.la.business.websocket.WebSocketPublisher;
import eng.la.model.rest.Event;
import eng.la.model.rest.RisultatoOperazioneRest;

@Controller("websocketPublisherController")
public class WebsocketPublisherController {

	
	
	@RequestMapping(value="/websocket/publish", consumes="application/json", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody RisultatoOperazioneRest publishEvent(@RequestBody Event event) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


		try {
	
			WebSocketPublisher.getInstance().sendEvent(event);
			
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return risultato;
	}

}
