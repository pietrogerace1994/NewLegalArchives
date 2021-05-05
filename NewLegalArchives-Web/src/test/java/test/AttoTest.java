package test;
 
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.AttoService;
import eng.la.model.view.AttoView;
import junit.framework.TestCase;

public class AttoTest extends TestCase {
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	 
	// 20160701 - Test --
	public void testLeggiAtto() {
		configure(); 
		AttoService service = (AttoService) ctx.getBean("attoService");
		try { 
			List<AttoView> attiView = service.leggi();
			System.out.println("Lettura attiView  riuscita: "+  attiView == null ? 0 : attiView.size());
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