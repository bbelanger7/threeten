import java.util.Scanner;

/**
 * Simple IO class designed to interact with the console.
 * 
 * @author Yasha
 *
 */
public class IOconsole implements IOInterface
{
	private Scanner scan = new Scanner(System.in);

	@Override
	public void print(String response) 
	{
		System.out.println("BatterBot: " + response);
	}

	@Override
	public String read() 
	{
		return scan.nextLine();
	}

}
