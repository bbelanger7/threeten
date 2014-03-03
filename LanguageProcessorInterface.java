/**
 * This interface is designed to setup the main public method signatures that can
 * be used by the BatterBotDriver class.
 * 
 * @author Yasha
 *
 */
public interface LanguageProcessorInterface extends SharedData
{
	/**
	 * This function has one main goal, that is to extract all of the keywords from the input String.
	 * 
	 * @param input This user response as a String
	 * @return A list of keywords found in the input as a KeyWordList
	 */
	public KeyWordList extractKeyWords(String input);
	
	
}
