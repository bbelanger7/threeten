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
public class IO extends JPanel implements IOInterface, ActionListener
{
	//Because Eclipse was complaining
	private static final long serialVersionUID = 1L;
	//private Scanner askUser = new Scanner(System.in);
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private boolean clearToRead = false;
    //private JPanel panel;
    
    //When I used the console, la di da
    /*
	public String read()
    {
        //askUser = new Scanner(System.in);
        System.out.print("Enter your answer: ");
        
        String read = askUser.nextLine();
        return read;
    }
	*/
    /**
     * 
     */
    public void print (String response)
    {
    	//Old console code
        //System.out.println("Bot: " + response + "\n");
    	
    	//GUI code
    	textArea.append("Bot: " + response + newline);
    	textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
     
    /*
    public IO()
    {
    	setLayout(new BorderLayout());
    	panel = new JPanel();
    	textField = new JTextField(20);
    	textArea = new JTextArea(10, 20);
    	textArea.setEditable(false);
    	JScrollPane scrollPane = new JScrollPane(textArea);
    	getContentPane().add(panel, BorderLayout.CENTER);
    	getContentPane().add(textArea, BorderLayout.NORTH);
    	getContentPane().add(textField, BorderLayout.SOUTH);
    	getContentPane().add(scrollPane, BorderLayout.SOUTH);
    	pack();
    	setVisible(true);
    	
    	
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	String text = textField.getText();
    	textArea.append(text + newline);
    	textField.selectAll();
    	textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    */
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
     */
    public String read()
    {
    	while(true) //Spin until we receive a signal from the event
    	{
    		System.out.println("");  //The console wants this for some reason
    		//if (clearToRead == true)
    		//{
    		//	clearToRead = false;
    		//	break;
    		//I don't want this to run UNLESS it was called by actionperformed
    		//return textField.getText();
    		if (clearToRead == true)
    			break;
    	}
    	clearToRead = false;
    	return textField.getText();
    	
    }
    
}
