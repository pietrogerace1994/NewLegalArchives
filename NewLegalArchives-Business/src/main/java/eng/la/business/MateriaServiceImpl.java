package eng.la.business;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Materia;
import eng.la.model.RSettoreGiuridicoMateria;
import eng.la.model.SettoreGiuridico;
import eng.la.model.view.LinguaView;
import eng.la.model.view.MateriaView;
import eng.la.persistence.MateriaDAO;
import eng.la.persistence.SettoreGiuridicoDAO;
import eng.la.util.ListaPaginata;

@Service("materiaService")
public class MateriaServiceImpl extends BaseService<Materia,MateriaView> implements MateriaService {
	
	@Autowired
	private MateriaDAO materiaDAO;
	
	@Autowired
	private SettoreGiuridicoDAO settoreGiuridicoDAO;
	

	public void setDao(MateriaDAO dao) {
		this.materiaDAO = dao;
	}

	public MateriaDAO getDao() {
		return materiaDAO;
	}
	
	@Override
	public List<MateriaView> leggi() throws Throwable {
		List<Materia> lista = materiaDAO.leggi();
		List<MateriaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<MateriaView> leggi(Locale locale) throws Throwable {
		 
		List<Materia> lista = materiaDAO.leggi(locale.getLanguage().toUpperCase()); 
		List<MateriaView> listaRitorno = convertiVoInView(lista);
		
		return listaRitorno;
	}
	
	@Override
	public MateriaView leggi(long id) throws Throwable {
		Materia materia = materiaDAO.leggi(id);
		return (MateriaView) convertiVoInView(materia);
	} 

	@Override
	public MateriaView leggi(String codGruppoLingua, String codiceLingua) throws Throwable {
		Materia materia = materiaDAO.leggi(codGruppoLingua,codiceLingua,true);
		return (MateriaView) convertiVoInView(materia);
	}
	
	@Override
	public List<MateriaView> leggi(String codGruppoLingua) throws Throwable {
		List<Materia> lista = materiaDAO.leggiPerCodice(codGruppoLingua);
		List<MateriaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public List<MateriaView> cerca(String nome ) throws Throwable {
		List<Materia> lista = materiaDAO.cerca(nome) ;
		List<MateriaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public ListaPaginata<MateriaView> cerca(String nome, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<Materia> lista = materiaDAO.cerca(nome, elementiPerPagina, numeroPagina, ordinamento, ordinamentoDirezione) ;
		List<MateriaView> listaView = convertiVoInView(lista);
		ListaPaginata<MateriaView> listaRitorno = new ListaPaginata<MateriaView>();
		Long conta = materiaDAO.conta(nome); 
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta); 
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno; 
	}

	@Override
	public Long conta(String nome) throws Throwable { 
		return materiaDAO.conta(nome);
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public MateriaView inserisci(MateriaView materiaView) throws Throwable {
		
		int max = 0;
		List<String> codGruppoLinguaList = materiaDAO.getCodGruppoLinguaList();
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
			}
		}
		String codice = "MATR_"+String.valueOf(max + 1);
		
		List<LinguaView> listaLingua = materiaView.getListaLingua();
		for(int i=0; i< listaLingua.size(); i++) {
			LinguaView lingua = materiaView.getListaLingua().get(i);
			String lang = lingua.getVo().getLang();
					
			Materia vo = new Materia();
			String nome = materiaView.getNomeIns().get((int)lingua.getVo().getId());
			vo.setNome(nome);
			vo.setLang(lang);
			vo.setCodGruppoLingua(codice);
			
			String codicePadre = materiaView.getMateriaPadreCodGruppoLinguaIns();
			Materia materiaPadre = materiaDAO.leggi(codicePadre, lang, false);
			vo.setMateriaPadre(materiaPadre);
			
			String settoreGiuridicoCodice = materiaView.getSettoreGiuridicoCodGruppoLingua();
			SettoreGiuridico settoreGiuridicoVo = settoreGiuridicoDAO.leggi(settoreGiuridicoCodice, lang, false);
			RSettoreGiuridicoMateria r = new RSettoreGiuridicoMateria();
			r.setMateria(vo);
			r.setSettoreGiuridico(settoreGiuridicoVo);
			vo.setRSettoreGiuridicoMaterias(new HashSet<RSettoreGiuridicoMateria>());
			vo.addRSettoreGiuridicoMateria(r);
			
			materiaDAO.inserisci(vo);
			materiaDAO.inserisciRSettoreGiuridicoMateria(r);
			
		}

		return materiaView;
	}
	

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void modifica(MateriaView materiaView) throws Throwable {
		
		List<LinguaView> listaLingua = materiaView.getListaLingua();
		for(int i=0; i< listaLingua.size(); i++) {
			LinguaView lingua = materiaView.getListaLingua().get(i);
			String lang = lingua.getVo().getLang();
					
			Materia vo = new Materia();
			Long id = materiaView.getMateriaNomeId().get((int)lingua.getVo().getId());
			String nome = materiaView.getNome().get((int)lingua.getVo().getId());
			vo.setId(id.longValue());
			vo.setNome(nome);
			vo.setLang(lang);
			vo.setCodGruppoLingua(materiaView.getMateriaCodGruppoLingua());
			
			long idPadre = materiaView.getMateriaIdPadre().get((int)lingua.getVo().getId());
			Materia materiaPadre = materiaDAO.leggi(idPadre);
			vo.setMateriaPadre(materiaPadre);
			
			materiaDAO.modifica(vo);
		}
		
	}

	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	@Override
	public void cancella(MateriaView materiaView) throws Throwable {
		
		List<LinguaView> listaLingua = materiaView.getListaLingua();
		for(int i=0; i< listaLingua.size(); i++) {
			LinguaView lingua = materiaView.getListaLingua().get(i);
			Long id = materiaView.getMateriaNomeId().get((int)lingua.getVo().getId());
			materiaDAO.cancella(id);
		}
		
	}
	
	@Override
	public List<MateriaView> leggiDaSettoreId(long id) throws Throwable {
		List<Materia> lista = materiaDAO.leggiDaSettoreId(id) ;
		List<MateriaView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;	
	}
 
	@Override
	public MateriaView leggi(String codice, Locale locale, boolean tutte) throws Throwable {
		Materia materia = materiaDAO.leggi(codice, locale.getLanguage().toUpperCase(), tutte);
		return (MateriaView) convertiVoInView(materia);
	}

	@Override
	public JSONArray leggiAlberaturaMateria(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieRoot = materiaDAO.leggiMaterieRootDaSettoreId(idSettoreGiuridico, locale.getLanguage().toUpperCase());
		JSONArray jsonArray = new JSONArray();
		if( materieRoot != null && materieRoot.size() > 0 ){
			for( Materia materia : materieRoot ){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("text", "<input type='checkbox' id='"+materia.getCodGruppoLingua()+"' value='"+materia.getCodGruppoLingua() +"' name='materie'/> " +StringEscapeUtils.escapeJavaScript( materia.getNome() ));
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );
				jsonObject.put("isExpanded", alberaturaAperta);
				aggiungiFigli(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject); 
			}
		} 
		
		return jsonArray;				
	}
	
