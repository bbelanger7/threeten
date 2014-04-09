//import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.darkprograms.*;


/**
 * Implementation of the IO interface.
 * Most of the GUI code was adapted from here:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextDemoProject/src/components/TextDemo.java
 * 
 * @author Benjamin
 */
public class IOold extends JPanel implements IOInterface, ActionListener
{
	/**
	 * Because Eclipse was complaining
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Variable to make the text field
	 */
    protected JTextField textField;
    /**
	 * Variable to make the text area
	 */
    protected JTextArea textArea;
    /**
	 * Bookkeeping variable to make a new line quick
	 */
    private final static String newline = "\n";
    /**
	 * Variable that sets when the user presses enter so the reader knows to read then.
	 */
    private boolean clearToRead = false;
    
    /**
     * A function that prints responses from the bot.
     * @param A response string from the bot
     */
    public void print (String response)
    {
    	//GUI code
    	textArea.append("Bot: " + response + newline);
    	textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    /**
     * Constructor.
     */
    public IOold()
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
        
        JFrame frame = new JFrame("BatBot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(this);
 
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
     * @return The string from the user- from the text field.
     */
    public String read()
    {
    	while(true) //Spin until we receive a signal from the event
    	{
    		System.out.println("");  //The console wants this for some reason
    		if (clearToRead == true)
    			break;
    	}
    	clearToRead = false;
    	return textField.getText();
    	
    }
    
}
