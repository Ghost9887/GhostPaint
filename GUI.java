import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
  private JLabel actionLabel = new JLabel("Paint");

  // BUTTONS
  private JButton undoButton;
  private JButton redoButton;
  private JButton saveButton;
  private JButton exportButton;
  private JButton helpButton;
  private JButton brushToolButton;
  private JButton drawToolButton;
  private JButton bucketToolButton;
  private JButton rubberToolButton;

  private final int AMOUNT_OF_SHAPES = 2;
  private ShapeEnum shapeArr[];
  private JButton shapeButtons[] = new JButton[AMOUNT_OF_SHAPES];

  private final int AMOUNT_OF_COLOURS = 12;
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

    undoButton = new JButton(icons.getUIIcon("undo"));
    redoButton = new JButton(icons.getUIIcon("redo"));
    saveButton = new JButton(icons.getUIIcon("save"));
    exportButton = new JButton(icons.getUIIcon("export"));
    helpButton = new JButton(icons.getUIIcon("help"));

    navPanel.add(undoButton);
    navPanel.add(redoButton);
    navPanel.add(saveButton);
    navPanel.add(exportButton);
    navPanel.add(helpButton);

    posLabel = new JLabel("");
    posLabel.setFont(new Font(posLabel.getFont().getName(), posLabel.getFont().getStyle(), 20));
    navPanel.add(posLabel, BorderLayout.NORTH);

    actionLabel.setFont(new Font(actionLabel.getFont().getName(), actionLabel.getFont().getStyle(), 20));
    navPanel.add(actionLabel, BorderLayout.NORTH);
    // END <-

    // TOOL PANEL ->
    toolPanel = new JPanel();
    toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
    toolPanel.setBackground(Color.GRAY);

    // --Tools
    JLabel tools = new JLabel("Tools");
    toolPanel.add(tools);

    JPanel toolSelectorPanel = new JPanel(new GridLayout(2, 2));

    brushToolButton = new JButton(icons.getUIIcon("brush"));
    brushToolButton.setEnabled(false);
    drawToolButton = new JButton(icons.getUIIcon("draw"));
    bucketToolButton = new JButton(icons.getUIIcon("bucket"));
    rubberToolButton = new JButton(icons.getUIIcon("rubber"));

    toolSelectorPanel.add(brushToolButton);
    toolSelectorPanel.add(drawToolButton);
    toolSelectorPanel.add(bucketToolButton);
    toolSelectorPanel.add(rubberToolButton);

    toolPanel.add(toolSelectorPanel);

    // -- shape selector
    JLabel shapes = new JLabel("Shapes");
    toolPanel.add(shapes);

    shapeArr = new ShapeEnum[] { ShapeEnum.CIRCLE, ShapeEnum.RECTANGLE };

    JPanel shapeSelectorPanel = new JPanel(new GridLayout(1, 2));

    for (int i = 0; i < AMOUNT_OF_SHAPES; i++) {
      shapeButtons[i] = new JButton(icons.getShapeIcon(shapeArr[i]));
      shapeSelectorPanel.add(shapeButtons[i]);
    }
    shapeButtons[0].setEnabled(false);
    toolPanel.add(shapeSelectorPanel);

    // --colours
    JLabel colours = new JLabel("Colours");
    toolPanel.add(colours);

    JPanel colourPanel = new JPanel(new GridLayout(4, 3));

    colourArr = new Color[] { Color.BLACK, Color.RED,
        Color.GREEN, Color.BLUE, Color.WHITE, Color.YELLOW,
        Color.PINK, Color.GRAY, Color.ORANGE, Color.MAGENTA,
        Color.CYAN, Color.LIGHT_GRAY
    };

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

  // TODO: Refacotr calling context depnded of the case but there might be a
  // better way
  private void checkMouseInputs() {
    // when clicked (ONLY PAINTS THE ONE LOCATION)
    mainPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent event) {
        switch (context.getAction()) {
          case Action.PAINT:
            context.setPrevPos(context.getPos());
            context.setPos(event.getPoint());
            ArrayList<Point> list = new ArrayList<>();
            list.add(context.getPos());
            mainPanel.paint(list);
            break;
          case Action.DRAW:
            context.setPrevPos(context.getPos());
            break;
          case Action.ERASE:
            context.setPrevPos(context.getPos());
            context.setPos(event.getPoint());
            ArrayList<Point> list1 = new ArrayList<>();
            list1.add(context.getPos());
            mainPanel.paint(list1);
            break;
          default:
            break;
        }
      }
    });
    mainPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent event) {
        switch (context.getAction()) {
          case Action.PAINT:
            context.setPos(event.getPoint());
            break;
          case Action.DRAW:
            context.setPos(event.getPoint());
            break;
          case Action.ERASE:
            context.setPos(event.getPoint());
          default:
            break;
        }
      }

      // when released
      @Override
      public void mouseReleased(MouseEvent event) {
        switch (context.getAction()) {
          case Action.DRAW:
            context.setPos(event.getPoint());
            mainPanel.draw();
            break;
          default:
            break;
        }
      }
    });
    mainPanel.addMouseMotionListener(new MouseAdapter() {
      // when dragged (ONLY PAINTS WHEN MOUSE IS MOVED)
      @Override
      public void mouseDragged(MouseEvent event) {
        switch (context.getAction()) {
          case Action.PAINT:
            context.setPrevPos(context.getPos());
            context.setPos(event.getPoint());
            mainPanel.paint(getNewPos());
            break;
          case Action.DRAW:
            break;
          case Action.ERASE:
            context.setPrevPos(context.getPos());
            context.setPos(event.getPoint());
            mainPanel.paint(getNewPos());
          default:
            break;
        }
        posLabel.setText("X: " + context.getPos().x + " | Y: " + context.getPos().y + " | ");
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

    // Nav bar
    undoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        mainPanel.undo();
      }
    });

    redoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        mainPanel.redo();
      }
    });

    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        mainPanel.saveFile();
      }
    });

    exportButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        mainPanel.loadFile();
      }
    });

    // TOOLS
    brushToolButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        brushToolButton.setEnabled(false);
        drawToolButton.setEnabled(true);
        bucketToolButton.setEnabled(true);
        rubberToolButton.setEnabled(true);
        context.setAction(Action.PAINT);
        actionLabel.setText("Paint");
      }
    });
    drawToolButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        drawToolButton.setEnabled(false);
        brushToolButton.setEnabled(true);
        bucketToolButton.setEnabled(true);
        rubberToolButton.setEnabled(true);
        context.setAction(Action.DRAW);
        actionLabel.setText("Draw");
      }
    });
    bucketToolButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        bucketToolButton.setEnabled(false);
        brushToolButton.setEnabled(true);
        drawToolButton.setEnabled(true);
        rubberToolButton.setEnabled(true);
        context.setAction(Action.FILL);
        actionLabel.setText("Fill");
      }
    });
    rubberToolButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        rubberToolButton.setEnabled(false);
        brushToolButton.setEnabled(true);
        drawToolButton.setEnabled(true);
        bucketToolButton.setEnabled(true);
        context.setAction(Action.ERASE);
        actionLabel.setText("Erase");
      }
    });

    // SHAPES
    for (int i = 0; i < AMOUNT_OF_SHAPES; i++) {
      final int index = i;
      shapeButtons[index].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
          context.setShape(shapeArr[index]);
          shapeButtons[index].setEnabled(false);
          for (int j = 0; j < AMOUNT_OF_SHAPES; j++) {
            if (index != j) {
              shapeButtons[j].setEnabled(true);
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

  public ArrayList<Point> getNewPos() {
    ArrayList<Point> newPositions = new ArrayList<>();
    int x = context.getPos().x;
    int y = context.getPos().y;
    int newX = context.getPrevPos().x;
    int newY = context.getPrevPos().y;

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
