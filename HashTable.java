import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Data structure used to store the pointers to the responseTemplates.
 * 
 * For some reason the existing Hashtable only seemed to be able to contain
 * one  element, so I made us a new data structure by combining the hash
 * table with the ArrayList. This data structure now also conveniently returns
 * the response templates as an array.
 * 
 * @author Yasha
 *
 */
public class HashTable 
{
	private Hashtable<String,ArrayList<ResponseTemplate>> hash = new Hashtable<String,ArrayList<ResponseTemplate>>();
	
	/**
	 * Adds the new the value into the bucket in the hash table with the associated key. 
	 * 
	 * @param key String the key as a String
	 * @param value ResponseTemplate 
	 */
	public void put(String key, ResponseTemplate value)
	{
		ArrayList<ResponseTemplate> bucket = hash.get(key);
		if(bucket == null)
		{
			bucket = new ArrayList<ResponseTemplate>();
			hash.put(key,bucket);
		}
		
		bucket.add(value);

	}
	
	/**
	 * Get the ResponseTemplates associated with the keyword Key
	 * 
	 * @param key The keyword as a String
	 * @return The ResponseTemplates as an array.
	 */
	public ResponseTemplate[] get(String key)
	{
		ArrayList<ResponseTemplate> bucket = hash.get(key);
		
		if(bucket == null)
			throw new NullPointerException("There is no ResponseTemplate associated with that keyword");
		
		ResponseTemplate[] temp = new ResponseTemplate[bucket.size()];
		return (ResponseTemplate[]) bucket.toArray(temp);
		
	}
}
