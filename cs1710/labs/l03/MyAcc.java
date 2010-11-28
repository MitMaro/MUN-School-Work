/*
+-------------------------------------------------+
|Computer Science 1710                            |
|Lab 03 - Exercise 01                             |
|Creation Date: 01/29/07                          |
|Last Modified: 01/29/07                          |
|Version: 0.1 Revision: 001                       |
|Programmer: Tim Oram                             |
|Memorial University of Newfoundland              |
+-------------------------------------------------+
|Discription:                                     |
|   A program to use the BankAccount Class        |
+-------------------------------------------------+
|Copyright 2007 Mit Maro Productions              |
|All Rights Reserved                              |
|http://www.mitmaro.ca                            |
+-------------------------------------------------+
*/

public class MyAcc{
	public static void main(String[] args){
            // Create a bank account
            BankAccount myAccount = new BankAccount();
            
            // Deposit some money
            myAccount.deposit(1000);
            // Withdraw some moneu
            myAccount.withdraw(400);
            //Print out balance
            System.out.println("The account has a balance of: " + myAccount.getBalance());
	}
}