import javax.swing.*;
import java.awt.*;

public class GUI {

  private final int SCREEN_WIDTH = 1200;
  private final int SCREEN_HEIGHT = 800;

  void createFrame() {

    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    label.setText("Hello, World!");
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));

    frame.setVisible(true);
    frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    frame.add(label);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
