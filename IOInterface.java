/**
 * This interface is designed to control the IO between the batterbot 
 * and the user.
 * 
 * @author Yasha
 *
 */
public interface IOInterface 
{
	/**
	 * Used to print the response of the batterbot.
	 * 
	 * @param response message to be printed by the batterbot.
	 */
	public void print(String response);
	
	/**
	 * Used to read the user input to the batterbot.
	 * This method will block if the next user input
	 * is not ready.
	 * 
	 * Oh, no it won't.
	 * 
	 * @return Returns the next user input as a String.
	 */
	public String read();
	
	
}
