
// Liang 10th Edition
// Threads: Pg 1100

/*
 *  Sample OUPUT:
Runtime.getRuntime().availableProcessors(); = 4
ba 1b 2aba 3ab 4ab 5 6baa 7bba 8b 9a 10
Runtime.getRuntime().availableProcessors(); = 4
ab 1ab 2b 3ab 4ab 5ab 6aa 7bba 8ba 9 10
Runtime.getRuntime().availableProcessors(); = 4
b 1aba 2b 3aab 4ab 5a 6ba 7ba 8bba 9 10
 * 
 */

public class TaskThreadDemo
{ 
    public static void main()
    {
        final int N = 100;  // = 100;  // = 1000;

        int threadCount = Runtime.getRuntime().availableProcessors();   
        System.out.println("\n Runtime.getRuntime().availableProcessors(); = " + threadCount);   

        // Create Runnable Objects
        Runnable printA = new PrintChar('a',N);
        Runnable printB = new PrintChar('b',N);        
        Runnable printN = new PrintNum(N);  

        // Create threads
        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(printN);

        // Start threads
        thread1.start();              
        thread2.start();
        thread3.start();                 

    }// main
} // TaskThreadDemo

// =======================================================================
//The thread class for printing a specified character in specified times
class PrintChar implements Runnable 
{
    private char charToPrint;  //the character to print
    private int times;  //the times to repeat

    //The thread class constructor
    public PrintChar(char c, int t) 
    {
        charToPrint = c;
        times = t;
    }

    //override the run() method to tell the system what the thread will do
    public void run()
    {
        for (int i=1; i < times; i++) 
            System.out.print(charToPrint);
    }
}