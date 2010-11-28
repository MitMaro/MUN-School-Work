/*
  CS 2710 (Fall 2008), Assignment #3, Question #3
          File Name: Main.java
       Student Name: Tim Oram
              MUN #: #########
 */


// tests the BankAccount class
public class Main {

	public static void main(String[] args) {
		
		// try making a invalid BankAccount and catch error
		try{
			BankAccount b1 = new BankAccount(-1);
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
		}

		// try depositing a invalid amount and catch error
		try{
			BankAccount b1 = new BankAccount(100);
			b1.deposit(-1);
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
		}

		// try withdrawing a invalid amount and catch error
		try{
			BankAccount b1 = new BankAccount(100);
			b1.withdraw(-1);
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
		}
		
		// try withdrawing an amount more then current balance and catch error
		try{
			BankAccount b1 = new BankAccount(100);
			b1.withdraw(101);
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
		}
	}

}
