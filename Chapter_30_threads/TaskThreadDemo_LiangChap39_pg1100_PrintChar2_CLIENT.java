// ===============================================================
public class TaskThreadDemo_LiangChap39_pg1100_PrintChar2_CLIENT
{ 
    public static void main()
    {       
        final int N = 100;

        // Create tasks
        Runnable printA = new PrintChar2("a1",N);
        Runnable printB = new PrintChar2("b1",N);        
        Runnable printC = new PrintChar2("c1",N);  

        // Create threads
        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(printC);

        // Start threads
        thread1.start();                   
        thread2.start();              
        thread3.start();

    }// main

} //  TaskThreadDemo_LiangChap39_pg1100_PrintChar2_CLIENT

/*
 * Possible OUTPUT:

charsToPrint = c1     r1 = 4    Thread.getName()  = Thread-7
Thread.getName()  = Thread-7 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1     r1 = 9    Thread.getName()  = Thread-6
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-6
charsToPrint = a1     r1 = 8    Thread.getName()  = Thread-5
r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = Thread-5
charsToPrint = a1     r1 = 5    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = a1     r1 = 6    Thread.getName()  = Thread-5
r=6 New Thread via newThread.start().  newThread.getName() = Thread-8
charsToPrint = a1a2     r1 = 2    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = a1a2     r1 = 3    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = a1a2     r1 = 8    Thread.getName()  = Thread-5
r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = Thread-5
charsToPrint = a1a2     r1 = 6    Thread.getName()  = Thread-5
r=6 New Thread via newThread.start().  newThread.getName() = Thread-9
charsToPrint = a1a2a3     r1 = 5    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = a1a2a3     r1 = 1    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = a1a2a3     r1 = 8    Thread.getName()  = Thread-5
charsToPrint = a1a2a3     r1 = 1    Thread.getName()  = Thread-9
Thread.getName()  = Thread-9 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = a1a2a3     r1 = 7    Thread.getName()  = Thread-9
charsToPrint = a1a2a3     r1 = 4    Thread.getName()  = Thread-9
Thread.getName()  = Thread-9 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = a1a2     r1 = 2    Thread.getName()  = Thread-8
Thread.getName()  = Thread-8 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = Thread-5
charsToPrint = c1     r1 = 2    Thread.getName()  = Thread-7
Thread.getName()  = Thread-7 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = c1     r1 = 1    Thread.getName()  = Thread-7
Thread.getName()  = Thread-7 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = c1     r1 = 6    Thread.getName()  = Thread-7
r=6 New Thread via newThread.start().  newThread.getName() = Thread-10
charsToPrint = c1c2     r1 = 4    Thread.getName()  = Thread-7
Thread.getName()  = Thread-7 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = c1c2     r1 = 1    Thread.getName()  = Thread-7
Thread.getName()  = Thread-7 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = a1a2a3     r1 = 6    Thread.getName()  = Thread-5
charsToPrint = a1a2a3     r1 = 7    Thread.getName()  = Thread-5
charsToPrint = a1a2a3     r1 = 1    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = a1a2a3     r1 = 6    Thread.getName()  = Thread-5
charsToPrint = a1a2a3     r1 = 7    Thread.getName()  = Thread-5
charsToPrint = a1a2a3     r1 = 4    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = a1a2a3     r1 = 9    Thread.getName()  = Thread-5
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-5
charsToPrint = c1c2     r1 = 4    Thread.getName()  = Thread-10
Thread.getName()  = Thread-10 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = a1a2a3     r1 = 6    Thread.getName()  = Thread-9
charsToPrint = a1a2a3     r1 = 10    Thread.getName()  = Thread-9
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-9
charsToPrint = a1a2     r1 = 10    Thread.getName()  = Thread-8
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-8
charsToPrint = c1c2     r1 = 9    Thread.getName()  = Thread-7
charsToPrint = c1c2     r1 = 6    Thread.getName()  = Thread-10
r=6 New Thread via newThread.start().  newThread.getName() = Thread-11
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-7
charsToPrint = c1c2c3     r1 = 10    Thread.getName()  = Thread-10
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-10
charsToPrint = c1c2c3     r1 = 10    Thread.getName()  = Thread-11
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-11
charsToPrint = b1     r1 = 6    Thread.getName()  = Thread-6
r=6 New Thread via newThread.start().  newThread.getName() = Thread-12
charsToPrint = b1b2     r1 = 7    Thread.getName()  = Thread-6
r=7: New Thread via t2.start()/t2.join()  t2.getName() = Thread-13
charsToPrint = b1b2     r1 = 5    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2     r1 = 6    Thread.getName()  = Thread-12
r=6 New Thread via newThread.start().  newThread.getName() = Thread-14
charsToPrint = b1b2b3     r1 = 3    Thread.getName()  = Thread-13
Thread.getName()  = Thread-13 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2b3     r1 = 8    Thread.getName()  = Thread-13
r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = Thread-13
charsToPrint = b1b2b3     r1 = 2    Thread.getName()  = Thread-13
Thread.getName()  = Thread-13 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-13
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-13
charsToPrint = b1b2b3     r1 = 2    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = b1b2b3     r1 = 5    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-12
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-12
charsToPrint = b1b2b3     r1 = 10    Thread.getName()  = Thread-14
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-14
charsToPrint = a1a2a3     r1 = 8    Thread.getName()  = Thread-5
r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = Thread-5
charsToPrint = a1a2a3     r1 = 6    Thread.getName()  = Thread-5
charsToPrint = a1a2a3     r1 = 7    Thread.getName()  = Thread-5
charsToPrint = c1c2     r1 = 7    Thread.getName()  = Thread-7
charsToPrint = a1a2a3     r1 = 6    Thread.getName()  = Thread-5
charsToPrint = a1a2a3     r1 = 3    Thread.getName()  = Thread-5
Thread.getName()  = Thread-5 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
r=7: New Thread via t2.start()/t2.join()  t2.getName() = Thread-15
charsToPrint = a1a2a3     r1 = 10    Thread.getName()  = Thread-5
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-5
charsToPrint = c1c2c3     r1 = 5    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = c1c2c3     r1 = 1    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = c1c2c3     r1 = 1    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = c1c2c3     r1 = 7    Thread.getName()  = Thread-15
charsToPrint = c1c2c3     r1 = 6    Thread.getName()  = Thread-15
charsToPrint = c1c2c3     r1 = 3    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = c1c2c3     r1 = 2    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = c1c2c3     r1 = 7    Thread.getName()  = Thread-15
charsToPrint = c1c2c3     r1 = 2    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = c1c2c3     r1 = 7    Thread.getName()  = Thread-15
charsToPrint = c1c2c3     r1 = 5    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = c1c2c3     r1 = 4    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = c1c2c3     r1 = 8    Thread.getName()  = Thread-15
r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = Thread-15
charsToPrint = c1c2c3     r1 = 3    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = c1c2c3     r1 = 4    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = c1c2c3     r1 = 9    Thread.getName()  = Thread-15
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-15
charsToPrint = b1b2b3     r1 = 6    Thread.getName()  = Thread-13
charsToPrint = b1b2b3     r1 = 4    Thread.getName()  = Thread-13
Thread.getName()  = Thread-13 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-13
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 10    Thread.getName()  = Thread-13
charsToPrint = b1b2b3     r1 = 3    Thread.getName()  = Thread-12
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-13
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = b1b2b3     r1 = 8    Thread.getName()  = Thread-12
r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = Thread-12
charsToPrint = b1b2b3     r1 = 6    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-12
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-12
charsToPrint = c1c2c3     r1 = 5    Thread.getName()  = Thread-15
Thread.getName()  = Thread-15 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = c1c2c3     r1 = 10    Thread.getName()  = Thread-15
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-15
charsToPrint = b1b2b3     r1 = 5    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = b1b2b3     r1 = 6    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 4    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = b1b2b3     r1 = 4    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-12
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-12
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 3    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = b1b2b3     r1 = 3    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-12
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-12
charsToPrint = b1b2b3     r1 = 4    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = b1b2b3     r1 = 6    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 2    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2b3     r1 = 6    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 4    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = b1b2b3     r1 = 3    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = b1b2b3     r1 = 6    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 1    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)
charsToPrint = b1b2b3     r1 = 2    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-12
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-12
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-12
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-12
charsToPrint = b1b2b3     r1 = 4    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2b3     r1 = 2    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = b1b2b3     r1 = 2    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 4    Thread.getName()  = Thread-12
Thread.getName()  = Thread-12 & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)
charsToPrint = b1b2b3     r1 = 9    Thread.getName()  = Thread-12
r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = Thread-12
charsToPrint = b1b2b3     r1 = 7    Thread.getName()  = Thread-12
charsToPrint = b1b2b3     r1 = 10    Thread.getName()  = Thread-12
r=10: Thread.currentThread().stop());  Thread.currentThread().getName() = Thread-12 */