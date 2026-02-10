import tester.*;
import java.util.Comparator;

// Represents an Abstract Binary Search Tree
abstract class ABST<T> {
  Comparator<T> order;

  ABST(Comparator<T> order) {
    this.order = order;
  }

  // Inserts a node into the tree
  abstract ABST<T> insert(T item);

  // Checks if the item is present
  abstract boolean present(T item);

  // Gets the left most node within the tree
  abstract T getLeftmost();

  // A helper to assist in retrieving the left most node
  abstract T getLeftmostHelper(T item);

  // Returns a tree including all nodes but the left most
  abstract ABST<T> getRight();

  // Checks whether this tree is a leaf
  abstract boolean isLeaf();

  // Checks whether both trees have matching structure and data
  abstract boolean sameTree(ABST<T> other);

  // A helper used to help determine whether the trees are the same
  abstract boolean sameTreeHelper(T data, ABST<T> left, ABST<T> right);

  // Builds an IList using the tree
  abstract IList<T> buildList();

  // Checks whether both trees have the same order and data
  abstract boolean sameData(ABST<T> other);
}

// Represents a leaf, which holds no data
class Leaf<T> extends ABST<T> {

  // Constructor for leaf
  Leaf(Comparator<T> order) {
    super(order);
  }

  // Returns a node based on the given item
  ABST<T> insert(T item) {
    return new Node<>(order, item, new Leaf<>(order), new Leaf<>(order));
  }

  // The current node is a leaf, so the item can't be present
  boolean present(T item) {
    return false;
  }

  // The current node is a leaf, throw an exception as there is no more left
  T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // The current node is a leaf, thus, return the previously stored node
  // as we have found left most
  T getLeftmostHelper(T current) {
    return current;
  }

  // Throw an exception, there is no right for an empty tree
  ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // This is a leaf, thus, true
  boolean isLeaf() {
    return true;
  }

  // If the other tree is a leaf, they are the same
  boolean sameTree(ABST<T> other) {
    return other.isLeaf();
  }

  // This is a leaf, but the other tree contains data, thus, false
  boolean sameTreeHelper(T data, ABST<T> left, ABST<T> right) {
    return false;
  }

  // Return an empty list due to this being a leaf
  IList<T> buildList() {
    return new MtList<>();
  }

  // If the other tree is a leaf, the data is the same
  boolean sameData(ABST<T> other) {
    return other.isLeaf();
  }
}

// Represents a node which contains data, and branches to the left and right
class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  // Constructor for Node
  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }

  // Compares the data and item to determine which node to traverse to insert
  // a new node
  ABST<T> insert(T item) {
    if (order.compare(item, data) < 0) {
      return new Node<>(order, data, left.insert(item), right);
    }
    return new Node<>(order, data, left, right.insert(item));
  }

  // Compares the data and item to determine which node to traverse, until it
  // either finds a matching node or fails
  boolean present(T item) {
    int comp = order.compare(item, data);
    if (comp == 0) {
      return true;
    }
    else if (comp < 0) {
      return left.present(item);
    }
    return right.present(item);
  }

  // Calls a helper to traverse the tree while storing the current data
  T getLeftmost() {
    return left.getLeftmostHelper(data);
  }

  // Traverses left through the tree while storing the current data
  T getLeftmostHelper(T current) {
    return left.getLeftmostHelper(data);
  }

  // Traverses the tree recursively to determine what the left most node is
  // before returning the rest of the tree
  ABST<T> getRight() {
    if (left.isLeaf()) {
      return right;
    }
    return new Node<>(order, data, left.getRight(), right);
  }

  // This is a node, thus, false
  boolean isLeaf() {
    return false;
  }

  // Checks if the other tree is a leaf, before traversing the rest of the list
  // using the helper
  boolean sameTree(ABST<T> other) {
    if (other.isLeaf()) {
      return false;
    }
    return other.sameTreeHelper(data, left, right);
  }

  // Compares the data with the data of other nodes to determine if the tree
  // is the same
  boolean sameTreeHelper(T data, ABST<T> left, ABST<T> right) {
    return order.compare(this.data, data) == 0 && this.left.sameTree(left)
        && this.right.sameTree(right);
  }

  // Builds a list using the tree by appending each side
  IList<T> buildList() {
    return left.buildList().append(new ConsList<>(data, right.buildList()));
  }

  // Checks whether the trees have the same data by turning them into lists,
  // then checking if they are equal
  boolean sameData(ABST<T> other) {
    IList<T> thisList = this.buildList();
    IList<T> otherList = other.buildList();
    return thisList.equalsList(otherList);
  }
}