	@Override
	public JSONArray leggiAlberaturaMateriaTutte(Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieRoot = materiaDAO.leggiMaterieRootTutte(locale.getLanguage().toUpperCase());
		JSONArray jsonArray = new JSONArray();
		if( materieRoot != null && materieRoot.size() > 0 ){
			for( Materia materia : materieRoot ){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("text", "<input type='checkbox' id='"+materia.getCodGruppoLingua()+"' value='"+materia.getCodGruppoLingua() +"' name='materie'/> " +StringEscapeUtils.escapeJavaScript( materia.getNome() ));
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );
				jsonObject.put("isExpanded", alberaturaAperta);
				aggiungiFigli(jsonObject, materia, locale, alberaturaAperta);
				jsonArray.put(jsonObject); 
			}
		} 
		
		return jsonArray;				
	}
	
	
	@Override
	public JSONArray leggiAlberoMateriaOpEdit(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieRoot = materiaDAO.leggiMaterieRootDaSettoreId(idSettoreGiuridico, locale.getLanguage().toUpperCase());
		JSONArray jsonArray = new JSONArray();
		if( materieRoot != null && materieRoot.size() > 0 ){
			for( Materia materia : materieRoot ){
				JSONObject jsonObject = new JSONObject();
				
				String codGruppoLingua = materia.getCodGruppoLingua();
				String nome = StringEscapeUtils.escapeJavaScript(materia.getNome());
				String htmlStr = "<input type='radio' name='alberoMateria'  onclick=\"javascript:scegliMateria('"+codGruppoLingua+"','"+nome+"')\"    /> "+nome;
				jsonObject.put(
						"text", 
						htmlStr
				);
				
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );
				aggiungiFigliOpEdit(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject); 
			}
		} 
		
		return jsonArray;				
	}
	
	@Override
	public JSONArray leggiAlberoMateriaOpVis(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieRoot = materiaDAO.leggiMaterieRootDaSettoreId(idSettoreGiuridico, locale.getLanguage().toUpperCase());
		JSONArray jsonArray = new JSONArray();
		if( materieRoot != null && materieRoot.size() > 0 ){
			for( Materia materia : materieRoot ){
				JSONObject jsonObject = new JSONObject();
				
				String codGruppoLingua = materia.getCodGruppoLingua();
				String nome = StringEscapeUtils.escapeJavaScript(materia.getNome());
				String htmlStr = "<input type='radio' name='alberoMateria'  onclick=\"javascript:scegliMateriaVis('"+codGruppoLingua+"','"+nome+"')\"    /> "+nome;
				jsonObject.put(
						"text", 
						htmlStr
				);
				
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );
				aggiungiFigliOpVis(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject); 
			}
		} 
		
		return jsonArray;				
	}
	
	private void aggiungiFigliOpVis(JSONObject object, Materia materiaPadre, long idSettoreGiuridico , Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieFiglie = materiaDAO.leggiMaterieFiglie(materiaPadre.getId(), idSettoreGiuridico, locale.getLanguage().toUpperCase());		
		if( materieFiglie != null && materieFiglie.size() > 0 ){
			JSONArray jsonArray = new JSONArray();
			for( Materia materia : materieFiglie ){
				JSONObject jsonObject = new JSONObject();
				
				String codGruppoLingua = materia.getCodGruppoLingua();
				String nome = StringEscapeUtils.escapeJavaScript(materia.getNome());
				String htmlStr = "<input type='radio' name='alberoMateria'  onclick=\"javascript:scegliMateriaVis('"+codGruppoLingua+"','"+nome+"')\"    /> "+nome;
				jsonObject.put(
						"text", 
						htmlStr
				);
				
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );		
				aggiungiFigliOpVis(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject);
			}
			object.put("children", jsonArray);
		} 
		
	}
	
	private void aggiungiFigliOpEdit(JSONObject object, Materia materiaPadre, long idSettoreGiuridico , Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieFiglie = materiaDAO.leggiMaterieFiglie(materiaPadre.getId(), idSettoreGiuridico, locale.getLanguage().toUpperCase());		
		if( materieFiglie != null && materieFiglie.size() > 0 ){
			JSONArray jsonArray = new JSONArray();
			for( Materia materia : materieFiglie ){
				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("text", "<input type='checkbox' id='"+materia.getCodGruppoLingua()+"' value='"+materia.getCodGruppoLingua() +"' name='materie'/> " + materia.getNome() );
				
				String codGruppoLingua = materia.getCodGruppoLingua();
				String nome = StringEscapeUtils.escapeJavaScript(materia.getNome());
				String htmlStr = "<input type='radio' name='alberoMateria'  onclick=\"javascript:scegliMateria('"+codGruppoLingua+"','"+nome+"')\"    /> "+nome;
				jsonObject.put(
						"text", 
						htmlStr
				);
				
				
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );		
				jsonObject.put("isExpanded", alberaturaAperta);		
				aggiungiFigliOpEdit(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject);
			}
			object.put("children", jsonArray);
		} 
		
	}
	
	@Override
	public JSONArray leggiAlberoMateriaOp(long idSettoreGiuridico, Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieRoot = materiaDAO.leggiMaterieRootDaSettoreId(idSettoreGiuridico, locale.getLanguage().toUpperCase());
		JSONArray jsonArray = new JSONArray();
		if( materieRoot != null && materieRoot.size() > 0 ){
			for( Materia materia : materieRoot ){
				JSONObject jsonObject = new JSONObject();
				
		      //jsonObject.put("text", "<input type='checkbox' id='"+materia.getCodGruppoLingua()+"' value='"+materia.getCodGruppoLingua() +"' name='materie'/> " +StringEscapeUtils.escapeJavaScript( materia.getNome() ));
				
				String codGruppoLingua = materia.getCodGruppoLingua();
				String nome = StringEscapeUtils.escapeJavaScript(materia.getNome());
				String htmlStr = "<input type='radio' name='alberoMateriaPadre'  onclick=\"javascript:scegliMateriaPadre('"+codGruppoLingua+"','"+nome+"')\"    /> "+nome;
				jsonObject.put(
						"text", 
						htmlStr
				);
				
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );
				aggiungiFigliOp(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject); 
			}
		} 
		
		return jsonArray;				
	}
	
	private void aggiungiFigliOp(JSONObject object, Materia materiaPadre, long idSettoreGiuridico , Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieFiglie = materiaDAO.leggiMaterieFiglie(materiaPadre.getId(), idSettoreGiuridico, locale.getLanguage().toUpperCase());		
		if( materieFiglie != null && materieFiglie.size() > 0 ){
			JSONArray jsonArray = new JSONArray();
			for( Materia materia : materieFiglie ){
				JSONObject jsonObject = new JSONObject();
				
				String codGruppoLingua = materia.getCodGruppoLingua();
				String nome = StringEscapeUtils.escapeJavaScript(materia.getNome());
				String htmlStr = "<input type='radio' name='alberoMateriaPadre'  onclick=\"javascript:scegliMateriaPadre('"+codGruppoLingua+"','"+nome+"')\"    /> "+nome;
				jsonObject.put(
						"text", 
						htmlStr
				);
				
				
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );		
				aggiungiFigliOp(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject);
			}
			object.put("children", jsonArray);
		} 
		
	}
	
	private void aggiungiFigli(JSONObject object, Materia materiaPadre, long idSettoreGiuridico , Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieFiglie = materiaDAO.leggiMaterieFiglie(materiaPadre.getId(), idSettoreGiuridico, locale.getLanguage().toUpperCase());		
		if( materieFiglie != null && materieFiglie.size() > 0 ){
			JSONArray jsonArray = new JSONArray();
			for( Materia materia : materieFiglie ){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("text", "<input type='checkbox' id='"+materia.getCodGruppoLingua()+"' value='"+materia.getCodGruppoLingua() +"' name='materie'/> " + materia.getNome() );
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );		
				jsonObject.put("isExpanded", alberaturaAperta);		
				aggiungiFigli(jsonObject, materia, idSettoreGiuridico, locale, alberaturaAperta);
				jsonArray.put(jsonObject);
			}
			object.put("children", jsonArray);
		} 
	}
	
	private void aggiungiFigli(JSONObject object, Materia materiaPadre, Locale locale, boolean alberaturaAperta) throws Throwable {
		List<Materia> materieFiglie = materiaDAO.leggiMaterieFiglie(materiaPadre.getId(), locale.getLanguage().toUpperCase());		
		if( materieFiglie != null && materieFiglie.size() > 0 ){
			JSONArray jsonArray = new JSONArray();
			for( Materia materia : materieFiglie ){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("text", "<input type='checkbox' id='"+materia.getCodGruppoLingua()+"' value='"+materia.getCodGruppoLingua() +"' name='materie'/> " + materia.getNome() );
				jsonObject.put("codice", materia.getCodGruppoLingua() );
				jsonObject.put("id", materia.getId() );		
				jsonObject.put("isExpanded", alberaturaAperta);		
				aggiungiFigli(jsonObject, materia, locale, alberaturaAperta);
				jsonArray.put(jsonObject);
			}
			object.put("children", jsonArray);
		} 
	}
	
	@Override
	public List<MateriaView> leggiDaBeautyContest(long id) throws Throwable {
		List<Materia> lista = materiaDAO.leggiDaBCId(id) ;
		List<MateriaView> listaRitorno = (List<MateriaView>)convertiVoInView(lista);
		return listaRitorno;
	}
	@Override
	protected Class<Materia> leggiClassVO() { 
		return Materia.class;
	}
	
	@Override
	protected Class<MateriaView> leggiClassView() {
		return MateriaView.class;
	}
}
