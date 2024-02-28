import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        APPFrame mainAppFrame = new APPFrame("Test frame");
        mainAppFrame.setSize(500, 500);
        mainAppFrame.setResizable(false);
        mainAppFrame.setVisible(true);
        mainAppFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}