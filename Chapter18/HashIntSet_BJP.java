
/**
 * HashIntSet: Use this for Chapter 18.1 pg 1068     
 * The Chapter_18_Exercises MAIN is located at: Chapter18_Exercises#1-12_MAIN
 * You put YOUR CODE down at the bottom.
 */      
import java.util.*;

public class HashIntSet_BJP
{
    private static final double MAX_LOAD_FACTOR = 0.75;
    private HashEntry elementData[];   
    private int size;

    public HashIntSet_BJP()
    {
        elementData = new HashEntry[10];
        for (int i=0; i<elementData.length; i++)
            elementData[i] = new HashEntry();
        size = 0;
    }

    // Hash Function
    private int hashFunction(int value)
    { 
        return Math.abs(value) % elementData.length; 
    } 

    // Returns true if the given value is found in this set.     
    public boolean contains(int value)
    {     
        int bucket = hashFunction(value);     
        HashEntry current = elementData[bucket]; 

        while (current != null) {     
            if (current.data == value) {     
                return true;     
            }     
            current = current.next;     
        }     
        return false; 
    } // contains()

    // Give the loadFactor
    private double loadFactor()
    {
        return (double) size / elementData.length;
    }

    // Adds the given element to this set, if it was not 
    // already contained in the set. 
    public void add(int value)
    {       
        if (!contains(value)) 
        {     
            if (loadFactor() >= MAX_LOAD_FACTOR) { 
                rehash(); 
            } 

            // insert new value at front of list     
            int bucket = hashFunction(value);     
            elementData[bucket] = new HashEntry(value, elementData[bucket]);     
            size++; 

        } 
    } // add()

    // Resize the entire array and rehash it
    private void rehash()
    { 
        // replace element data array with a larger empty version 
        HashEntry[] oldElementData = elementData; 

        elementData = new HashEntry[2 * oldElementData.length];     
        size = 0; 

        // re-add all of the old data into the new array 
        for (int i = 0; i < oldElementData.length; i++) 
        { 
            HashEntry current = oldElementData[i]; 

            while (current != null) 
            {     
                add(current.data);     
                current = current.next; 

            } 
        } 
    } // rehash()

    // Removes the given value if it is contained in the set. 
    public void remove(int value) 
    { 
        int bucket = hashFunction(value); 
        if (elementData[bucket] != null)
        { 

            // check front of list 

            if (elementData[bucket].data == value) { 
                elementData[bucket] = elementData[bucket].next; 
                size--;     
            } else { 
                // check rest of list 
                HashEntry current = elementData[bucket]; 
                while (current.next != null && current.next.data != value)
                { 
                    current = current.next; 
                } 

                // if the element is found, remove it 
                if (current.next != null) 
                { 
                    current.next = current.next.next; 
                    size--; 
                } 
            } 
        } 
    } // remove()    

    // ====== HashEntry Inner Class =======
    public class HashEntry 
    {
        private int data;
        private HashEntry next;

        public HashEntry() 
        {
            this(0,null); 
        }

        public HashEntry(int data)
        {
            this(data,null);        
        }

        public HashEntry(int data,HashEntry next)
        {
            this.data = data;
            this.next = next;
        }

    }  // HashEntry

    // =====================================================================
    // >>>>>>>>>>>>>>>>>>>>>>>>>>  YOUR CODE HERE <<<<<<<<<<<<<<<<<<<<<<<<<<
    // =====================================================================

} // HashIntSet


// ====================== Test CLIENT ===========================
// This client program just tests our hash set of integers by adding and removing several 
// elements from it.  For the real main use:  Chapter18_Exercises#1-12_MAIN