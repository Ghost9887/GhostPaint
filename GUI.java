import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  private JFrame mainFrame;
  private PaintPanel mainPanel;

  // mouse Pos
  private Point mousePos = new Point(0, 0);

  public void createFrame() {

    mainFrame = new JFrame();
    mainPanel = new PaintPanel();
    mainPanel.setLayout(new BorderLayout());
    JLabel label = new JLabel();

    label.setHorizontalAlignment(JLabel.LEFT);
    label.setVerticalAlignment(JLabel.TOP);
    label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));

    mainFrame.add(mainPanel);
    mainPanel.add(label);
    mainFrame.setVisible(true);
    mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    draw(label);
  }

  private void draw(JLabel label) {
    // when clicked (ONLY PAINTS THE ONE LOCATION)
    mainPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        mainPanel.setPos(mousePos.x, mousePos.y);
      }
    });
    mainPanel.addMouseMotionListener(new MouseAdapter() {
      // when pressed (ONLY PAINTS WHEN MOUSE IS MOVED)
      @Override
      public void mouseDragged(MouseEvent event) {
        mousePos = event.getPoint();
        label.setText("X: " + mousePos.x + " | Y: " + mousePos.y);
        mainPanel.setPos(mousePos.x, mousePos.y);
      }

      // when not pressed
      @Override
      public void mouseMoved(MouseEvent event) {
        mousePos = event.getPoint();
        label.setText("X: " + mousePos.x + " | Y: " + mousePos.y);
      }
    });
  }
}
