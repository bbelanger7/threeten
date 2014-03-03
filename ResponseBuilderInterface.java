/**
 * Interface to define the method signatures of the ResponseBuilder.
 * 
 * Builds the next response of the batterbot. 
 * 
 * @author Yasha
 *
 */
public interface ResponseBuilderInterface extends WordBuckets, SharedData
{

	/**
	 * This is the main function that builds the response of the batterbot. 
	 * 
	 * @param template ResponseTemplateInterface that needs to be filled in. 
	 * @param keys The List of keywords as a KeyWordList that were extracted from the previous user input. 
	 * @return The batterbot's response as a String. 
	 */
	public String buildResponse(ResponseTemplate template, KeyWordList keys);

}
