package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.ScadenzaService;
import eng.la.model.view.ScadenzaView;
import junit.framework.TestCase;

public class ScadenzaTest extends TestCase {
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	 
	// 20160701 - Test --
	public void testLeggiScadenza() {
		configure(); 
		ScadenzaService service = (ScadenzaService) ctx.getBean("scadenzaService");
		try {
			long id=1;
			ScadenzaView scadenzaView = service.leggi(id);
			System.out.println("Lettura fattura con id=1 riuscita: ");
			System.out.println("id : " + scadenzaView.getVo().getId());
			System.out.println("TempoADisposizione  : " + scadenzaView.getVo().getTempoADisposizione());
			System.out.println("Oggetto  : " + scadenzaView.getVo().getOggetto());
			System.out.println("Descrizione  : " + scadenzaView.getVo().getDescrizione());
			System.out.println("DelayAvviso  : " + scadenzaView.getVo().getDelayAvviso());
			System.out.println("FrequenzaAvviso  : " + scadenzaView.getVo().getFrequenzaAvviso());
			
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