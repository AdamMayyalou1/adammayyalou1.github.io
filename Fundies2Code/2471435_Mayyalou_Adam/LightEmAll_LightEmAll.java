import javalib.impworld.*;
import javalib.worldimages.*;
import tester.Tester;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Collections;

// Represents a single tile on the board
class GamePiece {
  // in logical coordinates, with the origin
  // at the top-left corner of the screen
  int row;
  int col;
  // whether this GamePiece is connected to the
  // adjacent left, right, top, or bottom pieces
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  // whether the power station is on this piece
  boolean powerStation;
  boolean powered;

  // Constructor for GamePiece
  GamePiece(int row, int col, boolean left, boolean right, boolean top, boolean bottom) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.powerStation = false;
    this.powered = false;
  }

  // Rotates this tile in a clockwise motion
  void rotateClockwise() {
    boolean temp = this.left;
    this.left = this.bottom;
    this.bottom = this.right;
    this.right = this.top;
    this.top = temp;
  }

  // Generate an image of this, the given GamePiece.
  // - size: the size of the tile, in pixels
  // - wireWidth: the width of wires, in pixels
  // - wireColor: the Color to use for rendering wires on this
  // - hasPowerStation: if true, draws a fancy star on this tile to represent the
  // power station
  //
  WorldImage tileImage(int size, int wireWidth, Color wireColor, boolean hasPowerStation) {
    // Start tile image off as a blue square with a wire-width square in the middle,
    // to make image "cleaner" (will look strange if tile has no wire, but that
    // can't be)
    WorldImage image = new OverlayImage(
        new RectangleImage(wireWidth, wireWidth, OutlineMode.SOLID, wireColor),
        new RectangleImage(size, size, OutlineMode.SOLID, Color.DARK_GRAY));
    WorldImage vWire = new RectangleImage(wireWidth, (size + 1) / 2, OutlineMode.SOLID, wireColor);
    WorldImage hWire = new RectangleImage((size + 1) / 2, wireWidth, OutlineMode.SOLID, wireColor);

    if (this.top) {
      image = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, vWire, 0, 0, image);
    }
    if (this.right) {
      image = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE, hWire, 0, 0, image);
    }
    if (this.bottom) {
      image = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM, vWire, 0, 0, image);
    }
    if (this.left) {
      image = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, hWire, 0, 0, image);
    }
    if (hasPowerStation) {
      image = new OverlayImage(
          new OverlayImage(new StarImage(size / 3, 7, OutlineMode.OUTLINE, new Color(255, 128, 0)),
              new StarImage(size / 3, 7, OutlineMode.SOLID, new Color(0, 255, 255))),
          image);
    }
    return image;
  }
}

// Main game class
class LightEmAll extends World {
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  // the width and height of the board
  int width;
  int height;
  // the current location of the power station,
  // as well as its effective radius
  int powerRow;
  int powerCol;
  int radius;
  int tileSize = 50;
  Random rand1;
  Random rand2;

  // Constructor - makes two new randoms for testing
  LightEmAll(int width, int height) {
    this(width, height, new Random(), new Random());
  }

  // Constructor for LightEmAll
  LightEmAll(int width, int height, Random rand1, Random rand2) {
    this.width = width;
    this.height = height;
    this.rand1 = rand1;
    this.rand2 = rand2;
    this.powerRow = height / 2;
    this.powerCol = width / 2;
    this.board = new ArrayList<ArrayList<GamePiece>>();
    this.initBoard();
    this.updatePowered();
  }

