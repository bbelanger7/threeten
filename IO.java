import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.LineUnavailableException;

import com.darkprograms.speech.microphone.*;
import com.darkprograms.speech.recognizer.*;

/**
 * Implementation of the IO interface.
 * Most of the GUI code was adapted from here:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextDemoProject/src/components/TextDemo.java
 * 
 * I used The-Shadow's java-speech-api for speech recognition.  
 * It can be found here:
 * https://github.com/The-Shadow/java-speech-api
 * 
 * @author Benjamin
 */
public class IO extends JPanel implements IOInterface, ActionListener
{
	//Because Eclipse was complaining
	private static final long serialVersionUID = 1L;
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private boolean clearToRead = false;
    //For Google.
    private boolean clearToHear = true;
    private GoogleResponse gR = new GoogleResponse();
    private String textFromGoogle = "";
    private Recognizer rec = new Recognizer();
    //Speech capture needs a class too
    private Microphone mic = new Microphone(AudioFileFormat.Type.WAVE);
    
    
    
    //Just printing for now, the bot doesn't need to talk
    public void print (String response)
    {
    	textArea.append("Bot: " + response + newline);
    	textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    //Sets up the system.
    public IO()
    {
    	super(new GridBagLayout());
    	textField = new JTextField(20);
    	textField.addActionListener(this);
    	
    	textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
 
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        JFrame frame = new JFrame("BatBot 1.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(this);
        frame.setPreferredSize(new Dimension(1000,600));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);    	
    }
    
    /**
     * When the user presses Enter (the action we're listening for), clear the reader to return something.
     * @param Event [created by pressing 'Enter']
     */
    public void actionPerformed(ActionEvent evt)
    {
    	//This was a test.
    	//textField.setText("Hello");
    	//Grab the text from the text field
    	String text = textField.getText();
    	
    	//Unblock that reader
    	clearToRead = true;
    	
    	//Paste it to the history
        textArea.append("User: " + text + newline);
        
        //Select everything from the text field
        textField.selectAll();
         
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
        
    }
    
    /**
     * Reads from the console when the user presses Enter.
     */
    public String read()
    {
    	while(true) //Spin until we receive a signal from the event
    	{
    		System.out.print("");  //The console wants this for some reason.  I don't know why.
    		if (clearToHear == true)
    		{
    			//Debug
    			System.out.println("I'm listening.");
    			//Start listening for user input; listen for 5 seconds.
    			try
    			{
    				mic.captureAudioToFile("temp.wav");
    			}
    			catch (LineUnavailableException x)
    			{
    				textFromGoogle = "The file didn't take.";
    			}
    			try
        		{
    	    		//Wait 4 seconds for the user to finish talking
    	    		Thread.sleep(4000);
        		}
        		catch(InterruptedException ex)
        		{
        			Thread.currentThread().interrupt();
        			System.out.println("My sleep was interrupted.");
        		}
    			mic.close();
    			System.out.println("I'm done listening.");
    			
    			//Send that file to Google and get a response, so long as Google takes no exception of course
    			try
    			{
    			//Now with less needing to save.
    			Thread.sleep(100);
    			gR = rec.getRecognizedDataForWave("temp.wav");
    			textFromGoogle = gR.getResponse();
    			//System.out.println(textFromGoogle);
    			}
    			catch (IOException e)
    			{
    			textFromGoogle = "Oops, something went wrong.";
    			}
    			catch(InterruptedException ex)
    			{
    			Thread.currentThread().interrupt();
    			}
    			
    			if (textFromGoogle == null)
    			{
    				System.out.println("Oops, I didn't understand that.  Try typing.");
    			}
    			else
    			{
    				//If Google understood what the user said, replace the text field with it.
    				textField.setText(textFromGoogle);
    			}
    			
    			//Don't repeat this more than once
    			clearToHear = false;
    			System.out.println("Press Enter to continue.  Correct the text if it's wrong.");
    			
    		}
    		//Loop until the user presses Enter
    		if (clearToRead == true)
    			break;
    	}
    	clearToRead = false;
    	clearToHear = true;
    	return textField.getText();
    }
    
}
