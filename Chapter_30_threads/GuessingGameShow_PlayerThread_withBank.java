import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// ******************************************************************
// ************************* INNER CLASSES  ************************* 
// ******************************************************************    
/**
 * GuessingGameShow_PlayerThread_withBank:
 *  Has all the same fields as GuessingGameShow_PlayerThread as well as:
 *  (1) Has 2 ints 'playersBank' and 'playersBet' and static Bank (see below) object named 'bigBank'
 *      constructed with 100 (dollars).  It is static because no one player owns it.  Instead ALL of the 
 *       players need to see it.
 *  (2) Is constructed the same as GuessingGameShow_PlayerThread and is passed player's startingBank.
 *  (3) Has the get() methods: getName(), getGuessNumber(), getGuessCount(), getActualNumber(),
 *                             getPlayersBet(), & getPlayersBank()
 *  (4) Has 2 other methods: addToPlayersBank(int) and subtractFromPlayersBank(int)
 *  (5) The 'run()' has the addition of gameplay via the bigBank and the playersBet:
 *         - The playersBet = random number 1-100, turned into a decimal,
 *             and then taken as a percentage of the playersBank. 
 *           Ex.   r = 30    30/100 = 0.30    So playersBet = 0.30*playersBank
 *                       See Bank class below for its part.          
 *           NOTE:
 *                  (1) if playersBet=0 OR playersBank=1 then playersBet = 1 
 *                  (2) if bigBank.getBankBalance()==0 then GameOver for all, stop all threads,
 *                       and print out which threads were still running via a threadSet:
 *                                    Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

 *         - Calls the shared bigBank's bet method passing in the object itself.   
 */                                    
public static class GuessingGameShow_PlayerThread_withBank implements Runnable {

    private String name;
    private int actualNum;
    private int guessNum;
    private int guessCount = 0;
    private int low = 1;
    private int high;

    public GuessingGameShow_PlayerThread_withBank(String thatName, int thatNum, int thatHigh) {
        name = thatName;
        actualNum = thatNum;
        high = thatHigh;
    }

    public String getName() 
    {
        return name;
    }

    public int getActualNumber() {
        return actualNum;
    }

    public int getGuessNumber() {
        return guessNum;
    }

    public int getGuessCount() {
        return guessCount;
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

class threadGuessCount_Comparator implements Comparator<GuessingGameShow_PlayerThread> {
    public int compare(GuessingGameShow_PlayerThread p1, GuessingGameShow_PlayerThread p2) {
        return Integer.compare(p1.getGuessCount(), p2.getGuessCount());
    }
}