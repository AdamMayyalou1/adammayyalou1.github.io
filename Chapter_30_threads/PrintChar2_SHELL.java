// Given the below main(), complete PrintChar2_SHELL run() as specified below:

//The thread class for printing specific characters in specified times
class PrintChar2 implements Runnable // get rid of _SHELL     
{
    private String charsToPrint;   // the character(s) to print
    private int times;                    // the times to repeat

    // thread class constructor
    public PrintChar2(String s,int t) { 
        charsToPrint = s;
        times = t;
    }

    public void run()
    {
        System.out.println("Thread.getName()  = " + Thread.currentThread().getName() );   
        for (int i=1; i <= times; i++) {
            System.out.println(charsToPrint);
            if ((i>=times/2) && (times%2 == 0)) {
                int r1 = (int) (Math.random()*10+1);   // 1-10
                switch (r1) {
                        // >>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<
                        // >>>>>YOUR CODE REPLACES THE FOLLOWING <<<<<
                        // >>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<
                        // Random number r1 (1-10) results as follows:
                        /* (1)-(5) [50% of the time] generate an r2 such that 1 <= r2 <= 3 resulting as follows:
                        //    (1) Set the Thread Priority to MIN.
                        //    (2) Set the Thread Priority to NORMAL.
                        //    (c) Set the Thread Priority to MAX.                                
                        (6) Create a new thread off of the current one and “start” it.   
                        Ex.  current thread = a1  new thread = a1a2  and a thread off of that would be a1a2a3
                        NOTE1: there most likely will be more than one time that a1 spons a new a1a2                     
                        thread and that is ok.  Same with the others.
                        NOTE2:  Make sure to STOP the Thread when it reaches its 3rd generation,      
                        Otherwise it may go on forever!
                        Ex  a1a2a3  STOP it at this point!  So NO a1a2a3a4!
                        (7) Create a new thread off of the current one and “join” it.   
                        Ex.  current thread = a1  new thread = a1a2  and a thread off of that would be a1a2a3
                        NOTE1: there most likely will be more than one time that a1 spawns a new a1a2                     
                        thread and that is ok.  Same with the others.
                        NOTE2:  Make sure to STOP the Thread when it reaches its 3rd generation,      
                        Otherwise it may go on forever! 
                        Ex  a1a2a3  STOP it at this point!  So NO a1a2a3a4!
                        (8) Yield the current thread.
                        (9) Sleep the current thread for 500 milliseconds.  (½ second)
                        (10) Stop the current thread.
                        (Bonus): Add in a few of your own.
                        NOTE: you will have to look through the book, online, etc in order to accomplish this.  There 
                        are various places that will need to be in a try-catch Exception Handler.
                         */
                } // switch r1
            }// if   
        }  // run()
    } // PrintChar2  
}