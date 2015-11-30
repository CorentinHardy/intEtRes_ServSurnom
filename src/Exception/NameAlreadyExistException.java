package exception;

public class NameAlreadyExistException extends StringException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3799568841627351388L;
		
	public NameAlreadyExistException(String name){
		super(name);
	}
	
	@Override
	public String toString(){
		return "this name " + this.getString() + " already exist";
	}
}
