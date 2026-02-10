import tester.*;
import java.util.function.Predicate;

// Class that represents an abstract Node to extend
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // Constructor for ANode
  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }

  // Method that counts the amount of Nodes in the list
  abstract int countNodes();

  // Method to update next
  void updateNext(ANode<T> newNode) {
    this.next = newNode;
  }

  // Method to update prev
  void updatePrev(ANode<T> newNode) {
    this.prev = newNode;
  }

  // Method that removes a node
  abstract T remove();

  // Method that searches for a node that satisfies the predicate
  abstract ANode<T> findNode(Predicate<T> pred);
}

// Class that represents the Sentinel node
class Sentinel<T> extends ANode<T> {
  // Constructor for Sentinel
  Sentinel() {
    super(null, null);
    this.next = this;
    this.prev = this;
  }

  // End of the list, return 0 (if empty also returns 0)
  int countNodes() {
    return 0;
  }

  // Empty list, throw exception
  T remove() {
    throw new RuntimeException("Cannot remove from an empty Deque");
  }

  // End of list, return this node as target wasn't found
  ANode<T> findNode(Predicate<T> pred) {
    return this;
  }
}

// Class that represents a generic node
class Node<T> extends ANode<T> {
  T data;

  // Constructor that takes a type T
  Node(T data) {
    super(null, null);
    this.data = data;
  }

  // Constructor that takes a type T and two ANodes<T>
  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Next and prev nodes cannot be null");
    }
    this.data = data;
    next.prev = this;
    prev.next = this;
  }

  // Increments count by one and continues searching the list
  int countNodes() {
    return 1 + this.next.countNodes();
  }

  // Removes this node from the list and returns its data
  T remove() {
    this.next.updatePrev(this.prev);
    this.prev.updateNext(this.next);
    return this.data;
  }

  // Tests the predicate and returns this node if it satisfies it.
  // Otherwise, continue searching the list
  ANode<T> findNode(Predicate<T> pred) {
    if (pred.test(data)) {
      return this;
    }
    return this.next.findNode(pred);
  }
}

// A class representing the Deque
class Deque<T> {
  Sentinel<T> header;

  // Constructor for Deque with no arguments
  Deque() {
    this.header = new Sentinel<>();
  }

  // Constructor for convenience
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // Returns the number of nodes in a Deque
  int size() {
    return header.next.countNodes();
  }

  // Inserts the given data at the front of the list
  void addAtHead(T data) {
    new Node<>(data, header.next, header);
  }

  // Inserts the given data at the tail of the list
  void addAtTail(T data) {
    new Node<>(data, header, header.prev);
  }

  // Removes the first node from the Deque and throws an exception
  // if empty
  T removeFromHead() {
    return header.next.remove();
  }

  // Removes the second node from the Deque and throws an exception
  // if empty
  T removeFromTail() {
    return header.prev.remove();
  }

  // Finds the first node in the Deque that satisfies the predicate
  ANode<T> find(Predicate<T> predicate) {
    return header.next.findNode(predicate);
  }
}

class ExamplesDeque {

  // Example 1
  Deque<String> deque1 = new Deque<>();

  // Example 2
  Sentinel<String> sentinel = new Sentinel<>();

  Node<String> node1 = new Node<>("abc", sentinel, sentinel);
  Node<String> node2 = new Node<>("bcd", sentinel, node1);
  Node<String> node3 = new Node<>("cde", sentinel, node2);
  Node<String> node4 = new Node<>("def", sentinel, node3);

  Deque<String> deque2 = new Deque<>(sentinel);

  // Example 3
  Sentinel<Integer> sentinel2 = new Sentinel<>();

  Node<Integer> node5 = new Node<>(10, sentinel2, sentinel2);
  Node<Integer> node6 = new Node<>(5, sentinel2, node5);
  Node<Integer> node7 = new Node<>(15, sentinel2, node6);
  Node<Integer> node8 = new Node<>(25, sentinel2, node7);

  Deque<Integer> deque3 = new Deque<>(sentinel2);

  // Reset examples before each test
  void initData() {
    // Reset example 1
    deque1 = new Deque<>();

    // Reset example 2
    sentinel = new Sentinel<>();
    node1 = new Node<>("abc", sentinel, sentinel);
    node2 = new Node<>("bcd", sentinel, node1);
    node3 = new Node<>("cde", sentinel, node2);
    node4 = new Node<>("def", sentinel, node3);
    deque2 = new Deque<>(sentinel);

    // Reset example 3
    sentinel2 = new Sentinel<>();
    node5 = new Node<>(10, sentinel2, sentinel2);
    node6 = new Node<>(5, sentinel2, node5);
    node7 = new Node<>(15, sentinel2, node6);
    node8 = new Node<>(25, sentinel2, node7);
    deque3 = new Deque<>(sentinel2);
  }

