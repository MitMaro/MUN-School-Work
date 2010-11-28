/*
  CS 2710 (Fall 2008), Assignment #3, Question #2
          File Name: WithdrawListener.java
       Student Name: Tim Oram
              MUN #: #########
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;


// withdraw button listener
public class WithdrawListener implements ActionListener {
	
	// some things that need to be referenced in the listener
	JLabel lbl_balance;
	JTextField txt_withdraw;
	BankAccount account;
	
	// constructs the listener
	public WithdrawListener(JLabel label, JTextField withdraw, BankAccount account){
		this.lbl_balance = label;
		this.txt_withdraw = withdraw;
		this.account = account;
	}
	
	// action was performed
	public void actionPerformed(ActionEvent e) {
		
		// withdraw ammount from account
		this.account.withdraw(Double.parseDouble(this.txt_withdraw.getText()));
		// update balance label
		this.lbl_balance.setText(String.format("Current Balance: $%.2f%n",this.account.getBalance()));
		
		
	}

}
