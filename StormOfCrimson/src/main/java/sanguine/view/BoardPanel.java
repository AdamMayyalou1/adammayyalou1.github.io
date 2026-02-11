package sanguine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import sanguine.model.PlayerId;
import sanguine.model.Position;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.model.cell.Cell;

/**
 * BoardPanel implementation of the JPanel Class. Handles the modification and presentation of the
 * hand to the GUI. Maintains relevant state based on the ReadOnlyModel.
 */
public class BoardPanel extends AbstractSanguinePanel {
  private final ReadOnlySanguineModel model;
  private final List<ViewCommands> commandListeners = new ArrayList<>();
  private final PlayerId perspective;

  private int rowChosen = -1;
  private int colChosen = -1;

  /**
   * Basic Constructor for BoardPanel. Creates a MouseListener to take User inputs.
   *
   * @param model The Read Only Model to present to the User.
   */
  public BoardPanel(ReadOnlySanguineModel model, PlayerId perspective) {
    this.model = model;
    this.perspective = perspective;
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMousePressed(e.getX(), e.getY());
      }
    });
  }

  /**
   * Setter to set the commands from the Controller to push commands to the Controller to then
   * modify the model.
   *
   * @param commands The wanted commands for the HandPanel to read from the Controller.
   */
  public void addCommands(ViewCommands commands) {
    this.commandListeners.add(commands);
  }

  /**
   * Highlights the cell at this position.
   *
   * @param row (int) the cell's row.
   * @param col (int) the cell's column.
   */
  public void highlightCell(int row, int col) {
    this.rowChosen = row;
    this.colChosen = col + 1;
    repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 800);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int rows = model.rows();
    int cols = model.cols() + 2;
    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    for (int row = 0; row < rows; row++) {
      for (int col = 1; col < cols - 1; col++) {
        int x = col * cellWidth;
        int y = row * cellHeight;

        Cell cell = model.cellAt(new Position(row, col - 1));
        if (row == rowChosen && col == colChosen) {
          g2d.setColor(Color.GREEN);
          g2d.fillRect(x, y, cellWidth, cellHeight);
        } else {
          if (cell.kind() == Cell.Kind.CARD) {
            if (cell.owner() == PlayerId.RED) {
              g2d.setColor(Color.RED);
            } else {
              g2d.setColor(Color.BLUE);
            }
          } else {
            g2d.setColor(Color.DARK_GRAY);
          }
          g2d.fillRect(x, y, cellWidth, cellHeight);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, cellWidth, cellHeight);

        PlayerId owner = cell.owner();

        String cellText;
        Color textColor = Color.BLACK;

        switch (cell.kind()) {
          case PAWNS -> {
            cellText = String.valueOf(cell.pawnCount());
            if (cell.owner() == PlayerId.RED) {
              textColor = Color.RED;
            } else {
              textColor = Color.BLUE;
            }
            g2d.setColor(textColor);
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 18f));
            g2d.drawString(cellText, x + cellWidth / 2, y + cellHeight / 2);
          }
          case CARD -> {
            cellText = cell.toString();
            String[] lines = cell.toString().split("\n");
            for (int line = 0; line < lines.length; line++) {
              g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 9f));
              g2d.drawString(lines[line], x + 5, y + (line * 12) + 15);
            }
          }
          default -> cellText = "";
        }

      }
    }

    int redX = 0;
    int blueX = (cols - 1) * cellWidth;
    for (int row = 0; row < rows; row++) {
      int redScore;
      redScore = model.rowScores().get(row)[0];
      int blueScore;
      blueScore = model.rowScores().get(row)[1];
      int y = row * cellHeight;

      g2d.setColor(Color.LIGHT_GRAY);
      g2d.fillRect(redX, y, cellWidth, cellHeight);
      g2d.fillRect(blueX, y, cellWidth, cellHeight);
      g2d.setColor(Color.BLACK);
      g2d.drawRect(redX, y, cellWidth, cellHeight);
      g2d.drawRect(blueX, y, cellWidth, cellHeight);


      if (redScore > blueScore) {
        g2d.setColor(Color.RED);
        g2d.fillOval(redX + 10, y + 10, cellWidth - 20, cellHeight - 30);
      } else if (blueScore > redScore) {
        g2d.setColor(Color.BLUE);
        g2d.fillOval(blueX + 10, y + 10, cellWidth - 20, cellHeight - 30);
      }


      g2d.setColor(Color.BLACK);
      g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 18f));
      g2d.drawString(String.valueOf(redScore), redX + cellWidth / 2, y + cellHeight / 2);
      g2d.drawString(String.valueOf(blueScore), blueX + cellWidth / 2, y + cellHeight / 2);
    }
  }

  @Override
  protected void handleMousePressed(int chosenX, int chosenY) {
    int rows = model.rows();
    int cols = model.cols() + 2;
    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;

    int colPress = chosenX / cellWidth;
    int rowPress = chosenY / cellHeight;

    if (colPress < 1 || rowPress < 0 || colPress > cols - 2 || rowPress > rows - 1) {
      return;
    }

    if (colPress == colChosen && rowPress == rowChosen) {
      colChosen = -1;
      rowChosen = -1;
    } else {
      colChosen = colPress;
      rowChosen = rowPress;
    }

    if (!commandListeners.isEmpty()) {
      cellClickedPush(new Position(rowPress, colPress - 1));
    }
    repaint();
  }

  private void cellClickedPush(Position position) {
    for (ViewCommands command : commandListeners) {
      command.cellClicked(position);
    }
  }

}
