import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class PaintPanel extends JPanel {

  private final Context context;

  public PaintPanel(Context context) {
    this.context = context;
  }

  private ArrayList<Point> positions = new ArrayList<>();
  private Point pos = new Point(0, 0);

  public void setPos(Point pos) {
    this.pos = pos;
    repaint(); // calls the paint component
  }

  public void addNewPositions(ArrayList<Point> newPositions) {
    for (int i = 0; i < newPositions.size(); i++) {
      positions.add(new Point(newPositions.get(i)));
    }
    repaint(); // calls the paintComponent
  }

  // needs to inheret the JPanel class to be able to call this
  @Override
  protected void paintComponent(Graphics g) {

    // draw
    if (context.getAction() == Action.PAINT) {
      g.setColor(Color.BLACK);
      for (int i = 0; i < positions.size(); i++) {
        g.fillOval(positions.get(i).x - 5, positions.get(i).y - 5, 10, 10);
      }
      // we clear the array as we don't repaint the screen
      positions.clear();
    }

    // draw the tool outline
    else if (context.getAction() == Action.DEFAULT) {
      super.paintComponent(g); // clears the panel
      g.drawOval(pos.x - 5, pos.y - 5, 10, 10);
    }
  }
}
