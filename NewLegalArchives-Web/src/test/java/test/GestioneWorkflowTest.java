package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import eng.la.business.IncaricoService;
import eng.la.business.UtenteService;
import eng.la.business.workflow.AttoWfService;
import eng.la.business.workflow.ConfigurazioneStepWfService;
import eng.la.business.workflow.FascicoloWfService;
import eng.la.business.workflow.IncaricoWfService;
import eng.la.business.workflow.StepWfService;
import eng.la.model.view.ConfigurazioneStepWfView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.StepWfView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.exceptions.LAException;
import junit.framework.TestCase;

public class GestioneWorkflowTest extends TestCase{
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	
	
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
	
	public void _testLeggiSeResponsabileFoglia(){  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		
		try {
			UtenteView utenteView = service.leggiUtenteDaUserId("RI04738");
			utenteView.setResponsabileFoglia(service.leggiSeResponsabileFoglia(utenteView));
			boolean b = utenteView.isResponsabileFoglia();
			if(b)
				System.out.println("vero");
			else
				System.out.println("falso");
			
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testLeggiProfiloUtente(){  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		
		try {
			UtenteView utenteView = service.leggiUtenteDaUserId("RI04793");
			utenteView.setResponsabileFoglia(service.leggiSeResponsabileFoglia(utenteView));
			
			//DARIO ********************************************************************
			//utenteView.setPrimoRiporto(service.leggiSePrimoRiporto(utenteView));
			utenteView = service.settaMatricoleTopManagers(utenteView); 
			//**************************************************************************
			
			utenteView.setOperatoreSegreteria(service.leggiSeOperatoreSegreteria(utenteView));
			utenteView.setLegaleInterno(service.leggiSeLegaleInterno(utenteView));
			utenteView.setResponsabileSenzaCollaboratori(service.leggiSeResponsabileSenzaCollaboratori(utenteView));

			boolean b = utenteView.isResponsabileFoglia();
			if(b)
				System.out.println("ResponsabileFoglia vero");
			else
				System.out.println("ResponsabileFoglia falso");
			b = utenteView.isPrimoRiporto();
			if(b)
				System.out.println("PrimoRiporto vero");
			else
				System.out.println("PrimoRiporto falso");
			
			b = utenteView.isOperatoreSegreteria();
			if(b)
				System.out.println("OperatoreSegreteria vero");
			else
				System.out.println("OperatoreSegreteria falso");
			b = utenteView.isLegaleInterno();
			if(b)
				System.out.println("LegaleInterno vero");
			else
				System.out.println("LegaleInterno falso");
			b = utenteView.isResponsabileSenzaCollaboratori();
			if(b)
				System.out.println("ResponsabileSenzaCollaboratori vero");
			else
				System.out.println("ResponsabileSenzaCollaboratori falso");
			
			}catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				assertTrue(false);
			}
		}
	
	public void _testLeggiSeLegaleInterno(){  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		
		try {
			UtenteView utenteView = service.leggiUtenteDaUserId("RI04738");
			utenteView.setLegaleInterno(service.leggiSeLegaleInterno(utenteView));
			boolean b = utenteView.isLegaleInterno();
			if(b)
				System.out.println("vero");
			else
				System.out.println("falso");
			
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testLeggiAssegnatarioCorrente(){  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		try {
			UtenteView utente = service.leggiAssegnatarioWfCorrente(127,3);
			
			System.out.println(utente.getVo().getMatricolaUtil());
			
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testLeggiUtenteDaMatricola(){  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		try {
			UtenteView utente = service.leggiUtenteDaMatricola("0910004797");
			boolean b = service.leggiSePrimoRiporto(utente);
			if(b){
				assertTrue(true);
			}
			
			System.out.println(utente.getVo().getNominativoUtil());
			
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testLeggiConfigurazioneStepSuccessivo(){  
		configure(); 
		ConfigurazioneStepWfService service = (ConfigurazioneStepWfService) ctx.getBean("configurazioneStepWfService");
		
		try {
			ConfigurazioneStepWfView configurazione = service.leggiConfigurazioneStepSuccessivo(1, "APAP",   true, "IT");
			
			System.out.println(configurazione.getVo().getId());
			
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testLeggiConfigurazioneStepCorrente(){  
		configure(); 
		ConfigurazioneStepWfService service = (ConfigurazioneStepWfService) ctx.getBean("configurazioneStepWfService");
		
		try {
			ConfigurazioneStepWfView configurazione = service.leggiConfigurazioneStepCorrente(1, 1, "IT");
			
			System.out.println(configurazione.getVo().getId());
			
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
	
	public void _testAvviaWorkflow() throws Throwable{  
		configure(); 
		IncaricoWfService service = (IncaricoWfService) ctx.getBean("incaricoWfService");
		
		try {
			boolean b = service.avviaWorkflow(246, "RI04738", false,"");
			if(b){
				assertTrue(true);
			}
			} catch (LAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getCode());
			assertTrue(false);
		}
	}

	
	public void _testAvviaWorkflowAtto(){  
		configure(); 
		AttoWfService service = (AttoWfService) ctx.getBean("attoWfService");
		
		try {
			boolean b = service.avviaWorkflow(60, "0910004563", "RI03667" );
			if(b){
				assertTrue(true);
			}
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testLeggiAttivitaPendenti() throws Throwable{  
		configure(); 
		StepWfService service = (StepWfService) ctx.getBean("stepWfService");
		
		try {
			List<StepWfView>  lista = service.leggiAttivitaPendenti( "0910004563", "IT");
			for( StepWfView step : lista ){
				System.out.println(step.getDescLinguaCorrente());
				System.out.println(step.getVo().getConfigurazioneStepWf().getClasseWf().getCodice());
				
			}
			
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testLeggiCollaboratoriLegaliInterni() throws Throwable{  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		try {
			List<UtenteView>  lista = service.leggiCollaboratoriLegaliInterni("0910004793");
			for( UtenteView utente : lista ){
				System.out.println(utente.getVo().getNominativoUtil());
			}
			
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testLeggiAttoriWf() throws Throwable{  
		configure(); 
		UtenteService service = (UtenteService) ctx.getBean("utenteService");
		
		try {
			List<UtenteView>  lista = service.leggiAttoriWorkflow(7, "");
			for( UtenteView utente : lista ){
				System.out.println(utente.getVo().getEmailUtil());
			}
			
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	public void _testLeggiIncaricoDaProforma() throws Throwable{  
		configure(); 
		IncaricoService service = (IncaricoService) ctx.getBean("proformaService");
		
		try {
			IncaricoView  incarico = service.leggiIncaricoAssociatoAProforma(137);
				System.out.println(incarico.getNomeIncarico());
				System.out.println(incarico.getNomeFascicolo());
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testChiudiFascicolo(){  
		configure(); 
		FascicoloWfService service = (FascicoloWfService) ctx.getBean("fascicoloWfService");
		
		try {
			boolean b = service.chiudiFascicolo(8, "RI04797" );
			if(b){
				assertTrue(true);
			}
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testChiudiAtto(){  
		configure(); 
		AttoWfService service = (AttoWfService) ctx.getBean("attoWfService");
		
		try {
			boolean b = service.chiudiAtto(1, CostantiDAO.ATTO_STATO_REGISTRATO,"RI03743"  );
			if(b){
				assertTrue(true);
			}
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testAvanzaIncarico(){  
		configure(); 
		IncaricoWfService service = (IncaricoWfService) ctx.getBean("incaricoWfService");
		
		try {
			boolean b = service.avanzaWorkflow(1, "RI03826" );
			if(b){
				assertTrue(true);
			}
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testAvanzaAtto(){  
		configure(); 
		AttoWfService service = (AttoWfService) ctx.getBean("attoWfService");
		
		try {
			boolean b = service.avanzaWorkflow(4, "0910003826", "RI03743" );
			if(b){
				assertTrue(true);
			}
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	public void _testRifiutaFascicolo(){  
		configure(); 
		FascicoloWfService service = (FascicoloWfService) ctx.getBean("fascicoloWfService");
		
		try {
			boolean b = service.rifiutaWorkflow(8, "RI04797", "test" );
			if(b){
				assertTrue(true);
			}
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void _testRifiutaAtto(){  
		configure(); 
		AttoWfService service = (AttoWfService) ctx.getBean("attoWfService");
		
		try {
			boolean b = service.rifiutaWorkflow(5, "RI04563", "prova rifiuto da General Counsel verso segretaria" );
			if(b){
				assertTrue(true);
			}
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
	}


}
