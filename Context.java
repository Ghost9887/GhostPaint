import java.awt.*;

public class Context {

  private Point size = new Point(10, 10);
  private Point pos = new Point(0, 0);
  private Action action = Action.DEFAULT;
  private ShapeEnum shape = ShapeEnum.CIRCLE;
  private Color colour = Color.BLACK;

  public void setPos(Point pos) {
    this.pos = pos;
  }

  public void setSize(Point size) {
    this.size = size;
  }

  public void setShape(ShapeEnum shape) {
    this.shape = shape;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public void setColour(Color colour) {
    this.colour = colour;
  }

  public Point getPos() {
    return pos;
  }

  public Point getSize() {
    return size;
  }

  public Action getAction() {
    return action;
  }

  public ShapeEnum getShape() {
    return shape;
  }

  public Color getColour() {
    return colour;
  }

}
