package server;

import Exception.NameAlreadyExistException;
import Exception.NameNotFoundException;
import Exception.NicknameAlreadyExistException;
import Exception.NicknameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestionNomSurnom {
	private HashMap<String, String> nicknames;
	private HashMap<String, ArrayList<String>> names;

	/**
	 * Constructor for GestionNomSurnom.
	 */
	public GestionNomSurnom(){
		nicknames = new HashMap<String, String>();
		names = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * tell if this name is already use.
	 * 
	 * @param name a String
	 * @return true if we know this name, false otherwise. 
	 */
	public boolean haveName(String name){
		return names.containsKey(name);
	}
	
	/**
	 * tell if this nickname is already use.
	 * 
	 * @param nickname a String
	 * @return true if we know this nickname, false otherwise. 
	 */
	public boolean haveNickname(String nickname){
		return nicknames.containsKey(nickname);
	}
	
	/**
	 * add the String name to the database as a name.
	 * 
	 * @param name
	 * @throws NameAlreadyExistException if the name already exist.
	 */
	public void addName(String name) throws NameAlreadyExistException{
		if (this.haveName(name))
			throw new NameAlreadyExistException(name);
		names.put(name, new ArrayList<String>());
	}
	
	/**
	 * add the String nickname to name.
	 * 
	 * @param name a String
	 * @param nickname a String
	 * @throws NameNotFoundException if the name doesn't exist.
	 * @throws NicknameAlreadyExistException if the nickname already exist
	 */
	public void addNickname(String name, String nickname) throws NicknameAlreadyExistException, NameNotFoundException{
		if (! this.haveName(name))
			throw new NameNotFoundException(name);
		if (this.haveNickname(nickname))
			throw new NicknameAlreadyExistException(nickname);
		String sn = new String(nickname);
		nicknames.put(sn, name);
		names.get(name).add(sn);
	}

	/**
	 * find and return all nickname associated with the String name.
	 *
	 * @param name we want nicknames of him.
	 * @return List<String> of the nicknames.
	 * @throws NameNotFoundException if we don't know name.
	 */
	public List<String> getNicknames(String name) throws NameNotFoundException {
		if (! this.haveName(name))
			throw new NameNotFoundException(name);
		return names.get(name);
	}

	/**
	 * get the name associate with the String nickname.
	 *
	 * @param nickname a String
	 * @return the String name associate with the nickname.
	 * @throws NicknameNotFoundException if the nickname don't exist.
	 */
	public String getName(String nickname) throws NicknameNotFoundException{
		if (! this.haveNickname(nickname))
			throw new NicknameNotFoundException(nickname);
		return nicknames.get(nickname);
	}

	/**
	 * Remove a nickname of a name.
	 *
	 * @param nickname a String which represent a Nickname of someone
	 * @throws NicknameNotFoundException if this nickname doesn't exist.
	 */
	public void removeNickname(String nickname) throws NicknameNotFoundException {
		try{
			this.getNicknames(this.getName(nickname)).remove(nickname);
		}catch(NameNotFoundException e){
			System.err.println("Something bad have happened: a nickname haven't name with it.");
			e.printStackTrace();
		}
		if (nicknames.remove(nickname) == null)
			throw new NicknameNotFoundException(nickname);
	}

	/**
	 * (Optional) Remove a name and all his nickname.
	 *
	 * @param name a String
	 * @throws NameNotFoundException
	 */
	public void removeName(String name) throws NameNotFoundException{
		if(! this.haveName(name))
			throw new NameNotFoundException(name);
		for (String sn: names.get(name)) {
			System.out.println("We remove nickname: " + sn);
			nicknames.remove(sn);
		}
		names.remove(name);
	}

}
