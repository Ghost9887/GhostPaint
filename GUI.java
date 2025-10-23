import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.FlavorEvent;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class GUI {

  private final Context context = new Context();

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  private JFrame mainFrame;
  private PaintPanel mainPanel;
  private JPanel navPanel;
  private JPanel toolPanel;

  // mouse Pos
  private Point mousePos = new Point(0, 0);

  // action
  public void createFrame() {

    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainPanel = new PaintPanel(context);
    mainPanel.setLayout(new BorderLayout());

    // NAV PANEL->
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
    // END <-

    // TOOL PANEL ->
    toolPanel = new JPanel();
    toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
    toolPanel.setBackground(Color.GRAY);

    // --shapes selector
    JLabel shapes = new JLabel("Shapes");
    toolPanel.add(shapes);

    JPanel shapeSelectorPanel = new JPanel(new GridLayout(2, 2));
    shapeSelectorPanel.setOpaque(false); // use the gray background
    JButton rectButton = new JButton("Rectangle");
    JButton circleButton = new JButton("Circle");
    shapeSelectorPanel.add(rectButton);
    shapeSelectorPanel.add(circleButton);
    toolPanel.add(shapeSelectorPanel);

    // --colours
    JLabel colours = new JLabel("Colours");
    toolPanel.add(colours);

    JPanel colourPanel = new JPanel(new GridLayout(10, 2));
    colourPanel.setOpaque(false);
    JButton selectColour = new JButton("Select Colour");
    colourPanel.add(selectColour);
    toolPanel.add(colourPanel);
    // END <-

    // MAIN
    mainFrame.add(navPanel, BorderLayout.NORTH);
    mainFrame.add(toolPanel, BorderLayout.WEST);
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
        context.setAction(Action.PAINT);
        context.setPos(mousePos);
        context.setSize(new Point(10, 10));
        context.setShape(ShapeEnum.CIRCLE);
        mainPanel.paint();
      }
    });
    mainPanel.addMouseMotionListener(new MouseAdapter() {
      // when pressed (ONLY PAINTS WHEN MOUSE IS MOVED)
      @Override
      public void mouseDragged(MouseEvent event) {
        mousePos = event.getPoint();
        label.setText("X: " + mousePos.x + " | Y: " + mousePos.y);
        context.setPos(mousePos);
        context.setSize(new Point(10, 10));
        context.setShape(ShapeEnum.CIRCLE);
        mainPanel.paint();
      }

      // when not pressed
      @Override
      public void mouseMoved(MouseEvent event) {
        mousePos = event.getPoint();
        label.setText("X: " + mousePos.x + " | Y: " + mousePos.y);
        context.setAction(Action.DEFAULT);
        context.setPos(mousePos);
        mainPanel.cursor();
      }
    });
  }

  /*
   * public ArrayList<Point> getNewPos() {
   * ArrayList<Point> newPositions = new ArrayList<>();
   * int x = mousePos.x;
   * int y = mousePos.y;
   * int newX = newMousePos.x;
   * int newY = newMousePos.y;
   * 
   * while (true) {
   * if (x != newX && x > newX) {
   * x--;
   * newPositions.add(new Point(x, y));
   * }
   * if (y != newY && y > newY) {
   * y--;
   * newPositions.add(new Point(x, y));
   * }
   * if (x != newX && x < newX) {
   * x++;
   * newPositions.add(new Point(x, y));
   * }
   * if (y != newY && y < newY) {
   * y++;
   * newPositions.add(new Point(x, y));
   * }
   * if (x == newX && y == newY)
   * break;
   * }
   * 
   * return newPositions;
   * }
   */
}
