import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class PaintPanel extends JPanel {

  private final Context context;

  private Stack<Shape> shapes = new Stack<>();

  public PaintPanel(Context context) {
    this.context = context;
  }

  public void paint(ArrayList<Point> list) {
    for(int i = 0; i < list.size(); i++){
    Shape newShape = new Shape(
          list.get(i),
          context.getSize(),
          context.getShape(),
          context.getColour(),
          true);
      shapes.push(newShape);
    }
    repaint();
  }

  public void draw() {
    // cant put negative numbers in g.drawRect/Oval/...(x, y, width, height);
    int width = Math.abs(context.getPos().x - context.getPrevPos().x);
    int height = Math.abs(context.getPos().y - context.getPrevPos().y);

    // find the corner that closer to the left edge
    int topLeftX = Math.min(context.getPrevPos().x, context.getPos().x);
    int topLeftY = Math.min(context.getPrevPos().y, context.getPos().y);

    Point position = new Point(topLeftX, topLeftY);
    Point size = new Point(width, height);

    Shape newShape = new Shape(
        position,
        size,
        context.getShape(),
        context.getColour(),
        false);
    shapes.push(newShape);
    repaint();
  }

  public void cursor() {
    repaint();
  }

  public void clearScreen() {
    shapes.clear();
  }

  // needs to inheret the JPanel class to be able to call this
  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g); // clear the screen

    if (context.getAction() == Action.PAINT) {
      // change cursor colour
      if (context.getColour() != Color.WHITE)
        g.setColor(context.getColour());
      else
        g.setColor(Color.BLACK);
      if (context.getShape() == ShapeEnum.CIRCLE) {
        g.drawOval(
            context.getPos().x - context.getSize().x / 2,
            context.getPos().y - context.getSize().y / 2,
            context.getSize().x,
            context.getSize().y);
      } else if (context.getShape() == ShapeEnum.RECTANGLE) {
        g.drawRect(
            context.getPos().x - context.getSize().x / 2,
            context.getPos().y - context.getSize().y / 2,
            context.getSize().x,
            context.getSize().y);
      }
    }

    else if (context.getAction() == Action.DRAW) {
      g.setColor(context.getColour());
      //default size for the cursor
      g.fillOval(
          context.getPos().x - 5,
          context.getPos().y - 5,
          10,
          10);
    }

    if (!shapes.empty()) {
      for (Shape shape : shapes) {
        g.setColor(shape.getColour());
        if (shape.getFill() == true) {
          if (shape.getId() == ShapeEnum.CIRCLE) {
            g.fillOval(
                shape.getPos().x - shape.getSize().x / 2,
                shape.getPos().y - shape.getSize().y / 2,
                shape.getSize().x,
                shape.getSize().y);
          }

          else if (shape.getId() == ShapeEnum.RECTANGLE) {
            g.fillRect(
                shape.getPos().x - shape.getSize().x / 2,
                shape.getPos().y - shape.getSize().y / 2,
                shape.getSize().x,
                shape.getSize().y);
          }
        } else if (shape.getFill() == false) {
          if (shape.getId() == ShapeEnum.CIRCLE) {
            g.drawOval(
                shape.getPos().x,
                shape.getPos().y,
                shape.getSize().x,
                shape.getSize().y);
          } else if (shape.getId() == ShapeEnum.RECTANGLE) {
            g.drawRect(
                shape.getPos().x,
                shape.getPos().y,
                shape.getSize().x,
                shape.getSize().y);
          }
        }
      }
    }
  }

}
