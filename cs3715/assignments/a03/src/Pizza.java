/*
Computer Science 3715
Assignment #3
Pizza Order

By: Tim Oram
Student Number: #########
*/

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;

class Pizza {
	
	// the size of the pizza
	public String size;
	
	// total cost of the pizza
	public double total_cost;
	
	// cost of each ingredient
	public double ingredient_cost;
	
	// an ArrayList of ingredients
	protected ArrayList<String> ingredients = new ArrayList<String>();
	
	// make a pizza of a size "small", "medium" or "large"
	public Pizza(String size) {
		size = size.trim().toLowerCase();
		
		// minor data validation on the size
		if (size.equals("small") || size.equals("large")) {
			this.size = size;
		}
		else {
			this.size = "medium";
		}
		
		// update the total_cost with the pizza base price and set the ingredient
		// price
		if (this.size.equals("small")) {
			this.total_cost = 4.00;
			this.ingredient_cost = 0.50;
		}
		else if (this.size.equals("medium")) {
			this.total_cost = 6.00; 
			this.ingredient_cost = 0.75;
		}
		else if (this.size.equals("large")) {
			this.total_cost = 8.00;
			this.ingredient_cost = 1.00;
		}
	}
	
	// add a ingredient to the pizza also increases the cost
	public void addIngredient(String name) {
		name = name.trim();
		
		// stop double insertions. Giggity
		if (!this.ingredients.contains(name)) {
			this.ingredients.add(name);
			this.total_cost += this.ingredient_cost;
		}
		
	}
	
	// returns the size of the pizza
	public String getSize() {
		return this.size;
	}
	
	// returns the total cost as a string
	public String getTotalCost() {
		return String.format("%.2f", this.total_cost);
	}
	
	// returns an array of ingredients in the pizza
	public String[] getIngredients() {
		return this.ingredients.toArray(new String[0]);
	}
}