/**
 * This class is made up of response string arrays, and word bucket lists
 * 
 * Here is an example of a response template:
 * "Are you feeling _______? I am feeling __________." 
 * then the response would be stored as:
 * response = {"Are you feeling ", "? I am feeling ", "."};
 * 
 * This is an example of a possible corresponding  word bucket:
 * 
 * blanks = {{"happy", "sad", "lonely", "angry", "amused", "bored"},
 * 			 {"happy", "sad", "lonely", "angry", "amused", "bored"}};
 * 
 * Where the first array corresponds to a list of words which can be used
 * to fill in the first blank in the response template, and similarly the 
 * second array is a list of words that can be used to fill in the second
 * blank in the response template.  
 * 
 * @author Yasha
 *
 */
public interface ResponseTemplateInterface extends WordBuckets
{
	/**
	 * Prints a fill-in-the-blank style representation of this response template.
	 * @return
	 */
	public String toString();


	/**
	 * Method to check if the conversation is coming to a close.
	 * 
	 * @return true if response is to end conversation, false otherwise
	 */
	public abstract boolean isValediction();
	
	
	public abstract boolean needsWiki();
	
	/**
	 * Puts the response from the user into the corresponding memory location if applicable. 
	 * 
	 * @param input The most recent user input
	 * @param keys The keywords found in the most recent user input.
	 */
	public void processResponse(String input, KeyWordList keys);


}
