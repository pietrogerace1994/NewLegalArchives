package test;

import java.util.Locale;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.MateriaService;
import junit.framework.TestCase;

public class MateriaTest extends TestCase {

	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}


	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	public void testCercaMateria(){  
		configure(); 
		
		MateriaService service = (MateriaService) ctx.getBean("materiaService");
		Locale locale = Locale.ITALIAN;
		try {
			service.leggiAlberaturaMateria(1, locale, false);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
