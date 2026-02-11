package sanguine.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import sanguine.model.PlayerId;
import sanguine.model.ReadOnlySanguineModel;

/**
 * Sanguine Swing View is the GUI using JFrame to connect all the panels together and connect
 * the view to the controller. Composes of the key presses to pass to the commands to handle
 * changes. As well, handles delegation to the panels.
 */
public class SanguineSwingView extends JFrame implements SanguineView {

  private final ReadOnlySanguineModel model;
  private final List<ViewCommands> commandListeners = new ArrayList<>();
  private final BoardPanel boardPanel;
  private final HandPanel handPanel;
  private final JLabel headerLabel;
  private final PlayerId perspective;


  /**
   * Basic constructor for the SanguineSwingView.
   *
   * @param model Read Only Version on the model to take in to present to the GUI.
   */
  public SanguineSwingView(ReadOnlySanguineModel model, PlayerId perspective) {
    this.model = model;
    this.perspective = perspective;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(Color.WHITE);
    headerLabel = new JLabel("Current Player: RED");
    headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
    headerPanel.add(headerLabel);

    this.boardPanel = new BoardPanel(model, perspective);
    this.handPanel = new HandPanel(model, perspective);
    this.add(boardPanel, BorderLayout.CENTER);
    this.add(handPanel, BorderLayout.SOUTH);
    this.add(headerPanel, BorderLayout.NORTH);
    this.pack();

    this.setKeyBindings();
  }

  @Override
  public void addCommands(ViewCommands commands) {
    this.commandListeners.add(commands);
    boardPanel.addCommands(commands);
    handPanel.addCommands(commands);
  }

  @Override
  public void refresh() {

    headerLabel.repaint();
    boardPanel.repaint();
    handPanel.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void highlightCard(Integer index) {
    handPanel.highlightIndex(index);
  }

  @Override
  public void highlightCell(Integer row, Integer col) {
    boardPanel.highlightCell(row, col);
  }

  @Override
  public void clearHighlights() {
    handPanel.highlightIndex(-1);
    boardPanel.highlightCell(-1, -1);

  }

  @Override
  public void setTurnActive(boolean active) {
    if (model.currentPlayer() == perspective) {
      headerLabel.setText("Your turn");
    } else {
      headerLabel.setText("Waiting for Opponent...");
    }

    handPanel.turnInputOn(active);


    boardPanel.setActive(active);
    handPanel.setActive(active);

    refresh();
  }

  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(
        this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showGameOver(PlayerId winner, int redScore, int blueScore) {
    String gameOverMessage;
    if (winner == null) {
      gameOverMessage = "GAME OVER It's a tie!";
    } else {
      gameOverMessage = "GAME OVER Winner is: " + winner + "!";
    }

    JOptionPane.showMessageDialog(
        this, gameOverMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Sets the keyBindings associated with the commands of the game. "ENTER" to confirm command.
   * "P" to pass command.
   */
  private void setKeyBindings() {
    JRootPane root = this.getRootPane();
    InputMap inputMap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = root.getActionMap();

    confirmKeyBinding(inputMap, actionMap);

    passKeyBinding(inputMap, actionMap);
  }

  /**
   * Handles pushing the pass command to the controller to modify the model.
   *
   * @param inputMap Input Map to pass key bindings
   * @param actionMap Output Map to handle execution.
   */
  private void passKeyBinding(InputMap inputMap, ActionMap actionMap) {
    inputMap.put(KeyStroke.getKeyStroke("P"), "pass");
    actionMap.put("pass", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!commandListeners.isEmpty()) {
          System.out.println("pass requested");
          passPush();
        }
      }
    });
  }

  /**
   * Handles pushing the confirm command to the controller to modify the model.
   *
   * @param inputMap Input Map to pass key bindings
   * @param actionMap Output Map to handle execution.
   */
  private void confirmKeyBinding(InputMap inputMap, ActionMap actionMap) {
    inputMap.put(KeyStroke.getKeyStroke("ENTER"), "confirm");
    actionMap.put("confirm", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!commandListeners.isEmpty()) {
          System.out.println("confirm requested");
          confirmPush();
        }
      }
    });
  }

  private void passPush() {
    for (ViewCommands command : commandListeners) {
      command.pass();
    }
  }

  private void confirmPush() {
    for (ViewCommands command : commandListeners) {
      command.confirmMove();
    }
  }

}
