package eng.la.business;
 
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.model.Nazione;
import eng.la.model.view.LinguaView;
import eng.la.model.view.NazioneView;
import eng.la.persistence.NazioneDAO;

@Service("nazioneService")
public class NazioneServiceImpl extends BaseService<Nazione,NazioneView> implements NazioneService {

	@Autowired
	private NazioneDAO nazioneDao;
	

	public NazioneDAO getNazioneDao() {
		return nazioneDao;
	}

	public void setNazioneDao(NazioneDAO nazioneDao) {
		this.nazioneDao = nazioneDao;
	}

	
	/**
	 * Ritorna la lista delle NazioniView. 
	 * @return      List<NazioneView>
	 */
	@Override
	public List<NazioneView> leggi() throws Throwable {
		 
		List<Nazione> lista = nazioneDao.leggi(); 
		List<NazioneView> listaRitorno = convertiVoInView(lista);
		
		return listaRitorno;
		
	}

	/**
	 * Ritorna un oggetto NazioniView.
	 * @param id
	 * @return NazioneView con la classe entity Nazione incapsulata
	 */
	@Override
	public NazioneView leggi(long id) throws Throwable {
		Nazione nazione = nazioneDao.leggi(id);
		return (NazioneView) convertiVoInView(nazione);
	}

	@Override
	public List<NazioneView> leggi(Locale locale, boolean tutte) throws Throwable {
		List<Nazione> lista = nazioneDao.leggi(locale.getLanguage().toUpperCase(), tutte);
		List<NazioneView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public NazioneView leggi(String codice, Locale locale, boolean tutte) throws Throwable { 
		Nazione nazione = nazioneDao.leggi(codice, locale.getLanguage().toUpperCase(), tutte);
		NazioneView nazioneView = new NazioneView();
		nazioneView.setVo(nazione);
		return nazioneView;
	}
	
	@Override
	public List<NazioneView> leggi(Locale locale) throws Throwable {
		List<Nazione> lista = nazioneDao.leggi(locale.getLanguage().toUpperCase());
		List<NazioneView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public List<NazioneView> leggiPartiCorrelate(Locale locale, boolean partiCorrelate) throws Throwable {
		List<Nazione> lista = nazioneDao.leggiPartiCorrelate(locale.getLanguage().toUpperCase(), partiCorrelate);
		List<NazioneView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Override
	public List<NazioneView> leggibyCodice(String codice) throws Throwable {
		List<Nazione> lista = nazioneDao.leggibyCodice(codice);
		List<NazioneView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean controlla(NazioneView nazioneView) throws Throwable	{
		String descIt = nazioneView.getNazioneIns().get(0);
		return descIt==null?false:nazioneDao.controlla(descIt);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void inserisci(NazioneView nazioneView) throws Throwable	{
		List<String> nazioneIns = nazioneView.getNazioneIns();
		List<LinguaView> listaLingua = nazioneView.getListaLingua();
		int max = 0;
		List<String> codGruppoLinguaList = nazioneDao.getCodGruppoLinguaList();
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
			}
		}
		String codice = "NAZI_"+String.valueOf(max + 1);
		for (int i = 0; i < nazioneIns.size(); i++) {
			LinguaView linguaView = listaLingua.get(i);
			Nazione vo = new Nazione();
			vo.setCodGruppoLingua(codice);
			vo.setDescrizione(nazioneIns.get(i));
			vo.setSoloParteCorrelata(nazioneView.isSoloParteCorrelataIns()?"T":"F");
			vo.setLang(linguaView.getVo().getLang());
			nazioneDao.inserisci(vo);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void modifica(NazioneView nazioneView) throws Throwable {
		List<NazioneView> leggibyCodice = leggibyCodice(nazioneView.getNazioneCode());
		List<String> nazioneDesc = nazioneView.getNazioneDesc();
		List<LinguaView> listaLingua = nazioneView.getListaLingua();
		
		String codice = "";
		if(leggibyCodice.size()!=nazioneDesc.size()){
			int indice = 0;
			//significa che la modifica comporta anche l'inserimento di una nuova descrizione per una nuova lingua
			for (int i = 0; i < leggibyCodice.size(); i++) {
				indice = i+1;
				NazioneView nazioneView2 = leggibyCodice.get(i);
				Nazione vo = nazioneView2.getVo();
				codice = vo.getCodGruppoLingua();
				vo.setDescrizione(nazioneDesc.get(i));
				vo.setSoloParteCorrelata(nazioneView.isSoloParteCorrelata()?"T":"F");
				nazioneDao.modifica(vo);
			}
			for (int i = indice; i < nazioneDesc.size(); i++) {
				LinguaView linguaView = listaLingua.get(i);
				Nazione vo = new Nazione();
				vo.setCodGruppoLingua(codice);
				vo.setDescrizione(nazioneDesc.get(i));
				vo.setSoloParteCorrelata(nazioneView.isSoloParteCorrelata()?"T":"F");
				vo.setLang(linguaView.getVo().getLang());
				nazioneDao.inserisci(vo);
			}
		} else {
			for (int i = 0; i < nazioneDesc.size(); i++) {
				NazioneView nazioneView2 = leggibyCodice.get(i);
				Nazione vo = nazioneView2.getVo();
				vo.setDescrizione(nazioneDesc.get(i));
				vo.setSoloParteCorrelata(nazioneView.isSoloParteCorrelata()?"T":"F");
				nazioneDao.modifica(vo);
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(long id) throws Throwable {
		nazioneDao.cancella(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void cancella(NazioneView nazioneView) throws Throwable {
		List<NazioneView> leggibyCodice = leggibyCodice(nazioneView.getNazioneCode());
		for (NazioneView nazioneView2 : leggibyCodice) {
			nazioneDao.cancella(nazioneView2.getVo().getId());
		}
	}
	
	@Override
	protected Class<Nazione> leggiClassVO() { 
		return Nazione.class;
	}

	@Override
	protected Class<NazioneView> leggiClassView() { 
		return NazioneView.class;
	}


}
