/*
  CS 2710 (Fall 2008), Assignment #3, Question #4
          File Name: InvalidWithdrawException.java
       Student Name: Tim Oram
              MUN #: #########
 */

// describes a invalid withdraw exception
public class InvalidWithdrawException extends RuntimeException {

	// holds the attempted amount drawn 
	public double withdraw;
	
	// creates a empty exception
	public InvalidWithdrawException() {
	}

	// creates a exception with a message
	public InvalidWithdrawException(String message) {
		super(message);
	}

	// creates a exception with a message and amount
	public InvalidWithdrawException(String message, double withdraw) {
		super(message);
		this.withdraw = withdraw;
	}
	
	// get the amount
	public double getWithdraw(){
		return this.withdraw;
	}
}
 