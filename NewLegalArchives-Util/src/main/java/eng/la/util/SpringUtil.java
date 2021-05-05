package eng.la.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringUtil {
	 
	 private static ApplicationContext ctx;
	 	
	 public static void setCtx(ApplicationContext ctx1) {
		ctx = ctx1;
	}

	private static ApplicationContext getCtx() {        
	    return ctx == null ? ContextLoader.getCurrentWebApplicationContext() : ctx;
     }

	 public static Object getBean(String beanName){
		 return getCtx().getBean(beanName);
	 }
}
