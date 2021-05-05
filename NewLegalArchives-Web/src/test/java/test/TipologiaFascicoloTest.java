package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.TipologiaFascicoloService;
import eng.la.model.view.TipologiaFascicoloView;
import eng.la.util.ListaPaginata;
import junit.framework.TestCase;

public class TipologiaFascicoloTest extends TestCase {
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}


	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}


	public void testCerca(){  
		configure(); 
		
		TipologiaFascicoloService service = (TipologiaFascicoloService) ctx.getBean("tipologiaFascicoloService");
		try {
			 
			ListaPaginata<TipologiaFascicoloView> lista0 = service.cerca("", 3, 1, null, null);
			ListaPaginata<TipologiaFascicoloView> lista1 = service.cerca("", 3, 2, null, null); 
			
			System.out.println("Lettura TipologiaFascicolo riuscita: "+ lista0);
			System.out.println("Lettura TipologiaFascicolo riuscita: "+ lista1); 
			
			 
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		} 
  
	}
	
	public void testLeggiTipologiaFascicolo(){  
		configure(); 
		
		TipologiaFascicoloService service = (TipologiaFascicoloService) ctx.getBean("tipologiaFascicoloService");
		try {
			 
			List<TipologiaFascicoloView> lista = service.leggi();
			System.out.println("Lettura tipologia fascicolo riuscita: "+ lista);
			 
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		} 
  
	}
 
	private void configure() { 
		if( LATestSuite.getCtx() == null ){
			ctx = new ClassPathXmlApplicationContext(new String[]{ "applicationContext.xml" });
		}else{
			ctx = LATestSuite.getCtx();
		}
		
	}
}
