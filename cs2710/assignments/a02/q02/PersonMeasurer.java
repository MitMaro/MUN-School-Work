/*
  CS 2710 (Fall 2008), Assignment #2, Question #2
          File Name: PersonMeasurer.java
       Student Name: Tim Oram
              MUN #: #########
 */

// class that measures People by their height
class PersonMeasurer implements Measurer {

	// does the measuring of a person (return the height of the person)
	public double measure(Object obj) {
		Person p = (Person) obj;
		return p.getHeight();
	}

}