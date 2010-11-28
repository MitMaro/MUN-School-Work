/*
  CS 2710 (Fall 2008), Assignment #3, Question #5
          File Name: Bank.java
       Student Name: Tim Oram
              MUN #: #########
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


// describes a bank of BankAccounts
public class Bank {
	private ArrayList<BankAccount> accounts;

	// setup the bank
	public Bank(){
		this.accounts = new ArrayList<BankAccount>();
	}

	// reads a file and adds bank account
	public boolean readFile(String filepath){
		try{
			ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
			BufferedReader input =  new BufferedReader(new FileReader(filepath));
			String line = null; //not declared within while loop
			String[] account_info;
			
			// read lines
			while (( line = input.readLine()) != null){
				
				// split line up
				account_info = line.split("\\s");
				
				// throw an error is not right result
				if(account_info.length != 2) throw new IOException("Format Error");
				
				// add an account
				int accountNumber = Integer.parseInt(account_info[0]);
				double accountBalance = Double.parseDouble(account_info[1]);
				accounts.add(new BankAccount(accountNumber, accountBalance));
				
			}
			// add all accounts parsed to the bank
			this.accounts.addAll(accounts);
			return true;
		}
		// some errors
		catch(FileNotFoundException ex){
			System.out.println("File Not Found: " + filepath);
		}
		catch(IOException ex){
			System.out.println("An error occured while parsing the file: " + ex.getMessage());
		}
		catch(NumberFormatException ex){
			System.out.println("An number format error occured while parsing the file.");
		}
		return false;
	}

	// prints the greatest balance
	public void printGreatestBalance(){
		// fort accounts by balance
		Collections.sort(this.accounts);
		
		// print the greatest account
		if(this.accounts.size() > 0){
			System.out.println("Highest Account: " + this.accounts.get(0).getAccountNumber() + "   Balance: " + this.accounts.get(0).getBalance());
		}	
	}
	
}
