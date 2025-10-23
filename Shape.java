import java.awt.*;

public class Shape {

  private Point pos;

  private Point size;

  private ShapeEnum id;

  public Shape(Point pos, Point size, ShapeEnum id) {
    this.pos = pos;
    this.size = size;
    this.id = id;
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

}
