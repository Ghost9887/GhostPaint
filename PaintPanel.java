import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class PaintPanel extends JPanel {

  private ArrayList<Point> positions = new ArrayList<>();

  public void addPos(ArrayList<Point> newPos) {
    for (int i = 0; i < newPos.size(); i++) {
      positions.add(new Point(newPos.get(i)));
    }
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
