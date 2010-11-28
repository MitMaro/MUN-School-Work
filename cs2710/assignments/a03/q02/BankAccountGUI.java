/*
  CS 2710 (Fall 2008), Assignment #3, Question #2
          File Name: BankAccountGUI.java
       Student Name: Tim Oram
              MUN #: #########
               From: Big Java 3e
 */


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


// creates a GUI for the BankAccount class
public class BankAccountGUI {

	// the two buttons, two text fields and the bank account
	private JButton btn_withdraw;
	private JButton btn_deposit;
	private JTextField txt_withdraw;
	private JTextField txt_deposit;
	private BankAccount account;
	
	
	// constants containing the frames height and width
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 200;
	
	
	public static void main(String[] args) {
		
		// create the frame
		JFrame frame = new JFrame();
		
		// create an instance of this class
		BankAccountGUI gui = new BankAccountGUI(); 
		
		// call the function to create the GUI
		gui.init(frame);
		
		// show the frame
		frame.setVisible(true);
	}
	
	// creates the GUI
	public void init(JFrame frame){
		
		// setup the frame
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Bank Account Graphical User Interface");
		
		// create panel
		JPanel panel = new JPanel(new GridBagLayout());

		// the constrant
		GridBagConstraints c = new GridBagConstraints();

		// create a bank account with an initial blance of $0.00
		this.account = new BankAccount(0.0);
		
		// set the position, width, padding, etc.. of the constraint
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 3;
		c.ipady = 5;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		
		// create a label to display the current blance
		JLabel lbl_balance = new JLabel("Current Balance: $" + String.valueOf(this.account.getBalance()));
	
		// add the label to the label
		panel.add(lbl_balance, c);
		
		// create a text field with a width of 12 characters
		txt_deposit = new JTextField(12);

		// create a button called Deposit and add an action listener
		btn_deposit = new JButton("Deposit");
		btn_deposit.addActionListener(new DepositListener(lbl_balance, txt_deposit, this.account));

		
		// set width, position, etc...
		c.gridwidth = 1;
		c.gridy = 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.NONE;
		
		// add a label
		panel.add(new JLabel("Deposit:"), c);
		
		// set the x position
		c.gridx = 1;
		// add a text field
		panel.add(txt_deposit, c);

		// set the x position
		c.gridx = 2;
		// add a deposit button
		panel.add(btn_deposit, c);

		
		// create a text field 
		txt_withdraw = new JTextField(12);
		// create a button with an action listener
		btn_withdraw = new JButton("Withdraw");
		btn_withdraw.addActionListener(new WithdrawListener(lbl_balance, txt_withdraw, this.account));
	
		// set some constraints
		c.gridy = 2;
		c.gridx = 0;
		// add Withdraw button
		panel.add(new JLabel("Withdraw:"), c);

		// set position and add text field
		c.gridx = 1;
		panel.add(txt_withdraw, c);

		// set position and add a button
		c.gridx = 2;
		panel.add(btn_withdraw, c);

		// add the panel to the frame
		frame.add(panel);
	}
	
}
