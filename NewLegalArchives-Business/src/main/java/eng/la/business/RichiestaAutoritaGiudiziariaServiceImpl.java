package eng.la.business;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Documento;
import eng.la.model.RichAutGiud;
import eng.la.model.view.AutoritaGiudiziariaView;
import eng.la.persistence.RichiestaAutGiudiziariaDAO;

@Service("richiestaAutoritaGiudiziariaService")
public class RichiestaAutoritaGiudiziariaServiceImpl extends BaseService<RichAutGiud,AutoritaGiudiziariaView> implements RichiestaAutoritaGiudiziariaService {

	@Autowired
	private RichiestaAutGiudiziariaDAO richiestaAutGiudiziariaDAO;
	

	@Override
	public RichAutGiud save(RichAutGiud richAutGiud) throws Throwable {
		RichAutGiud richAutGiudSaved = richiestaAutGiudiziariaDAO.save(richAutGiud);
		return richAutGiudSaved;
	}


	@Override
	public RichAutGiud update(RichAutGiud richAutGiud) throws Throwable {
		RichAutGiud richAutGiudSaved = richiestaAutGiudiziariaDAO.update(richAutGiud);
		return richAutGiudSaved;
	}
	
	@Override
	public List<AutoritaGiudiziariaView> searchRichAutGiud() throws Throwable {
		List<RichAutGiud> list = richiestaAutGiudiziariaDAO.search(true);
		List<AutoritaGiudiziariaView> convertedList = (List<AutoritaGiudiziariaView>) convertiVoInView(list);		
		return convertedList;
	}
	
	@Override
	public List<AutoritaGiudiziariaView> searchRichAutGiudByFilter(AutoritaGiudiziariaView autoritaGiudiziariaView) throws Throwable {
		List<RichAutGiud> list = richiestaAutGiudiziariaDAO.searchRichAutGiudByFilter(autoritaGiudiziariaView);
		List<AutoritaGiudiziariaView> convertedList = (List<AutoritaGiudiziariaView>) convertiVoInView(list);		
		return convertedList;
	}
	
	@Override
	public AutoritaGiudiziariaView readRichAutGiud(long id) throws Throwable {
		RichAutGiud richAutGiud = richiestaAutGiudiziariaDAO.read(id);
		return (AutoritaGiudiziariaView) convertiVoInView(richAutGiud);		
	}
	
	@Override
	public void delete(Long id) throws Throwable {
		richiestaAutGiudiziariaDAO.delete(id);
	}
	
	@Override
	protected Class<RichAutGiud> leggiClassVO() { 
		return RichAutGiud.class;
	}

	@Override
	protected Class<AutoritaGiudiziariaView>leggiClassView() { 
		return AutoritaGiudiziariaView.class;
	}


	public RichiestaAutGiudiziariaDAO getRichiestaAutGiudiziariaDAO() {
		return richiestaAutGiudiziariaDAO;
	}
	public void setRichiestaAutGiudiziariaDAO(RichiestaAutGiudiziariaDAO richiestaAutGiudiziariaDAO) {
		this.richiestaAutGiudiziariaDAO = richiestaAutGiudiziariaDAO;
	}


	@Override
	public RichAutGiud addStep1Document(RichAutGiud richAutGiud, long documentoStep1Id) throws Throwable {
		Documento documentoStep1 = new Documento();
		documentoStep1.setId(documentoStep1Id);
		RichAutGiud richAutGiudRead = richiestaAutGiudiziariaDAO.read(richAutGiud.getId());
		richAutGiudRead.setDocumentoStep1(documentoStep1);
		RichAutGiud richAutGiudSaved = richiestaAutGiudiziariaDAO.update(richAutGiudRead);
		return richAutGiudSaved;
	}
	
	@Override
	public RichAutGiud addStep2Document(RichAutGiud richAutGiud, long documentoStep2Id) throws Throwable {
		Documento documentoStep2 = new Documento();
		documentoStep2.setId(documentoStep2Id);
		RichAutGiud richAutGiudRead = richiestaAutGiudiziariaDAO.read(richAutGiud.getId());
		richAutGiudRead.setDocumentoStep2(documentoStep2);
		RichAutGiud richAutGiudSaved = richiestaAutGiudiziariaDAO.update(richAutGiudRead);
		return richAutGiudSaved;
	}
	
	@Override
	public RichAutGiud addStep3Document(RichAutGiud richAutGiud, long documentoStep3Id) throws Throwable {
		Documento documentoStep3 = new Documento();
		documentoStep3.setId(documentoStep3Id);
		RichAutGiud richAutGiudRead = richiestaAutGiudiziariaDAO.read(richAutGiud.getId());
		richAutGiudRead.setDocumentoStep3(documentoStep3);
		RichAutGiud richAutGiudSaved = richiestaAutGiudiziariaDAO.update(richAutGiudRead);
		return richAutGiudSaved;
	}
	

}




