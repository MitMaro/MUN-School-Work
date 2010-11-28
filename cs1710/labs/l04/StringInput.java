import java.util.Scanner;

public class StringInput {
    public static void main( String[] args )
    {
	Scanner scan = new Scanner(System.in);
        String english;
        System.out.println("Input the word in english that stands for 12.");
        english = scan.next();
        System.out.println("The english word is `" + english + "'" );
    } // method main
} // class StringInput
