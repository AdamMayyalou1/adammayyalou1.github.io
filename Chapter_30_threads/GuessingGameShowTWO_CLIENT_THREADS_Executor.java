// ---------------------------------------------------------------------------------------------------------------------------
/**  GuessingGameShow_TWO_CLIENT_THREADS_Executor
// ---------------------------------------------------------------------------------------------------------------------------
 *  GuessingGameShow_TWO_CLIENT_THREADS_Executor         
 * 
 *  >>> See Liang Book 10th Edition Sec 30.6 Thread Pools <<<
 *   
 *     CLIENT:      (Same as GuessingGameShowONE_CLIENT)
 *                  3 GuessingGameShow_Player's are trying to guess a number 1-max,
 *                  and see who is the fastest at getting it right.  With each guess,
 *                  the player hits a delay/sleep for a random amount of time. 
 *                  
 *     Directions:   (New Part)
 *                  (1) Create an ExecutorService and put numberOfPlayers (10) threads into it, 
 *                       remembering that they run automatically.       
 *                  (2) Create threadArray with size numberOfPlayers and put each 
 *                       GuessingGameShow_PlayerThread into it. 
 *                  (3) After the guessing is all done, sort threadArray via a
 *                       threadGuessCount_Comparator() you design that compares each 
 *                       GuessingGameShow_PlayerThread's GuessCount accordingly.
 *          
 *    Sample OUTPUT at the very bottom.      
 */
import java.util.concurrent.*;
import java.util.*;

public class GuessingGameShowTWO_CLIENT_THREADS_Executor
{
    public static void main()
    {    
        int max=1000000;
        int numberOfPlayers = 10;
        int n=0;        
        GuessingGameShow_PlayerThread p;  //REM: GuessingGameShow_PlayerThread implements Runnable ...
        Thread t;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfPlayers);
        GuessingGameShow_PlayerThread[] threadArray = new GuessingGameShow_PlayerThread[numberOfPlayers];
        for (int i=0; i<numberOfPlayers; i++)
        {
            n = (int) (Math.random()*(max-1)+1);
            p = new GuessingGameShow_PlayerThread("P"+(i+1),n,max);   
            t = new Thread(p);  // ... so 'p' can be put into a Thread.

            // Put p into 'threadArray'
            threadArray[i] = p;

            // Put t into the 'executor'
            executor.execute(t);

        }  // i   

        // Stop all threads via the 'shutdown()' method
        // >>>>> YOUR CODE HERE <<<<<       
        while(!executor.isTerminated()) {
            // EMPTY! - This is to stall/wait for all threads to stop
        }        

        System.out.println("\n\n\t Sort and Print threadArray: ");       
        Arrays.sort(threadArray, new threadGuessCount_Comparator()); 

        for (GuessingGameShow_PlayerThread gpt : threadArray)
        {
            System.out.println("Name = " + gpt.getName() + ": " + 
                "ActualNumber = " + gpt.getActualNumber() + "   " + 
                "GuessNumber = " + gpt.getGuessNumber() + "   " +
                "GuessCount = " + gpt.getGuessCount() + "   " +                               
                "gpt/this = " + gpt);
        }      

        System.exit(0);

    } // main

}  //  GuessingGameShow_CLIENT_THREADS_Executor_SHELL
