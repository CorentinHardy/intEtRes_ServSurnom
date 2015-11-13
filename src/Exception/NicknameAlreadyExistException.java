package Exception;

public class NicknameAlreadyExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1415170643742459018L;
	private String surname;
	
	public NicknameAlreadyExistException(String surname){
		super();
		this.surname = surname;
	}
	
	@Override
	public String toString(){
		return "this surname " + surname + " already exist";
	}
}
