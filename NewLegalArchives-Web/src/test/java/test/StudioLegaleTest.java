package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.StudioLegaleService;
import eng.la.model.view.StudioLegaleView;
import eng.la.util.ListaPaginata;
import junit.framework.TestCase;

public class StudioLegaleTest  extends TestCase {

	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}


	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void testCerca(){  
		configure(); 
		
		StudioLegaleService service = (StudioLegaleService) ctx.getBean("studioLegaleService");
		try {
			 
			ListaPaginata<StudioLegaleView> lista0 = service.cerca("", 1, "", 3, 1, null, null);
			ListaPaginata<StudioLegaleView> lista1 = service.cerca("", 1, "", 3, 2, null, null); 
			
			System.out.println("Lettura StudioLegale riuscita: "+ lista0);
			System.out.println("Lettura StudioLegale riuscita: "+ lista1); 
			
			 
			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		} 
  
	}

	public void testLeggiStudioLegale(){  
		configure(); 
		
		StudioLegaleService service = (StudioLegaleService) ctx.getBean("studioLegaleService");
		try {
			 
			List<StudioLegaleView> lista = service.leggi();
			System.out.println("Lettura studio legale riuscita: "+ lista);
			 
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