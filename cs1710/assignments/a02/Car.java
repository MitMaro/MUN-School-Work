/*
+-------------------+
|General Information|
+-------------------+
|
|Computer Science 1710
|Class Name: Car
|Creation Date: 01/29/07
|Last Modified: 02/07/07
|Version: 0.1 Revision: 019
|
|Programmer: Tim Oram
|Website: http://www.mitmaro.ca
|
|For: Memorial University of Newfoundland, http://www.mun.ca
+-----------------------------------------------------------+

+-----------+
|Description|
+-----------+
|
|A class that describes the fuel efficiency of a car. Allows
|a user|to create a car with a fuel efficiency, add gas to
|the tank, simulate driving, and return the amount of gas
|remaining. The class can also output the distance traveled
|and the amount of gas burned. The class is not unit
|specific, you can use km/liter, miles/gal, etc...
+-----------------------------------------------------------+

+------------+
|Legal Jargon|
+------------+
|
|Copyright 2007 Mit Maro Productions, All Rights Reserved
|
|Disclaimer:
|This source code is provided as-is, without any express or
|implied warranty. In no event will I the developer be held
|liable for any damages arising from the use or misuse of
|this or any part of this source code.
+-----------------------------------------------------------+

+--------------+
|Instances List|
+--------------+
|
|private double dblEfficiency;
|private double dblDistance;
|private double dblGasBurnt;
|private double dblGasLevel;
+-----------------------------------------------------------+

+--------------+
|Constants List|
+--------------+
|
|
+-----------------------------------------------------------+

+-----------------+
|Constructors List|
+-----------------+
|
|public Car(double dblEff)
+-----------------------------------------------------------+

+------------+
|Methods List|
+------------+
|
|public void addGas(double dblAmount)
|public void drive(double dblDist)
|public double getGas()
|public double getGasBurnt()
|public double getDistTraveled()
|public double getDistPossible()
+-----------------------------------------------------------+
*/

/**
Describes the fuel efficiency of a car. Allows a user to create a car with a fuel efficiency, add gas to the tank, simulate driving, and return the amount of gas remaining. The class can also output the distance traveled and the amount of gas burned. The class is not unit specific, you can use km/liter, miles/gal, etc... 
@author 	Tim Oram
@version	0.1.019
*/

public class Car{
	
	/* Instance Fields */
	
	// Used double to allow non-integer values, which is very possible in the real world.
	/** Stores efficiency of car */
	private double dblEfficiency;
	/** Stored total distance traveled */
	private double dblDistance;
	/** Amount of total gas burned */ 
	private double dblGasBurnt;
	/** Amount of gas remaining in gas tank */
	private double dblGasLevel;

	/*...*/
	
	
	/* Constants */
	
	/*...*/
	
	/**
	Constructor for the Car Class.
	@param dblEff The Efficiency of the car
	*/
	public Car(double dblEff){
		dblEfficiency = dblEff;
		dblDistance = 0.0;
		dblGasBurnt = 0.0;
		dblGasLevel = 0.0;
	}
	
	
	
	/**
	Simulate adding gas to the cars gas tank.
	@param dblAmount The amount of gas to add
	*/
	public void addGas(double dblAmount){
		dblGasLevel += dblAmount;
	}
	
	/**
	Simulate driving the car.
	@param dblDist The distance to travel
	*/
	public void drive(double dblDist){
		dblDistance += dblDist; // add distance
		dblGasBurnt += dblDist / dblEfficiency; // add gas burnt
		dblGasLevel -= dblDist / dblEfficiency; // subtract gas from tank
	}
	
	
	
	/**
	Gets the amount of gas remaining in the gas tank of the car.
	@return Amount of gas in tank
	*/
	public double getGas(){
		return dblGasLevel;
	}
	
	/**
	Gets the amount of gas burned by the car in its lifetime.
	@return Amount of gas used
	*/
	public double getGasBurnt(){
		return dblGasBurnt;
	}
	
	/**
	Gets the total distance traveled by the car in its lifetime.
	@return The distance traveled
	*/
	public double getDistTraveled(){
		return dblDistance;
	}
	
	/**
	Gets the distance that car can travel with the current amount of gas in the tank.
	@return The distance possible
	*/
	public double getDistPossible(){
	
		//Distance Possible = cars efficiency * Amount of gas in tank
		return dblEfficiency * dblGasLevel;
	}
	
}

// TEMPLATE VERSION 0.3 (02-07-07)