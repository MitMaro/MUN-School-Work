/*
  CS 2710 (Fall 2008), Assignment #3, Question #1
          File Name: TimeDepositAccount.java
       Student Name: Tim Oram
              MUN #: #########
 */


// describes a bank account that gains interest and matures after so many months
public class TimeDepositAccount extends BankAccount{

	// hold months, interest and penalty for account
	private int months;
	private double interest;
	static final double penalty = 20.0;

	// constructor that takes an intital balance, an interest rate and the number of months till maturity
	public TimeDepositAccount(double balance, double rate, int months) {
		// call the parent constructor
		super(balance);
		
		// set interest rate and months to maturity
		this.interest = rate;
		this.months = months;
	}

	// adds interest to the account
	public void addInterest() 
	{  
		// call the parents deposit function with the interest added in
		this.deposit(this.getBalance() * this.interest);
		
		// do not decrease below 0 since well to many interests payments would
		// cause an overflow, since an integer has limits.
		// I know this would only happen in well over 178 million years (if monthly interest) and all but you never know 
		if(this.months > 0){
			this.months--;
		}
	}
	
	// returns the number of months
	public int getMonths(){
		return this.months;
	}

	// withdraw removing a penalty if account has not yet matured
	public void withdraw(double amount){
		// withdraw amount and penalty (If required) 
		super.withdraw(amount + (this.months > 0 ? this.penalty : 0.0));
	}	

}
