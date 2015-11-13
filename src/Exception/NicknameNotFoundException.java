package Exception;

public class NicknameNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1051546637786114053L;
	
	private String surname;
	
	public NicknameNotFoundException(String surname){
		super();
		this.surname = surname;
	}
	
	@Override
	public String toString(){
		return "this surname " + surname + " not exist";
	}
}
