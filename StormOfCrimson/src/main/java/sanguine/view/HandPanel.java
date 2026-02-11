package sanguine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import sanguine.model.PlayerId;
import sanguine.model.ReadOnlySanguineModel;
import sanguine.model.card.Card;
import sanguine.model.cell.CardCell;
import sanguine.model.deck.Hand;

/**
 * HandPanel implementation of the JPanel Class. Handles the modification and presentation of the
 * hand to the GUI. Maintains relevant state based on the ReadOnlyModel.
 */
public class HandPanel extends AbstractSanguinePanel {

  private final ReadOnlySanguineModel model;
  private final List<ViewCommands> commandListeners = new ArrayList<>();
  private final PlayerId perspective;
  private int cardChosen = -1;


  /**
   * Basic Constructor for HandPanel. Creates a MouseListener to take User inputs.
   *
   * @param model The Read Only Model to present to the User.
   */
  public HandPanel(ReadOnlySanguineModel model, PlayerId perspective) {
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
   * Highlights the card at this index.
   *
   * @param index (int) the card's position.
   */
  public void highlightIndex(int index) {
    this.cardChosen = index;
    repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 200);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    Hand hand = model.handOf(perspective);
    int handSize = hand.size();

    int width = getWidth();
    int height = getHeight();

    int cardWidth = Math.max(width / handSize, 50);
    int cardHeight = Math.max(height, 50);

    for (int index = 0; index < handSize; index++) {
      int x = index * cardWidth; // might change for spacing
      int y = 0; // might change for spacing

      if (index == cardChosen) {
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x, y, cardWidth, cardHeight);
      } else {
        if (perspective == PlayerId.RED) {
          g2d.setColor(Color.RED);
        } else {
          g2d.setColor(Color.BLUE);
        }
        g2d.fillRect(x, y, cardWidth, cardHeight);
      }

      g2d.setColor(Color.BLACK);
      g2d.drawRect(x, y, cardWidth, cardHeight);

      Card card = hand.cards().get(index);

      CardCell cardCell = new CardCell(model.currentPlayer(), card);
      String[] lines = cardCell.toString().split("\n");
      for (int line = 0; line < lines.length; line++) {
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 16f));
        g2d.drawString(lines[line], x + 5, y + (line * 20) + 30);
      }
    }
  }

  @Override
  protected void handleMousePressed(int chosenX, int chosenY) {
    Hand hand = model.handOf(model.currentPlayer());
    int handSize = hand.size();

    int width = getWidth();
    int cardHeight = getHeight();
    int cardWidth = Math.max(width / handSize, 50);

    for (int index = 0; index < handSize; index++) {
      int x = index * cardWidth; // might change for spacing
      int y = 0; // might change for spacing
      Rectangle cardSize = new Rectangle(x, y, cardWidth, cardHeight);
      if (cardSize.contains(chosenX, chosenY)) {
        if (index == cardChosen) {
          cardChosen = -1;
        } else {
          cardChosen = index;
        }
        repaint();
        cardClickedPush(index);
      }
    }
  }

  private void cardClickedPush(int handIndex) {
    for (ViewCommands commands : commandListeners) {
      commands.cardClicked(handIndex);
    }
  }



}
