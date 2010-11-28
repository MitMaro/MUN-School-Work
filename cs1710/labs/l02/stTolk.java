
import java.util.StringTokenizer;

public class stTolk {

   public static void main(String[] args)
    {
       String sentence = "Mary had a little lamb.";
       StringTokenizer mystery = new StringTokenizer(sentence);
       System.out.println(mystery.countTokens());
       System.out.println(mystery.nextToken());
       System.out.println(mystery.nextToken());   
    }

    
}
