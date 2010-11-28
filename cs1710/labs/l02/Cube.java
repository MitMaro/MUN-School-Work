public class Cube{
    public static void main(String[] args)
    {
       double height = 3.0; //inches
       double cubeVolume = height * height * height;
       double surfaceArea = 6 * height * height;
       System.out.print("Volume = ");
       System.out.println(cubeVolume);
       System.out.print("Surface area = ");
       System.out.println(surfaceArea);
    }
}