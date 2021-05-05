package eng.la.util.filenet.model;

/*import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.exception.ExceptionCode;*/

/**
 *Gestione eccezioni creazione delle folder sul server filenet
 * 
 * @author 
 */
public class FileNetFolderException  extends Exception{

	private static String CONST_E_OBJECT_NOT_FOUND = "Folder non trovata";

	/*public FileNetFolderException(EngineRuntimeException cause)
	{
		super(cause.getExceptionCode());
	}
	
	public FileNetFolderException(ExceptionCode code)
	{
		super(code);
	}*/

	@Override
	public String getMessage()
	{
		/*if (getExceptionCode() == ExceptionCode.E_OBJECT_NOT_FOUND)
		{
			return CONST_E_OBJECT_NOT_FOUND;
		}*/
		return getMessage();
	}
}
