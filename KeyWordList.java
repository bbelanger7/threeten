import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This is a wrapper class meant to store the keywords.
 * 
 * It's current implementation uses an arrayList. 
 * 
 * We have created our own class, the KeyWordList, 
 * which is used to store a list of keywords. 
 * This class is very simple, and was designed to 
 * facilitate the integration of new features in the future. 
 * Currently it simply wraps an array of Strings. 
 * 
 * @author Yasha
 *
 */
public class KeyWordList implements java.lang.Iterable<String>
{
	/**
	 * A list of keywords, each string in the array is an individual keyword.
	 */
	private ArrayList<String> keys = new ArrayList<String>(0);
	
	/**
	 * Add a key to the end of the KeyWordList.
	 * 
	 * @param key - a String of the key to be added
	 */
	public void addKey(String key)
	{
		 keys.add(key);
	}
	
	/**
	 * Add a String[] of keywords to the KeyWordList.
	 * 
	 * @param keys - String[] of keys to be added.
	 */
	public void addKeys(String[] keys)
	{
		this.keys.addAll(new ArrayList<String>(Arrays.asList(keys)));
	}
	
	/**
	 * Returns a String[] of the keys.
	 * 
	 * @return a String[] of the keys.
	 */
	public String[] getKeys()
	{
		String[] temp = new String[keys.size()];
		return (String[]) keys.toArray(temp);
	}
	
	/**
	 * Set and overwrites any old keys with the input.
	 * 
	 * @param keys  a String[] of new keys.
	 */
	public void setKeys(String[] keys)
	{
		this.keys = new ArrayList<String>(Arrays.asList(keys));
	}

	@Override
	public Iterator<String> iterator() 
	{
		return keys.listIterator();
	}
	
	public int size()
	{
		return keys.size();
	}

	public String toString()
	{
		return keys.toString();
	}
}
