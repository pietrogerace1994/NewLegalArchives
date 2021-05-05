package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.ProformaService;
import eng.la.model.view.ProformaView;
import junit.framework.TestCase;

public class ProformaTest extends TestCase {

	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}


	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	  
	public void testLeggi(){  
		configure(); 
		
		ProformaService service = (ProformaService) ctx.getBean("proformaService");
		try {
			 
			List<ProformaView> lista = service.cerca("" );
			
			System.out.println("Lettura materia riuscita: "+ lista); 
			
			 
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