import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.awt.*;

import javax.swing.JFileChooser;

public class Save {

  public void saveFile(Stack<Shape> shapes) {
    JFileChooser files = new JFileChooser();
    files.setSelectedFile(new File("paint.gp"));
    int res = files.showSaveDialog(null);
    if (res == JFileChooser.APPROVE_OPTION) {
      File fileToSave = files.getSelectedFile();
      try (FileWriter writer = new FileWriter(fileToSave)) {
        for (Shape shape : shapes) {
          writer.write(shape.getId() + ";" +
              shape.getColour() + ";" + shape.getPos() + ";" +
              shape.getSize() + ";" + shape.getFill() + "\n");
        }
        System.out.println("File Saved: " + fileToSave.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public ArrayList<Shape> loadFile() {
    ArrayList<Shape> shapes = new ArrayList<>();
    JFileChooser files = new JFileChooser();
    int res = files.showOpenDialog(null);
    if (res == JFileChooser.APPROVE_OPTION) {
      File fileToLoad = files.getSelectedFile();
      try (Scanner scanner = new Scanner(fileToLoad)) {
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine();

          String[] parts = line.split(";");

          String type = parts[0];

          String colorStr = parts[1];
          int r = Integer.parseInt(colorStr.replaceAll(".*r=(\\d+).*", "$1"));
          int g = Integer.parseInt(colorStr.replaceAll(".*g=(\\d+).*", "$1"));
          int b = Integer.parseInt(colorStr.replaceAll(".*b=(\\d+).*", "$1"));
          Color color = new Color(r, g, b);

          String posStr = parts[2];
          int x1 = Integer.parseInt(posStr.replaceAll(".*x=(\\d+).*", "$1"));
          int y1 = Integer.parseInt(posStr.replaceAll(".*y=(\\d+).*", "$1"));
          Point position = new Point(x1, y1);

          String sizeStr = parts[3];
          int x2 = Integer.parseInt(sizeStr.replaceAll(".*x=(\\d+).*", "$1"));
          int y2 = Integer.parseInt(sizeStr.replaceAll(".*y=(\\d+).*", "$1"));
          Point size = new Point(x2, y2);

          boolean fill = Boolean.parseBoolean(parts[4]);

          ShapeEnum id = ShapeEnum.CIRCLE;
          switch (type) {
            case "CIRCLE":
              id = ShapeEnum.CIRCLE;
              break;
            case "RECTANGLE":
              id = ShapeEnum.RECTANGLE;
              break;
            default:
              break;
          }

          Shape shape = new Shape(
              position,
              size,
              id,
              color,
              fill);
          shapes.add(shape);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return shapes;
  }

}
