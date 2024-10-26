// Chapter 18 Exercises MAIN #1-15      

//  For Exercises #1-7 - put them in HashIntSet 
//  For Exercises #8-12 PriorityQueues - write them down at the bottom of this document
//  For Exercises #13-15 - put them in HeapIntPriorityQueue                 

import java.util.*;

public class Chapter18_Exercises_MAIN_1_to_15_SHELL
{

    public static void main()
    {

        //======================================================
        //  —------- Chap 18.1 hash Tables   Ex #1-7   —------- 
        //======================================================
        System.out.println("\n ============ Chap 18.1 Hash Tables Ex #1-7  ============ \n");  
        HashIntSet his1 = new HashIntSet();
        HashIntSet his2 = new HashIntSet();
        HashIntSet his3 = new HashIntSet();
        HashIntSet his4 = new HashIntSet();
        HashIntSet his5 = new HashIntSet();

        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 1:");
        his1.add(2);    his1.add(3); his1.add(6); his1.add(44); his1.add(79);
        his2.add(5);    his2.add(1); his2.add(2);
        his2.add(3); his2.add(6); his2.add(44); his2.add(79);
        System.out.println(" his1 Before: addAll(his2)");
        System.out.println("his1: " + his1);
        his1.addAll(his2);
        System.out.println("his1 After addAll(his2):");
        System.out.println("his1: " + his1);
        System.out.println("\n"); 

        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 2:");
        his1.reset();    
        his1.add(-2);    his1.add(3); his1.add(5); his1.add(6); his1.add(8);
        his2.add(3);    his2.add(6); his2.add(8);
        his3.add(3); his3.add(6); his3.add(8);
        System.out.println( "\t his1: " + his1);
        System.out.println( "\t his2: " + his2);
        System.out.println( "\t his3: " + his3);
        System.out.println("his1.containsAll(his2): " + his1.containsAll(his2));
        System.out.println("his1.containsAll(his3): " + his1.containsAll(his3));
        System.out.println("\n");      

        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 3:");
        his1.reset();
        his2.reset();  
        his3.reset();  
        his1.add(1);    his1.add(2); his1.add(3);
        his2.add(2);    his2.add(3); his2.add(1);
        his3.add(1); his3.add(2); his3.add(3); his3.add(4);
        System.out.println( "\t his1: " + his1);
        System.out.println( "\t his2: " + his2);
        System.out.println( "\t his3: " + his3);
        System.out.println("his1.equals(his2): " + his1.equals(his2));
        System.out.println("his1.equals(his3): " + his1.equals(his3));
        System.out.println("\n");  

        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 4:");
        his1.reset();    
        his2.reset();
        his1.add(-2);    his1.add(3); his1.add(5); his1.add(6); his1.add(8);
        his2.add(2);    his2.add(3); his2.add(6);   his2.add(8); his2.add(11);
        System.out.println( "\t his1: " + his1);
        System.out.println( "\t his2: " + his2);
        System.out.println(" his1 Before: his1.removeAll(his2)");
        System.out.println( "\t his1: " + his1);
        his1.removeAll(his2);
        System.out.println("his1 After: his1.removeAll(his2)");
        System.out.println("\t his1: " + his1);
        System.out.println("\n"); 

        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 5:");
        his1.reset();
        his2.reset();
        his1.add(-2);    his1.add(3); his1.add(5); his1.add(6); his1.add(8);
        his2.add(2);    his2.add(3); his2.add(6);  his2.add(8); his2.add(11); 
        System.out.println( "\t his1: " + his1);
        System.out.println( "\t his2: " + his2);
        System.out.println(" his1 Before: his1.retainAll(his2)");
        System.out.println("\t his1: " + his1);
        his1.retainAll(his2);
        System.out.println("his1 After: his1.retainAll(his2)");
        System.out.println("\t his1: " + his1);
        System.out.println("\n");       

        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 6:");
        his1.reset();    
        his1.add(2);    his1.add(3); his1.add(6); his1.add(44); his1.add(79);

        System.out.println("his1: " + his1);
        int his1Array[ ] = his1.toArray();  
        System.out.println("his1.toArray: ");
        System.out.print(his1Array[0] );
        for (int i=1; i<his1Array.length; i++)     
        {
            System.out.print("," + his1Array[i] );
        }
        System.out.println("\n\n"); 

        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 7:");
        his1.reset();    
        his1.add(-2); his1.add(3); his1.add(5); his1.add(6); his1.add(8);
        his1.add(33); his1.add(66); his1.add(76);

        System.out.println("his1: " + his1);
        System.out.println("his1.toString: " + his1.toString());
        System.out.println("\n\n\n"); 

        //======================================================
        //   —-- Chap 18.2 Heaps/PriorityQueues Ex #8-12 —--
        //======================================================
        System.out.println("\n ============ Chap 18.2 Heaps/PriorityQueues Ex #8-12 ============ \n");      
        //  —---------------------------------------------------------------------------------------
        System.out.println("Exercise 8 - descending(nums): ");
        int[] nums = {42, 9, 22, 17, -3, 81};
        System.out.println("Before:");
        System.out.println(Arrays.toString(nums));
        descending(nums);
        System.out.println("After:");
        for(int stuff: nums) {
            System.out.print(stuff + ", ");
        }
        System.out.println("\n"); 

        //======================================================
        System.out.println("Exercise 9 - kthSmallest(priority, 4):");
        PriorityQueue<Integer> priority = new PriorityQueue<Integer>(Arrays.asList(42, 50, 45, 78, 61));
        int result = kthSmallest(priority, 4);
        System.out.println(result);
        System.out.println();

        //======================================================
        System.out.println("Exercise 10 - isConsecutive(numbers):");
        PriorityQueue<Integer> numbers = new PriorityQueue<Integer>(Arrays.asList(7, 8, 9, 10, 11));
        boolean done = isConsecutive(numbers);
        System.out.println(done);
        System.out.println();
        
        //======================================================
        System.out.println("Exercise 11 - removeDuplicates(doubles):"); 
        PriorityQueue<Integer> doubles = new PriorityQueue<Integer>(Arrays.asList(7, 7, 8, 8, 8, 10, 45, 45));
        System.out.println("Before:");
        System.out.println(doubles);
        removeDuplicates(doubles);
        System.out.println("After:");
        System.out.println(doubles);
        System.out.println();       

        //======================================================
        System.out.println("Exercise 12 - stutter(stuff):");
        PriorityQueue<Integer> stuff = new PriorityQueue<Integer>(Arrays.asList(7, 8, 10, 45));
        System.out.println("Before:");
        System.out.println(stuff);
        stutter(stuff);
        System.out.println("After:");
        System.out.println(stuff);
        System.out.println("\n\n");   

        // ===================================================================
        System.out.println("\n ============ Chap 18.2 HeapIntPriorityQueue Ex #13-15 ============ \n");   
        
        System.out.println("Exercise 13 - toArray(): ");
        HeapIntPriorityQueue hpq1 = new HeapIntPriorityQueue(new int[] {Integer.MAX_VALUE,12,29,70,30,39,84,91,55,64,40,99});
        int a[] = hpq1.toArray();
        System.out.print(a[0]);
        int i;
        for (i=1; i<a.length-1; i++)
        System.out.print(", " + a[i]);
        System.out.println(a[i] + "]");

        //======================================================       
        System.out.println();
        System.out.println("Exercise 14 - toString(): ");
        HeapIntPriorityQueue hpq2 = new HeapIntPriorityQueue(new int[] {Integer.MAX_VALUE,42,50,45,78,61});
        System.out.print(hpq2.toString());       

        //======================================================       
        System.out.println();
        System.out.println();
        System.out.println("Exercise 15 - merge(): ");
        HeapIntPriorityQueue hpq3a = new HeapIntPriorityQueue(new int[] {Integer.MAX_VALUE,1,5,9,3,7});   
        HeapIntPriorityQueue hpq3b = new HeapIntPriorityQueue(new int[] {Integer.MAX_VALUE,6,10,4,2,8});    
        hpq3a.merge(hpq3b);
        System.out.print(hpq3a.toString());        

    } // main

