package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.SettoreGiuridicoService;
import eng.la.model.view.SettoreGiuridicoView;
import eng.la.util.ListaPaginata;
import junit.framework.TestCase;

public class SettoreGiuridicoTest extends TestCase {

	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}


	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void testCerca(){  
		configure(); 
		
		SettoreGiuridicoService service = (SettoreGiuridicoService) ctx.getBean("settoreGiuridicoService");
		try {
			 
			ListaPaginata<SettoreGiuridicoView> lista0 = service.cerca("", 3, 1, null, null);
			ListaPaginata<SettoreGiuridicoView> lista1 = service.cerca("", 3, 2, null, null); 
			
			System.out.println("Lettura SettoreGiuridico riuscita: "+ lista0);
			System.out.println("Lettura SettoreGiuridico riuscita: "+ lista1); 
			
			 
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
