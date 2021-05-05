package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class SendMailTest extends TestCase{
	ClassPathXmlApplicationContext ctx; 
	
	public ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ClassPathXmlApplicationContext ctx) {
		this.ctx = ctx;
	}

	public void testInviaEmail(){  
	/*	configure(); 
		EmailNotificationService service = (EmailNotificationService) ctx.getBean("emailNotificationService");
		
		try {
			Mail mail = new Mail();
		//	mail.setDocumentLink("link");
			mail.setNomeDocumento("Nome Documento");
		  
			
			} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}*/
	} 
	
//	public void testVerificaFolderIncarico(){
//		configure(); 
//		IncaricoService service = (IncaricoService) SpringUtil.getBean("incaricoService");
//		DocumentaleDAO documentaleDao = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
//		DocumentaleCryptDAO documentaleCryptDao = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
//		Map<Long, String> listaIdDaFixare = new HashMap<Long,String>();
//		try {
//			List<IncaricoView> l = service.leggi();
//			for( IncaricoView view: l ){
//				IncaricoView viewSaved = service.leggi(view.getVo().getId());
//				String folderIncarico  = FileNetUtil.getIncaricoCartella(viewSaved.getVo().getId(), viewSaved.getVo().getFascicolo().getDataCreazione(),
//						viewSaved.getVo().getFascicolo().getNome(), viewSaved.getVo().getNomeIncarico());
//				boolean isPenale = viewSaved.getVo().getFascicolo().getTipologiaFascicolo().getCodGruppoLingua().equals(Costanti.TIPOLOGIA_FASCICOLO_GIUDIZIALE_COD) &&
//						viewSaved.getVo().getFascicolo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
//				boolean daFixare = false;
//				if( isPenale ){
//					Folder folder = documentaleCryptDao.leggiCartella(folderIncarico);
//					if( folder == null || folder.get_Id() == null ){
//						daFixare = true;
//					}
//				}else{
//					Folder folder = documentaleDao.leggiCartella(folderIncarico);
//					if( folder == null || folder.get_Id() == null ){
//						daFixare = true;
//					}
//				}
//				
//				if( daFixare ){
//					listaIdDaFixare.put(view.getVo().getId(), viewSaved.getVo().getNomeIncarico());
//				}
//				 
//			}
//			
//			Set<Long> keys = listaIdDaFixare.keySet();
//			for( Long id : keys ){
//				System.out.println("ID incarico: " + id + ", path folder: " + listaIdDaFixare.get(id));
//			}
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/*private void configure() { 
		if( LATestSuite.getCtx() == null ){
			ctx = new ClassPathXmlApplicationContext(new String[]{ "applicationContext.xml" });
		}else{
			ctx = LATestSuite.getCtx();
		}
		
	}*/
	
	
}
