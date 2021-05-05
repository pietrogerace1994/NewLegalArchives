package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Specializzazione;
import eng.la.model.view.LinguaView;
import eng.la.model.view.SpecializzazioneView;
import eng.la.persistence.SpecializzazioneDAO;

@Service("specializzazioneService")
public class SpecializzazioneServiceImpl extends BaseService<Specializzazione,SpecializzazioneView> implements SpecializzazioneService {

	@Autowired
	private SpecializzazioneDAO specializzazioneDao;
	

	public SpecializzazioneDAO getSpecializzazioneDao() {
		return specializzazioneDao;
	}

	public void setSpecializzazioneDao(SpecializzazioneDAO specializzazioneDao) {
		this.specializzazioneDao = specializzazioneDao;
	}

	
	/**
	 * Ritorna la lista delle SpecializzazioneView. 
	 * @return      List<SpecializzazioneView>
	 */
	@Override
	public List<SpecializzazioneView> leggi() throws Throwable {
		 
		List<Specializzazione> lista = specializzazioneDao.leggi();
		List<SpecializzazioneView> listaRitorno = convertiVoInView(lista);
		
		return listaRitorno;
		
	}

	/**
	 * Ritorna un oggetto SpecializzazioneView.
	 * @param id
	 * @return SpecializzazioneView con la classe entity Specializzazione incapsulata
	 */
	@Override
	public SpecializzazioneView leggi(long id) throws Throwable {
		Specializzazione specializzazione = specializzazioneDao.leggi(id);
		return (SpecializzazioneView) convertiVoInView(specializzazione);
	}

	@Override
	public List<SpecializzazioneView> leggi(Locale locale) throws Throwable {
		List<Specializzazione> lista = specializzazioneDao.leggi(locale.getLanguage().toUpperCase());
		List<SpecializzazioneView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public SpecializzazioneView leggi(String codice, Locale locale) throws Throwable { 
		Specializzazione specializzazione = specializzazioneDao.leggi(codice, locale.getLanguage().toUpperCase());
		SpecializzazioneView specializzazioneView = new SpecializzazioneView();
		specializzazioneView.setVo(specializzazione);
		return specializzazioneView;
	}
	
	@Override
	public List<SpecializzazioneView> leggibyCodice(String codice) throws Throwable {
		List<Specializzazione> lista = specializzazioneDao.leggibyCodice(codice);
		List<SpecializzazioneView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean controlla(SpecializzazioneView specializzazioneView) throws Throwable	{
		String descIt = specializzazioneView.getSpecializzazioneIns().get(0);
		return descIt==null?false:specializzazioneDao.controlla(descIt);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void inserisci(SpecializzazioneView specializzazioneView) throws Throwable	{
		List<String> specializzazioneIns = specializzazioneView.getSpecializzazioneIns();
		List<LinguaView> listaLingua = specializzazioneView.getListaLingua();
		int max = 0;
		List<String> codGruppoLinguaList = specializzazioneDao.getCodGruppoLinguaList();
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
			}
		}
		String codice = "TSPC_"+String.valueOf(max + 1);
		for (int i = 0; i < specializzazioneIns.size(); i++) {
			LinguaView linguaView = listaLingua.get(i);
			Specializzazione vo = new Specializzazione();
			vo.setCodGruppoLingua(codice);
			vo.setDescrizione(specializzazioneIns.get(i));
			vo.setLang(linguaView.getVo().getLang());
			specializzazioneDao.inserisci(vo);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(SpecializzazioneView specializzazioneView) throws Throwable {
		List<SpecializzazioneView> leggibyCodice = leggibyCodice(specializzazioneView.getSpecializzazioneCode());
		List<String> specializzazioneDesc = specializzazioneView.getSpecializzazioneDesc();
		List<LinguaView> listaLingua = specializzazioneView.getListaLingua();
		
		String codice = "";
		if(leggibyCodice.size()!=specializzazioneDesc.size()){
			int indice = 0;
			//significa che la modifica comporta anche l'inserimento di una nuova descrizione per una nuova lingua
			for (int i = 0; i < leggibyCodice.size(); i++) {
				indice = i+1;
				SpecializzazioneView specializzazioneView2 = leggibyCodice.get(i);
				Specializzazione vo = specializzazioneView2.getVo();
				codice = vo.getCodGruppoLingua();
				vo.setDescrizione(specializzazioneDesc.get(i));
				specializzazioneDao.modifica(vo);
			}
			for (int i = indice; i < specializzazioneDesc.size(); i++) {
				LinguaView linguaView = listaLingua.get(i);
				Specializzazione vo = new Specializzazione();
				vo.setCodGruppoLingua(codice);
				vo.setDescrizione(specializzazioneDesc.get(i));
				vo.setLang(linguaView.getVo().getLang());
				specializzazioneDao.inserisci(vo);
			}
		} else {
			for (int i = 0; i < specializzazioneDesc.size(); i++) {
				SpecializzazioneView specializzazioneView2 = leggibyCodice.get(i);
				Specializzazione vo = specializzazioneView2.getVo();
				vo.setDescrizione(specializzazioneDesc.get(i));
				specializzazioneDao.modifica(vo);
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		specializzazioneDao.cancella(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(SpecializzazioneView specializzazioneView) throws Throwable {
		List<SpecializzazioneView> leggibyCodice = leggibyCodice(specializzazioneView.getSpecializzazioneCode());
		for (SpecializzazioneView specializzazioneView2 : leggibyCodice) {
			specializzazioneDao.cancella(specializzazioneView2.getVo().getId());
		}
	}
	
	@Override
	protected Class<Specializzazione> leggiClassVO() { 
		return Specializzazione.class;
	}

	@Override
	protected Class<SpecializzazioneView> leggiClassView() { 
		return SpecializzazioneView.class;
	}

}
