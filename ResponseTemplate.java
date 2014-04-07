/**
 * This class is used to store "canned" sentences and their variable 
 * substitutions. Each ResponseTemplate is also required to contain
 * a list of associated keywords, and optional additional rules,
 * that trigger this response to be selected.
 *
 *   
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
public class ResponseTemplate implements ResponseTemplateInterface, SharedData
{
	/**
	 * A unique ID that can be used to identify the response template.
	 */
	public int ID = 0;
	/**
	 * A counter that should be incremented each time a new response
	 * template object is instantiated so as to preserve the uniqueness
	 * of the response templates.
	 */
	public static int IDCounter = 0;
	
	/**
	 * This is an internal counter of the number of questions that have
	 * been asked. It can be used in the scaleRules function. It is automatically
	 * incremented when the setAsked() method is called. 
	 */
	private static int questionsAsked = 0;
	
	/**
	 * Provides access to contains().
	 */
	public static LanguageProcessor LP = new LanguageProcessor();
	
	/**
	 * Simple flag for determining whether or not this
	 * question has been asked previously. 0 for has not been asked,
	 * 1 for has been asked.
	 */
	private int asked = 0;
	
	/**
	 * Determines if this response is to end the conversation. 
	 */
	public boolean valediction = false;
	
	//Determines if this response gets to call Wikipedia.
	public boolean needsWiki = false;

	
	public static KeyWordList fullKeys = null;
	
	/**
	 * This is an array of strings that form the parts of the response delimited
	 * by words that need to be filled in. For example suppose the response
	 * template was: "Are you feeling _______? I am feeling __________." then the response would be stored as:
	 * response = {"Are you feeling ", "? I am feeling ", "."};
	 */
	public String[] response = null;

	/**
	 * This is an array of word buckets. These correspond to the blanks that are
	 * left in the response string. For example, if the response string is stored
	 * as: response = {"Are you feeling ", "? I am feeling ", "."}; then the corresponding word bucket
	 * might be filled with: 
	 * 
	 * blanks = {{"happy", "sad", "lonely", "angry", "amused", "bored"},
	 * 			 {"happy", "sad", "lonely", "angry", "amused", "bored"}};
	 * 
	 * Where the first array corresponds to a list of words which can be used
	 * to fill in the first blank in the response template, and similarly the 
	 * second array is a list of words that can be used to fill in the second
	 * blank in the response template. 
	 * 
	 * See also WordBuckets.java
	 */
	public String[][] wordBuckets = null;
	
	/**
	 * A list of keywords that are relevant to this response. These are the 
	 * words that the user needs to enter in order for this question to be
	 * ranked as relevant, and thus asked. If the keys are null then this
	 * indicates that there are no relevant keys. The response, or question,
	 * should then be a generic one. 
	 */
	public String[] keys = null;
	
	public String memEntry = null;
	
	/**
	 * Each response template I determine should have a Wiki entry needs a corresponding term the builder searches for.
	 */
	public String wikiTerm = null;
	
	/**
	 * Constructor to build a response template.
	 * This constructor also assigns a unique ID to each responseTemplate.
	 * 
	 * @param response An array of strings that form the parts of the response delimited by blank words.
	 * @param wordBuckets An array of string arrays, or word buckets, that fill in the blanks in the response.
	 * @param keys An array of keys that are relevant to this question, null indicates that there are no relevant keys.
	 * @param memEntry The key used to store a piece of data obtained from the user in the memTable.
	 */
	public ResponseTemplate(String[] response, String[][] wordBuckets, String[] keys, String memEntry)
	{
		this.ID = IDCounter++;
		
		this.response = response;
		this.wordBuckets = wordBuckets;
		
		if(keys != null)
			this.keys = keys;
		else
		{
			String[] temp = {"null"};
			this.keys = temp;
		}
		
		this.memEntry = memEntry;
		
		//If this ResponseTemplate is looking for data reserve a spot for it in memEntry.
		if(memEntry != null)
			memTable.put(memEntry, null);
		
		//Store a pointer to this ResponseTemplate in each location corresponding to each of this response's keys.
		for(String key:keys)
			responses.put(key, this);
			
				
		//Builds the masterKeys list
		masterKeys.addKeys(keys);
	}
	
	/**
	 * Constructor to build a response template.
	 * This constructor also assigns a unique ID to each responseTemplate.
	 * 
	 * @param response An array of strings that form the parts of the response delimited by blank words.
	 * @param wordBuckets An array of string arrays, or word buckets, that fill in the blanks in the response.
	 * @param keys An array of keys that are relevant to this question, null indicates that there are no relevant keys.
	 */
	public ResponseTemplate(String[] response, String[][] wordBuckets, String[] keys)
	{
		this(response,wordBuckets,keys,null);
	}
	
	public boolean isValediction() 
	{
		return valediction; 
	}
	
	public boolean needsWiki()
	{
		return needsWiki;
	}
	
	public String wikiTerm()
	{
		return wikiTerm;
	}
	
	/**
	 * This method is used to define criteria for a response template that
	 * determines whether or not this question can be asked during the conversation.
	 * 
	 * The default is for this function to return 1, which indicates that the
	 * question may be asked at any time during the conversation. 
	 * 
	 * More specific rules should be defined during the ResponseTemplate instantiation. 
	 * 
	 * @return The integer output for this function will be multiplied to the relevancy
	 * ranking of the response template. This means that a value of 0 will force the 
	 * question to not be asked. A value of 1 will allow the question. Other values
	 * are allowed, but should be used with care, as they will affect the ordering of
	 * the questions asked.
	 */
	public double scaleRules()
	{
		return 1;
	}

	/**
	 * This is an optional function that can be defined when the response template is made.
	 * 
	 * @param input
	 */
	private String extractResponse(String input)
	{
		return input;
	}
	
	public void processResponse(String input, KeyWordList keys)
	{
		if(memEntry == null)
			return;
		else
		{
			memTable.put(memEntry, extractResponse(input));
		}
	}
	
	public int isAsked()
	{
		return asked;
	}
	
	public void setAsked()
	{
		asked = 1;
		questionsAsked++;
	}
	
	public int questionsAsked()
	{
		return questionsAsked;
	}

	public String toString()
	{
		String str = "";

		for(int i = 0; i < response.length; i++)
		{
			str+= response[i];
			if (i < response.length - 1)
				str+= "_________";
		}

		return str;
	}
}
