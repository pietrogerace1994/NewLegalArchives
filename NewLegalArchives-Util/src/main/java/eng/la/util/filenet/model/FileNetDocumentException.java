package eng.la.util.filenet.model;

/*import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.exception.ExceptionCode;*/

/**
 *Gestione eccezioni creazione documenti e file sul server filenet
 * 
 * @author 
 */
public class FileNetDocumentException extends Exception {

	
	public static String CONST_E_OBJECT_NOT_FOUND = "Nessun documento trovato";
	public static String CONST_API_NOT_A_CONTENT_TRANSFER = "Impossibile leggere il contenuto del file";

	/*public FileNetDocumentException(EngineRuntimeException e)
	{
		super(e.getExceptionCode());
	}

	public FileNetDocumentException(ExceptionCode code)
	{
		super(code);
	}*/

	public FileNetDocumentException()
	{
		super();
	}

	@Override
	public String getMessage()
	{
		/*if (getExceptionCode() == ExceptionCode.E_OBJECT_NOT_FOUND)
		{
			return CONST_E_OBJECT_NOT_FOUND;
		}else if  (getExceptionCode() == ExceptionCode.API_NOT_A_CONTENT_TRANSFER)
		{
			return CONST_API_NOT_A_CONTENT_TRANSFER;
		}*/
		
		return getMessage();
	}
}
