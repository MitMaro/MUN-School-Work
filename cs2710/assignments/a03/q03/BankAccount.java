/*
  CS 2710 (Fall 2008), Assignment #3, Question #3
          File Name: BankAccount.java
       Student Name: Tim Oram
              MUN #: #########
               From: Big Java 3e (modified)
 */


/**
   A bank account has a balance that can be changed by 
   deposits and withdrawals.
 */
public class BankAccount
{  
	/**
      Constructs a bank account with a zero balance.
	 */
	public BankAccount()
	{  
		balance = 0;
	}

	/**
      Constructs a bank account with a given balance.
      @param initialBalance the initial balance
	 */
	public BankAccount(double initialBalance)
	{  
		// throw an IllegalArgumentException if balance it less then zero
		if(initialBalance < 0){
			throw new IllegalArgumentException("Initial balance is less then 0");
		}
		balance = initialBalance;
	}

	/**
      Deposits money into the bank account.
      @param amount the amount to deposit
	 */
	public void deposit(double amount) 
	{  
		// throw an IllegalArgumentException if amount is less then 0
		if(amount < 0){
			throw new IllegalArgumentException("Can not deposit a negative amount");
		}
		
		balance = balance + amount;
	}

	/**
      Withdraws money from the bank account.
      @param amount the amount to withdraw
	 */
	public void withdraw(double amount)
	{  
		// throw an IllegalArgumentException if amount is less then zero or more then the balance of account
		if(amount < 0 || amount > this.balance){
			throw new IllegalArgumentException("The amount to withdraw needs to be between 0 and current balance");
		}
		balance = balance - amount;
	}

	/**
      Gets the current balance of the bank account.
      @return the current balance
	 */
	public double getBalance()
	{  
		return balance; 
	}

	/**
      Transfers money from the bank account to another account
      @param amount the amount to transfer
      @param other the other account
	 */
	public void transfer(double amount, BankAccount other)
	{  
		withdraw(amount);
		other.deposit(amount);
	}

	private double balance; 
}
