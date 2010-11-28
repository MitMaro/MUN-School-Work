/*
+-------------------------------------------------+
|Computer Science 1710                            |
|Class Name: Savings Account                      |
|Creation Date: 01/29/07                          |
|Last Modified: 01/29/07                          |
|Version: 0.1 Revision: 001                       |
|Programmer: Tim Oram                             |
|Memorial University of Newfoundland              |
+-------------------------------------------------+
|Discription:                                     |
|   A class that discribes a Savings Account      |
+-------------------------------------------------+
|Copyright 2007 Mit Maro Productions              |
|All Rights Reserved                              |
|http://www.mitmaro.ca                            |
+-------------------------------------------------+
 */

public class SavingsAccount{
    
    /* Add variables here... */
    
    private double balance;
    
    /*...*/
    
    // Constructor
    public SavingsAccount(){
        balance = 0;
    }
    
    // Constructor
    public SavingsAccount(double dblInitialBalance, double dblInteresst) {
        balance = dblInitialBalance;
    }
    
    public void deposit(double dblAmount) {
        balance += dblAmount;
    }
    
    public void withdraw(double dblAmount) {
        balance -= dblAmount;
    }
    
    public double getBalance() {
        return balance;
    }
}