/*
+-------------------------------------------------+
|Computer Science 1710                            |
|Lab 01 - Exercise 2.1                            |
|Creation Date: 01/29/07                          |
|Last Modified: 01/29/07                          |
|Version: 0.1 Revision: 001                       |
|Programmer: Tim Oram                             |
|Memorial University of Newfoundland              |
+-------------------------------------------------+
|Discription:                                     |
|   Test program for the vending machine class    |
+-------------------------------------------------+
|Copyright 2007 Mit Maro Productions              |
|All Rights Reserved                              |
|http://www.mitmaro.ca                            |
+-------------------------------------------------+
*/

public class BuyCans{
    public static void main(String[] args)
    {
       VendingMachine machine = new VendingMachine();
       machine.addCans(10); // fill up with ten cans
       machine.buyCan();
       machine.buyCan();
       System.out.println("Token count = " + machine.getTokens());
       System.out.println("Can count = " + machine.getCansRemaining());
    } 
}