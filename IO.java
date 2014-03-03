import java.util.Scanner;
import java.io.*;

/**
 * Implementation of the IO interface.  It might even work.
 * 
 * @author Benjamin
 */
public class IO implements IOInterface
{

    public String read()
    {
        Scanner askUser = new Scanner(System.in);
        System.out.print("Enter your answer: ");
        
        String read = askUser.next();
        return read;
    }
    
    public void print (String response)
    {
        System.out.println(response + "\n");
    }
}