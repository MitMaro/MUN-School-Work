public class stringtest {
    
    public static void main(String[] args) {
    String first, second;
    first = new String("Yellow");
    second = first;
    String third = "Blue";
    System.out.println(first + " " + second + " " + " " + third);
    
    first = "Red";
    System.out.println(first + " " + second);
    
    third = first;
    second = new String("Green");
    System.out.println(first + " " + second + " " + third);
    
    }
    
}