    // ===================================================================
    //            >>>>>>>>> YOUR CODE FOR Exercises #8-12 HERE <<<<<<<<<<<<<<
    // ===================================================================    
    // Exercise #8
    public static void descending(int a[])
    {
        Queue<Integer> pq = new PriorityQueue<Integer>();
        for (int i=0; i<a.length; i++)
        {
            pq.add(a[i]);
        }
        int i=0;
        while (!pq.isEmpty())
        {
            a[i]=pq.remove();
            i++;
        }
    }
    // Exercise #9

    public static int kthSmallest(PriorityQueue<Integer> pq, int k)
    {
        Queue<Integer> pq2 = new PriorityQueue<Integer>();
        while (!pq.isEmpty())
        {
            pq2.add(pq.remove());
        }
        int num;
        int count=1;
        while (!pq2.isEmpty())
        {
            if (count==k)
            {
                return pq2.remove();
            }
            pq2.remove();
            count++;
        }
        return 0;
    }    

    // Exercise #10
    public static boolean isConsecutive(PriorityQueue<Integer> pq)
    {
        Queue<Integer> q = new LinkedList<Integer>();
        while(!pq.isEmpty())
        {
            int a = pq.remove();
            if(!pq.isEmpty()&& a != pq.peek()-1)
            {
                return false;
            }
            q.add(a);
        }
        while(!q.isEmpty())
        {
            pq.add(q.remove());
        }
        return true;
    }    

    // Exercise #11
    public static void removeDuplicates(PriorityQueue<Integer> pq)
    {
        Queue<Integer> q = new LinkedList<Integer>();
        int e = Integer.MAX_VALUE;
        while(!pq.isEmpty())
        {
            int a = pq.remove();
            if(a != e)
            {
                q.add(a);
            }
            e = a;
        }
        while(!q.isEmpty())
        {
            pq.add(q.remove());
        }
    }   

    // Exercise #12     
    public static void stutter(PriorityQueue<Integer> pq)
    {
        Queue<Integer> q = new LinkedList<Integer>();
        while(!pq.isEmpty())
        {
            int a = pq.remove();
            q.add(a);
            q.add(a);
        }
        while(!q.isEmpty())
        {
            pq.add(q.remove());
        }
    }
    
    public static void sttr(PriorityQueue<Integer> pq)
    {
        Queue<Integer> q = new LinkedList<Integer>();
        while(!pq.isEmpty())
        {
            int a = pq.remove();
            q.add(a);
            q.add(a);
        }
        while(!q.isEmpty())
        {
            pq.add(q.remove());
        }
    }
} // Chapter18_Exercises_MAIN_1_to_15
