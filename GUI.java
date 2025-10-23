import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

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

  private Point mousePos = new Point(0, 0);
  private ShapeEnum currentShape = ShapeEnum.CIRCLE;

  public void createFrame() {

    mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainPanel = new PaintPanel(context);
    mainPanel.setLayout(new BorderLayout());

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
    colourPanel.add(blueButton);
    colourPanel.add(redButton);
    colourPanel.add(greenButton);
    colourPanel.add(whiteButton);
    colourPanel.add(blackButton);
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
        context.setPos(mousePos);
        context.setSize(new Point(10, 10));
        context.setShape(currentShape);
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
        context.setShape(currentShape);
        mainPanel.paint();
      }

      // when not pressed
      @Override
      public void mouseMoved(MouseEvent event) {
        mousePos = event.getPoint();
        label.setText("X: " + mousePos.x + " | Y: " + mousePos.y);
        context.setAction(Action.DEFAULT);
        context.setPos(mousePos);
        context.setShape(currentShape);
        mainPanel.cursor();
      }
    });
  }

  // TODO: change into arrays
  private void checkButtonInputs() {
    rectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        currentShape = ShapeEnum.RECTANGLE;
        rectButton.setEnabled(false);
        circleButton.setEnabled(true);
      }
    });
    circleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        currentShape = ShapeEnum.CIRCLE;
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
