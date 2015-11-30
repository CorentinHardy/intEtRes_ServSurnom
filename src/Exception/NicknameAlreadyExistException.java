package exception;

public class NicknameAlreadyExistException extends StringException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1415170643742459018L;
	
	public NicknameAlreadyExistException(String surname){
		super(surname);
	}
	
	@Override
	public String toString(){
		return "this surname " + this.getString() + " already exist";
	}
}
