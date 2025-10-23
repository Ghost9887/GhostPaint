import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI {

  private final Context context = new Context();

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  private JFrame mainFrame;
  private PaintPanel mainPanel;
  private JPanel navPanel;

  // mouse Pos
  private Point mousePos = new Point(0, 0);
  private Point newMousePos = new Point(0, 0);

  // action
  public void createFrame() {

    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());

    mainPanel = new PaintPanel(context);
    mainPanel.setLayout(new BorderLayout());

    navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    navPanel.setBackground(Color.GRAY);

    JButton homeButton = new JButton("Home");
    JButton fileButton = new JButton("File");
    JButton helpButton = new JButton("Help");
    navPanel.add(homeButton);
    navPanel.add(fileButton);
    navPanel.add(helpButton);

    JLabel label = new JLabel("");
    label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));
    navPanel.add(label, BorderLayout.NORTH);

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
        context.changeAction(Action.PAINT);
        mainPanel.addNewPositions(getNewPos());
      }
    });
    mainPanel.addMouseMotionListener(new MouseAdapter() {
      // when pressed (ONLY PAINTS WHEN MOUSE IS MOVED)
      @Override
      public void mouseDragged(MouseEvent event) {

        mousePos = newMousePos;
        newMousePos = event.getPoint();

        label.setText("X: " + mousePos.x + " | Y: " + mousePos.y);

        mainPanel.addNewPositions(getNewPos());
      }

      // when not pressed
      @Override
      public void mouseMoved(MouseEvent event) {
        mousePos = newMousePos;
        newMousePos = event.getPoint();

        label.setText("X: " + mousePos.x + " | Y: " + mousePos.y);

        context.changeAction(Action.DEFAULT);
        mainPanel.setPos(newMousePos);
      }
    });
  }

  public ArrayList<Point> getNewPos() {
    ArrayList<Point> newPositions = new ArrayList<>();
    int x = mousePos.x;
    int y = mousePos.y;
    int newX = newMousePos.x;
    int newY = newMousePos.y;

    while (true) {
      if (x != newX && x > newX) {
        x--;
        newPositions.add(new Point(x, y));
      }
      if (y != newY && y > newY) {
        y--;
        newPositions.add(new Point(x, y));
      }
      if (x != newX && x < newX) {
        x++;
        newPositions.add(new Point(x, y));
      }
      if (y != newY && y < newY) {
        y++;
        newPositions.add(new Point(x, y));
      }
      if (x == newX && y == newY)
        break;
    }

    return newPositions;
  }
}
