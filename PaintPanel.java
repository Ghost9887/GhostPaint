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

  private ArrayList<Point> positions = new ArrayList<>();

  public void paint() {
    Shape newShape = new Shape(
        context.getPos(),
        context.getSize(),
        context.getShape());
    shapes.push(newShape);
    repaint();
  }

  public void cursor() {
    repaint();
  }

  // needs to inheret the JPanel class to be able to call this
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g); // clear the screen
    g.setColor(Color.BLACK);
    if (context.getAction() == Action.DEFAULT) {
      g.drawOval(
          context.getPos().x - context.getSize().x / 2,
          context.getPos().y - context.getSize().y / 2,
          context.getSize().x,
          context.getSize().y);
    }
    if (!shapes.empty()) {
      for (Shape shape : shapes) {

        if (shape.getId() == ShapeEnum.CIRCLE) {
          g.fillOval(shape.getPos().x, shape.getPos().y,
              shape.getSize().x, shape.getSize().y);
        }

        else if (shape.getId() == ShapeEnum.RECTANGLE) {
          g.fillRect(shape.getPos().x, shape.getPos().y,
              shape.getSize().x, shape.getSize().y);
        }
      }
    }
  }

}