  // Test for constructor exception
  void testNodeException(Tester t) {
    t.checkConstructorException(new IllegalArgumentException("Next and prev nodes cannot be null"),
        "Node", 5, null, null);
    t.checkConstructorException(new IllegalArgumentException("Next and prev nodes cannot be null"),
        "Node", 5, sentinel, null);
    t.checkConstructorException(new IllegalArgumentException("Next and prev nodes cannot be null"),
        "Node", 5, null, sentinel);
  }

  // Test size method
  void testSize(Tester t) {
    initData();
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(deque3.size(), 4);
  }

  // Test addAtHead method
  void testAddAtHead(Tester t) {
    initData();
    // Test on empty deque
    t.checkExpect(deque1, new Deque<>());

    deque1.addAtHead("first");

    t.checkExpect(deque1.size(), 1);
    //Sentinel<String> sentinel5 = new Sentinel<>();
    //t.checkExpect(deque1, new Node<>("first", sentinel5, sentinel5));
    t.checkExpect(((Node<String>) deque1.header.next).data, "first");
    t.checkExpect(deque1.header.next.next, deque1.header);
    t.checkExpect(deque1.header.prev, deque1.header.next);

    // Test on non-empty deque
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(((Node<String>) deque2.header.next).data, "abc");

    deque2.addAtHead("new");

    t.checkExpect(deque2.size(), 5);
    t.checkExpect(((Node<String>) deque2.header.next).data, "new");
    t.checkExpect(deque2.header.next.next, node1);
  }

  // Test addAtTail method
  void testAddAtTail(Tester t) {
    initData();
    // Test on empty deque
    t.checkExpect(deque1.size(), 0);
    t.checkExpect(deque1.header.next, deque1.header);
    t.checkExpect(deque1.header.prev, deque1.header);

    deque1.addAtTail("last");

    t.checkExpect(deque1.size(), 1);
    t.checkExpect(((Node<String>) deque1.header.prev).data, "last");
    t.checkExpect(deque1.header.prev.prev, deque1.header);
    t.checkExpect(deque1.header.next, deque1.header.prev);

    // Test on non-empty deque
    t.checkExpect(deque2.size(), 4);
    t.checkExpect(((Node<String>) deque2.header.prev).data, "def");

    deque2.addAtTail("new");

    t.checkExpect(deque2.size(), 5);
    t.checkExpect(((Node<String>) deque2.header.prev).data, "new");
    t.checkExpect(deque2.header.prev.prev, node4);
  }

  // Test removeFromHead method
  void testRemoveFromHead(Tester t) {
    initData();

    // Test on an empty deque
    t.checkException(new RuntimeException("Cannot remove from an empty Deque"), deque1,
        "removeFromHead");

    // Test on non-empty deque
    String removed = deque2.removeFromHead();
    t.checkExpect(removed, "abc");
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(((Node<String>) deque2.header.next).data, "bcd");
  }

  // Test removeFromTail method
  void testRemoveFromTail(Tester t) {
    initData();

    // Test on an empty deque
    t.checkException(new RuntimeException("Cannot remove from an empty Deque"), deque1,
        "removeFromTail");

    // Test on non-empty deque
    String removed = deque2.removeFromTail();
    t.checkExpect(removed, "def");
    t.checkExpect(deque2.size(), 3);
    t.checkExpect(((Node<String>) deque2.header.prev).data, "cde");

    // Test removing multiple items
    removed = deque2.removeFromTail();
    t.checkExpect(removed, "cde");
    t.checkExpect(deque2.size(), 2);
    t.checkExpect(((Node<String>) deque2.header.prev).data, "bcd");
  }

  // Test find method
  void testFind(Tester t) {
    initData();
    // Test on empty deque
    t.checkExpect(deque1.find(s -> s.equals("anything")), deque1.header);

    // Test element that exists
    t.checkExpect(deque2.find(s -> s.equals("cde")), node3);

    // Test element that doesn't exist
    t.checkExpect(deque2.find(s -> s.equals("xyz")), deque2.header);

    // Test with Integer deque
    t.checkExpect(deque3.find(i -> i > 20), node8);
    t.checkExpect(deque3.find(i -> i > 7), node5);
  }
}
