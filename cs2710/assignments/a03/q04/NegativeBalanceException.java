/*
  CS 2710 (Fall 2008), Assignment #3, Question #4
          File Name: NegativeBalanceException.java
       Student Name: Tim Oram
              MUN #: #########
 */

// describes a negative balance exception
public class NegativeBalanceException extends RuntimeException {

	// the balance given
	private double balance;
	
	public NegativeBalanceException() {
	}

	public NegativeBalanceException(String message) {
		super(message);
	}

	// constructor with a message and a balance
	public NegativeBalanceException(String message, double balance) {
		super(message);
		this.balance = balance;
	}

	// gets the balance
	public double getBalance(){
		return this.balance;
	}

}
