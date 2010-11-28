
public class AssociativityTest {
    public static void main( String[] args )
    { 
        // note: x = (byte) y [converts y into a byte]
        byte a = (byte)5;
        byte b = (byte)3;
        byte c = (byte)2;

        byte x = (byte) (a * b);

        byte lhs = (byte) (x / c);
        System.out.println("LHS = "  + lhs );

        byte y = (byte) (b / c);
        byte rhs = (byte) (a * y );
        System.out.println("RHS = "  + rhs );
    } // method main
} // class AssociativityTest
