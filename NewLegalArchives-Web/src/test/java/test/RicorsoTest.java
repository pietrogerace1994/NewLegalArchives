package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.RicorsoService;
import eng.la.model.view.RicorsoView;
import junit.framework.TestCase;

public class RicorsoTest extends TestCase {

	ClassPathXmlApplicationContext ctx;

	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void testLeggi() {
		configure();

		RicorsoService service = (RicorsoService) ctx.getBean("ricorsoService");
		try {

			List<RicorsoView> lista = service.leggi();
			System.out.println("Lettura RicorsoView riuscita: " + lista);

			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}

	}
 

	private void configure() {
		if (LATestSuite.getCtx() == null) {
			ctx = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		} else {
			ctx = LATestSuite.getCtx();
		}

	}
}
