package Exception;

public class NicknameNotFoundException extends StringException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1051546637786114053L;
		
	public NicknameNotFoundException(String surname){
		super(surname);
	}
	
	@Override
	public String toString(){
		return "this surname " + this.getString() + " not exist";
	}
}
