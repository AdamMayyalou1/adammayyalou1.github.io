import java.awt.Color;

import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import tester.Tester;

//represents a list of words
interface ILoWord {
  

  //Sorts a list of words
  ILoWord sort();

  //Determines whether the list is sorted
  boolean isSorted();

  //Interswaps elements between this list and that list
  ILoWord interleave(ILoWord other);

  //Merges two sorted lists into a new sorted list
  ILoWord merge(ILoWord other);

  //Checks if each active word in the list has the given letter
  //at the front of the word and removes it
  ILoWord checkAndReduce(String letter);

  //Adds a word to the end of the list
  ILoWord addToEnd(IWord word);

  //Filters out any empty words
  ILoWord filterOutEmpties();

  //Draws each word in the list onto the given scene
  WorldScene draw(WorldScene scene);

  // Helper to insert words
  ILoWord insert(IWord word);

  // Helper for sort
  boolean isSortedHelper(IWord word);
  
  
}

//represents an empty list of words
class MtLoWord implements ILoWord {
  IWord first;
  ILoWord rest;

  /*
   * // ** TEMPLATE ** methods: ... this.sort() ... -- ILoWord ...
   * this.insert(IWord word) ... -- ILoWord ... this.isSorted() ... -- boolean ...
   * this.isSortedHelper(IWord word) ... -- boolean ... this.interleave(ILoWord
   * that) ... -- ILoWord ... this.merge(ILoWord that) ... -- ILoWord ...
   * this.checkAndReduce(String letter) ... -- ILoWord ... this.addToEnd(IWord
   * word) ... -- ILoWord ... this.filterOutEmpties() ... -- ILoWord ...
   * this.draw(WorldScene scene) ... -- WorldScene
   */

  // An empty list is automatically sorted
  public ILoWord sort() {
    return this;
  }

  // Once reaching the empty list, return the results
  public ILoWord insert(IWord word) {
    return new ConsLoWord(word, this);
  }

  // An empty list is already sorted
  public boolean isSorted() {
    return true;
  }

  // An empty list is already sorted
  public boolean isSortedHelper(IWord word) {
    return true;
  }

  // At empty, returns that element (leftover)
  public ILoWord interleave(ILoWord that) {
    return that;
  }

  // At empty, return that (leftover
  public ILoWord merge(ILoWord that) {
    return that;
  }

  // If empty, there is nothing left to reduce
  public ILoWord checkAndReduce(String letter) {
    return this;
  }

  // If empty, reached the end and add the word
  public ILoWord addToEnd(IWord word) {
    return new ConsLoWord(word, this);
  }

  // If empty, no empty words to filter out
  public ILoWord filterOutEmpties() {
    return this;
  }

  // Finished drawing scenes
  public WorldScene draw(WorldScene scene) {
    return scene;
  }
}

class ConsLoWord implements ILoWord {
  IWord first;
  ILoWord rest;

  ConsLoWord(IWord first, ILoWord rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * // ** TEMPLATE ** fields: ... this.first ... -- IWord ... this.rest ... --
   * ILoWord
   * 
   * methods: ... this.sort() ... -- ILoWord ... this.insert(IWord word) ... --
   * ILoWord ... this.isSorted() ... -- boolean ... this.isSortedHelper(IWord
   * word) ... -- boolean ... this.interleave(ILoWord that) ... -- ILoWord ...
   * this.merge(ILoWord that) ... -- ILoWord ... this.checkAndReduce(String
   * letter) ... -- ILoWord ... this.addToEnd(IWord word) ... -- ILoWord ...
   * this.filterOutEmpties() ... -- ILoWord ... this.draw(WorldScene scene) ... --
   * WorldScene
   * 
   * methods for fields: ... this.first.word ... String
   */

  // produces a list of word in this non-empty list, sorted
  // in alphabetical order
  public ILoWord sort() {
    return this.rest.sort().insert(this.first);
  }

  // Inserts the given word to into the list of words
  public ILoWord insert(IWord word) {
    if (word.compare(this.first) < 0) {
      return new ConsLoWord(this.first, this.rest.insert(word));
    }
    return new ConsLoWord(word, this);
  }

  // Returns a boolean determining whether the list is sorted
  public boolean isSorted() {
    return this.rest.isSortedHelper(this.first);
  }

  // Looks through the list and determines whether it is sorted
  public boolean isSortedHelper(IWord word) {
    return word.compare(this.first) >= 0 && this.rest.isSortedHelper(this.first);
  }

  // Swaps elements between this list and the given list to form a new list
  public ILoWord interleave(ILoWord that) {
    return new ConsLoWord(this.first, that.interleave(this.rest));
  }

  //Merges two sorted lists into a new sorted list
  public ILoWord merge(ILoWord that) {
    if (that instanceof MtLoWord) {
      return this;
    }
    ConsLoWord that2 = (ConsLoWord) that;
    if (this.first.compare(that2.first) <= 0) {
      return new ConsLoWord(this.first, this.rest.merge(that));
    }
    return new ConsLoWord(that2.first, this.merge(that2.rest));
  }


  // Takes in a string of length 1
  // and reduces an active word by the first letter
  // if said first letter matches
  public ILoWord checkAndReduce(String letter) {
    IWord reducedWord = this.first.checkAndReduce(letter);
    return new ConsLoWord(reducedWord, this.rest.checkAndReduce(letter));
  }

  // Add word to the end of the list
  public ILoWord addToEnd(IWord word) {
    return new ConsLoWord(this.first, this.rest.addToEnd(word));
  }

  //Filters out any empty words
  public ILoWord filterOutEmpties() {
    if (this.first.isEmpty()) {
      return this.rest.filterOutEmpties();
    }
    return new ConsLoWord(this.first, this.rest.filterOutEmpties());
  }

  //Draws each word in the list onto the given scene
  public WorldScene draw(WorldScene scene) {
    scene = this.first.draw(scene);

    return this.rest.draw(scene);
  }
}

//represents a word in the ZType game
interface IWord {

