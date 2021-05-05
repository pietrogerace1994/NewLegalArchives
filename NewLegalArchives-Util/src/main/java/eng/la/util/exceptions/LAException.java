package eng.la.util.exceptions;

public class LAException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6340043784244932598L;
	private String message = null;
	private String code = null;

	 

    public LAException() {

        super();

    }

 

    public LAException(String message) {

        super(message);

        this.message = message;

    }
    
    public LAException(String message, String code) {

        super(message);

        this.message = message;
        this.code = code;

    }

 

    public LAException(Throwable cause) {

        super(cause);

    }

 

    @Override

    public String toString() {

        return message;

    }

 

    @Override

    public String getMessage() {

        return message;

    }



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}
    
    
}
