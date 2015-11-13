package server;

import Exception.NameAlreadyExistException;
import Exception.NameNotFoundException;
import Exception.NicknameAlreadyExistException;
import Exception.NicknameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestionNomSurnom {
	private ArrayList<String> surnames;
	private HashMap<String, ArrayList<Integer>> names;

	/**
	 * Constructor for GestionNomSurnom.
	 */
	public GestionNomSurnom(){
		surnames = new ArrayList<String>();
		names = new HashMap<String, ArrayList<Integer>>();
	}

	/**
	 * look for a String surname. 
	 * 
	 * @param surname a String which can be o not a surname.
	 * @return -1 if surnames haven't been found. A int >= 0 otherwise.
	 */
	private int searchSurname(String surname){
		int stop = surnames.size();
		for(int i = 0; i < stop; i++)
			if(surname.equals(surnames.get(i)))
				return i;
		return -1;
				
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
	 * tell if this surname is already use.
	 * 
	 * @param surname a String
	 * @return true if we know this surname, false otherwise. 
	 */
	public boolean haveSurname(String surname){
		return (this.searchSurname(surname) != -1);
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
		names.put(name, new ArrayList<Integer>());
	}
	
	/**
	 * add the String surname to name.
	 * 
	 * @param name a String
	 * @param surname a String
	 * @throws NameAlreadyExistException if the name doesn't exist.
	 * @throws NicknameAlreadyExistException if the surname already exist
	 */
	public void addSurname(String name, String surname) throws NicknameAlreadyExistException, NameAlreadyExistException{
		if (! this.haveName(name))
			throw new NameAlreadyExistException(name);
		if (this.haveSurname(surname))
			throw new NicknameAlreadyExistException(surname);
		surnames.add(surname);
		names.get(name).add(surnames.size() - 1);
	}

	/**
	 * find and return all surname associated with the String name.
	 *
	 * @param name we want surnames of him.
	 * @return List<String> of the surnames.
	 * @throws NameNotFoundException if we don't know name.
	 */
	public List<String> getSurnames(String name) throws NameNotFoundException {
		if (!this.haveName(name))
			throw new NameNotFoundException(name);
		List<String> al = new ArrayList<String>();
		List<Integer> sn = names.get(name);
		for (int i : sn)
			al.add(surnames.get(i));
		return al;
	}

	/**
	 *
	 * @param surname
	 * @return
	 * @throws NicknameNotFoundException
	 * @throws NicknameAlreadyExistException
	 */
	public String getName(String surname) throws NicknameNotFoundException, NicknameAlreadyExistException{
		if (! this.haveSurname(surname))
			throw new NicknameAlreadyExistException(surname);
		for (String aName: names.keySet())
			for (int i : names.get(aName))
				if(surname.equals(surnames.get(i)))
					return aName;
		throw new NicknameNotFoundException(surname);
	}
}
