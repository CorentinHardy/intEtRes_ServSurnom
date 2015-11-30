package exception;

public class StringException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3668294232784604042L;
	
	private String str;
	
	public StringException(String str){
		super();
		this.str = str;
	}
	
	public String getString(){
		return str;
	}
}
