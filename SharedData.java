import java.util.Map;
import java.util.HashMap;

/**
 * This interface has no methods, it's only purpose is to contain a pointer 
 * to the shared data structures so that they can be accessed by any class
 * that implements this interface. 
 * 
 * 
 * @author Yasha
 *
 */
public interface SharedData
{
	/**
	 * The master list of all the recognized key words.
	 */
	public static KeyWordList masterKeys = new KeyWordList();
	
	/**
	 * The Memory Table, this is used to store data from user input for future
	 * use or reference.
	 * 
	 * This is a dictionary data structure used to store previous user input.
	 * For example, the key may be “Name” and the value “John Doe”. When the
	 * question “What is your name?” Is asked, the response would then be parsed
	 * and stored in the memTable.
	 */
	public static Map<String,String> memTable = new HashMap<String,String>();
	
	/**
	 * The Hash-table data structure containing pointers to all of the
	 * response templates. They will have an entry in each bucket corresponding
	 * to each of their keywords.
	 */
	public static HashTable responses = new HashTable();
}

