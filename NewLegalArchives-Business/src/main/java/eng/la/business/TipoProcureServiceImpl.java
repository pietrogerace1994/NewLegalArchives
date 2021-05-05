package eng.la.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.TipoProcure;
import eng.la.model.view.LinguaView;
import eng.la.model.view.TipoProcureView;
import eng.la.persistence.TipoProcureDAO;

@Service("tipoProcureService")
public class TipoProcureServiceImpl extends BaseService<TipoProcure,TipoProcureView> implements TipoProcureService {

	@Autowired
	private TipoProcureDAO tipoProcureDao;

	public TipoProcureDAO getTipoProcureDao() {
		return tipoProcureDao;
	}

	public void setTipoProcureDao(TipoProcureDAO tipoProcureDao) {
		this.tipoProcureDao = tipoProcureDao;
	}
	
	/**
	 * Ritorna la lista delle TipoProcureView. 
	 * @return      List<TipoProcureView>
	 */
	@Override
	public List<TipoProcureView> leggi(Locale locale) throws Throwable {
		 
		List<TipoProcure> lista = tipoProcureDao.leggi(locale.getLanguage().toUpperCase()); 
		List<TipoProcureView> listaRitorno = convertiVoInView(lista);
		
		return listaRitorno;
	}
	
	/**
	 * Ritorna la lista delle TipoProcureView. 
	 * @return      List<TipoProcureView>
	 */
	@Override
	public TipoProcureView leggi(Long id) throws Throwable {
		 
		TipoProcure tipoProcure = tipoProcureDao.leggi(id); 
		TipoProcureView tipoProcureView = (TipoProcureView) convertiVoInView(tipoProcure);
		
		return tipoProcureView;
	}

	@Override
	protected Class<TipoProcure> leggiClassVO() {
		return TipoProcure.class;
	}

	@Override
	protected Class<TipoProcureView> leggiClassView() {
		return TipoProcureView.class;
	}

	@Override
	public List<TipoProcureView> leggibyCodice(String tipoProcureCode) throws Throwable {
		List<TipoProcure> lista = tipoProcureDao.leggibyCodice(tipoProcureCode);
		List<TipoProcureView> listaRitorno = convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public boolean controlla(TipoProcureView tipoProcureView) throws Throwable {
		String descIt = tipoProcureView.getTipoProcureIns().get(0);
		return descIt==null?false:tipoProcureDao.controlla(descIt);
	}

	@Override
	public void inserisci(TipoProcureView tipoProcureView) throws Throwable {
		List<String> tipoProcureIns = tipoProcureView.getTipoProcureIns();
		List<LinguaView> listaLingua = tipoProcureView.getListaLingua();
		int max = 0;
		List<String> codGruppoLinguaList = tipoProcureDao.getCodGruppoLinguaList();
		for (int i = 0; i < codGruppoLinguaList.size(); i++) {
			String cod = codGruppoLinguaList.get(i);
			String[] parts = cod.split("_");
			int intValue = Integer.valueOf(parts[1]).intValue();
			if(intValue > max){
				max = intValue;
			}
		}
		String codice = "TPPR_"+String.valueOf(max + 1);
		for (int i = 0; i < tipoProcureIns.size(); i++) {
			LinguaView linguaView = listaLingua.get(i);
			TipoProcure vo = new TipoProcure();
			vo.setCodGruppoLingua(codice);
			vo.setDescrizione(tipoProcureIns.get(i));
			vo.setLang(linguaView.getVo().getLang());
			tipoProcureDao.inserisci(vo);
		}
	}

	@Override
	public void cancella(TipoProcureView tipoProcureView) throws Throwable {
		List<TipoProcureView> leggibyCodice = leggibyCodice(tipoProcureView.getTipoProcureCode());
		for (TipoProcureView tipoProcureView2 : leggibyCodice) {
			tipoProcureDao.cancella(tipoProcureView2.getVo().getId());
		}
	}

	@Override
	public void modifica(TipoProcureView tipoProcureView) throws Throwable {
		List<TipoProcureView> leggibyCodice = leggibyCodice(tipoProcureView.getTipoProcureCode());
		List<String> tipoProcureDesc = tipoProcureView.getTipoProcureDesc();
		List<LinguaView> listaLingua = tipoProcureView.getListaLingua();
		
		String codice = "";
		if(leggibyCodice.size()!=tipoProcureDesc.size()){
			int indice = 0;
			//significa che la modifica comporta anche l'inserimento di una nuova descrizione per una nuova lingua
			for (int i = 0; i < leggibyCodice.size(); i++) {
				indice = i+1;
				TipoProcureView tipoProcureView2 = leggibyCodice.get(i);
				TipoProcure vo = tipoProcureView2.getVo();
				codice = vo.getCodGruppoLingua();
				vo.setDescrizione(tipoProcureDesc.get(i));
				tipoProcureDao.modifica(vo);
			}
			for (int i = indice; i < tipoProcureDesc.size(); i++) {
				LinguaView linguaView = listaLingua.get(i);
				TipoProcure vo = new TipoProcure();
				vo.setCodGruppoLingua(codice);
				vo.setDescrizione(tipoProcureDesc.get(i));
				vo.setLang(linguaView.getVo().getLang());
				tipoProcureDao.inserisci(vo);
			}
		} else {
			for (int i = 0; i < tipoProcureDesc.size(); i++) {
				TipoProcureView tipoProcureView2 = leggibyCodice.get(i);
				TipoProcure vo = tipoProcureView2.getVo();
				vo.setDescrizione(tipoProcureDesc.get(i));
				tipoProcureDao.modifica(vo);
			}
		}
	}
	

	@Override
	public TipoProcureView leggiNotDataCancellazione(long id) throws Throwable {
		TipoProcure tipoProcure = tipoProcureDao.leggiNotDataCancellazione(id); 
		TipoProcureView tipoProcureView = (TipoProcureView) convertiVoInView(tipoProcure);
		
		return tipoProcureView;
	}

	@Override
	public List<TipoProcureView> leggiListNotDataCancellazione(Locale locale) throws Throwable {
		List<TipoProcure> lista = tipoProcureDao.leggiListNotDataCancellazione(locale.getLanguage().toUpperCase()); 
		List<TipoProcureView> listaRitorno = convertiVoInView(lista);
		
		return listaRitorno;
	}
	
}
