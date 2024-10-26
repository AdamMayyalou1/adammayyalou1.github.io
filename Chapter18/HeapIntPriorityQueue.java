// ========================== HeapIntPriorityQueue  ======================
// Implements a priority queue of integers using a
// min-heap represented as an array.
// Building Java Programs 3rd Edition

import java.util.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class HeapIntPriorityQueue 
{
    private int[] heap;
    private int size;

    public HeapIntPriorityQueue() {
        heap = new int[10];
        heap[0] = Integer.MAX_VALUE;
        size = 0;
    }

    public HeapIntPriorityQueue(int[] nums) {
        this();        
        for (int num : nums) {
            add(num);
        }
    }

    // Returns the number of elements in the queue.    
    public int size()
    {
        return size;
    }

    public void add(int num) {
        if (size + 1 >= heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }

        if (size == 0) {
            heap[1] = num;
        } else {
            heap[size + 1] = num;
            int curIndex = size + 1;
            int parentIndex = getParentIndex(curIndex);

            while ((curIndex > 1) && (heap[curIndex] < heap[parentIndex])) {
                swap(heap, curIndex, parentIndex);
                curIndex = parentIndex;
                parentIndex = getParentIndex(parentIndex);
            }
        }

        size++;
    }

    // Removes and returns the minimum value in the queue.
    // If the queue is empty, throws a NoSuchElementException.    
    public int remove() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int min = heap[1];
        heap[1] = heap[size];
        heap[size] = 0;
        size--;
        int curIndex = 1;
        int leftChildIndex = getLeftChildIndex(curIndex);
        int rightChildIndex = getRightChildIndex(curIndex);

        while ((heap[curIndex] > heap[leftChildIndex]) || (heap[curIndex] > heap[rightChildIndex])) {
            int smallerChildIndex;

            if (heap[leftChildIndex] < heap[rightChildIndex]) {
                smallerChildIndex = leftChildIndex;
            } else {
                smallerChildIndex = rightChildIndex;
            }

            swap(heap, curIndex, smallerChildIndex);
            curIndex = smallerChildIndex;
            leftChildIndex = getLeftChildIndex(curIndex);
            rightChildIndex = getRightChildIndex(curIndex);
        }

        return min;       
    }

    // Returns true if there are no elements in this queue.    
    public boolean isEmpty() {
        return size == 0;
    }
    // Returns the minimum value in the queue without modifying the queue.
    // If the queue is empty, throws a NoSuchElementException.
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap[1];
    }    

    // Swap the 2 indexes.
    private void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    // helpers for navigating indexes up/down the tree    
    private int getParentIndex(int childIndex) {
        return childIndex / 2;
    }

    private int getLeftChildIndex(int parentIndex) {
        int leftChildIndex = parentIndex * 2;

        return (leftChildIndex <= size) ? leftChildIndex : 0;
    }

    private int getRightChildIndex(int parentIndex) {
        int rightChildIndex = parentIndex * 2 + 1;

        return (rightChildIndex <= size) ? rightChildIndex : 0;
    }

    // 
    // ===================================================================  
    // ================  CODE TO Exercises 13-15 HERE ========================
    // ===================================================================    
    // Exercise #13
    // Returns the internal heap array used to implement the queue.
    // Not generally considered good practice, but provided to
    // illustrate heap sort.
    public int[] toArray() 
    {
        int[] r = new int[size];
        for (int i=0; i<size; i++) {
            r[i] = heap[i+1];
        }
        return r;
    }

    // Exercise #14    
    // Returns a string representation of this queue, such as "[10, 20, 30]";
    // The elements are NOT guaranteed to be listed in sorted order.
    public String toString()
    {
        String r = "[";

        for (int i=1; i<=size; i++) {
            r += heap[i];
            if (i < size) {
                r += ", ";
            }
        }
        r += "]";
        return r;
    }    

    // Exercise #15
    //  Accepts another HeapIntPriorityQueue as a parameter and adds all elements from the 
    // other queue into the current queue, maintaining proper heap order such that the elements 
    // will still come out in ascending order when they are removed. Your code should not modify 
    // the queue passed in as a parameter.        
    public void merge(HeapIntPriorityQueue that)
    {  
        for (int i=1; i<= that.size; i++) {
            add(that.heap[i]);
        }
    }

} // HeapIntPriorityQueue
