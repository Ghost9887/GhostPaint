import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

  private final Context context = new Context();
  private final IconManager icons = new IconManager();

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  private final int MAX_SIZE = 150;
  private final int MIN_SIZE = 2;

  private JFrame mainFrame;
  private PaintPanel mainPanel;
  private JPanel navPanel;
  private JPanel toolPanel;

  // LABLES
  private JLabel posLabel = new JLabel("");
  private JLabel actionLabel = new JLabel("");

  // BUTTONS
  private JButton homeButton = new JButton("Home");
  private JButton fileButton = new JButton("File");
  private JButton helpButton = new JButton("Help");
  private final int AMOUNT_OF_SHAPES = 2;
  private ShapeEnum shapeArr[];
  private JButton shapeButtons[] = new JButton[AMOUNT_OF_SHAPES];
  private JButton brushShapeButtons[] = new JButton[AMOUNT_OF_SHAPES];
  private final int AMOUNT_OF_COLOURS = 5;
  private Color colourArr[];
  private JButton colourButtons[] = new JButton[AMOUNT_OF_COLOURS];
  private JButton sizeButton = new JButton("Resize");
  private JButton clearButton;

  // TEXT FIELDS
  private JTextField sizeX = new JTextField();
  private JTextField sizeY = new JTextField();

  public void createFrame() {

    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainPanel = new PaintPanel(context);
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);

    // init icons
    icons.initIcons();

    // NAV PANEL->
    navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    navPanel.setBackground(Color.GRAY);

    navPanel.add(homeButton);
    navPanel.add(fileButton);
    navPanel.add(helpButton);

    posLabel = new JLabel("");
    posLabel.setFont(new Font(posLabel.getFont().getName(), posLabel.getFont().getStyle(), 20));
    navPanel.add(posLabel, BorderLayout.NORTH);

    actionLabel = new JLabel("");
    actionLabel.setFont(new Font(actionLabel.getFont().getName(), actionLabel.getFont().getStyle(), 20));
    navPanel.add(actionLabel, BorderLayout.NORTH);
    // END <-

    // TOOL PANEL ->
    toolPanel = new JPanel();
    toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
    toolPanel.setBackground(Color.GRAY);

    // -- shape selector(drawing shapes)
    JLabel shapes = new JLabel("Draw Shapes");
    toolPanel.add(shapes);

    shapeArr = new ShapeEnum[] { ShapeEnum.CIRCLE, ShapeEnum.RECTANGLE };

    JPanel shapeSelectorPanel = new JPanel(new GridLayout(1, 2));

    for (int i = 0; i < AMOUNT_OF_SHAPES; i++) {
      shapeButtons[i] = new JButton(icons.getShapeIcon(shapeArr[i]));
      shapeSelectorPanel.add(shapeButtons[i]);
    }
    toolPanel.add(shapeSelectorPanel);

    // -- brush shapes selector
    JLabel brushShapes = new JLabel("Paint Shapes");
    toolPanel.add(brushShapes);

    JPanel brushShapeSelectorPanel = new JPanel(new GridLayout(1, 2));

    for (int i = 0; i < AMOUNT_OF_SHAPES; i++) {
      brushShapeButtons[i] = new JButton(icons.getShapeIcon(shapeArr[i]));
      brushShapeSelectorPanel.add(brushShapeButtons[i]);
    }
    brushShapeButtons[0].setEnabled(false); // default shape
    toolPanel.add(brushShapeSelectorPanel);

    // --colours
    JLabel colours = new JLabel("Colours");
    toolPanel.add(colours);

    JPanel colourPanel = new JPanel(new GridLayout(3, 2));

    colourArr = new Color[] { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.WHITE };

    for (int i = 0; i < AMOUNT_OF_COLOURS; i++) {
      colourButtons[i] = new JButton();
      colourButtons[i].setBackground(colourArr[i]);
      colourPanel.add(colourButtons[i]);
    }
    colourButtons[0].setEnabled(false); // default colour
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

    // --clear

    JLabel clearLabel = new JLabel("Clear Screen");
    toolPanel.add(clearLabel);
    JPanel clearPanel = new JPanel(new GridLayout(1, 1));
    clearButton = new JButton(icons.getUIIcon("bin"));
    clearPanel.add(clearButton);
    toolPanel.add(clearPanel);
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
    draw();
  }

  private void draw() {
    checkMouseInputs();
    checkButtonInputs();
  }

  private void checkMouseInputs() {
    // when clicked (ONLY PAINTS THE ONE LOCATION)
    mainPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        context.setPos(event.getPoint());
        switch (context.getAction()) {
          case Action.PAINT:
            mainPanel.paint();
            break;
          case Action.DRAW:
            context.setPrevPos(context.getPos());
            break;
          default:
            break;
        }
      }
    });
    mainPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent event) {
        context.setPos(event.getPoint());
        switch (context.getAction()) {
          case Action.PAINT:
            break;
          case Action.DRAW:
            break;
          default:
            break;
        }
      }

      // when released
      @Override
      public void mouseReleased(MouseEvent event) {
        context.setPos(event.getPoint());
        switch (context.getAction()) {
          case Action.DRAW:
            mainPanel.draw();
            break;
          default:
            break;
        }
      }
    });
    mainPanel.addMouseMotionListener(new MouseAdapter() {
      // when pressed (ONLY PAINTS WHEN MOUSE IS MOVED)
      @Override
      public void mouseDragged(MouseEvent event) {
        context.setPos(event.getPoint());
        posLabel.setText("X: " + context.getPos().x + " | Y: " + context.getPos().y + " | ");
        switch (context.getAction()) {
          case Action.PAINT:
            mainPanel.paint();
            break;
          case Action.DRAW:
            break;
          default:
            break;
        }
      }

      // when not pressed
      @Override
      public void mouseMoved(MouseEvent event) {
        context.setPos(event.getPoint());
        posLabel.setText("X: " + context.getPos().x + " | Y: " + context.getPos().y + " | ");
        mainPanel.cursor(); // just repaints the cursor
      }
    });
  }

  public void checkButtonInputs() {

    // SHAPES
    for (int i = 0; i < AMOUNT_OF_SHAPES; i++) {
      final int index = i;
      shapeButtons[index].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
          context.setShape(shapeArr[index]);
          context.setAction(Action.DRAW);
          actionLabel.setText("Draw");
          shapeButtons[index].setEnabled(false);
          for (int j = 0; j < AMOUNT_OF_SHAPES; j++) {
            // unselect the bursh shape buttons
            brushShapeButtons[j].setEnabled(true);
            if (index != j) {
              shapeButtons[j].setEnabled(true);
            }
          }
        }
      });
    }

    // PAINT BRUSH SHAPES
    for (int i = 0; i < AMOUNT_OF_SHAPES; i++) {
      final int index = i;
      brushShapeButtons[index].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
          context.setShape(shapeArr[index]);
          context.setAction(Action.PAINT);
          actionLabel.setText("Paint");
          brushShapeButtons[index].setEnabled(false);
          for (int j = 0; j < AMOUNT_OF_SHAPES; j++) {
            // unselect the shape buttons
            shapeButtons[j].setEnabled(true);
            if (index != j) {
              brushShapeButtons[j].setEnabled(true);
            }
          }
        }
      });
    }

    // COLOURS
    for (int i = 0; i < AMOUNT_OF_COLOURS; i++) {
      // java thing needs to be like this because of the anonymous function
      final int index = i;
      colourButtons[index].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
          context.setColour(colourArr[index]);
          colourButtons[index].setEnabled(false);
          for (int j = 0; j < AMOUNT_OF_COLOURS; j++) {
            if (index != j) {
              colourButtons[j].setEnabled(true);
            }
          }
        }
      });
    }

    // SIZE
    sizeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        if (Integer.valueOf(sizeX.getText()) < MIN_SIZE) {
          sizeX.setText(String.valueOf(MIN_SIZE));
        } else if (Integer.valueOf(sizeX.getText()) > MAX_SIZE) {
          sizeX.setText(String.valueOf(MAX_SIZE));
        }
        if (Integer.valueOf(sizeY.getText()) < MIN_SIZE) {
          sizeY.setText(String.valueOf(MIN_SIZE));
        } else if (Integer.valueOf(sizeY.getText()) > MAX_SIZE) {
          sizeY.setText(String.valueOf(MAX_SIZE));
        }
        context.setSize(new Point(
            Integer.valueOf(sizeX.getText()),
            Integer.valueOf(sizeY.getText())));
        mainPanel.cursor();
      }
    });
    clearButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        mainPanel.clearScreen();
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
