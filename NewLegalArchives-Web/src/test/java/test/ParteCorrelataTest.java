package test;

//import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.ParteCorrelataService;
import eng.la.model.ParteCorrelata;
//import eng.la.model.ParteCorrelata;
import eng.la.model.view.ParteCorrelataView;
import junit.framework.TestCase;

public class ParteCorrelataTest extends TestCase {
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Test inserimento parte correlata
	 */

	public void testInserisciParteCorrelata(){   
		configure();
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		//NazioneService nazioneService = (NazioneService) ctx.getBean("nazioneService");
		try {
			long id=1;
			ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
			parteCorrelataView.setVo(new ParteCorrelata());
			parteCorrelataView.getVo().setId(id);
			parteCorrelataView.getVo().setCodFiscale("00167880XXX");
			parteCorrelataView.getVo().setPartitaIva("00167880XXX");
			parteCorrelataView.getVo().setDenominazione("SRT SPA");
			parteCorrelataView.getVo().setDataInserimento(new Date());
			
			service.inserisci(parteCorrelataView);
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}


	/**
	 * Test di cancellazione parte correlata
	 */
	/*
	public void testCancellaParteCorrelata(){   
		configure();
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		try {
			long id=6;
			ParteCorrelataView parteCorrelata = service.leggi(id);
			if (parteCorrelata!=null) {
				service.cancella(id);
				System.out.println("l'occorrenza (" + id + ") è stata cancellata.");
			} else {
				System.out.println("l'occorrenza (" + id + ") da cancellare non esiste.");
			}
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		} 
  
	}
	*/
	
	/**
	 * Test di modifica di una parte correlata
	 */
	/*
	public void testModificaParteCorrelata(){   
		configure(); 
		
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		try {
			long id=8;
			String codFiscPartIva="00167880900"; // PIVA da modificare 00167880988 ( il precedente valore)
			String denominazione = "TESTONE S.p.A.";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateInString = "21-06-2016";
			Date dataInserimento = sdf.parse(dateInString);

			ParteCorrelataView parteCorrelataView = new ParteCorrelataView();
			parteCorrelataView.setVo(new ParteCorrelata());
			parteCorrelataView.getVo().setId(id);
			parteCorrelataView.getVo().setCodFisPartIva(codFiscPartIva);
			parteCorrelataView.getVo().setDenominazione(denominazione);
			parteCorrelataView.getVo().setDataInserimento(dataInserimento);
			// esecuzione modifica
			service.modifica(parteCorrelataView);
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	/**
	 * Test lettura parti correlate
	 */
	/*
	public void testLeggiPartiCorrelate(){  
		configure(); 
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		try {
			List<ParteCorrelataView> lista = service.leggi();
			System.out.println("Lettura parti correlate riuscita, num: "+ lista.size());
			ParteCorrelataView parteCorrelataView = service.leggi(1);
			System.out.println("Lettura parte correlata con id=1 riuscita: "+ parteCorrelataView.getVo().getDenominazione());			
			List<ParteCorrelataView> listaAttive = service.leggi("A");
			System.out.println("Lettura parti correlate (attive) riuscita, num: "+ listaAttive.size());
			List<ParteCorrelataView> listaNoAttive = service.leggi("N");
			System.out.println("Lettura parti correlate (no attive) riuscita, num: "+ listaNoAttive.size());
			List<ParteCorrelataView> listaTutte = service.leggi("T");
			System.out.println("Lettura parti correlate (tutte) riuscita, num: "+ listaTutte.size());
			
			//parteCorrelataView = service.leggi("00166888612");
			//System.out.println("Lettura parte correlata con codCfPiva=00166888612 riuscita: "+ parteCorrelataView.getVo().getDenominazione());
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	/*
	public void testLeggiPaginata(){  
		configure();
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		try {
			ListaPaginata<ParteCorrelataView> lista0 = service.leggi(1, 1, null, null);
			ListaPaginata<ParteCorrelataView> lista1 = service.leggi(1, 2, null, null); 
			
			System.out.println("Lettura ParteCorrelata riuscita lista0 size : "+ lista0.size());
			System.out.println("Elenco ParteCorrelata paginata (lista0): ");
			for(ParteCorrelataView row:lista0){
				System.out.println("id: "+ row.getVo().getId());
				System.out.println("denominazione: " + row.getVo().getDenominazione());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataInserimento: "+ row.getVo().getDataInserimento());
				System.out.println("dataCancellazione: "+ row.getVo().getDataCancellazione());
			}

			System.out.println("Lettura ParteCorrelata riuscita lista1 size : "+ lista1.size());
			System.out.println("Elenco ParteCorrelata paginata (lista1): ");
			for(ParteCorrelataView row:lista1){
				System.out.println("id: "+ row.getVo().getId());
				System.out.println("denominazione: " + row.getVo().getDenominazione());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataInserimento: "+ row.getVo().getDataInserimento());
				System.out.println("dataCancellazione: "+ row.getVo().getDataCancellazione());
			}
			
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}  
	}
	*/
	
	/**
	 * Test ricerca parti correlate.
	 */
	/*
	public void testCercaPartiCorrelate(){  
		configure(); 
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		try {
			// chiavi di ricerca.
			String denominazione="SRT SPA";
			String codFiscPIva="00166888612";
			// esecuzione ricerca.
			List<ParteCorrelataView> list = service.cerca(denominazione, codFiscPIva);
			System.out.println("Ricerca parte correlata eseguita con successo : ");
			for(ParteCorrelataView row:list){
				System.out.println("id: "+ row.getVo().getId());
				System.out.println("denominazione: " + row.getVo().getDenominazione());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataInserimento: "+ row.getVo().getDataInserimento());
				System.out.println("dataCancellazione: "+ row.getVo().getDataCancellazione());
			}
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	/**
	 * Test della search parti correlate.
	 */
	// vecchio metodo....
	/*
	public void testSearchPartiCorrelate(){  
		configure(); 
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		
		try {
			boolean isUtenteSS = true;
			String denominazione = "Generadora solar";
			String codFiscPIva = "";
			Date data = new Date();
			List<ParteCorrelataView> lista = service.cerca(isUtenteSS, denominazione, codFiscPIva, data);
			
			System.out.println(lista.size());
			
			System.out.println("Ricerca parte correlata eseguita con successo : ");
			for(ParteCorrelataView row:lista){
				System.out.println("-------------------------------------------------");
				System.out.println("denominazione: " + row.getVo().getDenominazione());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataInserimento: "+ row.getVo().getDataInserimento());
				System.out.println("tipoCorrelazione: " + row.getVo().getTipoCorrelazione().getDescrizione());
			}
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	/**
	 * Test di ricerca parti correlate per similitudini
	 */
	/*
	public void testSearchPartiCorrelate(){  
		configure(); 
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		
		try {
			boolean isUtenteSS = true;
			String denominazione = "Generadora solar";
			String codFiscPIva = "";
			Date data = new Date();
			//List<ParteCorrelataView> lista = service.cerca(isUtenteSS, denominazione, codFiscPIva, data);
			HashMap<String, Object> hashRet = service.cerca(isUtenteSS, denominazione, codFiscPIva, data);
			List<ParteCorrelataView> lista =  (List<ParteCorrelataView>) hashRet.get("partiCorList");
			Boolean esitoRicerca = (Boolean) hashRet.get("esitoRicerca");
			RicercaParteCorrelataView ricercaParteCorrelataView =  
					(RicercaParteCorrelataView)hashRet.get("ricercaParteCorrelataView");
			Long ricercaParteCorrelataId =  ricercaParteCorrelataView.getVo().getId();
			
			System.out.println("Dimensione Lista : " + lista.size());
			System.out.println("esitoRicerca : " + esitoRicerca);
			System.out.println("ricercaParteCorrelataId : " + ricercaParteCorrelataId);
			
			System.out.println("Ricerca parte correlata eseguita con successo : ");
			for(ParteCorrelataView row:lista){
				System.out.println("-------------------------------------------------");
				System.out.println("denominazione: " + row.getVo().getDenominazione());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataInserimento: "+ row.getVo().getDataInserimento());
				System.out.println("tipoCorrelazione: " + row.getVo().getTipoCorrelazione().getDescrizione());
			}
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	/**
	 * Test report parti correlate attive e non attive
	 */
	/*
	public void testReportAttiveCessate(){  
		configure(); 
		ParteCorrelataService service = (ParteCorrelataService) ctx.getBean("parteCorrelataService");
		
		try {
			boolean isUtenteSS = true;
			Date data = new Date();
			byte[] out = service.generaReportXLS(service.ESTRAI_ATTIVE);
			System.out.println("Test eseguito correttamente. size out " + out.length);
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	private void configure() { 
		if( LATestSuite.getCtx() == null ){
			ctx = new ClassPathXmlApplicationContext(new String[]{ "applicationContext.xml" });
		}else{
			ctx = LATestSuite.getCtx();
		}
		
	}
}