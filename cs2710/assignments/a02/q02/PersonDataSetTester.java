/*
  CS 2710 (Fall 2008), Assignment #2, Question #2
          File Name: PersonDataSetTester.java
       Student Name: Tim Oram
              MUN #: #########
 */

// test class for a dataset of persons
public class PersonDataSetTester {

	// the main method. Tests the dataset class on a group of persons
	public static void main(String[] args) {

		// create a Person Measurer
		Measurer m = new PersonMeasurer();

		// create a dataset
		DataSet data = new DataSet(m);

		// add some data (people)
		data.add(new Person("Charles Babbage", 176));
		data.add(new Person("Alan Turing", 164));
		data.add(new Person("Alonzo Church", 180));
		data.add(new Person("Claude Elwood Shannon", 156));
		data.add(new Person("Grace Hopper", 159));

		// get the person with the maximum height
		Person max = (Person) data.getMaximum();

		// print out the average height
		System.out.println("Average Height: " + data.getAverage() + " cm");
		System.out.println("Expected: 167.0 cm\n");

		// print out the tallest person
		System.out.println("Tallest Perons: " + max.toString());
		System.out.println("Expected: Alonzo Church is 180.0 cm tall.");
	}
}
