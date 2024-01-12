import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CookieClicker extends JFrame {
    private JPanel panel1;
    private JButton clickButton;
    private JLabel Score;
    private JLabel Bonus;
    private JButton saveButton;
    int i = 0;
    int bonus = 1;


    ImageIcon logo = new ImageIcon(".//src//cookie.png");


    public static void main(String[] args) {
        CookieClicker c = new CookieClicker();
        c.setVisible(true);
    }

    public CookieClicker() {
        super("CookieClicker");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800, height = 600;
        this.setSize(width, height);
        this.setIconImage(logo.getImage());
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                i += bonus;
                Score.setText("Score:" + i);
            }
        });
    }
}
