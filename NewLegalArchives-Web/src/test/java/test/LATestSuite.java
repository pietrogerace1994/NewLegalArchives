package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.Test;
import junit.framework.TestSuite;

public class LATestSuite extends TestSuite {

	private static ClassPathXmlApplicationContext ctx;

	public static ClassPathXmlApplicationContext getCtx() {
		return ctx; 
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(LATestSuite.class.getName());
		System.out.println("Avvio test suite");
		configure();
		// $JUnit-BEGIN$
		System.out.println("Aggiungo classi di test alla suite");
		suite.addTestSuite(MateriaTest.class);
		suite.addTestSuite(NazioneTest.class);
		suite.addTestSuite(SocietaTest.class);
		suite.addTestSuite(StudioLegaleTest.class);
		suite.addTestSuite(TipologiaFascicoloTest.class);
		suite.addTestSuite(SettoreGiuridicoTest.class);
		suite.addTestSuite(ParteCorrelataTest.class);
		suite.addTestSuite(CentroDiCostoTest.class);
		suite.addTestSuite(VoceDiContoTest.class);
		//suite.addTestSuite(RicercaParteCorrelataTest.class);
		suite.addTestSuite(GiudizioTest.class);
		suite.addTestSuite(RicorsoTest.class);
		suite.addTestSuite(DueDiligenceTest.class);
	   // suite.addTestSuite(FatturaTest.class);
		suite.addTestSuite(AttoTest.class);
	 //	suite.addTestSuite(EventoTest.class);
		suite.addTestSuite(SendMailTest.class);
		/*suite.addTestSuite(GestioneWorkflowTest.class);*/

		// $JUnit-END$
		return suite;
	}

	private static void configure() {
		System.out.println("Inizializzo Spring Context"); 
		ctx = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });

	}
}
