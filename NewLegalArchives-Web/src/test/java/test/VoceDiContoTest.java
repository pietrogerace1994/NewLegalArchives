package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.VoceDiContoService;
import eng.la.model.view.VoceDiContoView;
import eng.la.util.ListaPaginata;
import junit.framework.TestCase;

public class VoceDiContoTest extends TestCase {

	ClassPathXmlApplicationContext ctx;

	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void testCerca() {
		configure();

		VoceDiContoService service = (VoceDiContoService) ctx.getBean("voceDiContoService");
		try {

			ListaPaginata<VoceDiContoView> lista0 = service.cerca(0, 0, "", 3, 1, null, null);
			ListaPaginata<VoceDiContoView> lista1 = service.cerca(0, 0, "", 3, 2, null, null);

			System.out.println("Ricerca VoceDiContoView riuscita: " + lista0);
			System.out.println("Ricerca VoceDiContoView riuscita: " + lista1);

			assertTrue(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}

	}

	public void testLeggi() {
		configure();

		VoceDiContoService service = (VoceDiContoService) ctx.getBean("voceDiContoService");
		try {

			List<VoceDiContoView> lista = service.leggi();
			System.out.println("Lettura VoceDiContoView riuscita: " + lista);

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