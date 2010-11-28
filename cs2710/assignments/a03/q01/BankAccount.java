/*
  CS 2710 (Fall 2008), Assignment #3, Question #1
          File Name: BankAccount.java
       Student Name: Tim Oram
              MUN #: #########
               From: Big Java 3e
 */


/**
   A bank account has a balance that can be changed by 
   deposits and withdrawals.
 */
public class BankAccount{  
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
		balance = initialBalance;
	}

	/**
      Deposits money into the bank account.
      @param amount the amount to deposit
	 */
	public void deposit(double amount) 
	{  
		balance = balance + amount;
	}

	/**
      Withdraws money from the bank account.
      @param amount the amount to withdraw
	 */
	public void withdraw(double amount) 
	{  
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
