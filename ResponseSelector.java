import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Response Selector contains a reference to a Hash Table storing pointers
 * to all of the Response Templates. The keys for the hash table are the
 * keywords, and the values are then the pointers to the Response Templates.
 * This allows for quickly finding all of the relevant Response Tables based on
 * a set of found keywords.
 * 
 * Evaluates all of the possible relevant keywords.
 * 
 * 
 * @author Yasha
 * 
 */
public class ResponseSelector implements ResponseSelectorInterface, SharedData
{

	@Override
	public ResponseTemplate selectTemplate(KeyWordList keys) 
	{
		ArrayList<ResponseTemplate> list = new ArrayList<ResponseTemplate>();

		//Make a list of all the matching responses.
		for(String key:keys)
			list.addAll(Arrays.asList(responses.get(key)));
				
		int rank = Integer.MIN_VALUE;
		ResponseTemplate bestResponse = null;

		for(ResponseTemplate response:list)
		{
			int curRank = 0;
			
			//Find the number of matching keywords.
			for(String keyFound:keys)
				for(String key:response.keys)
					curRank+= match(key,keyFound);
			
			//evaluate the scaling rules for this response, and multiply by the curRank.
			curRank*=response.scaleRules();
			
			//Check if this response the new best response.
			if(curRank > rank)
			{
				rank = curRank;
				bestResponse = response;
			}else if(curRank == rank)
			{
				//If they're the same, randomly select between them. 
				java.util.Random rand = new java.util.Random();
				
				if(rand.nextBoolean())
					bestResponse = response;
			}
		}

		return bestResponse;
	}



	/**
	 * Helper method to compare if two strings are the same.
	 * 
	 * @param key1 First String
	 * @param key2 Second String
	 * @return int value of their comparison. 1 for same, 0 for different.
	 */
	private int match(String key1, String key2)
	{
		if (key1.equals(key2))
			return 1;
		else
			return 0;
	}

}