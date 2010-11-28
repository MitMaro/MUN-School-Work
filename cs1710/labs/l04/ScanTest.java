import java.util.Scanner;
public class ScanTest {
    private static final int FEETINYARDS = 3;
    public static void main(String args[]){
        
        Scanner in = new Scanner(System.in);

        System.out.println("Enter a number: ");
        int number = in.nextInt();
        System.out.println("Your number was: " + number);
    }
    
}
