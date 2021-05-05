package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.GiudizioService;
import eng.la.model.view.GiudizioView;
import junit.framework.TestCase;

public class GiudizioTest extends TestCase {

	ClassPathXmlApplicationContext ctx;

	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void testLeggi() {
		configure();

		GiudizioService service = (GiudizioService) ctx.getBean("giudizioService");
		try {

			List<GiudizioView> lista = service.leggi();
			System.out.println("Lettura GiudizioView riuscita: " + lista);

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
