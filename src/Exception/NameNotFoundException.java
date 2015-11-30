package exception;

public class NameNotFoundException extends StringException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4897980790694133502L;
	public NameNotFoundException(String name){
		super(name);
	}
	
	@Override
	public String toString(){
		return "this name " + this.getString() + " not exist";
	}
}
