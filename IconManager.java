import java.util.HashMap;
import java.util.Map;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

//TODO: find a way to include more types not just this enum
public class IconManager {
  private HashMap<ShapeEnum, ImageIcon> shapeIcons;
  private HashMap<String, ImageIcon> UIIcons;

  private Point size = new Point(50, 50);

  public void initIcons() {
    // SHAPE ICONS
    shapeIcons = new HashMap<ShapeEnum, ImageIcon>();
    shapeIcons.put(ShapeEnum.CIRCLE, new ImageIcon("icons/circle.png"));
    shapeIcons.put(ShapeEnum.RECTANGLE, new ImageIcon("icons/rectangle.png"));

    // UI ICONS
    UIIcons = new HashMap<String, ImageIcon>();
    UIIcons.put("redo", new ImageIcon("icons/redo.png"));
    UIIcons.put("undo", new ImageIcon("icons/undo.png"));
    UIIcons.put("bin", new ImageIcon("icons/bin.png"));
    UIIcons.put("save", new ImageIcon("icons/save.png"));
    UIIcons.put("load", new ImageIcon("icons/load.png"));
    UIIcons.put("export", new ImageIcon("icons/export.png"));
    UIIcons.put("brush", new ImageIcon("icons/brush.png"));
    UIIcons.put("draw", new ImageIcon("icons/draw.png"));
    UIIcons.put("bucket", new ImageIcon("icons/bucket.png"));
    UIIcons.put("rubber", new ImageIcon("icons/rubber.png"));
    resizeShapeIcons();
    resizeUIIcons();
  }

  private void resizeShapeIcons() {
    HashMap<ShapeEnum, ImageIcon> resizedIcons = new HashMap<>();
    for (Map.Entry<ShapeEnum, ImageIcon> entry : shapeIcons.entrySet()) {
      ShapeEnum shape = entry.getKey();
      ImageIcon originalIcon = entry.getValue();

      Image scaledImage = originalIcon.getImage().getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
      ImageIcon resizedIcon = new ImageIcon(scaledImage);

      resizedIcons.put(shape, resizedIcon);
    }
    // replace the hashmap
    shapeIcons = resizedIcons;
  }

  private void resizeUIIcons() {
    HashMap<String, ImageIcon> resizedIcons = new HashMap<>();
    for (Map.Entry<String, ImageIcon> entry : UIIcons.entrySet()) {
      String key = entry.getKey();
      ImageIcon originalIcon = entry.getValue();

      Image scaledImage = originalIcon.getImage().getScaledInstance(size.x, size.y, Image.SCALE_SMOOTH);
      ImageIcon resizedIcon = new ImageIcon(scaledImage);

      resizedIcons.put(key, resizedIcon);
    }
    // replace the hashmap
    UIIcons = resizedIcons;
  }

  public ImageIcon getShapeIcon(ShapeEnum key) {
    return shapeIcons.get(key);
  }

  public ImageIcon getUIIcon(String key) {
    return UIIcons.get(key);
  }

}
