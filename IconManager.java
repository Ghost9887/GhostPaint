import java.util.HashMap;
import java.util.Map;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

//TODO: find a way to include more types not just this enum
public class IconManager {
  private HashMap<ShapeEnum, ImageIcon> icons;

  private Point size = new Point(50, 50);

  public void initIcons() {
    icons = new HashMap<ShapeEnum, ImageIcon>();
    icons.put(ShapeEnum.CIRCLE, new ImageIcon("icons/circle.png"));
    icons.put(ShapeEnum.RECTANGLE, new ImageIcon("icons/rectangle.png"));
    resizeIcons();
  }

  private void resizeIcons() {
    HashMap<ShapeEnum, ImageIcon> resizedIcons = new HashMap<>();
    for (Map.Entry<ShapeEnum, ImageIcon> entry : icons.entrySet()) {
      ShapeEnum shape = entry.getKey();
      ImageIcon originalIcon = entry.getValue();

      Image scaledImage = originalIcon.getImage().getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
      ImageIcon resizedIcon = new ImageIcon(scaledImage);

      resizedIcons.put(shape, resizedIcon);
    }
    // replace the hashmap
    icons = resizedIcons;
  }

  public ImageIcon getIcon(ShapeEnum key) {
    return icons.get(key);
  }

}