// Represents a book with a title, author, and price
class Book {
  String title;
  String author;
  int price;

  // Constructor for book
  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

}

// The class for comparing books by title
class BooksByTitle implements Comparator<Book> {
  // Compares book 1 and book 2, returns a positive number if title 1
  // comes first, negative if title 2 comes first, or 0 if both are equal
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title);
  }
}

// The class for comparing books by author
class BooksByAuthor implements Comparator<Book> {
  // Compares book 1 and book 2, returns a positive number if author 1
  // comes first, negative if author 2 comes first, or 0 if both are equal
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author);
  }
}

// The class for comparing books by price
class BooksByPrice implements Comparator<Book> {
  // Compares book 1 and book 2, returns a positive number if price 1
  // is greater, negative if price 2 is greater, or 0 if both are equal
  public int compare(Book b1, Book b2) {
    return b1.price - b2.price;
  }
}

// represents a generic list
interface IList<T> {
  // Adds the other list to the end of the first
  IList<T> append(IList<T> other);

  // Determines if this list is Empty
  boolean isEmpty();

  // Checks if both lists are equal
  boolean equalsList(IList<T> other);

  // Helper method that assists in determining if the lists equal
  boolean equalsHelper(T first, IList<T> rest);
}

// represents a generic empty list
class MtList<T> implements IList<T> {
  // Constructor for MtList
  MtList() {
  }

  // If this list is empty, return other
  public IList<T> append(IList<T> other) {
    return other;
  }

  // An empty list is empty, so true
  public boolean isEmpty() {
    return true;
  }

  // Checks if the other list is empty to determine equality
  public boolean equalsList(IList<T> other) {
    return other.isEmpty();
  }

  // Since this list is empty, return false
  public boolean equalsHelper(T first, IList<T> rest) {
    return false;
  }
}

// represents a generic non-empty list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  // Constructor for ConsList
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // Adds the other list to the end of the first list
  public IList<T> append(IList<T> other) {
    return new ConsList<>(first, rest.append(other));
  }

  // List contains elements, so false
  public boolean isEmpty() {
    return false;
  }

  // Calls a helper method to bypass the issues of the type of other
  public boolean equalsList(IList<T> other) {
    return other.equalsHelper(first, rest);
  }

  // Compares the first and rest of both lists
  public boolean equalsHelper(T first, IList<T> rest) {
    return this.first.equals(first) && this.rest.equalsList(rest);
  }
}

class BinarySearchExamples {
  Comparator<Book> byTitle = new BooksByTitle();
  Comparator<Book> byAuthor = new BooksByAuthor();
  Comparator<Book> byPrice = new BooksByPrice();

  Book java = new Book("Java for Beginners!", "Arthur", 50);
  Book dorian = new Book("The Picture of Dorian Gray", "Oscar Wilde", 20);
  Book goldfinch = new Book("Goldfinch", "Donna Tart", 15);

  ABST<Book> emptyTree = new Leaf<>(byTitle);
  ABST<Book> tree1 = new Node<>(byTitle, new Book("Java for Beginners!", "Arthur", 50),
      new Leaf<>(byTitle), new Leaf<>(byTitle));
  ABST<Book> tree2 = tree1.insert(new Book("The Picture of Dorian Gray", "Oscar Wilde", 20));
  ABST<Book> tree3 = tree2.insert(new Book("Goldfinch", "Donna Tart", 15));

  ABST<Book> emptyTree2 = new Leaf<>(byTitle);
  ABST<Book> tree4 = new Node<>(byAuthor, new Book("Java for Beginners!", "Arthur", 50),
      new Leaf<>(byTitle), new Leaf<>(byTitle));
  ABST<Book> tree5 = tree4.insert(new Book("The Picture of Dorian Gray", "Oscar Wilde", 20));
  ABST<Book> tree6 = tree5.insert(new Book("Goldfinch", "Donna Tart", 15));

