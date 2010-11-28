/*
  CS 2710 (Fall 2008), Assignment #3, Question #4
          File Name: Main.java
       Student Name: Tim Oram
              MUN #: #########
 */

// tests the BankAccount class
public class Main {

	public static void main(String[] args) {
		// try creating a invalid BankAccount and catch error
		try{
			BankAccount b1 = new BankAccount(-1);
		}
		catch(NegativeBalanceException ex){
			System.out.println(ex.getMessage() + ". Balance Given: " + ex.getBalance());
		}
		
		// deposit an invalid amount and catch error
		try{
			BankAccount b1 = new BankAccount(100);
			b1.deposit(-1);
		}
		catch(NegativeDepositException ex){
			System.out.println(ex.getMessage() + ". Deposit Given: " + ex.getDeposit());
		}
		
		// try withdrawing a amount greater then current balance and catch error
		try{
			BankAccount b1 = new BankAccount(100);
			b1.withdraw(101);
		}
		catch(InvalidWithdrawException ex){
			System.out.println(ex.getMessage() + ". Withdraw Given: " + ex.getWithdraw());
		}
	}

}
