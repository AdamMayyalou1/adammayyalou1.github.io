//  SHELL_GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses:
//   Play the Guessing game as before only this time the players make random bets each time
//     from a shared (static) Bank object.  
//   (1) I give you the main(), you complete the 2 Inner Classes:
//          Bank and GuessingGameShow_PlayerThread_withBank     
//   (2) You write threadPlayersBank_Comparator

//  Possible OUTPUT at bottom of program

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class SHELL_GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses
{
    public static void main()
    {    
        int max=1000;
        int numberOfPlayers = 3;
        int playersActualNumber;        
        int playersStartingBank = 100;
        Thread t;
        GuessingGameShow_PlayerThread_withBank p;  
        ExecutorService executor = Executors.newFixedThreadPool(numberOfPlayers);
        GuessingGameShow_PlayerThread_withBank threadArray[] =
            new GuessingGameShow_PlayerThread_withBank[numberOfPlayers];

        for (int i=1; i<=numberOfPlayers; i++)
        {
            playersActualNumber = (int) (Math.random()*max+1);
            p = new GuessingGameShow_PlayerThread_withBank("P"+i,playersActualNumber,max,playersStartingBank);   
            t = new Thread(p);  // ... so 'p' can be put into a Thread.            
            threadArray[i-1] = p; 
            executor.execute(t);            
        }  // i   

        // Stop all threads but also ...
        executor.shutdown();   
        while(!executor.isTerminated()) {
            // ... stall / wait for all threads to stop
        }        

        System.out.println("\n\n\t Sort and Print threadArray: ");       
        Arrays.sort(threadArray, new threadPlayersBank_Comparator()); 

        for (GuessingGameShow_PlayerThread_withBank gpt : threadArray)
        {
            System.out.println("Name = " + gpt.getName() + ": " + 
                "ActualNumber = " + gpt.getActualNumber() + "   " + 
                "GuessNumber = " + gpt.getGuessNumber() + "   " +
                "GuessCount = " + gpt.getGuessCount() + "   " +   
                "PlayersBank = " + gpt.getPlayersBank() + "   " + 
                "gpt/this = " + gpt);
        }      
        System.exit(0);

    } // main    

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
    public static class GuessingGameShow_PlayerThread_withBank implements Runnable
    {
        private static final Bank bigBank = new Bank(1000);
        private final String name;
        private final int actualNumber;
        private final int maxNumber;
        private int guessNumber;
        private int guessCount;
        private int playersBank;
        private int playersBet;

        public GuessingGameShow_PlayerThread_withBank(String name, int actualNumber, int maxNumber, int playersBank)
        {
            this.name = name;
            this.actualNumber = actualNumber;
            this.maxNumber = maxNumber;
            this.playersBank = playersBank;
        }

        public String getName() { 
            return name; 
        }

        public int getActualNumber() { 
            return actualNumber; 
        }

        public int getGuessNumber() { 
            return guessNumber; 
        }

        public int getGuessCount() { 
            return guessCount; 
        }

        public int getPlayersBet() { 
            return playersBet; 
        }

        public int getPlayersBank() { 
            return playersBank; 
        }

        public void addToPlayersBank(int amount) { 
            playersBank += amount; 
        }

        public void subtractFromPlayersBank(int amount) { 
            playersBank -= amount; 
        }

        public void run()
        {
            while (guessNumber != actualNumber && playersBank > 0 && bigBank.getBankBalance() > 0)
            {
                guessCount++;
                guessNumber = (int) (Math.random() * maxNumber) + 1;

                // Calculate playersBet
                double betPercentage = (Math.random() * 100) / 100.0;
                playersBet = (int) (betPercentage * playersBank);
                if (playersBet == 0) playersBet = 1;

                // Make the bet
                bigBank.bet(this);

                // Check if guess is too high or too low
                if (guessNumber < actualNumber) {
                    System.out.println(name + ": Guess too LOW! You lose " + playersBet + " playersBank = " + playersBank);
                } else if (guessNumber > actualNumber) {
                    System.out.println(name + ": Guess too HIGH! You lose " + playersBet + " playersBank = " + playersBank);
                } else {
                    System.out.println(name + ": CORRECT! You won $" + playersBet + "!" + " playersBank = " + playersBank);
                }
            }

            if (bigBank.getBankBalance() == 0) {
                System.out.println(">>>>> bigBank.getBankBalance() == 0!!! Game OVER!!! Thread: " + this);
            }
            System.out.println(">>>>> " + name + ": Your guessCount = " + guessCount +
                " actualNumber = " + actualNumber + " playersBank = " + playersBank);
        }
    }

    /**
     * Bank:
     * (1) Has a shared Lock that all player's can see.
     * (2) Has it's own 'bankBalance'
     * (3) Has one constructor that initializes its 'bankBalance'
     * (4) Turns the Lock on/off as required to guard its 'bankBalance'
     * (5) Has percentageBet = playersBet / playersBank;
     * (6) Bank's bet():
     * (a) Accepts the player as a parameter
     * (b) Check's player's numbers and either adds/subtracts to 'playersBank'
     * and 'bank' accordingly VIA the percentageBet calculated.
     * Ex   playersBet = $30  playersBank = $100  percentageBet = 0.30
     * bank.bankBalance = $1000
     * Case 1: player match/wins
     * playersBank += $30   AND bigBank.bankBalance -= $30
     * Thus, playersBank = $130 AND bigBank.bankBalance = $970
     * and the other players will keep trying to win a percentage of
     * whatever is left over in bigBank, $970.
     * Case 2: player does not match/looses
     * playersBank -= $30 AND bank.bankBalance += $30
     * Thus, playersBank = $70 AND bank.bankBalance = $1030
     * 
     * Thus, the players most likely will be loosing until they hit/win or they
     * run out of money, or perhaps the bigBank runs out of money!
     * So if at any point playersBank = $0 then their game is
     * over and their Thread should stop.
     *       
     **/

    public static class Bank
    {
        private static Lock lock = new ReentrantLock();  
        private int bankBalance;

        public Bank(int b)
        {
            bankBalance = b;
        }

        public int getBankBalance()
        {
            return bankBalance;
        }

        public void bet(GuessingGameShow_PlayerThread_withBank p)
        {
            lock.lock();
            double percentageBet = p.getPlayersBet() / p.getPlayersBank();
            if (p.getGuessNumber()==p.getActualNumber())
            {
                p.addToPlayersBank((int)(percentageBet*bankBalance));
                bankBalance -= (percentageBet*bankBalance);
            } else {
                p.subtractFromPlayersBank((int)p.getPlayersBet());
                bankBalance += p.getPlayersBet();                
            }
            lock.unlock();
        } // bet 
    } // Bank

    public static class threadPlayersBank_Comparator implements Comparator<GuessingGameShow_PlayerThread_withBank>
    {
        public int compare(GuessingGameShow_PlayerThread_withBank p1, GuessingGameShow_PlayerThread_withBank p2)
        {
            return Integer.compare(p2.getPlayersBank(), p1.getPlayersBank());
        }
    }
}
// SHELL_GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses

/* >>>>>>>>>>>>>>>>>>>> OUTPUT Example <<<<<<<<<<<<<<<<<<<<
P2: guessNumber = 500000     guessCount = 1     playersBet = 6 Guess too LOW! You lose 6 playersBank = 94 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 750000     guessCount = 2     playersBet = 6 Guess too LOW! You lose 6 playersBank = 88 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 875000     guessCount = 3     playersBet = 6 Guess too LOW! You lose 6 playersBank = 82 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 937500     guessCount = 4     playersBet = 5 Guess too LOW! You lose 5 playersBank = 77 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 968750     guessCount = 5     playersBet = 6 Guess too HIGH! You lose 6 playersBank = 71 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 953125     guessCount = 6     playersBet = 5 Guess too LOW! You lose 5 playersBank = 66 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 960937     guessCount = 7     playersBet = 5 Guess too HIGH! You lose 5 playersBank = 61 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 957031     guessCount = 8     playersBet = 4 Guess too HIGH! You lose 4 playersBank = 57 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 955078     guessCount = 9     playersBet = 5 Guess too LOW! You lose 5 playersBank = 52 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 956054     guessCount = 10     playersBet = 3 Guess too LOW! You lose 3 playersBank = 49 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 956542     guessCount = 11     playersBet = 5 Guess too HIGH! You lose 5 playersBank = 44 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 956298     guessCount = 12     playersBet = 1 Guess too HIGH! You lose 1 playersBank = 43 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 956176     guessCount = 13     playersBet = 4 Guess too LOW! You lose 4 playersBank = 39 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P1: guessNumber = 500000     guessCount = 1     playersBet = 7 Guess too LOW! You lose 7 playersBank = 93 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 750000     guessCount = 2     playersBet = 6 Guess too HIGH! You lose 6 playersBank = 87 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 625000     guessCount = 3     playersBet = 3 Guess too HIGH! You lose 3 playersBank = 84 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 562500     guessCount = 4     playersBet = 1 Guess too HIGH! You lose 1 playersBank = 83 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531250     guessCount = 5     playersBet = 7 Guess too LOW! You lose 7 playersBank = 76 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 546875     guessCount = 6     playersBet = 6 Guess too HIGH! You lose 6 playersBank = 70 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 539062     guessCount = 7     playersBet = 5 Guess too HIGH! You lose 5 playersBank = 65 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 535156     guessCount = 8     playersBet = 1 Guess too HIGH! You lose 1 playersBank = 64 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 533203     guessCount = 9     playersBet = 4 Guess too HIGH! You lose 4 playersBank = 60 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 532226     guessCount = 10     playersBet = 5 Guess too HIGH! You lose 5 playersBank = 55 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531738     guessCount = 11     playersBet = 5 Guess too LOW! You lose 5 playersBank = 50 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531982     guessCount = 12     playersBet = 2 Guess too HIGH! You lose 2 playersBank = 48 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531860     guessCount = 13     playersBet = 2 Guess too LOW! You lose 2 playersBank = 46 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531921     guessCount = 14     playersBet = 3 Guess too HIGH! You lose 3 playersBank = 43 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531890     guessCount = 15     playersBet = 1 Guess too HIGH! You lose 1 playersBank = 42 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531875     guessCount = 16     playersBet = 3 Guess too LOW! You lose 3 playersBank = 39 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531882     guessCount = 17     playersBet = 1 Guess too LOW! You lose 1 playersBank = 38 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531886     guessCount = 18     playersBet = 3 Guess too LOW! You lose 3 playersBank = 35 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P1: guessNumber = 531888     guessCount = 19     playersBet = 3 Guess too LOW! You lose 3 playersBank = 32 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
P3: guessNumber = 500000     guessCount = 1     playersBet = 7 Guess too LOW! You lose 7 playersBank = 93 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P1: guessNumber = 531889     guessCount = 20     playersBet = 2 CORRECT! You win 44 playersBank = 46 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
>>>>> P1: Your guessCount = 20 actualNumber = 531889 playersBank = 46
P3: guessNumber = 750000     guessCount = 2     playersBet = 1 Guess too LOW! You lose 1 playersBank = 92 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 875000     guessCount = 3     playersBet = 3 Guess too HIGH! You lose 3 playersBank = 89 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 812500     guessCount = 4     playersBet = 6 Guess too LOW! You lose 6 playersBank = 83 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 843750     guessCount = 5     playersBet = 2 Guess too HIGH! You lose 2 playersBank = 81 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 828125     guessCount = 6     playersBet = 6 Guess too HIGH! You lose 6 playersBank = 75 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 820312     guessCount = 7     playersBet = 6 Guess too LOW! You lose 6 playersBank = 69 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 824218     guessCount = 8     playersBet = 5 Guess too LOW! You lose 5 playersBank = 64 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 826171     guessCount = 9     playersBet = 5 Guess too LOW! You lose 5 playersBank = 59 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827148     guessCount = 10     playersBet = 5 Guess too LOW! You lose 5 playersBank = 54 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827636     guessCount = 11     playersBet = 2 Guess too LOW! You lose 2 playersBank = 52 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827880     guessCount = 12     playersBet = 1 Guess too HIGH! You lose 1 playersBank = 51 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827758     guessCount = 13     playersBet = 1 Guess too LOW! You lose 1 playersBank = 50 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827819     guessCount = 14     playersBet = 4 Guess too HIGH! You lose 4 playersBank = 46 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827788     guessCount = 15     playersBet = 4 Guess too LOW! You lose 4 playersBank = 42 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827803     guessCount = 16     playersBet = 1 Guess too HIGH! You lose 1 playersBank = 41 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827795     guessCount = 17     playersBet = 1 Guess too LOW! You lose 1 playersBank = 40 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827799     guessCount = 18     playersBet = 1 Guess too LOW! You lose 1 playersBank = 39 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
P3: guessNumber = 827801     guessCount = 19     playersBet = 1 CORRECT! You win 45 playersBank = 46 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
>>>>> P3: Your guessCount = 19 actualNumber = 827801 playersBank = 46
P2: guessNumber = 956237     guessCount = 14     playersBet = 3 Guess too LOW! You lose 3 playersBank = 36 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 956267     guessCount = 15     playersBet = 3 Guess too HIGH! You lose 3 playersBank = 33 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 956252     guessCount = 16     playersBet = 2 Guess too HIGH! You lose 2 playersBank = 31 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
P2: guessNumber = 956244     guessCount = 17     playersBet = 2 CORRECT! You win 46 playersBank = 48 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f
>>>>> P2: Your guessCount = 17 actualNumber = 956244 playersBank = 48

Sort and Print threadArray: 
Name = P1: ActualNumber = 531889   GuessNumber = 531889   GuessCount = 20   PlayersBank = 46.0   gpt/this = GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@15923f3
Name = P3: ActualNumber = 827801   GuessNumber = 827801   GuessCount = 19   PlayersBank = 46.0   gpt/this = GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@7ef42c
Name = P2: ActualNumber = 956244   GuessNumber = 956244   GuessCount = 17   PlayersBank = 48.0   gpt/this = GuessingGameShow_THREE_CLIENT_THREADS_Executor_withLocksAndInnerClasses$GuessingGameShow_PlayerThread_withBank@4954f

 * 
 */
