import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

  private final Context context = new Context();

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  private JFrame mainFrame;
  private PaintPanel mainPanel;
  private JPanel navPanel;
  private JPanel toolPanel;

  // BUTTONS
  private JButton homeButton = new JButton("Home");
  private JButton fileButton = new JButton("File");
  private JButton helpButton = new JButton("Help");
  private JButton rectButton = new JButton("Rectangle");
  private JButton circleButton = new JButton("Circle");
  private JButton blueButton = new JButton("Blue");
  private JButton redButton = new JButton("Red");
  private JButton greenButton = new JButton("Green");
  private JButton whiteButton = new JButton("White");
  private JButton blackButton = new JButton("Black");
  private JButton sizeButton = new JButton("Resize");

  // TEXT FIELDS
  private JTextField sizeX = new JTextField();
  private JTextField sizeY = new JTextField();

  public void createFrame() {

    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainPanel = new PaintPanel(context);
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);

    // NAV PANEL->
    navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    navPanel.setBackground(Color.GRAY);

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
    // default shape
    circleButton.setEnabled(false);

    JLabel shapes = new JLabel("Shapes");
    toolPanel.add(shapes);

    JPanel shapeSelectorPanel = new JPanel(new GridLayout(2, 2));
    shapeSelectorPanel.setOpaque(false); // use the gray background
    shapeSelectorPanel.add(rectButton);
    shapeSelectorPanel.add(circleButton);
    toolPanel.add(shapeSelectorPanel);

    // --colours
    // default colour
    blackButton.setEnabled(false);

    JLabel colours = new JLabel("Colours");
    toolPanel.add(colours);

    JPanel colourPanel = new JPanel(new GridLayout(3, 2));
    colourPanel.setOpaque(false);
    blueButton.setBackground(Color.BLUE);
    colourPanel.add(blueButton);
    redButton.setBackground(Color.RED);
    colourPanel.add(redButton);
    greenButton.setBackground(Color.GREEN);
    colourPanel.add(greenButton);
    whiteButton.setBackground(Color.WHITE);
    colourPanel.add(whiteButton);
    blackButton.setBackground(Color.BLACK);
    colourPanel.add(blackButton);
    toolPanel.add(colourPanel);

    // --size
    JLabel size = new JLabel("Size");
    toolPanel.add(size);

    JPanel sizePanel = new JPanel(new GridLayout(6, 1));
    sizeX.setText(String.valueOf(context.getSize().x));
    JLabel labelX = new JLabel("width: ");
    sizeY.setText(String.valueOf(context.getSize().y));
    JLabel labelY = new JLabel("height: ");
    sizePanel.add(labelX);
    sizePanel.add(sizeX);
    sizePanel.add(labelY);
    sizePanel.add(sizeY);
    sizePanel.add(sizeButton);
    toolPanel.add(sizePanel);

    // END <-

    // MAIN
    mainFrame.add(navPanel, BorderLayout.NORTH);
    mainFrame.add(toolPanel, BorderLayout.WEST);
    mainFrame.add(mainPanel, BorderLayout.CENTER);
    mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setVisible(true);

    // kicks off the events
    draw(label);
  }

  private void draw(JLabel label) {
    checkMouseInputs(label);
    checkButtonInputs();
  }

  // TODO: maybe move actions to a seperate class
  private void checkMouseInputs(JLabel label) {
    // when clicked (ONLY PAINTS THE ONE LOCATION)
    mainPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        context.setAction(Action.PAINT);
        context.setPos(event.getPoint());
        mainPanel.paint();
      }
    });
    mainPanel.addMouseMotionListener(new MouseAdapter() {
      // when pressed (ONLY PAINTS WHEN MOUSE IS MOVED)
      @Override
      public void mouseDragged(MouseEvent event) {
        context.setPos(event.getPoint());
        label.setText("X: " + context.getPos().x + " | Y: " + context.getPos().y);
        mainPanel.paint();
      }

      // when not pressed
      @Override
      public void mouseMoved(MouseEvent event) {
        context.setPos(event.getPoint());
        label.setText("X: " + context.getPos().x + " | Y: " + context.getPos().y);
        context.setAction(Action.DEFAULT);
        mainPanel.cursor(); // just repaints the cursor
      }
    });
  }

  // TODO: change into array
  private void checkButtonInputs() {
    rectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setShape(ShapeEnum.RECTANGLE);
        rectButton.setEnabled(false);
        circleButton.setEnabled(true);
      }
    });
    circleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setShape(ShapeEnum.CIRCLE);
        circleButton.setEnabled(false);
        rectButton.setEnabled(true);
      }
    });
    blueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setColour(Color.BLUE);
        blueButton.setEnabled(false);
        redButton.setEnabled(true);
        greenButton.setEnabled(true);
        whiteButton.setEnabled(true);
      }
    });
    redButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setColour(Color.RED);
        redButton.setEnabled(false);
        blueButton.setEnabled(true);
        greenButton.setEnabled(true);
        whiteButton.setEnabled(true);
        blackButton.setEnabled(true);
      }
    });
    greenButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setColour(Color.GREEN);
        greenButton.setEnabled(false);
        blueButton.setEnabled(true);
        redButton.setEnabled(true);
        whiteButton.setEnabled(true);
        blackButton.setEnabled(true);
      }
    });
    whiteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setColour(Color.WHITE);
        whiteButton.setEnabled(false);
        blueButton.setEnabled(true);
        redButton.setEnabled(true);
        greenButton.setEnabled(true);
        blackButton.setEnabled(true);
      }
    });
    blackButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setColour(Color.BLACK);
        blackButton.setEnabled(false);
        blueButton.setEnabled(true);
        redButton.setEnabled(true);
        greenButton.setEnabled(true);
        whiteButton.setEnabled(true);
      }
    });
    sizeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        context.setSize(new Point(
            Integer.valueOf(sizeX.getText()),
            Integer.valueOf(sizeY.getText())));
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