  //Calls the necessary compare method
  int compare(IWord word);

  //Checks if the first letter of the string matches the 
  //given letter and removes it if it was an active word
  IWord checkAndReduce(String letter);

  //Compares this word with an active word
  int compareWithActive(ActiveWord word);

  //Compares this word with an inactive word
  int compareWithInactive(InactiveWord word);

  //Determines if the word is empty (length of 0)
  boolean isEmpty();

  //Draws the word onto the given scene
  //Color is determined by whether the word is active or not
  WorldScene draw(WorldScene scene);
}

//represents an active word in the ZType game
class ActiveWord implements IWord {
  String word;
  int x;
  int y;

  ActiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }

  /*
   * // ** TEMPLATE **
   * 
   * methods: ... this.compare(IWord that) ... -- int ...
   * this.compareWithActive(ActiveWord that) ... -- int ...
   * this.compareWithInactive(InactiveWord that) ... -- int ...
   * this.checkAndReduce(String letter) ... -- IWord ... this.isEmpty() ... --
   * boolean ... this.draw(WorldScene scene) ... -- WorldScene
   * 
   * fields: ... this.word ... String ... this.x ... int ... this.y ... int
   */

  // Calls the appropriate compare method
  public int compare(IWord that) {
    return that.compareWithActive(this);
  }

  // Compare with another ActiveWord
  public int compareWithActive(ActiveWord that) {
    return this.word.toLowerCase().compareTo(that.word.toLowerCase());
  }

  // Compare with an InactiveWord
  public int compareWithInactive(InactiveWord that) {
    return this.word.toLowerCase().compareTo(that.word.toLowerCase());
  }

  // Checks the given letter to determine whether to reduce
  public IWord checkAndReduce(String letter) {
    if (this.word.startsWith(letter)) {
      return new ActiveWord(this.word.substring(1), this.x, this.y);
    }
    return this;
  }

  // Checks whether the active word is empty
  public boolean isEmpty() {
    return this.word.isEmpty();
  }

  // Draws the scene with a green word
  public WorldScene draw(WorldScene scene) {
    TextImage image = new TextImage(this.word, 13, Color.GREEN);
    scene = scene.placeImageXY(image, x, y);
    return scene;
  }

}

//represents an inactive word in the ZType game
class InactiveWord implements IWord {
  String word;
  int x;
  int y;

  InactiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }

  /*
   * // ** TEMPLATE **
   * 
   * methods: ... this.compare(IWord that) ... -- int ...
   * this.compareWithActive(ActiveWord that) ... -- int ...
   * this.compareWithInactive(InactiveWord that) ... -- int ...
   * this.checkAndReduce(String letter) ... -- IWord ... this.isEmpty() ... --
   * boolean ... this.draw(WorldScene scene) ... -- WorldScene
   * 
   * fields: ... this.word ... String ... this.x ... int ... this.y ... int
   */

  // Calls the appropriate compare method
  public int compare(IWord that) {
    return that.compareWithInactive(this);
  }

  // Compare with another ActiveWord
  public int compareWithActive(ActiveWord that) {
    return this.word.toLowerCase().compareTo(that.word.toLowerCase());
  }

  // Compare with an InactiveWord
  public int compareWithInactive(InactiveWord that) {
    return this.word.toLowerCase().compareTo(that.word.toLowerCase());
  }

  //No need to reduce an inactive word
  public IWord checkAndReduce(String letter) {
    return this;
  }

  // Checks whether the inactive word is empty
  public boolean isEmpty() {
    return this.word.isEmpty();
  }

  // Draws the scene with a red word
  public WorldScene draw(WorldScene scene) {
    TextImage image = new TextImage(this.word, 13, Color.RED);
    scene = scene.placeImageXY(image, x, y);
    return scene;
  }
}

