import tester.*; // The tester library
import javalib.worldimages.*; // Images (e.g., RectangleImage, TextImage, etc.)
import javalib.impworld.*; // The abstract World class and the big-bang library
import java.awt.Color; // Colors
import java.util.ArrayList; // ArrayList for collections

// Define constants for the game.
interface Constants {
  int GAME_WIDTH = 800; // Width of the game screen
  int GAME_HEIGHT = 600; // Height of the game screen
}

// represents a single word square in the grid.
class WordSquare {
  int x;
  int y;
  int width;
  int height;
  String word;
  boolean selected;

  // Constructor for WordSquare
  WordSquare(int x, int y, int width, int height, String word) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.word = word;
    this.selected = false;
  }

  // Draws a word (either selected or not) for the game
  WorldImage draw() {
    Color bgColor;
    if (this.selected) {
      bgColor = Color.DARK_GRAY;
    }
    else {
      bgColor = Color.LIGHT_GRAY;
    }
    WorldImage background = new RectangleImage(this.width, this.height, OutlineMode.SOLID, bgColor);
    WorldImage text = new TextImage(this.word, 16, Color.BLACK);
    return new OverlayImage(text, background);
  }
}

// World class for the Connections game
class ConnectionsWorld extends World implements Constants {
  ArrayList<WordSquare> squares; // Grid of word squares.
  int triesLeft; // Number of tries left.
  ArrayList<String> words; // List of words to display.

  // Constructor for ConnectionsWorld that takes in a list of words
  ConnectionsWorld(ArrayList<String> words) {
    this.triesLeft = 4;
    this.words = words;
    this.squares = new ArrayList<WordSquare>();
    this.createGrid();
  }

  // Uses loops to create a 4×4 grid of WordSquare objects using the provided
  // words.
  void createGrid() {
    int rows = 4;
    int cols = 4;
    int tileWidth = GAME_WIDTH / cols;
    int tileHeight = (GAME_HEIGHT - 50) / rows; // Reserve space for UI elements.

    // Use the provided words list.
    for (int i = 0; i < this.words.size(); i++) {
      int row = i / cols;
      int col = i % cols;
      int x = col * tileWidth;
      int y = row * tileHeight;
      String word = this.words.get(i);
      squares.add(new WordSquare(x, y, tileWidth, tileHeight, word));
    }
  }

  // Creates the scene for the Connections game
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(GAME_WIDTH, GAME_HEIGHT);

    // Draws each word square.
    for (WordSquare ws : squares) {
      scene.placeImageXY(ws.draw(), ws.x + ws.width / 2, ws.y + ws.height / 2);
    }

    WorldImage triesDisplay = new TextImage("Tries Left: " + this.triesLeft, 16, Color.RED);
    scene.placeImageXY(triesDisplay, GAME_WIDTH / 2, GAME_HEIGHT - 20);

    return scene;
  }
}

class ExamplesConnectionsGame {
  ArrayList<String> wordList;
  ArrayList<String> wordList2;
  ArrayList<String> wordList3;