  void initBoard() {
    for (int col = 0; col < this.width; col++) {
      ArrayList<GamePiece> column = new ArrayList<GamePiece>();
      for (int row = 0; row < this.height; row++) {
        // Start with no connections.
        GamePiece piece = new GamePiece(row, col, false, false, false, false);
        column.add(piece);
      }
      this.board.add(column);
    }

    ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
    for (int col = 0; col < this.width; col++) {
      ArrayList<Node> colNodes = new ArrayList<>();
      for (int row = 0; row < this.height; row++) {
        colNodes.add(new Node(board.get(col).get(row)));
      }
      nodes.add(colNodes);
    }

    //List all edges between adjacent pieces.
    ArrayList<Edge> edges = new ArrayList<>();
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        GamePiece current = board.get(col).get(row);
        // Edge to right neighbor
        if (col < this.width - 1) {
          GamePiece neighbor = board.get(col + 1).get(row);
          int weight = rand1.nextInt(1000);
          edges.add(new Edge(current, neighbor, weight));
        }
        // Edge to bottom neighbor
        if (row < this.height - 1) {
          GamePiece neighbor = board.get(col).get(row + 1);
          int weight = rand1.nextInt(1000);
          edges.add(new Edge(current, neighbor, weight));
        }
      }
    }

    //Sort the edges by weight.
    Collections.sort(edges, (e1, e2) -> Integer.compare(e1.weight, e2.weight));

    //Process edges using our union-find Node structure.
    this.mst = new ArrayList<>();
    for (Edge e : edges) {
      // Get corresponding nodes for both GamePieces.
      Node nodeFrom = nodes.get(e.from.col).get(e.from.row);
      Node nodeTo = nodes.get(e.to.col).get(e.to.row);

      if (nodeFrom.find() != nodeTo.find()) {
        // Union the two sets.
        nodeFrom.union(nodeTo);
        // Add this edge to the MST.
        this.mst.add(e);

        // Carve the connection between the pieces.
        if (e.from.row == e.to.row) { // Horizontal neighbors.
          if (e.from.col < e.to.col) {
            e.from.right = true;
            e.to.left = true;
          } else {
            e.from.left = true;
            e.to.right = true;
          }
        }
        if (e.from.col == e.to.col) { // Vertical neighbors.
          if (e.from.row < e.to.row) {
            e.from.bottom = true;
            e.to.top = true;
          } else {
            e.from.top = true;
            e.to.bottom = true;
          }
        }
      }
    }

    // Set the power station
    for (ArrayList<GamePiece> colList : board) {
      for (GamePiece gp : colList) {
        if (gp.row == this.powerRow && gp.col == this.powerCol) {
          gp.powerStation = true;
        }
      }
    }

    // Scramble the board by randomly rotating each GamePiece.
    for (ArrayList<GamePiece> colList : board) {
      for (GamePiece gp : colList) {
        int rotations = rand2.nextInt(4);
        for (int i = 0; i < rotations; i++) {
          gp.rotateClockwise();
        }
      }
    }
  }

  // Randomly decides whether to scramble a piece
  GamePiece scramble(GamePiece piece) {
    if (rand1.nextInt(2) == 1) {
      for (int i = 0; i < rand2.nextInt(3); i++) {
        piece.rotateClockwise();
      }
    }
    return piece;
  }

  // Makes the scene for the game
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.width * tileSize, this.height * tileSize);
    for (ArrayList<GamePiece> column : this.board) {
      for (GamePiece piece : column) {
        Color wireColor;
        if (piece.powered) {
          wireColor = Color.YELLOW;
        }
        else {
          wireColor = Color.GRAY;
        }
        WorldImage image = piece.tileImage(tileSize, 8, wireColor, piece.powerStation);
        scene.placeImageXY(image, piece.col * tileSize + tileSize / 2,
            piece.row * tileSize + tileSize / 2);
      }
    }

    // Conditions for the game to end
    boolean gameOver = true;
    for (ArrayList<GamePiece> column : board) {
      for (GamePiece gp : column) {
        if (!gp.powered) {
          gameOver = false;
        }
      }
    }
    if (gameOver) {
      WorldImage win = new TextImage("Congratulations! Press 'q' to end world.", 30, Color.green);
      scene.placeImageXY(win, 250, 250);
    }

    // Return
    return scene;
  }

  // Detects when the mouse clicks on a piece to rotate it
  public void onMouseClicked(Posn pos) {
    int col = pos.x / tileSize;
    int row = pos.y / tileSize;
    for (ArrayList<GamePiece> column : this.board) {
      for (GamePiece gp : column) {
        if (gp.row == row && gp.col == col) {
          gp.rotateClockwise();
          this.updatePowered();
          return;
        }
      }
    }
  }

  // Detects when up, down, left, or right are pressed to move the power station
  // If the user presses 'q' when all tiles are powered, game ends
  public void onKeyEvent(String key) {
    for (ArrayList<GamePiece> column : this.board) {
      for (GamePiece current : column) {
        if (current.row == powerRow && current.col == powerCol) {
          int newRow = powerRow;
          int newCol = powerCol;
          for (ArrayList<GamePiece> colList : this.board) {
            for (GamePiece neighbor : colList) {
              if (key.equals("up") && current.top && neighbor.row == powerRow - 1
                  && neighbor.col == powerCol && neighbor.bottom) {
                newRow--;
              }
              if (key.equals("down") && current.bottom && neighbor.row == powerRow + 1
                  && neighbor.col == powerCol && neighbor.top) {
                newRow++;
              }
              if (key.equals("left") && current.left && neighbor.col == powerCol - 1
                  && neighbor.row == powerRow && neighbor.right) {
                newCol--;
              }
              if (key.equals("right") && current.right && neighbor.col == powerCol + 1
                  && neighbor.row == powerRow && neighbor.left) {
                newCol++;
              }
            }
          }
          if (newRow != powerRow || newCol != powerCol) {
            current.powerStation = false;
            for (ArrayList<GamePiece> cList : this.board) {
              for (GamePiece gp : cList) {
                if (gp.row == newRow && gp.col == newCol) {
                  gp.powerStation = true;
                }
              }
            }
            powerRow = newRow;
            powerCol = newCol;
            updatePowered();
            return;
          }
        }
      }
    }
    // Way to quit the game (when all tiles are powered)
    boolean gameOver = true;
    for (ArrayList<GamePiece> column : board) {
      for (GamePiece gp : column) {
        if (!gp.powered) {
          gameOver = false;
        }
      }
    }
    if (key.equals("q") && gameOver) {
      this.endOfWorld("Thank you for playing!");
    }
  }

  // Updates the state of power
  void updatePowered() {
    for (ArrayList<GamePiece> column : board) {
      for (GamePiece gp : column) {
        gp.powered = false;
      }
    }
    Queue<GamePiece> queue = new LinkedList<>();
    for (ArrayList<GamePiece> column : this.board) {
      for (GamePiece gp : column) {
        if (gp.row == powerRow && gp.col == powerCol) {
          gp.powered = true;
          queue.add(gp);
        }
      }
    }

    while (!queue.isEmpty()) {
      GamePiece current = queue.remove();
      int row = current.row;
      int col = current.col;

      for (ArrayList<GamePiece> column : board) {
        for (GamePiece neighbor : column) {
          if (!neighbor.powered) {
            if (current.top && neighbor.bottom && neighbor.row == row - 1 && neighbor.col == col) {
              neighbor.powered = true;
              queue.add(neighbor);
            }
            if (current.bottom && neighbor.top && neighbor.row == row + 1 && neighbor.col == col) {
              neighbor.powered = true;
              queue.add(neighbor);
            }
            if (current.left && neighbor.right && neighbor.col == col - 1 && neighbor.row == row) {
              neighbor.powered = true;
              queue.add(neighbor);
            }
            if (current.right && neighbor.left && neighbor.col == col + 1 && neighbor.row == row) {
              neighbor.powered = true;
              queue.add(neighbor);
            }
          }
        }
      }
    }
  }
}

