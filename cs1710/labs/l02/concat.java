
public class concat {
     public static void main(String[] args)
    {
       String animal1 = "quick brown fox";
       String animal2 = "lazy dog";
       String article = "the";
       String action = "jumps over";

       String strFull = article.concat(" " + animal1) + action.concat(" " + animal2);
       System.out.println(strFull);
       System.out.println("Length: " + strFull.length());
   
    }
    
}
