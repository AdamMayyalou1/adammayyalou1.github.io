package sanguine.view;


import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Abstract class that represents a SanguinePanel.
 */
public abstract class AbstractSanguinePanel extends JPanel {


  protected boolean inputEnabled = true;
  protected boolean active = true;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  /**
   * Sets the input to the given boolean.
   *
   * @param active (Boolean) whether the input is enabled.
   */
  public void turnInputOn(Boolean active) {
    this.inputEnabled = active;
  }

  /**
   * Sets panel's activity to the given boolean and repaints it.
   *
   * @param active (boolean) whether the panel is active or not.
   */
  public void setActive(boolean active) {
    this.active = active;
    repaint();
  }

  /**
   * Pipeline to handle x and y pixel inputs from the user and transition that to a handIndex that
   * can then modify the view of the Panel.
   *
   * @param chosenX Pixel x clicked by the user.
   * @param chosenY Pixel y clicked by the user.
   */
  protected abstract void handleMousePressed(int chosenX, int chosenY);
}
