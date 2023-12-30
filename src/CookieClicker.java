import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CookieClicker extends JFrame{
    private JPanel panel1;
    private JButton clickButton;
    private JButton upgrade1Button;
    private JButton upgrade2Button;
    private JButton upgrade3Button;
    private JButton upgrade4Button;
    private JLabel Score;
    private JLabel Upgrade1;
    private JLabel Upgrade2;
    private JLabel Upgrade3;
    private JLabel Upgrade4;
    private JLabel Bonus;
    private JButton saveButton;
    private JButton autoclick1Button;
    private JButton autoclick2Button;
    private JButton autoclick3Button;
    private JButton autoclick4Button;
    int i = 0;
    int bonus = 1;
    int ilosc1 = 0;

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
            Score.setText("Score:"+i);
        }
    });
    upgrade1Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (i >= 100){
            i -= 100;
            Score.setText("Score:"+i);
            if(ilosc1 == 0){
                bonus = 0;
            }
            bonus += 2;
            Bonus.setText("Bonus:"+bonus);
            ilosc1++;
            Upgrade1.setText("Ilość:"+ilosc1);
        }}

    });
    upgrade2Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int ilosc2 = 0;
            if (i >= 1000){
                i -= 1000;
                Score.setText("Score:"+i);
                if(ilosc1 == 0){
                    bonus = 0;
                }
                bonus += 10;
                Bonus.setText("Bonus:"+bonus);
                ilosc2++;
                Upgrade2.setText("  Ilość:"+ilosc2);
        }}
    });
    upgrade3Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int ilosc3 = 0;
            if (i >= 10000){
                i -= 10000;
                Score.setText("Score:"+i);
                if(ilosc1 == 0){
                    bonus = 0;
                }
                bonus += 100;
                Bonus.setText("Bonus:"+bonus);
                ilosc3++;
                Upgrade3.setText("Ilość:"+ilosc3);
            }
        }
    });
    upgrade4Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int ilosc4 = 0;
            if (i >= 10000){
                i -= 10000;
                Score.setText("Score:"+i);
                if(ilosc1 == 0){
                    bonus = 0;
                }
                bonus += 150;
                Bonus.setText("Bonus:"+bonus);
                ilosc4++;
                Upgrade4.setText("Ilość:"+ilosc4);
            }
        }
    });
    autoclick1Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (i >= 100){
                i -= 100;
                Score.setText("Score:"+i);
                if(ilosc1 == 0)
            }
        }
    });
}
}
