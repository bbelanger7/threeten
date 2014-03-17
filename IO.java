import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Implementation of the IO interface.  It might even work.
 * Special thanks go to this site, because that's where
 * quite a bit of this code is from:
 * 
 * 
 * 
 * @author Benjamin
 */
public class IO extends JPanel implements IOInterface, ActionListener
{

    private Scanner askUser = new Scanner(System.in);
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";
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
        
        JFrame frame = new JFrame("TextDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(this);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);    	
    }
    
    //So when the 'Enter' key is pressed
    public void actionPerformed(ActionEvent evt)
    {
    	//Grab the text from the text field
    	String text = textField.getText();
    	//Send it as a read event?
    	read();
    	//Paste it to the history
        textArea.append(text + newline);
        //Select everything from the text field
        textField.selectAll();
         
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    public String read()
    {
    	//if (textField.getText() != "")
    		return textField.getText();
    }
    
}
