/**
 * This interface sets up the method signatures for the response selector component. 
 * 
 * The functions of this component include taking in a list of keywords and selecting
 * the most appropriate response template to be used. Notifying the underlying ResponseGraph
 * which response template has been selected so that the ResponseGraph can update to
 * the new position in the ResponseGraph.
 * @author Yasha
 *
 */
public interface ResponseSelectorInterface
{
	
	
	/**
	 * Selects the most relevant response template by using the keywords from the last user input.
	 * 
	 * @param keys List of keywords selected by the LP
	 * @return Returns a pointer to the selected response template.
	 */
	public ResponseTemplate selectTemplate(KeyWordList keys);
	
	
	
}
