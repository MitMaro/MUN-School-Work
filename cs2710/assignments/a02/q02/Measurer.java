/*
  CS 2710 (Fall 2008), Assignment #2, Question #2
          File Name: Measurer.java
       Student Name: Tim Oram
              MUN #: #########
               From: Big Java 3e
*/

/**
   Describes any class whose objects can measure other objects.
*/
public interface Measurer
{
	/**
	 * Computes the measure of an object.
	 * 
	 * @param anObject
	 *            the object to be measured
	 * @return the measure
	 */
	double measure(Object anObject);
}
