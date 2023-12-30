import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

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
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user", "Try again", JOptionPane.ERROR_MESSAGE);
        }
    }
public User user;
    private User addUserToDatabase(String name, String email, String password){
        User user = null;
        //spr czy podlaczone jest do bazy danych
        final String DB_URL = "jdbc:mysql://localhost/ProjektPO?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (name, email, password) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            //insert row into the table

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows > 0){
                user = new User();
                user.name = name;
                user.email = email;
                user.password = password;
            }
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
    public static void main(String[] args) {
        Register r = new Register(null);
        User user = r.user;
        if(user != null){
            System.out.println("Successful registration of: "+user.name);
        }else{
            System.out.println("Failed registration");
        }
    }
}