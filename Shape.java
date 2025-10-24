import java.awt.*;

public class Shape {

  private Point pos;
  private Point size;
  private ShapeEnum id;
  private Color colour;
  private boolean fill;

  public Shape(Point pos, Point size, ShapeEnum id, Color colour, boolean fill) {
    this.pos = pos;
    this.size = size;
    this.id = id;
    this.colour = colour;
    this.fill = fill;
  }

  // getters
  public Point getPos() {
    return pos;
  }

  public Point getSize() {
    return size;
  }

  public ShapeEnum getId() {
    return id;
  }

  public Color getColour() {
    return colour;
  }

  public boolean getFill() {
    return fill;
  }

}
