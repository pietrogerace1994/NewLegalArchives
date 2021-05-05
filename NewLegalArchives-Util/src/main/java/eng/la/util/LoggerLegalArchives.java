package eng.la.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class LoggerLegalArchives {

	private static final Logger logger = Logger.getLogger(LoggerLegalArchives.class);
	
	   @Pointcut("execution(* eng.la.*.*.*(..))")
	   private void selectAll(){}

	   @AfterReturning(pointcut = "selectAll()", returning="retVal")
	   public void afterReturningAdvice(JoinPoint joinPoint,Object retVal){
		   logger.info("ESECUZIONE METODO: "+joinPoint.getTarget().getClass()+"."+ joinPoint.getSignature().getName()+" COMPLETATA.");	
	   }
  
	   @AfterThrowing(pointcut = "selectAll()", throwing = "ex")
	   public void AfterThrowingAdviceEng(JoinPoint joinPoint, Throwable ex){
		   logger.error("ECCEZIONE - METODO: "+joinPoint.getTarget().getClass()+"."+ joinPoint.getSignature().getName());
		   logger.error("ECCEZIONE - MESSAGGIO: "+ex.getMessage());
		   Writer writer = new StringWriter();
		   PrintWriter printWriter = new PrintWriter(writer);
		   ex.printStackTrace(printWriter);
		   logger.error("ECCEZIONE - STACK TRACE: "+writer.toString());
		   CurrentSessionUtil csu = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		   if( csu != null )
			   csu.setLastError(ex);
	   } 
	   
	   
	   /*
	    * Applicato a tutti i metodi pubblici 
	    * delle classi contenute nei package della gerarchia it
	    */
	    
	   @Around("execution(* it.eng.la.ws.*.*.*(..))")
	   public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
	        
	       // Log prima dell'invocazione del metodo
	       logger.debug("[" + new Date() + "]nt"
	               +  pjp.getTarget().getClass() + "."  +  pjp.getSignature().getName()
	               +  "ntArgomenti: "+ Arrays.toString(pjp.getArgs()));
	        
	       Object result = null;
	       try
	       {
	           // Invocazione metodo
	           result = pjp.proceed();
	       }
	       catch (Exception ex)
	       {
	    	   logger.error("tErrore: " + pjp.getSignature().getName());
	       }
	        
	       //Operazioni dopo l'esecuzione
	       logger.debug("tRisultato: "+ result);         
	       return result;
	   }
	   
	   @AfterThrowing(pointcut = "execution(* it.eng.la.ws.*.*.*(..))", throwing = "ex")
	   public void AfterThrowingAdviceEngWS(JoinPoint joinPoint, Throwable ex){
		   logger.error("ECCEZIONE - METODO: "+joinPoint.getTarget().getClass()+"."+ joinPoint.getSignature().getName());
		   logger.error("ECCEZIONE - MESSAGGIO: "+ex.getMessage());
		   Writer writer = new StringWriter();
		   PrintWriter printWriter = new PrintWriter(writer);
		   ex.printStackTrace(printWriter);
		   logger.error("ECCEZIONE - STACK TRACE: "+writer.toString());
		   CurrentSessionUtil csu = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		   if( csu != null )
			   csu.setLastError(ex);
	   } 
 
}