//Represents an edge between two GamePieces with a weight
class Edge {
  GamePiece from;
  GamePiece to;
  int weight;

  Edge(GamePiece from, GamePiece to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }
}

//Represents a union-find set for Kruskal's algorithm
class Node {
  GamePiece piece;
  Node parent;

  Node(GamePiece piece) {
    this.piece = piece;
    this.parent = this;
  }

  //Finds the root of the node
  Node find() {
    if (this.parent != this) {
      this.parent = this.parent.find();
    }
    return this.parent;
  }

  //Unions this node and another
  void union(Node other) {
    Node root1 = this.find();
    Node root2 = other.find();
    if (root1 != root2) {
      root2.parent = root1;
    }
  }
}

// Example class for LightEmAll
class ExamplesLightEmAll {
  // Test rotateClockwise
  boolean testRotateClockwise(Tester t) {
    GamePiece gp = new GamePiece(0, 0, true, false, true, false);
    gp.rotateClockwise();

    return t.checkExpect(gp.left, false) && t.checkExpect(gp.bottom, false)
        && t.checkExpect(gp.right, true) && t.checkExpect(gp.top, true);
  }

  // Test tileImage
  boolean testTileImage(Tester t) {
    GamePiece gp = new GamePiece(0, 0, true, false, true, false);

    WorldImage img = gp.tileImage(50, 8, Color.YELLOW, false);

    return t.checkExpect(img.getClass().getSimpleName(), "OverlayOffsetAlign");
  }

