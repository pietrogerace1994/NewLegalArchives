package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	private String cause;
	private String stackTrace;
	private String methodName;
	private String fileName;
	private String lineNumber;
	private String className;
	private String nativeMethod;
	

	public ErrorMessage() {
		// TODO Auto-generated constructor stub
	}


	public ErrorMessage(String cause, String stackTrace, String methodName, String fileName, String lineNumber,
			String className, String nativeMethod) {
		super();
		this.cause = cause;
		this.stackTrace = stackTrace;
		this.methodName = methodName;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.className = className;
		this.nativeMethod = nativeMethod;
	}


	public String getCause() {
		return cause;
	}


	public void setCause(String cause) {
		this.cause = cause;
	}


	public String getStackTrace() {
		return stackTrace;
	}


	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getLineNumber() {
		return lineNumber;
	}


	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getNativeMethod() {
		return nativeMethod;
	}


	public void setNativeMethod(String nativeMethod) {
		this.nativeMethod = nativeMethod;
	}
	
	
	
	
	

}