//all examples and tests for ILoWord
class ExamplesWordLists {

  IWord activeWord1 = new ActiveWord("Hello", 50, 50);
  IWord activeWord2 = new ActiveWord("Goodmorning", 200, 150);
  IWord activeWordEmpty = new ActiveWord("", 0, 0);

  IWord inactiveWord1 = new InactiveWord("Done", 25, 50);
  IWord inactiveWord2 = new InactiveWord("Midnight", 75, 75);
  IWord inactiveWordEmpty = new InactiveWord("", 0, 0);

  ILoWord emptyList = new MtLoWord();
  ILoWord list1 = new ConsLoWord(activeWord1, new ConsLoWord(activeWord2, emptyList));
  ILoWord list2 = new ConsLoWord(inactiveWord1, new ConsLoWord(inactiveWord2, emptyList));
  ILoWord list3 = new ConsLoWord(activeWord1, new ConsLoWord(inactiveWord2, emptyList));
  ILoWord list4 = new ConsLoWord(activeWord1, new ConsLoWord(activeWordEmpty, emptyList));
  ILoWord list1Sorted = new ConsLoWord(activeWord2, new ConsLoWord(activeWord1, emptyList));

  // test for sort that tests an empty list, and a list that is
  // either already sorted or not
  boolean testSort(Tester t) {
    return t.checkExpect(this.emptyList.sort(), emptyList)
        && t.checkExpect(this.list1.sort(),
            new ConsLoWord(activeWord2, new ConsLoWord(activeWord1, emptyList)))
        && t.checkExpect(this.list2.sort(), list2);
  }

  // test for isSorted that tests an empty list, and a list that is
  // either already sorted or not
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.emptyList.isSorted(), true)
        && t.checkExpect(this.list1.isSorted(), false)
        && t.checkExpect(this.list2.isSorted(), true);
  }

  // test for interleave that tests empty lists, two basic lists,
  // and a list and empty list
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.emptyList.interleave(emptyList), emptyList)
        && t.checkExpect(this.list1.interleave(list2),
            new ConsLoWord(activeWord1,
                new ConsLoWord(inactiveWord1,
                    new ConsLoWord(activeWord2, new ConsLoWord(inactiveWord2, emptyList)))))
        && t.checkExpect(this.list2.interleave(emptyList), list2);
  }

  // test for merge that tests empty lists, two basic lists,
  // and a list and empty list
  boolean testMerge(Tester t) {
    return t.checkExpect(this.emptyList.merge(emptyList), emptyList)
        && t.checkExpect(this.list2.merge(list1Sorted),
            new ConsLoWord(inactiveWord1,
                new ConsLoWord(activeWord2,
                    new ConsLoWord(activeWord1, new ConsLoWord(inactiveWord2, emptyList)))))
        && t.checkExpect(this.list2.merge(emptyList), list2);
  }

  // test for checkAndReduce that tests empty lists, two basic lists,
  // and a list and empty list
  boolean testCheckAndReduce(Tester t) {
    return t.checkExpect(this.emptyList.checkAndReduce("A"), emptyList)
        && t.checkExpect(this.list1.checkAndReduce("H"),
            new ConsLoWord(new ActiveWord("ello", 50, 50), new ConsLoWord(activeWord2, emptyList)))
        && t.checkExpect(this.list2.checkAndReduce("G"), list2);
  }

  // test for addToEnd that tests an empty list and a word, a list and word,
  // and a list and empty word
  boolean testAddToEnd(Tester t) {
    return t.checkExpect(this.emptyList.addToEnd(activeWord1),
        new ConsLoWord(activeWord1, emptyList))
        && t.checkExpect(this.list1.addToEnd(inactiveWord2),
            new ConsLoWord(activeWord1,
                new ConsLoWord(activeWord2, new ConsLoWord(inactiveWord2, emptyList))))
        && t.checkExpect(this.emptyList.addToEnd(activeWordEmpty),
            new ConsLoWord(activeWordEmpty, emptyList));
  }

  // test for filterOutEmpties that tests an empty list, and a list that is
  // either already sorted or not
  boolean testFilterOutEmpties(Tester t) {
    return t.checkExpect(this.emptyList.filterOutEmpties(), emptyList)
        && t.checkExpect(this.list4.filterOutEmpties(), new ConsLoWord(activeWord1, emptyList))
        && t.checkExpect(this.list2.filterOutEmpties(), list2);
  }

  // test for draw that tests an empty list and non-empty list with
  // active and inactive words
  boolean testDraw(Tester t) {
    return t.checkExpect(this.emptyList.draw(new WorldScene(200, 200)), new WorldScene(200, 200))
        && t.checkExpect(this.list3.draw(new WorldScene(200, 200)),
            new WorldScene(200, 200).placeImageXY(new TextImage("Hello", Color.GREEN), 50, 50)
                .placeImageXY(new TextImage("Midnight", Color.RED), 75, 75));
  }
}