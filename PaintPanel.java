import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class PaintPanel extends JPanel {

  private Point pos = new Point(0, 0);

  private ArrayList<Point> positions = new ArrayList<>();

  public void setPos(int x, int y) {
    this.pos.x = x;
    this.pos.y = y;
    positions.add(new Point(pos)); // have to create a new Point object
    repaint(); // calls the paintComponent
  }

  // needs to inheret the JPanel class to be able to call this
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g); // clears the panel
    g.setColor(Color.BLUE);
    for (int i = 0; i < positions.size(); i++) {
      g.fillOval(positions.get(i).x, positions.get(i).y, 20, 20);
    }
  }
}