/*
public static class Bank
{
private static Lock lock = new ReentrantLock();  
private int bankBalance;

public Bank(int b)
{
bankBalance = b;
}

public int getBankBalance()
{
return bankBalance;
}

public void bet(GuessingGameShow_PlayerThread_withBank p)
{
lock.lock();
double percentageBet = p.getPlayersBet() / p.getPlayersBank();
if (p.getGuessNumber()==p.getActualNumber())
{
p.addToPlayersBank((int)(percentageBet*bankBalance));
bankBalance -= (percentageBet*bankBalance);
} else {
p.subtractFromPlayersBank((int)p.getPlayersBet());
bankBalance += p.getPlayersBet();                
}
lock.unlock();
} // bet 
} // Bank
// GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks

/*
P3: guessNumber = 500000     guessCount = 1  playersBet = 5 Guess too LOW! You lose 5 playersBank = 95.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 750000     guessCount = 2  playersBet = 5 Guess too HIGH! You lose 5 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 625000     guessCount = 3  playersBet = 4 Guess too HIGH! You lose 4 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 562500     guessCount = 4  playersBet = 2 Guess too LOW! You lose 2 playersBank = 84.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 593750     guessCount = 5  playersBet = 4 Guess too HIGH! You lose 4 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 578125     guessCount = 6  playersBet = 4 Guess too HIGH! You lose 4 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 570312     guessCount = 7  playersBet = 6 Guess too LOW! You lose 6 playersBank = 70.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P1: guessNumber = 500000     guessCount = 1  playersBet = 8 Guess too LOW! You lose 8 playersBank = 92.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 750000     guessCount = 2  playersBet = 3 Guess too LOW! You lose 3 playersBank = 89.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 875000     guessCount = 3  playersBet = 9 Guess too HIGH! You lose 9 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 812500     guessCount = 4  playersBet = 6 Guess too LOW! You lose 6 playersBank = 74.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 843750     guessCount = 5  playersBet = 8 Guess too HIGH! You lose 8 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 828125     guessCount = 6  playersBet = 1 Guess too LOW! You lose 1 playersBank = 65.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 835937     guessCount = 7  playersBet = 0 Guess too HIGH! You lose 0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 832031     guessCount = 8  playersBet = 3 Guess too HIGH! You lose 3 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 830078     guessCount = 9  playersBet = 2 Guess too HIGH! You lose 2 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829101     guessCount = 10     playersBet = 3 Guess too LOW! You lose 3 playersBank = 57.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829589     guessCount = 11     playersBet = 4 Guess too LOW! You lose 4 playersBank = 53.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829833     guessCount = 12     playersBet = 1 Guess too LOW! You lose 1 playersBank = 52.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829955     guessCount = 13     playersBet = 4 Guess too HIGH! You lose 4 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829894     guessCount = 14     playersBet = 4 Guess too HIGH! You lose 4 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829863     guessCount = 15     playersBet = 1 Guess too LOW! You lose 1 playersBank = 43.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P3: guessNumber = 574218     guessCount = 8  playersBet = 1 Guess too LOW! You lose 1 playersBank = 69.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P2: guessNumber = 500000     guessCount = 1  playersBet = 9 Guess too LOW! You lose 9 playersBank = 91.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P1: guessNumber = 829878     guessCount = 16     playersBet = 2 Guess too HIGH! You lose 2 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829870     guessCount = 17     playersBet = 3 Guess too HIGH! You lose 3 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829866     guessCount = 18     playersBet = 2 Guess too HIGH! You lose 2 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P1: guessNumber = 829864     guessCount = 19     playersBet = 1 CORRECT! You win 37.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
P2: guessNumber = 750000     guessCount = 2  playersBet = 1 Guess too HIGH! You lose 1 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 625000     guessCount = 3  playersBet = 4 Guess too LOW! You lose 4 playersBank = 86.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 687500     guessCount = 4  playersBet = 8 Guess too HIGH! You lose 8 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 656250     guessCount = 5  playersBet = 6 Guess too LOW! You lose 6 playersBank = 72.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 671875     guessCount = 6  playersBet = 6 Guess too HIGH! You lose 6 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 664062     guessCount = 7  playersBet = 5 Guess too LOW! You lose 5 playersBank = 61.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 667968     guessCount = 8  playersBet = 6 Guess too HIGH! You lose 6 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P3: guessNumber = 576171     guessCount = 9  playersBet = 5 Guess too LOW! You lose 5 playersBank = 64.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577148     guessCount = 10     playersBet = 6 Guess too LOW! You lose 6 playersBank = 58.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577636     guessCount = 11     playersBet = 3 Guess too HIGH! You lose 3 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577392     guessCount = 12     playersBet = 2 Guess too HIGH! You lose 2 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577270     guessCount = 13     playersBet = 0 Guess too HIGH! You lose 0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577209     guessCount = 14     playersBet = 1 Guess too HIGH! You lose 1 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577178     guessCount = 15     playersBet = 3 Guess too HIGH! You lose 3 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577163     guessCount = 16     playersBet = 5 Guess too HIGH! You lose 5 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577155     guessCount = 17     playersBet = 2 Guess too LOW! You lose 2 playersBank = 42.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577159     guessCount = 18     playersBet = 0 Guess too LOW! You lose 0 playersBank = 42.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577161     guessCount = 19     playersBet = 2 Guess too LOW! You lose 2 playersBank = 40.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
P3: guessNumber = 577162     guessCount = 20     playersBet = 3 CORRECT! You win 44.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0
>>>>> P3: You got with guessCount = 20 actualNumber = 577162 playersBank = 47.0
P2: guessNumber = 666015     guessCount = 9  playersBet = 2 Guess too LOW! You lose 2 playersBank = 53.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666991     guessCount = 10     playersBet = 1 Guess too HIGH! You lose 1 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666503     guessCount = 11     playersBet = 2 Guess too HIGH! You lose 2 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
>>>>> P1: You got with guessCount = 19 actualNumber = 829864 playersBank = 38.0
P2: guessNumber = 666259     guessCount = 12     playersBet = 3 Guess too LOW! You lose 3 playersBank = 47.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666381     guessCount = 13     playersBet = 4 Guess too HIGH! You lose 4 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666320     guessCount = 14     playersBet = 4 Guess too HIGH! You lose 4 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666289     guessCount = 15     playersBet = 4 Guess too LOW! You lose 4 playersBank = 35.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666304     guessCount = 16     playersBet = 0 Guess too LOW! You lose 0 playersBank = 35.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666312     guessCount = 17     playersBet = 1 Guess too LOW! You lose 1 playersBank = 34.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
P2: guessNumber = 666316     guessCount = 18     playersBet = 1 CORRECT! You win 35.0 Thread: GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
>>>>> P2: You got with guessCount = 18 actualNumber = 666316 playersBank = 36.0

Sort and Print threadArray: 
Name = P2: ActualNumber = 666316   GuessNumber = 666316   GuessCount = 18   PlayersBank = 36.0   gpt/this = GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1abe4e7
Name = P1: ActualNumber = 829864   GuessNumber = 829864   GuessCount = 19   PlayersBank = 38.0   gpt/this = GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@c3763f
Name = P3: ActualNumber = 577162   GuessNumber = 577162   GuessCount = 20   PlayersBank = 47.0   gpt/this = GuessingGameShow_THREE_CLIENT_THREADS_Executor_Locks$GuessingGameShow_PlayerThread_withBank@1bba7a0

 */

