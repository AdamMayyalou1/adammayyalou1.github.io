// GuessingGameShow_PlayerThread  and GuessingGameShowONE_CLIENT

// GuessingGameShow_PlayerThread  and GuessingGameShowONE_CLIENT_SHELL
import java.util.*;
/**
 * GuessingGameShow_PlayerThread:  A GameShowPlayer keeps guessing for a number, 1-max, with higher or lower, it adjusts accordingly,
 *
 *  It must do the following:
 *  (1)'implements' the Runnable Interface  
 *  (2) Has each of the following fields:   
 *      1 String for the name
 *      3 ints for the actualNumber, guessNumber, and guessCount
 *      2 ints for low + high 
 *  (3) Has a constructor to assign each of the fields above.
 *  (4) run():
 *          * Have a loop 'while (guessNumber!=actualNumber)' that keeps trying to guess the actualNumber via a binary guess approach.  
 *          *  Output:  
 *                 - name, guessNumber, guessCount, and clue (too LOW, too HIGH), and the thread itself               
 *  (5) Finally, when it gets it right, Output the final guessCount and the actualNumber
 */

class GuessingGameShow_PlayerThread implements Runnable
{
    private String name;   //name
    private int actualNum;     // the times to repeat
    private int guessNum;
    private int guessCount=0;
    private int low=1;
    private int high;

    // thread class constructor
    public GuessingGameShow_PlayerThread(String thatName, int thatNum, int thatHigh) 
    {
        name=thatName;
        actualNum=thatNum;
        high=thatHigh;
    }

    public void run()
    {
        do
        {
            guessNum=(high+low)/2;
            guessCount++;
            
            String finalString=name + ": guessNumber = " + guessNum + "\t guessCount = " + guessCount;
            
            if (guessNum>actualNum)
            {
                high=guessNum;
                finalString += " too HIGH!";
            }
            else if (guessNum<actualNum)
            {
                low=guessNum;
                finalString += " too LOW!";
            }
            else
            {
                finalString += " You Win!";
            }
            
            finalString += "\t Thread: " + this;
            if (guessNum==actualNum)
            {
                finalString += "\n";
            }
            
            System.out.println(finalString);
        } while (guessNum!=actualNum);
    }
}