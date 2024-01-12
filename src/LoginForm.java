import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class LoginForm extends JDialog {
    private JPanel LoginPanel;
    private JTextField tfEmail;
    private JButton okButton;
    private JButton cancelButton;
    private JPasswordField pfPassword;

    public LoginForm(JFrame parent) {
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
                test();
                if (user != null) {
                    dispose();
                    CookieClicker cookieclicker = new CookieClicker();
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
    }

    public static User user;

    private User getAutenticateUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/ProjektPO?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("password"));
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public void test() {
        Database db = new Database();
        if (db.Connect()) {
            testItem(db);
            testAssistant(db);
            testScore(db);
            testScoreBoard(db);
            testPurchase(db);
            testContract(db);
            testAccount(db);
        }
    }

    public void testItem(Database db) {
        Items items = new Items(db);
        Item item = new Item("abc", 3);
        if (items.Create(item)) {
            JOptionPane.showMessageDialog(LoginForm.this, "Udało sie dodać item", "Item", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Nie udało sie dodać itema", "Item", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void testAssistant(Database db) {
        Assistants assitants = new Assistants(db);
        Assistant assistant = new Assistant("abc", 3);
        if (assitants.Create(assistant)) {
            JOptionPane.showMessageDialog(LoginForm.this, "Udało sie dodać assistanta", "Item", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Nie udało sie dodać assistanta", "Item", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void testScore(Database db) {
        Scores scores = new Scores(db);
        Score score = new Score(1, 124, 124, 142);
        if (scores.Create(score)) {
            JOptionPane.showMessageDialog(LoginForm.this, "Udało sie dodać score", "Item", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Nie udało sie dodać score'a", "Item", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void testScoreBoard(Database db) {
        ScoreBoards scoreboards = new ScoreBoards(db);
        ScoreBoard scoreBoard = new ScoreBoard("abc");
        if (scoreboards.Create(scoreBoard)) {
            JOptionPane.showMessageDialog(LoginForm.this, "Udało sie dodać item", "Item", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Nie udało sie dodać itema", "Item", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void testPurchase(Database db) {
        Purchases purchases = new Purchases(db);
        Purchase purchase = new Purchase(1, "abc", "abc");
        if (purchases.Create(purchase)) {
            JOptionPane.showMessageDialog(LoginForm.this, "Udało sie dodać purchase", "Item", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Nie udało sie dodać purchase'a", "Item", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void testContract(Database db) {
        Contracts contracts = new Contracts(db);
        Contract contract = new Contract(1, "abc", "abc");
        if (contracts.Create(contract)) {
            JOptionPane.showMessageDialog(LoginForm.this, "Udało sie dodać contract", "Item", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Nie udało sie dodać contract'a", "Item", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void testAccount(Database db) {
        Accounts accounts = new Accounts(db);
        User user = new User("abc", "abc@gmail.com", "abc");
        Score score = new Score(1, 124, 124, 142);
        ArrayList<Item> items = new ArrayList<Item>();
        Item item = new Item("abc", 3);
        Item item1 = new Item("bca", 53);
        items.add(item);
        items.add(item1);
        ArrayList<Assistant> assistants = new ArrayList<Assistant>();
        Assistant assistant = new Assistant("abc", 3);
        Assistant assistant1 = new Assistant("bdd", 41);
        assistants.add(assistant);
        assistants.add(assistant1);
        Account account = new Account(1000, user, score, items, assistants);
        if (accounts.Create(account)) {
            JOptionPane.showMessageDialog(LoginForm.this, "Udało sie dodać account", "Item", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(LoginForm.this, "Nie udało sie dodać accounta", "Item", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        LoginForm l = new LoginForm(null);
        User user = LoginForm.user;
        if (user != null) {
            System.out.println("Successful Authentication of " + user.name);
            System.out.println("\t\tEmail:" + user.email);
        } else {
            System.out.println("Authentication canceled");
        }
    }
}
