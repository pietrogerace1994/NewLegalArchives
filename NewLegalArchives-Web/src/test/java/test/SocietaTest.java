package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.SocietaService;
import eng.la.model.view.SocietaView;
import eng.la.util.ListaPaginata;
import junit.framework.TestCase;

public class SocietaTest extends TestCase {

	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}


	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}


	public void testCerca(){  
		configure(); 
		
		SocietaService service = (SocietaService) ctx.getBean("societaService");
		try {
			 
			ListaPaginata<SocietaView> lista0 = service.cerca("", 1, 3, 1, null, null);
			ListaPaginata<SocietaView> lista1 = service.cerca("", 1, 3, 2, null, null); 
			
			System.out.println("Lettura Societa riuscita: "+ lista0);
			System.out.println("Lettura Societa riuscita: "+ lista1); 
			
			 
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
