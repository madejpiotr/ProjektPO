import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm2 extends JDialog{
    private JPanel LoginPanel;
    private JTextField tfEmail;
    private JButton okButton;
    private JButton cancelButton;
    private JPasswordField pfPassword;

    public LoginForm2(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        int width = 450, height = 475;
        setMinimumSize(new Dimension(width, height));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAutenticateUser(email, password);

                if(user != null){
                    dispose();
                    CookieClicker cookieclicker = new CookieClicker();
                    cookieclicker.setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(LoginForm2.this,
                            "Email or password incorrect",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }
    public static User user;
    private User getAutenticateUser(String email, String password){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/ProjektPO?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.password = resultSet.getString("password");
            }

            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        LoginForm2 l = new LoginForm2(  null);
        User user = LoginForm2.user;
        if(user != null){
            System.out.println("Successful Authentication of "+user.name);
            System.out.println("\t\tEmail:"+user.email);
        }else{
            System.out.println("Authentication canceled");
        }
    }
}