  // Test initBoard
  boolean testInitBoard(Tester t) {

    LightEmAll world = new LightEmAll(3, 3, new Random(0), new Random(0));
    boolean dimsCorrect = (world.board.size() == 3);
    for (ArrayList<GamePiece> col : world.board) {
      dimsCorrect = dimsCorrect && (col.size() == 3);
    }
    int count = 0;
    for (ArrayList<GamePiece> col : world.board) {
      for (GamePiece gp : col) {
        if (gp.powerStation) {
          count++;
        }
      }
    }
    return t.checkExpect(dimsCorrect, true) && t.checkExpect(count, 1);
  }

  // Test scramble
  boolean testScramble(Tester t) {

    LightEmAll world = new LightEmAll(1, 1, new Random(0), new Random(0));

    GamePiece gp = new GamePiece(0, 0, true, false, true, false);
    world.scramble(gp);

    boolean option0 = (gp.left && !gp.right && gp.top && !gp.bottom);
    boolean option1 = (!gp.left && gp.right && gp.top && !gp.bottom);
    boolean option2 = (!gp.left && gp.right && !gp.top && gp.bottom);
    return t.checkExpect((option0 || option1 || option2), true);
  }

  // Test updatePowered
  boolean testUpdatePowered(Tester t) {
    LightEmAll world = new LightEmAll(1, 1);
    world.updatePowered();

    return t.checkExpect(world.board.get(0).get(0).powered, true);
  }

  // Test onMouseClicked
  boolean testOnMouseClicked(Tester t) {

    LightEmAll world = new LightEmAll(2, 2, new Random(0), new Random(0));

    GamePiece target = world.board.get(0).get(0);

    boolean origLeft = target.left;
    boolean origRight = target.right;
    boolean origTop = target.top;
    boolean origBottom = target.bottom;

    world.onMouseClicked(new Posn(25, 25));

    return t.checkExpect(target.left, origBottom) && t.checkExpect(target.bottom, origRight)
        && t.checkExpect(target.right, origTop) && t.checkExpect(target.top, origLeft);
  }

  // Test onKeyEvent
  boolean testOnKeyEvent(Tester t) {
    // Create a 3x3 world with fixed Random seeds.
    LightEmAll world = new LightEmAll(3, 3, new Random(0), new Random(0));
    GamePiece center = world.board.get(1).get(1);
    center.top = true;

    GamePiece above = world.board.get(1).get(0);
    above.bottom = true;

    world.onKeyEvent("up");

    return t.checkExpect(world.powerRow, 0) && t.checkExpect(world.powerCol, 1);
  }

  //Test for union-find functionality using the Tester library
  boolean testUnionFindBasic(Tester t) {
    Node a = new Node(new GamePiece(0, 0, false, false, false, false));
    Node b = new Node(new GamePiece(0, 1, false, false, false, false));
    Node c = new Node(new GamePiece(1, 0, false, false, false, false));

    // Initially, each node should be its own root.
    t.checkExpect(a.find(), a);
    t.checkExpect(b.find(), b);
    t.checkExpect(c.find(), c);

    // Union a and b and then check that they share the same root.
    a.union(b);
    t.checkExpect(a.find() == b.find(), true);

    // Union b and c and confirm all nodes are connected.
    b.union(c);
    t.checkExpect(a.find() == c.find(), true);

    return true;
  }
  
  // Test for mst Edge count
  boolean testMSTEdgeCount(Tester t) {
    LightEmAll world = new LightEmAll(3, 3, new Random(0), new Random(0));
    int totalPieces = 3 * 3;

    return t.checkExpect(world.mst.size(), totalPieces - 1);
  }

  // Test for whether the power station remains powered
  boolean testPowerStationRemainsPowered(Tester t) {
    LightEmAll world = new LightEmAll(3, 3, new Random(0), new Random(0));
    world.updatePowered();

    boolean stationPowered = false;
    for (ArrayList<GamePiece> col : world.board) {
      for (GamePiece gp : col) {
        if (gp.powerStation && gp.powered) {
          stationPowered = true;
        }
      }
    }
    return t.checkExpect(stationPowered, true);
  }
  
  // Test for the makeScene method
  void testMakeScene(Tester t) {
    Random r1 = new Random(1);
    Random r2 = new Random(2);
    LightEmAll game = new LightEmAll(3, 3, r1, r2);
    
    WorldScene scene = game.makeScene();
    
    t.checkExpect(scene.width, 3 * game.tileSize);
    t.checkExpect(scene.height, 3 * game.tileSize);
  }

  boolean testBigBang(Tester t) {
    LightEmAll world = new LightEmAll(10, 10);
    world.bigBang(500, 500, 0.1);
    return true;
  }
}