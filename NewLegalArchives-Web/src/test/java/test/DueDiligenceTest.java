package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.DueDiligenceService;
import eng.la.model.view.DueDiligenceView;
import junit.framework.TestCase;

public class DueDiligenceTest extends TestCase {
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Metdodo di test.
	 */
	// 20160629 - Test OK
	/*
	public void testInserisciDueDiligence(){   
		configure();
		DueDiligenceService service = (DueDiligenceService) ctx.getBean("dueDiligenceService");
		try {
			long id=1;
			DueDiligenceView dueDiligenceView = new DueDiligenceView();
			dueDiligenceView.setVo(new DueDiligence());
			//dueDiligenceView.getVo().setId(id);
			dueDiligenceView.getVo().setDataApertura(new Date());
			// professionista esterno
			ProfessionistaEsterno profExt = new ProfessionistaEsterno();profExt.setId(1);
			dueDiligenceView.getVo().setProfessionistaEsterno(profExt);
			// stato
			StatoDueDiligence statoDueDiligence = new StatoDueDiligence(); statoDueDiligence.setId(1);
			dueDiligenceView.getVo().setStatoDueDiligence(statoDueDiligence);			
			// byte array test
			byte[] documento = "[Test Inserisci] - 0qwerty5467839I".getBytes();
			// esegue inserisci
			service.inserisci(dueDiligenceView,documento);
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	// 20160629 - Test OK
	/*
	public void testCancellaDueDiligence(){   
		configure();
		DueDiligenceService service = (DueDiligenceService) ctx.getBean("dueDiligenceService");
		try {
			long id=3;
			DueDiligenceView dueDiligence = service.leggi(id);
			if (dueDiligence!=null) {
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
	
	// 20160629 - Test OK
	/*
	public void testModificaDueDiligence(){   
		configure();
		DueDiligenceService service = (DueDiligenceService) ctx.getBean("dueDiligenceService");
		try {
			long id=4;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateInString = "30-06-2016";
			Date dataChiusura = sdf.parse(dateInString);
			int idStato = 2;
			
			// prima lettura dati...
			DueDiligenceView dueDiligenceView = service.leggi(id);
			// set dei dati da modificare.
			dueDiligenceView.getVo().setDataChiusura(dataChiusura);
			StatoDueDiligence statoDue = new StatoDueDiligence(); statoDue.setId(idStato);
			dueDiligenceView.getVo().setStatoDueDiligence(statoDue);
			// byte array test
			byte[] documento = "[Test Modifica]-0qwerty5467839M".getBytes();
			// esecuzione modifica
			service.modifica(dueDiligenceView,documento);
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	// 20160629 - Test OK
	/*
	public void testLeggiDueDiligence() {
		configure(); 
		DueDiligenceService service = (DueDiligenceService) ctx.getBean("dueDiligenceService");
		try {
			long id=3;
			DueDiligenceView dueDiligenceView = service.leggi(id);
			System.out.println("Lettura dueDiligence con id=3 riuscita: ");
			System.out.println("Professionista Esterno - Cognome : " + dueDiligenceView.getVo().getProfessionistaEsterno().getCognome());
			System.out.println("StatoDueDiligence - statoDue : " + dueDiligenceView.getVo().getStatoDueDiligence().getNome());
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	// 20160629 - Test OK.
	public void testElenchi(){
		configure();
		DueDiligenceService service = (DueDiligenceService) ctx.getBean("dueDiligenceService");
		try {			
			
			long idProfEsterno = 1;
			List<Object> listAnni = service.elencoAnni(idProfEsterno);
			for (Object obj:listAnni){
				System.out.println("Anno: " + obj);
			}
			
			int anno=2016;
			List<DueDiligenceView> list = service.elenco(idProfEsterno,anno);
			for (DueDiligenceView obj:list){
				System.out.println("id DueDiligence : " + obj.getVo().getId());
				System.out.println("Id prof ext : " + obj.getVo().getProfessionistaEsterno().getId());
				System.out.println("Nome Prof Ext : " + obj.getVo().getProfessionistaEsterno().getNome());
				System.out.println("Cognome Prof Ext : " + obj.getVo().getProfessionistaEsterno().getCognome());
			}
			
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}		
	}

	/*
	public void testLeggiPartiCorrelate(){  
		configure(); 
		DueDiligenceService service = (DueDiligenceService) ctx.getBean("parteCorrelataService");
		try {
			DueDiligenceView dueDiligenceView = service.leggi(1);
			System.out.println("Lettura dueDiligence con id=1 riuscita: "+ dueDiligenceView.getVo().getProfessionistaEsterno().getCognome());
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	/*
	public void testCercaPartiCorrelate(){  
		configure(); 
		DueDiligenceService service = (DueDiligenceService) ctx.getBean("parteCorrelataService");
		try {
			// chiavi di ricerca.
			String denominazione="SRT SPA";
			String codFiscPIva="00166888612";
			// esecuzione ricerca.
			List<DueDiligenceView> list = service.cerca(denominazione, codFiscPIva);
			System.out.println("Ricerca parte correlata eseguita con successo : ");
			for(DueDiligenceView row:list){
				System.out.println("id: "+ row.getVo().getId());
				System.out.println("denominazione: " + row.getVo().getDenominazione());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataInserimento: "+ row.getVo().getDataInserimento());
			}
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