  ABST<Book> emptyTree3 = new Leaf<>(byPrice);
  ABST<Book> tree7 = new Node<>(byPrice, new Book("Java for Beginners!", "Arthur", 50),
      new Leaf<>(byPrice), new Leaf<>(byPrice));
  ABST<Book> tree8 = tree7.insert(new Book("The Picture of Dorian Gray", "Oscar Wilde", 20));
  ABST<Book> tree9 = tree8.insert(new Book("Goldfinch", "Donna Tart", 15));

  // ABST<Integer> numTree1 = new Node<>

  // Test for the insert method
  boolean testInsert(Tester t) {
    return t.checkExpect(emptyTree.insert(java), tree1)
        && t.checkExpect(tree1.insert(dorian), tree2)
        && t.checkExpect(tree2.insert(goldfinch), tree3)
        && t.checkExpect(tree4.insert(dorian), tree5)
        && t.checkExpect(tree5.insert(goldfinch), tree6)
        && t.checkExpect(tree7.insert(dorian), tree8)
        && t.checkExpect(tree8.insert(goldfinch), tree9);
  }

  // Test for the getLeftmost method
  boolean testGetLeftmost(Tester t) {
    return t.checkException(new RuntimeException("No leftmost item of an empty tree"), emptyTree,
        "getLeftmost") && t.checkExpect(tree1.getLeftmost(), java)
        && t.checkExpect(tree2.getLeftmost(), java) && t.checkExpect(tree3.getLeftmost(), goldfinch)
        && t.checkExpect(tree5.getLeftmost(), java) && t.checkExpect(tree6.getLeftmost(), java)
        && t.checkExpect(tree8.getLeftmost(), dorian)
        && t.checkExpect(tree9.getLeftmost(), goldfinch);
  }

  // Test for the isLeaf method
  boolean testIsLeaf(Tester t) {
    return t.checkExpect(emptyTree.isLeaf(), true) && t.checkExpect(tree1.isLeaf(), false);
  }

  // Test for the sameTree method
  boolean testSameTree(Tester t) {
    return t.checkExpect(emptyTree.sameTree(new Leaf<>(byTitle)), true)
        && t.checkExpect(tree1.sameTree(tree1), true) && t.checkExpect(tree1.sameTree(tree2), false)
        && t.checkExpect(tree5.sameTree(tree6), false)
        && t.checkExpect(tree4.sameTree(tree6), false) && t.checkExpect(tree8.sameTree(tree8), true)
        && t.checkExpect(tree9.sameTree(tree9), true);
  }

  // Test for the sameData method
  boolean testSameData(Tester t) {
    return t.checkExpect(emptyTree.sameData(new Leaf<>(byTitle)), true)
        && t.checkExpect(tree1.sameData(tree1), true) && t.checkExpect(tree1.sameData(tree2), false)
        && t.checkExpect(tree5.sameData(tree5), true) && t.checkExpect(tree6.sameData(tree4), false)
        && t.checkExpect(tree8.sameData(tree9), false)
        && t.checkExpect(tree7.sameData(tree7), true);
  }

  // Test for the buildList method
  boolean testBuildList(Tester t) {
    IList<Book> listOfBooks1 = new ConsList<>(java, new MtList<>());
    IList<Book> listOfBooks2 = new ConsList<>(dorian, new ConsList<>(goldfinch, new MtList<>()));
    IList<Book> listOfBooks3 = new ConsList<>(goldfinch,
        new ConsList<>(java, new ConsList<>(dorian, new MtList<>())));

    return t.checkExpect(emptyTree.buildList(), new MtList<>())
        && t.checkExpect(tree1.buildList(), listOfBooks1)
        && t.checkExpect(tree2.buildList(),
            new ConsList<>(java, new ConsList<>(dorian, new MtList<>())))
        && t.checkExpect(tree3.buildList(), listOfBooks3);
  }

