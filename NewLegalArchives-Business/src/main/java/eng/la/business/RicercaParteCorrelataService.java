package eng.la.business;

import java.util.Date;
//import java.util.Date;
import java.util.List;

//import eng.la.model.ParteCorrelata;
import eng.la.model.view.RicercaParteCorrelataView;

public interface RicercaParteCorrelataService {
	/* Nuovo */
	public List<RicercaParteCorrelataView> cerca(RicercaParteCorrelataView RicercaParteCorrelataView) throws Throwable;
	public List<RicercaParteCorrelataView> cerca(String denominazione, String codFiscale, String partitaIva) throws Throwable;
	public RicercaParteCorrelataView leggi(long id) throws Throwable;
	public RicercaParteCorrelataView leggi(String codFiscale, String partitaIva) throws Throwable;
	public List<RicercaParteCorrelataView> leggi() throws Throwable;
	public void cancella(long id) throws Throwable;
	public RicercaParteCorrelataView inserisci(RicercaParteCorrelataView RicercaParteCorrelataView) throws Throwable;
	public void modifica(RicercaParteCorrelataView RicercaParteCorrelata) throws Throwable;
	public List<RicercaParteCorrelataView> leggi(Date dataInizio, Date dataFine) throws Throwable;
}