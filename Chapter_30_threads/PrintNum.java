// =======================================================================
//The thread class for printing number from 1 to n for a given n.
class PrintNum implements Runnable
{
    private int lastNum;
    public PrintNum(int i) 
    {
        lastNum = i;
    }

    // Pg 1100 Sec 30.3: run()
    /*public void run()
    {
    for (int i = 1; i <= lastNum; i++) 
    {
    System.out.print(" " + i);
    }
    } */   

    //      Thread printSymbol = new PrintSymbol('*',1000);
    //      printSymbol.start();
    /*
    // Pg 1103 Sec 30.4: Thread.yield()
    public void run() 
    {
        for (int i = 1; i <= lastNum; i++) 
        {
            System.out.print(" " + i);
            Thread.yield();
        }
    }*/
    /*
    // Pg 1103 Sec 30.4: Thread.sleep()
    public void run() {
        try {
            for (int i = 1; i <= lastNum; i++) {
                System.out.print(" " + i);
                if (i >= 5) Thread.sleep(1500);
            }
        }
        catch (InterruptedException ex) {
        }
    }*/
    
    // Pg 1104 Sec 30.4: Thread.join() 
    public void run() 
    {
        Thread thread4 = new Thread(new PrintChar('c', 40));
        thread4.start();
        try {
            for (int i = 1; i <= lastNum; i++) {
                System.out.print (" " + i);
                if (i == 50) thread4.join();
            }
        }
        catch (InterruptedException ex) {
        }
    }    
}  // PrintNum
