import java.util.*;
public class HeightOfBinaryHeap
{
    public static void main(int num)
    {
        int count=0;
        int i=0;
        while (count<=num)
        {
            count+=Math.pow(2,i);
            i++;
        }
        System.out.print("Height: " + i);
    }
}