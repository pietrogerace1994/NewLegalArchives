package eng.la.business;

import java.util.List;

import eng.la.model.RichAutGiud;
import eng.la.model.view.AutoritaGiudiziariaView;

public interface RichiestaAutoritaGiudiziariaService {

	public RichAutGiud save(RichAutGiud richAutGiud) throws Throwable;

	public List<AutoritaGiudiziariaView> searchRichAutGiud() throws Throwable;

	public AutoritaGiudiziariaView readRichAutGiud(long id) throws Throwable;

	public RichAutGiud update(RichAutGiud richAutGiud) throws Throwable;

	public void delete(Long id) throws Throwable;

	public RichAutGiud addStep1Document(RichAutGiud richAutGiud, long documentoStep1Id) throws Throwable;

	public RichAutGiud addStep2Document(RichAutGiud richAutGiud, long documentoStep2Id) throws Throwable;

	public RichAutGiud addStep3Document(RichAutGiud richAutGiud, long documentoStep3Id) throws Throwable;

	public List<AutoritaGiudiziariaView> searchRichAutGiudByFilter(AutoritaGiudiziariaView autoritaGiudiziariaView)
			throws Throwable;
}
