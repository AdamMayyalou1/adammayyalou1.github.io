// Main class with Executor Service
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuessingGameShowTWO_CLIENT_THREADS_Executor_SHELL {
    public static void main(String[] args) {
        int max = 1000000;
        int numberOfPlayers = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfPlayers);
        GuessingGameShow_PlayerThread[] threadArray = new GuessingGameShow_PlayerThread[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            int n = (int) (Math.random() * max + 1);
            threadArray[i] = new GuessingGameShow_PlayerThread("P" + (i + 1), n, max);
            executor.execute(threadArray[i]);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        Arrays.sort(threadArray, new threadGuessCount_Comparator());

        System.out.println("\n\n\t Sort and Print threadArray: ");
        for (GuessingGameShow_PlayerThread gpt : threadArray) {
            System.out.println("Name = " + gpt.getName() + ": " +
                               "ActualNumber = " + gpt.getActualNumber() + "   " + 
                               "GuessNumber = " + gpt.getGuessNumber() + "   " +
                               "GuessCount = " + gpt.getGuessCount() + "   " +                               
                               "gpt/this = " + gpt);
        }

        System.exit(0);
    }
}