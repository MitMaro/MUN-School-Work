/*
  CS 2710 (Fall 2008), Assignment #3, Question #1
          File Name: Main.java
       Student Name: Tim Oram
              MUN #: #########
 */

// Tests the TimeDepositAccount class
public class Main {

	public static void main(String[] args) {
		
		// create a account
		TimeDepositAccount account = new TimeDepositAccount(100.0, 0.1, 2);
		
		// add interest and withdraw before maturity
		account.addInterest();
		account.withdraw(10.0);

		// print account info
		System.out.println("Account Balance $" + account.getBalance() + " Months to Maturity: " + account.getMonths());
		System.out.println("Should Have Been:");
		System.out.println("Account Balance $80.0 Months to Maturity: 1");
		System.out.println();

		// add intertest again
		account.addInterest();
		// withdraw after maturity
		account.withdraw(20.0);
		
		// print account info
		System.out.println("Account Balance $" + account.getBalance() + " Months to Maturity: " + account.getMonths());
		System.out.println("Should Have Been:");
		System.out.println("Account Balance $68.0 Months to Maturity: 0");		
		
		
	}

}