  IList<Book> listOfBooks1 = new ConsList<Book>(java, new MtList<Book>());
  IList<Book> listOfBooks2 = new ConsList<Book>(dorian,
      new ConsList<Book>(goldfinch, new MtList<Book>()));
  IList<Book> listOfBooks3 = new ConsList<Book>(java,
      new ConsList<Book>(dorian, new ConsList<Book>(goldfinch, new MtList<Book>())));

  IList<Integer> emptyNum = new MtList<Integer>();
  IList<Integer> listOfNums1 = new ConsList<Integer>(1,
      new ConsList<Integer>(5, new ConsList<Integer>(3, new MtList<Integer>())));
  IList<Integer> listOfNums2 = new ConsList<Integer>(9,
      new ConsList<Integer>(2, new MtList<Integer>()));
  IList<Integer> listOfNums3 = new ConsList<Integer>(1,
      new ConsList<Integer>(5, new ConsList<Integer>(3,
          new ConsList<Integer>(9, new ConsList<Integer>(2, new MtList<Integer>())))));

  // Test for the append method
  boolean testAppend(Tester t) {
    return t.checkExpect(listOfBooks1.append(listOfBooks2), listOfBooks3)
        && t.checkExpect(listOfNums1.append(listOfNums2), listOfNums3);
  }

  // Test for the equalsList method
  boolean testEqualsList(Tester t) {
    return t.checkExpect(listOfBooks1.equalsList(listOfBooks2), false)
        && t.checkExpect(listOfBooks3.equalsList(listOfBooks3), true);
  }

  // Test for isEmpty method
  boolean testIsEmpty(Tester t) {
    return t.checkExpect(emptyNum.isEmpty(), true) && t.checkExpect(listOfNums1.isEmpty(), false)
        && t.checkExpect(listOfBooks3.isEmpty(), false);
  }
}

class Number {
  int value;

  Number(int value) {
    this.value = value;
  }
}

// The class for comparing numbers
class NumbersByValue implements Comparator<Number> {
  public int compare(Number n1, Number n2) {
    return n1.value - n2.value;
  }
}

class NumberExamples {
  
  Comparator<Number> byValue = new NumbersByValue();

  ABST<Number> emptyTree = new Leaf<>(byValue);
  ABST<Number> tree1 = new Node<>(byValue, new Number(50), new Leaf<>(byValue),
      new Leaf<>(byValue));
  ABST<Number> tree2 = tree1.insert(new Number(20));
  ABST<Number> tree3 = tree2.insert(new Number(15));
  ABST<Number> tree4 = tree3.insert(new Number(70));
  ABST<Number> tree5 = tree4.insert(new Number(60));

  Number num50 = new Number(50);
  Number num20 = new Number(20);
  Number num15 = new Number(15);
  Number num70 = new Number(70);
  Number num60 = new Number(60);

  // Test for the insert method
  boolean testInsert(Tester t) {
    return t.checkExpect(emptyTree.insert(num50), tree1)
        && t.checkExpect(tree1.insert(num20), tree2) && t.checkExpect(tree2.insert(num15), tree3)
        && t.checkExpect(tree3.insert(num70), tree4) && t.checkExpect(tree4.insert(num60), tree5);
  }

  // Test for the present method
  boolean testPresent(Tester t) {
    return t.checkExpect(emptyTree.present(num50), false)
        && t.checkExpect(tree1.present(num50), true) && t.checkExpect(tree2.present(num20), true)
        && t.checkExpect(tree3.present(num15), true) && t.checkExpect(tree4.present(num70), true)
        && t.checkExpect(tree5.present(num60), true)
        && t.checkExpect(tree5.present(new Number(100)), false);
  }

  // Test for the getLeftmost method
  boolean testGetLeftmost(Tester t) {
    return t.checkException(new RuntimeException("No leftmost item of an empty tree"), emptyTree,
        "getLeftmost") && t.checkExpect(tree1.getLeftmost(), num50)
        && t.checkExpect(tree2.getLeftmost(), num20) && t.checkExpect(tree3.getLeftmost(), num15)
        && t.checkExpect(tree4.getLeftmost(), num15) && t.checkExpect(tree5.getLeftmost(), num15);
  }
}