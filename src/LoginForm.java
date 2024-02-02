import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JPanel LoginPanel;
    private JTextField tfName;
    private JButton okButton;
    private JButton cancelButton;
    private JPasswordField pfPassword;
    private JButton registerButton;

    public LoginForm(JFrame parent) {
        super();
        setTitle("Login");
        setContentPane(LoginPanel);
        int width = 450, height = 475;
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Database.getDB().Connect();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String password = String.valueOf(pfPassword.getPassword());

                Account account = Login.login(name, password);
                if (account != null) {
                    dispose();
                    CookieClicker cookieclicker = new CookieClicker(account);
                    cookieclicker.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or password incorrect",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Register register = new Register(LoginForm.this);
            }
        });
    }

    public static void main(String[] args) {
        LoginForm l = new LoginForm(null);
    }
}
