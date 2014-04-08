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
 * And I used the darkprograms->Google speech recognizer.
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
    		System.out.println("");  //The console wants this for some reason
    		if (clearToRead == true)
    			break;
    	}
    	clearToRead = false;
    	return textField.getText();
    }
    
}
