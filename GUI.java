import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  private JFrame mainFrame;
  private PaintPanel mainPanel;
  private JPanel navPanel;

  // mouse Pos
  private Point mousePos = new Point(0, 0);

  public void createFrame() {

    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());

    mainPanel = new PaintPanel();
    mainPanel.setLayout(new BorderLayout());

    navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    navPanel.setBackground(Color.GRAY);

    JButton homeButton = new JButton("Home");
    JButton fileButton = new JButton("File");
    JButton helpButton = new JButton("Help");
    navPanel.add(homeButton);
    navPanel.add(fileButton);
    navPanel.add(helpButton);

    JLabel label = new JLabel("Welcome!");
    label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));
    mainPanel.add(label, BorderLayout.NORTH);

    mainFrame.add(navPanel, BorderLayout.NORTH);
    mainFrame.add(mainPanel, BorderLayout.CENTER);

    mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setVisible(true);

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
