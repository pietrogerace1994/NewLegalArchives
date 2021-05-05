package test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.UtenteService;
import eng.la.model.view.UtenteView;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import junit.framework.TestCase;

public class CronologiaFascicoloTest  extends TestCase {
 
	static ClassPathXmlApplicationContext ctx;

	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		CronologiaFascicoloTest.ctx = ctx;
	}
	

public static void main(String[] args) {
	configure(); 
	UtenteService service = (UtenteService) ctx.getBean("utenteService");
	
	try {
		List<UtenteView> listaUtenti = service.leggiResponsabili("0910004724");
		
//		for(UtenteView row:listaUtenti){
//			System.out.println("matricola: "+ row.getVo().getMatricolaUtil());
//			System.out.println("dnominativo: " + row.getVo().getNominativoUtil());
//		}

		System.out.println("matricola: "+ listaUtenti.get(0).getVo().getNominativoUtil());
		System.out.println("matricola: "+ listaUtenti.get(1).getVo().getNominativoUtil());
		System.out.println("matricola: "+ listaUtenti.get(2).getVo().getNominativoUtil());
		
		} catch (Throwable e) {
		e.printStackTrace();
	}
}
//	public void testLeggi(){
//		configure();
//		FascicoloService fascicoloService = (FascicoloService) SpringUtil.getBean("fascicoloService"); 
//		try {
//			List<FascicoloView> fascicoliPage1 = (List<FascicoloView>) fascicoloService.cerca("", "", "", "", "", "", null, null, 17 , 0, "", "", 10, 1, "", "", null) ;
//			List<FascicoloView> fascicoliPage2 = (List<FascicoloView>) fascicoloService.cerca("", "", "", "", "", "", null, null, 17 , 0, "", "", 10, 2, "", "", null) ;
//			List<FascicoloView> fascicoliPage3 = (List<FascicoloView>) fascicoloService.cerca("", "", "", "", "", "", null, null, 17 , 0, "", "", 10, 3, "", "", null) ;
//		 
//			assertTrue(true);
//		} catch (Throwable e) {
//			assertTrue(false);
//			e.printStackTrace();
//		}
//	}
//	
//	public void testLeggiAperti(){
//		configure();
//		FascicoloService fascicoloService = (FascicoloService) SpringUtil.getBean("fascicoloService"); 
//		try {
//			FascicoloView fascicolo = (FascicoloView) fascicoloService.leggiPerCronologia(new Long(22251));
//		 
//
//				System.out.println("Incarichi Size:  "+fascicolo.getListaIncarichiCron().toString());
//
//			assertTrue(true);
//		} catch (Throwable e) {
//			assertTrue(false);
//			e.printStackTrace();
//		}
//	}
	
	public void _testLeggiResponsabili(){  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		try {
			List<UtenteView> listaUtenti = service.leggiResponsabili("0910004256");
			
			for(UtenteView row:listaUtenti){
				System.out.println("matricola: "+ row.getVo().getMatricolaUtil());
				System.out.println("dnominativo: " + row.getVo().getNominativoUtil());
			}

			
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
 
	
	private static void configure() { 
			ctx = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
			SpringUtil.setCtx(ctx);
			CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
			currentSessionUtil.setUserId("RI03736");
			List<String> rolesCode = new ArrayList<String>();
			rolesCode.add("LEG_ARC_AMMINISTRATORE");
			rolesCode.add("LEG_ARC_RESPONSABILE");
			rolesCode.add("LEG_ARC_GESTOREFASCICOLI");
			 
			currentSessionUtil.setRolesCode(rolesCode);

	}
}