/*
 *+-------------------+
 *|General Information|
 *+-------------------+
 *|
 *|Class Name: Sales
 *|Creation Date: March 9, 2007
 *|Last Modified: March 18, 2007
 *|Version: 0.1 Revision: 007
 *|
 *|Programmer: Tim Oram
 *|Website: http://www.mitmaro.ca
 *|
 *|For: Memorial University of Newfoundland, http://www.mun.ca
 *+-----------------------------------------------------------+
 *
 *+-----------+
 *|Description|
 *+-----------+
 *|
 *|Calculates Sales for a local retailer for the past few
 *|years.
 *+-----------------------------------------------------------+
 *
 *+------------+
 *|Legal Jargon|
 *+------------+
 *|
 *|Copyright 2007 Mit Maro Productions, All Rights Reserved
 *|
 *|Disclaimer:
 *|This source code is provided as-is, without any express or
 *|implied warranty. In no event will I the developer be held
 *|liable for any damages arising from the use or misuse of
 *|this or any part of this source code.
 *+-----------------------------------------------------------+
 *
 *+--------------+
 *|Instances List|
 *+--------------+
 *|
 *+-----------------------------------------------------------+
 *
 *+--------------+
 *|Constants List|
 *+--------------+
 *|
 *+-----------------------------------------------------------+
 *
 *+-----------------+
 *|Constructors List|
 *+-----------------+
 *|
 *+-----------------------------------------------------------+
 *
 *+------------+
 *|Methods List|
 *+------------+
 *|
 *|public static void main(String[] args);
 *+-----------------------------------------------------------+
 */

//Remove Before Submitting
//If I forget whoever is compiling this script please
//remove the package line.
//package A05;

/**
 *
 * @author 	Tim Oram
 * @version	0.1.007
 */

//Import scanner library
import java.util.Scanner;

public class Sales {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        // Number of years
        int numYears = 0;
        // Holds the number of times total sales exceeds $100,000
        int ExceedCount = 0;
        // Amount of money made per year
        double SalesAmount = 0;
        // Amount of money made total
        double SalesAmountTotal = 0;
        
        //Input number of years
        System.out.println("Input number of years:");
        numYears = sc.nextInt();
        
        //Loop thourgh each year
        for(int i1 = 0; i1 < numYears; i1++){
            //Loop thorugh each month
            for (int i2 = 0; 12 > i2; i2++){
                
                //Get amount of sales made this month
                System.out.println("Input Sales for month " + i2);
                //Add it too yearly sales
                SalesAmount += sc.nextDouble();
                
            }
            //Add this years sales to the total 
            SalesAmountTotal += SalesAmount;
            
            //Print out the sales this year
            System.out.println("Total Sales for year " + SalesAmount);
            
            //If this years sales is greater then 100000 then....
            if(100000 < SalesAmount){
                //...increase the counter
                ExceedCount++;
            }            
            
            //Reset the value of sales amount for the next year
            SalesAmount = 0;
            
        }
        //Print out the total sales for all years
        System.out.println("Total Sales for all years: " + SalesAmountTotal);
        
        //Print out how many years sales were greater then $100,000
        System.out.println("Number of years in which sales were greater then $100,000: " + ExceedCount);
        
    }
    
}