import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFileChooser;

public class Save {

  public void saveFile(Stack<Shape> shapes) {
    JFileChooser files = new JFileChooser();
    files.setSelectedFile(new File("paint.gp"));
    int res = files.showSaveDialog(null);
    // 1 - cancel
    // 0 - save
    if (res == 0) {
      File fileToSave = files.getSelectedFile();
      try (FileWriter writer = new FileWriter(fileToSave)) {
        for (Shape shape : shapes) {
          writer.write("Shape[" + shape.getId() + ";" +
              shape.getColour() + ";" + shape.getPos() + ";" +
              shape.getSize() + ";" + shape.getFill() + "]|");
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
    // 1 - cancel
    // 0 - save
    return shapes;
  }
}
