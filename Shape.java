import java.awt.*;

public class Shape {

  private Point pos;
  private Point size;
  private ShapeEnum id;
  private Color colour;

  public Shape(Point pos, Point size, ShapeEnum id, Color colour) {
    this.pos = pos;
    this.size = size;
    this.id = id;
    this.colour = colour;
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

}
