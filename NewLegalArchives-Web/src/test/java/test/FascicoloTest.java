package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.FascicoloService;
import eng.la.model.view.FascicoloView;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import junit.framework.TestCase;

public class FascicoloTest  extends TestCase {
 
	ClassPathXmlApplicationContext ctx;

	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
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
	public void testLeggiAperti(){
		configure();
		FascicoloService fascicoloService = (FascicoloService) SpringUtil.getBean("fascicoloService"); 
		try {
			List<FascicoloView> fascicoliPage1 = (List<FascicoloView>) fascicoloService.leggiFascicoloOrdData("RI04805");
		 
			for (Iterator<FascicoloView> iterator = fascicoliPage1.iterator(); iterator.hasNext();) {
				FascicoloView fascicoloView = (FascicoloView) iterator.next();
				System.out.println("Incarichi Size:  "+fascicoloView.getVo().getIncaricos().size());
			}
			assertTrue(true);
		} catch (Throwable e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
 
	
	private void configure() { 
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