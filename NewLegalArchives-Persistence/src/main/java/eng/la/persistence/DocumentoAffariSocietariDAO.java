package eng.la.persistence;

import eng.la.model.DocumentoAffariSocietari;

public interface DocumentoAffariSocietariDAO {

	public DocumentoAffariSocietari save(DocumentoAffariSocietari vo) throws Throwable;

	public DocumentoAffariSocietari read(long id) throws Throwable;

	public void deleteDocumentoAffariSocietari(DocumentoAffariSocietari documento) throws Throwable;

	public DocumentoAffariSocietari leggiPerIdAffariSocietari(long idAffariSocietari);

}
