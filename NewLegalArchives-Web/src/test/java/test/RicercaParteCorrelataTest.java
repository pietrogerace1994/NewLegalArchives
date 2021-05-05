package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;


public class RicercaParteCorrelataTest extends TestCase {
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}	
	
	/* Tests scrittura  */
	
	/*
	public void testInserisciRicercaParteCorrelata(){   
		configure();
		RicercaParteCorrelataService service = (RicercaParteCorrelataService) ctx.getBean("ricercaParteCorrelataService");
		try {
			RicercaParteCorrelataView ricercaParteCorrelataView = new RicercaParteCorrelataView();
			ricercaParteCorrelataView.setVo(new RicercaParteCorrelata());
			ricercaParteCorrelataView.getVo().setCodFisPartIva("00167880662");
			ricercaParteCorrelataView.getVo().setDenominazioneIn("SRT SPA");
			ricercaParteCorrelataView.getVo().setDataRicerca(new Date());
			ricercaParteCorrelataView.getVo().setUserRicerca("User Test");
			ricercaParteCorrelataView.getVo().setEsito("P");
			ricercaParteCorrelataView.getVo().setReport(readFile("C://tmp//test_doc.pdf"));
			
			service.inserisci(ricercaParteCorrelataView);
			assertTrue(true);
		} catch (Throwable t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	/*
	public void testCancellaRicercaParteCorrelata(){   
		configure();
		RicercaParteCorrelataService service = (RicercaParteCorrelataService) ctx.getBean("ricercaParteCorrelataService");
		try {
			long id=2;
			RicercaParteCorrelataView ricercaParteCorrelata = service.leggi(id);
			if (ricercaParteCorrelata!=null) {
				service.cancella(id);
				System.out.println("l'occorrenza (" + id + ") è stata cancellata.");
			} else {
				System.out.println("l'occorrenza (" + id + ") da cancellare non esiste.");
			}
			assertTrue(true);
		} catch (Throwable t) {
			t.printStackTrace();
			assertTrue(false);
		} 
  
	}
	*/
	
	/*
	public void testModificaParteCorrelata(){   
		configure();
		RicercaParteCorrelataService service = (RicercaParteCorrelataService) ctx.getBean("parteCorrelataService");
		try {
			long id=2;
			String codFiscPartIva="00167880999"; // PIVA da modificare 00167880988 ( il precedente valore)
			String denominazione = "SRT SPA";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateInString = "23-06-2016";
			
			Date dataRicerca = sdf.parse(dateInString);
			Date dataIn =  sdf.parse(dateInString);

			RicercaParteCorrelataView ricercaParteCorrelataView = new RicercaParteCorrelataView();
			ricercaParteCorrelataView.setVo(new RicercaParteCorrelata());
			ricercaParteCorrelataView.getVo().setId(id);
			ricercaParteCorrelataView.getVo().setCodFisPartIva(codFiscPartIva);
			ricercaParteCorrelataView.getVo().setDenominazioneIn(denominazione);
			ricercaParteCorrelataView.getVo().setDataRicerca(dataRicerca);
			ricercaParteCorrelataView.getVo().setDataIn(dataIn);
			
			// esecuzione modifica.
			service.modifica(ricercaParteCorrelataView);
			assertTrue(true);
		} catch (Throwable t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	/* Tests lettura */
	/*
	public void testLeggiPartiCorrelate(){  
		configure(); 
		RicercaParteCorrelataService service = (RicercaParteCorrelataService) ctx.getBean("ricercaParteCorrelataService");
		try {
			List<RicercaParteCorrelataView> lista = service.leggi();
			for(RicercaParteCorrelataView row:lista){
				System.out.println("id: "+ row.getVo().getId());
				System.out.println("denominazioneIn: " + row.getVo().getDenominazioneIn());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataRicerca: "+ row.getVo().getDataRicerca());
			}
			
			RicercaParteCorrelataView ricercaParteCorrelataView = service.leggi(1);
			System.out.println("Lettura ricerca parte correlata con id=1 riuscita: "+ ricercaParteCorrelataView.getVo().getDenominazioneIn());
			ricercaParteCorrelataView = service.leggi("00166888612");
			System.out.println("Lettura parte correlata con codCfPiva=00166888612 riuscita: "+ ricercaParteCorrelataView.getVo().getDenominazioneIn());
			assertTrue(true);
		} catch (Throwable t) {
			t.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	/*
	public void testCercaPartiCorrelate(){  
		configure(); 
		RicercaParteCorrelataService service = (RicercaParteCorrelataService) ctx.getBean("parteCorrelataService");
		try {
			// chiavi di ricerca.
			String denominazione="SRT SPA";
			String codFiscPIva="00166888612";
			// esecuzione ricerca.
			List<RicercaParteCorrelataView> list = service.cerca(denominazione, codFiscPIva);
			System.out.println("Ricerca parte correlata eseguita con successo : ");
			for(RicercaParteCorrelataView row:list){
				System.out.println("id: "+ row.getVo().getId());
				System.out.println("denominazioneIn: " + row.getVo().getDenominazioneIn());
				System.out.println("codFiscPIva: "+ row.getVo().getCodFisPartIva());
				System.out.println("dataRicerca: "+ row.getVo().getDataRicerca());
			}
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	*/
	
	
	/* Utility section */
	/*private byte[] readFile(String fileName) throws Throwable {
		RandomAccessFile f = new RandomAccessFile(fileName, "r");
		byte[] b = new byte[(int)f.length()];
		f.read(b);
		return b;
	}

	private void configure() { 
		if( LATestSuite.getCtx() == null ){
			ctx = new ClassPathXmlApplicationContext(new String[]{ "applicationContext.xml" });
		}else{
			ctx = LATestSuite.getCtx();
		}
		
	}*/
}