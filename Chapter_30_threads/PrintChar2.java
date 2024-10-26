class PrintChar2 implements Runnable {
    private String charsToPrint;   // the character(s) to print
    private int times;             // the times to repeat

    // thread class constructor
    public PrintChar2(String s, int t) {
        charsToPrint = s;
        times = t;
    }

    public void run() {
        System.out.println("Thread.getName() = " + Thread.currentThread().getName());
        for (int i = 1; i <= times; i++) {
            System.out.println(charsToPrint);
            if ((i >= times / 2) && (times % 2 == 0)) {
                int r1 = (int) (Math.random() * 10 + 1); // 1-10
                switch (r1) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        int r2 = (int) (Math.random() * 3 + 1); // 1-3
                        switch (r2) {
                            case 1:
                                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                                System.out.println("Thread.getName() = " + this.charsToPrint +
                                    "\t to: " + Thread.MIN_PRIORITY);
                                break;
                            case 2:
                                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
                                System.out.println("Thread.getName() = " + this.charsToPrint +
                                    "\t to: " + Thread.NORM_PRIORITY);
                                break;
                            case 3:
                                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                                System.out.println("Thread.getName() = " + this.charsToPrint +
                                    "\t to: " + Thread.MAX_PRIORITY);
                                break;
                        }
                        break;
                    case 6:
                        Thread newThread = new Thread(this);
                        newThread.start();
                        break;
                    case 7:
                        Thread joinThread = new Thread(this);
                        joinThread.start();
                        try {
                            joinThread.join();
                        } catch (Exception e) {

                        }
                        break;
                    case 8:
                        Thread.currentThread().yield();
                        System.out.println("r=8: Thread.currentThread().yield();  Thread.currentThread().getName() = "
                            + Thread.currentThread().getName());
                        break;
                    case 9:
                        try {
                            Thread.sleep(500);
                            System.out.println("r=9: Thread.currentThread().sleep(500);  Thread.currentThread().getName() = "
                                + Thread.currentThread().getName());
                        } catch (Exception e) {

                        }
                        break;
                    case 10:
                        System.out.println("r=10: Thread.currentThread().stop();  Thread.currentThread().getName() = "
                            + Thread.currentThread().getName());
                        Thread.currentThread().stop();
                        break;
                }
            }// if
        }  // run()
    } // PrintChar2
}

/*case 1:
Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
System.out.println("Thread.getName() = " + Thread.currentThread().getName() +
" & Thread.currentThread().setPriority(Thread.MIN_PRIORITY)");
break;
case 2:
Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
System.out.println("Thread.getName() = " + Thread.currentThread().getName() +
" & Thread.currentThread().setPriority(Thread.NORM_PRIORITY)");
break;
case 3:
Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
System.out.println("Thread.getName() = " + Thread.currentThread().getName() +
" & Thread.currentThread().setPriority(Thread.MAX_PRIORITY)");
break;
 */