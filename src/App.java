import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame obj = new JFrame();

        Gameplay gameplay = new Gameplay();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("BrickBreaker Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);

    }
}
