package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.view.NotificaWebView;

public interface NotificaWebService {
	
	public List<NotificaWebView> leggi(String matricola)throws Throwable;
	public void inserisci(NotificaWebView notificaWeb) throws Throwable;
	public void aggiorna(NotificaWebView notificaWeb) throws Throwable;
    public void inserisciAttoInviatoAdAltriUffici(NotificaWebView notificaWeb) throws Throwable;
	public void inserisciChiusuraFascicolo(NotificaWebView notificaWeb)  throws Throwable ;
	public void inserisciProfessionistaEsterno(NotificaWebView notificaWeb)  throws Throwable ;
	public void marcaLetti(List<NotificaWebView> notifiche) throws Throwable;
	public void marcaNotificaWebLetta(Long id, Date date) throws Throwable;
	public void inserisciEstensionePermessi(NotificaWebView notificaWeb, String[] permessiScritturaArray) throws Throwable;
}
