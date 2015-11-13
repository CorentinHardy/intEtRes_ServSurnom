package Exception;

public class NameAlreadyExistException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3799568841627351388L;
	
	private String name;
	
	public NameAlreadyExistException(String name){
		super();
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "this name " + name + " already exist";
	}
}
