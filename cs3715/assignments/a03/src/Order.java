/*
Computer Science 3715
Assignment #3
Pizza Order

By: Tim Oram
Student Number: #########
*/

import java.net.URLDecoder;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


class Order {
	
	// a pizza
	static Pizza pizza;
	
	// persons name a phone number
	static String name;
	static String phone;
	
	// parse the query string and fill in the variables
	public static void parseQuery(String query) throws Exception {
		
		String[] key_value_pairs;
		
		// split on the ampersand sign
		String[] tmps = query.split("&");
		
		// search for pizza size first
		for (String t: tmps) {
			key_value_pairs = t.split("=");
			if (key_value_pairs[0].equals("size")) {
				pizza = new Pizza(key_value_pairs[1]);
				break;
			}
		}
		
		// no size, no pizza, can not go any further
		if (pizza == null) {
			return;
		}
		
		// for each key value pair
		for (String t: tmps) {
			// split the pair into two strings
			key_value_pairs = t.split("=");
			if(key_value_pairs[0].equals("name")) {
				name = URLDecoder.decode(key_value_pairs[1], "UTF-8");
			}
			// persons phone number
			else if(key_value_pairs[0].equals("phone")) {
				phone = URLDecoder.decode(key_value_pairs[1], "UTF-8");
			}
			// if an ingredient
			else if(key_value_pairs[0].equals("ingredient")) {
				pizza.addIngredient(URLDecoder.decode(key_value_pairs[1], "UTF-8"));
			}
		}
	}
	
	
	// log the order to a file called orders.txt
	public static void logOrder() throws Exception {
		// create file lock
		FileOutputStream os = new FileOutputStream("orders.txt", true);
		FileChannel chan = os.getChannel();
		FileLock lock = chan.lock();
		
		os.write(("name=" + name + "\n").getBytes());
		os.write(("phone=" + phone + "\n").getBytes());
		os.write(("total=" + pizza.getTotalCost() + "\n").getBytes());
		os.write(("size=" + pizza.getSize() + "\n").getBytes());
		for (String in: pizza.getIngredients()) {
			os.write((in + "\n").getBytes());
		}
		os.write(("-----------------------------------------\n").getBytes());
		
		// release file
		lock.release();
		chan.close();
	}
	
	// load a template file into a string
	public static String loadTemplate(String filename) throws Exception {
		// make a byte array the size of the file
		byte[] buffer = new byte[(int)new File(filename).length()];
		
		// open, read and close file
		FileInputStream f = new FileInputStream(filename);
		f.read(buffer);
		f.close();
		return new String(buffer);
	}
	
	// load the order template file
	public static String loadOrderTemplate() throws Exception {
		return loadTemplate("./order.html");
	}
	
	// load and parse the confirm order page
	public static String loadConfirmTemplate() throws Exception {
		String template = loadTemplate("./confirm.html");
		
		// first replace size
		template = template.replaceAll("\\{size\\}", pizza.getSize());
		
		// and total cost
		template = template.replaceAll("\\{total\\}", pizza.getTotalCost());
		
		// the hard one, the ingredients block
		Pattern p = Pattern.compile("\\{block toppings\\}(.*)\\{/block\\}");
		Matcher m = p.matcher(template);
		
		while (m.find()) {
			String topping_template = m.group(1);
			
			String html = "";
			
			for (String ingredient: pizza.getIngredients()) {
				html += topping_template.replaceAll("\\{ingredient\\}", ingredient);
			}
			template = template.replaceAll(Pattern.quote(m.group()), html);
			
		}
		
		return template;
	}
	
	// load and parse the final page
	public static String loadFinalTemplate() throws Exception {
		String template = loadTemplate("final.html");
		
		// first replace size
		template = template.replaceAll("\\{size\\}", pizza.getSize());
		
		// and total cost
		template = template.replaceAll("\\{total\\}", pizza.getTotalCost());
		
		// and name
		template = template.replaceAll("\\{name\\}", name);
		
		// and phone number
		template = template.replaceAll("\\{phone\\}", phone);
		
		// the hard one, the ingredients block
		Pattern p = Pattern.compile("\\{block toppings\\}(.*)\\{/block\\}");
		Matcher m = p.matcher(template);
		
		// find and replace the blocks
		while (m.find()) {
			String topping_template = m.group(1);
			
			String html = "";
			
			for (String ingredient: pizza.getIngredients()) {
				html += topping_template.replaceAll("\\{ingredient\\}", ingredient);
			}
			template = template.replaceAll(Pattern.quote(m.group()), html);
			
		}
		
		return template;
	}
	
	public static void main(String[] args) throws Exception {
		// determine what paramaters were sent
		
		String qs = System.getenv("QUERY_STRING");
		
		if (qs != null & !qs.equals("")) {
			parseQuery(qs);
		}
		
		// content type is required
		System.out.print("Content-Type: text/html\r\n\r\n");
		
		// pizza is null, we haven't done anything yet
		if (pizza == null) {
			// no data to handle just show order page
			System.out.println(loadOrderTemplate());
		}
		// pizza is not null and name is, we need to confirm
		else if (name == null) {
			// show the confirm page
			System.out.println(loadConfirmTemplate());
		}
		// final stage
		else {
			logOrder();
			// show the final page
			System.out.println(loadFinalTemplate());
		}
	}
}