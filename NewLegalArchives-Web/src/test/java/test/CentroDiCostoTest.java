package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.CentroDiCostoService;
import eng.la.model.view.CentroDiCostoView;
import eng.la.util.ListaPaginata;
import junit.framework.TestCase;

public class CentroDiCostoTest extends TestCase {

	ClassPathXmlApplicationContext ctx;

	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void testCerca() {
		configure();

		CentroDiCostoService service = (CentroDiCostoService) ctx.getBean("centroDiCostoService");
		try {

			ListaPaginata<CentroDiCostoView> lista0 = service.cerca(0, 0, 0, "", 3, 1, null, null);
			ListaPaginata<CentroDiCostoView> lista1 = service.cerca(0, 0, 0, "", 3, 2, null, null);

			System.out.println("Lettura CentroDiCostoView riuscita: " + lista0);
			System.out.println("Lettura CentroDiCostoView riuscita: " + lista1);

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