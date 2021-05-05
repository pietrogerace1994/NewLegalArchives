package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.NazioneService;
import eng.la.model.view.NazioneView;
import junit.framework.TestCase;

public class NazioneTest extends TestCase {

	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}


	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}


	public void testLeggiNazioni(){  
		configure(); 
		
		NazioneService service = (NazioneService) ctx.getBean("nazioneService");
		try {
			 
			List<NazioneView> lista = service.leggi();
			System.out.println("Lettura nazioni riuscita: "+ lista);
			NazioneView nazione = service.leggi(1);
			System.out.println("Lettura nazione con id=1 riuscita: "+ nazione);
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
