package Exception;

public class NameNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4897980790694133502L;

	private String name;
	
	public NameNotFoundException(String name){
		super();
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "this name " + name + " not exist";
	}
}
