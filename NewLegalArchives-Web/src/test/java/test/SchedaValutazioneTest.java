package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.SchedaValutazioneService;
import eng.la.model.view.SchedaValutazioneView;
import junit.framework.TestCase;

public class SchedaValutazioneTest extends TestCase {
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	 
	// 20160701 - Test --
	public void testLeggiSchedaValutazione() {
		configure(); 
		SchedaValutazioneService service = (SchedaValutazioneService) ctx.getBean("schedaValutazioneService");
		try {
			 
			List<SchedaValutazioneView> schedeValutazioneView = service.leggi();
			System.out.println("Lettura schedeValutazioneView  riuscita: " + schedeValutazioneView == null ? 0 : schedeValutazioneView.size() ); 
			
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