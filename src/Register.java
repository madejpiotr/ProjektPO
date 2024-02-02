import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class Register extends JDialog {
    private JPanel registerPanel;
    private JTextField tfName;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JPasswordField pfPasswordConfirm;
    private JButton cancelButton;
    private JButton registerButton;

    public Register(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        int width = 450, height = 475;
        setMinimumSize(new Dimension(width, height));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Database.getDB().Connect();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = tfName.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfPasswordConfirm.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(name, email, password);
        if (user != null) {
            this.getParent().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user", "Try again", JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;

    private User addUserToDatabase(String name, String email, String password) {
        User user = new User(name, email, password);
        Users users = new Users(Database.getDB());
        if (!users.Create(user)) {
            return null;
        }
        Score score = new Score(0, 0, 0);
        Scores scores = new Scores(Database.getDB());
        scores.Create(score);
        ArrayList<Score> scoreList = new ArrayList<Score>();
        Account account = new Account(0, user, score, new ArrayList<Item>(), new ArrayList<Assistant>());
        Accounts accounts = new Accounts(Database.getDB());
        accounts.Create(account);
        return user;
    }

//    public static void main(String[] args) {
//        Register r = new Register(null);
//        User user = r.user;
//        if (user != null) {
//            System.out.println("Successful registration of: " + user.name);
//        } else {
//            System.out.println("Failed registration");
//        }
//    }
}