  // Constructor that initializes the set of words.
  ExamplesConnectionsGame() {
    this.wordList = new ArrayList<String>();
    // Example of a set of words from the assignment page.
    this.wordList.add("RIDGE");
    this.wordList.add("GANDER");
    this.wordList.add("FRONT");
    this.wordList.add("PEEK");
    this.wordList.add("LOOK");
    this.wordList.add("CHARADE");
    this.wordList.add("PEAK");
    this.wordList.add("CLIFF");
    this.wordList.add("BLUFF");
    this.wordList.add("PIQUE");
    this.wordList.add("CRAG");
    this.wordList.add("GLANCE");
    this.wordList.add("PEKE");
    this.wordList.add("LEDGE");
    this.wordList.add("GLIMPSE");
    this.wordList.add("ACT");

    this.wordList2 = new ArrayList<String>();
    // Example of a set of words from the assignment page.
    this.wordList2.add("GOUDA");
    this.wordList2.add("FETA");
    this.wordList2.add("GLIMPSE");
    this.wordList2.add("AZURE");
    this.wordList2.add("BRIE");
    this.wordList2.add("CAT");
    this.wordList2.add("COBALT");
    this.wordList2.add("FARCE");
    this.wordList2.add("TEAL");
    this.wordList2.add("BLUFF");
    this.wordList2.add("NAVY");
    this.wordList2.add("ACT");
    this.wordList2.add("LOOK");
    this.wordList2.add("PEAK");
    this.wordList2.add("GANDER");
    this.wordList2.add("CHEDDAR");

    this.wordList3 = new ArrayList<String>();
    // Example of a set of words from the assignment page.
    this.wordList3.add("GOUDA");
    this.wordList3.add("FETA");
    this.wordList3.add("BEAGLE");
    this.wordList3.add("AZURE");
    this.wordList3.add("BRIE");
    this.wordList3.add("CAT");
    this.wordList3.add("COBALT");
    this.wordList3.add("BAT");
    this.wordList3.add("TEAL");
    this.wordList3.add("MAT");
    this.wordList3.add("NAVY");
    this.wordList3.add("HAT");
    this.wordList3.add("CORGIE");
    this.wordList3.add("HUSKY");
    this.wordList3.add("SHIBA");
    this.wordList3.add("CHEDDAR");
  }

  // Tests the createGrid method
  void testCreateGrid(Tester t) {
    ConnectionsWorld worldTest1 = new ConnectionsWorld(this.wordList);
    // Calls createGrid when constructing ConnectionsWorld, so default size is 16
    t.checkExpect(worldTest1.squares.size(), 16);
    worldTest1.createGrid();
    // Calling it a second time makes the size 32
    t.checkExpect(worldTest1.squares.size(), 32);
  }

  // Tests the makeScene method
  void testMakeScene(Tester t) {
    ConnectionsWorld worldTest1 = new ConnectionsWorld(this.wordList);
    WorldScene correctOutput = new WorldScene(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
    for (WordSquare ws : worldTest1.squares) {
      correctOutput.placeImageXY(ws.draw(), ws.x + ws.width / 2, ws.y + ws.height / 2);
    }
    correctOutput.placeImageXY(new TextImage("Tries Left: 4", 16, Color.RED),
        Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT - 20);
    t.checkExpect(worldTest1.makeScene(), correctOutput);

    ConnectionsWorld worldTest2 = new ConnectionsWorld(this.wordList2);
    WorldScene correctOutput2 = new WorldScene(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
    for (WordSquare ws : worldTest2.squares) {
      correctOutput2.placeImageXY(ws.draw(), ws.x + ws.width / 2, ws.y + ws.height / 2);
    }
    correctOutput2.placeImageXY(new TextImage("Tries Left: 4", 16, Color.RED),
        Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT - 20);
    t.checkExpect(worldTest2.makeScene(), correctOutput2);

    ConnectionsWorld worldTest3 = new ConnectionsWorld(this.wordList3);
    WorldScene correctOutput3 = new WorldScene(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
    for (WordSquare ws : worldTest3.squares) {
      correctOutput3.placeImageXY(ws.draw(), ws.x + ws.width / 2, ws.y + ws.height / 2);
    }
    correctOutput3.placeImageXY(new TextImage("Tries Left: 4", 16, Color.RED),
        Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT - 20);
    t.checkExpect(worldTest3.makeScene(), correctOutput3);
  }

  // Tests the draw method
  void testDraw(Tester t) {
    WordSquare unselectedWord = new WordSquare(0, 0, 200, 100, "RIDGE");
    WorldImage output1 = new OverlayImage(new TextImage("RIDGE", 16, Color.BLACK),
        new RectangleImage(200, 100, OutlineMode.SOLID, Color.LIGHT_GRAY));
    t.checkExpect(unselectedWord.draw(), output1);
  }

  // Test for BigBang (implemented early, intended for part 2)
  boolean testBigBang(Tester t) {
    ConnectionsWorld world = new ConnectionsWorld(this.wordList);
    world.bigBang(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, 0.1);
    return true;
  }
}
