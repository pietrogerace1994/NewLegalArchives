package eng.la.util.filenet.model;

/*import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.exception.ExceptionCode;*/

/**
 *Gestione eccezioni Connessione a filenet
 * 
 * @author 
 */
public class FileNetConnectionException  extends Exception{
	
	public static String CONST_E_BAD_PARAMETER = "I parametri specificati per l'oggetto non sono validi";
	public static String CONST_E_SERVER_ERROR = "Server FILENET specificato non valido";
	public static String CONST_E_NOT_AUTHENTICATED = "Utente e/o password specificati non validi";
	public static String CONST_API_UNABLE_TO_USE_CONNECTION = "URI specificato per la connessione al server non � valido";
	public static String CONST_E_OBJECT_NOT_FOUND = "Object Store specificato non valido";

	/*public FileNetConnectionException(EngineRuntimeException cause)
	{
		super(cause.getExceptionCode());
	}
	public FileNetConnectionException(ExceptionCode code)
	{
		super(code);
	}*/
	
	@Override
	public String getMessage()
	{
		/*if (getExceptionCode() == ExceptionCode.E_OBJECT_NOT_FOUND)
			return CONST_E_OBJECT_NOT_FOUND;
		else if (getExceptionCode() == ExceptionCode.E_BAD_PARAMETER)
			return CONST_E_BAD_PARAMETER;
		else if (getExceptionCode() == ExceptionCode.E_SERVER_ERROR)
			return CONST_E_SERVER_ERROR;
		else if (getExceptionCode() == ExceptionCode.E_NOT_AUTHENTICATED)
			return CONST_E_NOT_AUTHENTICATED;
		else if (getExceptionCode() == ExceptionCode.API_UNABLE_TO_USE_CONNECTION)
			return CONST_API_UNABLE_TO_USE_CONNECTION;*/
		
		return getMessage();
	}

}
