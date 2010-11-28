/*
+-------------------------------------------------+
|Computer Science 1710                            |
|Class Name: Vending Machine                      |
|Creation Date: 00/00/07                          |
|Last Modified: 00/00/07                          |
|Version: 0.0 Revision: 000                       |
|Programmer: Tim Oram                             |
|Memorial University of Newfoundland              |
+-------------------------------------------------+
|Discription:                                     |
|   A class that discribes a vending machine      |
+-------------------------------------------------+
|Copyright 2007 Mit Maro Productions              |
|All Rights Reserved                              |
|http://www.mitmaro.ca                            |
+-------------------------------------------------+
 */

public class VendingMachine{
    
    /* Add variables here... */
    
    private int intCans;
    private int intTokens;
    
    /*...*/
    
    // Constructor
    public VendingMachine(){
        intCans = 10;
        intTokens = 0;
    }
    // Overloaded Constructor
    public VendingMachine(int intStartCans){
        intCans = intStartCans;
        intTokens = 0;
    }
    
    // Allows the adding of additional cans
    public void addCans(int intCansToAdd){
        intCans += intCansToAdd;
    }
    
    // Allows the buying of a can.
    public void buyCan(){
        intCans -= 1;
        intTokens += 1;
    }
    
    // Returns the number of cans remaining
   public int getCansRemaining(){
       return intCans;
   }
   
   // Returns the number of tokens
   public int getTokens(){
       return intTokens;
   }
}
