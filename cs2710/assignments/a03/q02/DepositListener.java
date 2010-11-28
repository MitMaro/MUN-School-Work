/*
  CS 2710 (Fall 2008), Assignment #3, Question #2
          File Name: DepositListener.java
       Student Name: Tim Oram
              MUN #: #########
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;

// deposit listener
public class DepositListener implements ActionListener {
	
	// some things that are needed in the listener
	JLabel lbl_balance;
	JTextField txt_deposit;
	BankAccount account;
	
	// constructor
	public DepositListener(JLabel label, JTextField deposit, BankAccount account){
		this.lbl_balance = label;
		this.txt_deposit = deposit;
		this.account = account;
	}
	
	// action was performed
	public void actionPerformed(ActionEvent e) {
		
		// deposit value of text field
		this.account.deposit(Double.parseDouble(this.txt_deposit.getText())); 
		// set the balance label
		this.lbl_balance.setText(String.format("Current Balance: $%.2f%n",this.account.getBalance()));
		
		
	}

}
