/*
  CS 2710 (Fall 2008), Assignment #3, Question #4
          File Name: NegativeDepositException.java
       Student Name: Tim Oram
              MUN #: #########
 */

// describes a negative deposit exception
public class NegativeDepositException extends RuntimeException {

	// the amount supplied
	private double deposit;

	public NegativeDepositException() {
	}

	public NegativeDepositException(String message) {
		super(message);
	}
	
	// constructs a exception with a message and deposit
	public NegativeDepositException(String message, double deposit) {
		super(message);
	}
	
	// returns the deposit amount
	public double getDeposit(){
		return this.deposit;
	}

}
