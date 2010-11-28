/*
  CS 2710 (Fall 2008), Assignment #3, Question #4
          File Name: BankAccount.java
       Student Name: Tim Oram
              MUN #: #########
               From: Big Java 3e (modified)
 */

import java.util.Comparator;


/**
   A bank account has a balance that can be changed by 
   deposits and withdrawals. 
 */
public class BankAccount implements Comparable<BankAccount> { 
	
	// holds this bank accounts balance and account number
	private double balance;
	private int accountNumber; 
	
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
		this.balance = initialBalance;
		this.accountNumber = 0;
	}
	/**
   		Constructs a bank account with a given balance and account number.
	 */
	public BankAccount(int accountNumber, double initialBalance)
	{  
		this.balance = initialBalance;
		this.accountNumber = accountNumber;
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
      Gets the current balance of the bank account.
      @return the current balance
	 */
	public int getAccountNumber()
	{  
		return this.accountNumber; 
	}
	
	public void setAccountNumber(int number){
		this.accountNumber = number;
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
	/**
	  Compares a BankAccount to this bank account
	 */
	
	public int compareTo(BankAccount b){
		return (int)(b.getBalance() - this.getBalance());
	}
	
}
