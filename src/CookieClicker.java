import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CookieClicker extends JFrame {
    private JPanel panel1;
    private JButton clickButton;
    private JLabel Score;
    private JLabel Bonus;
    private JButton saveButton;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTable leftTable;
    private JTable rightTable;
    private JLabel PassiveBonus;
    private CookieTimer cookieTimer = null;
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
        Database.getDB().Connect();
        createTable(leftTable, "Items", new ItemButtonEditor(this, new JCheckBox()));
        createTable(rightTable, "Assistants", new AssistantButtonEditor(this, new JCheckBox()));
        loadShop();
        update();
        this.cookieTimer = new CookieTimer();
        this.cookieTimer.schedule(() -> onTick(), TimeUnit.SECONDS.toMillis(10));
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = ActualAccount.getActual();
                account.cookies += 1 + calculateBonus();
                Score.setText("Score:" + account.cookies);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                save();
            }
        });
    }

    public void onTick() {
        tick();
        this.cookieTimer.schedule(() -> onTick(), TimeUnit.SECONDS.toMillis(10));
    }

    public void tick() {
        Account account = ActualAccount.getActual();
        account.cookies += calculatePassiveBonus();
        Score.setText("Score:" + account.cookies);
    }

    public void update() {
        Score.setText("Score:" + ActualAccount.getActual().cookies);
        Bonus.setText("Bonus: " + calculateBonus());
        PassiveBonus.setText("Passive bonus: " + calculatePassiveBonus());
    }

    private int calculateBonus() {
        Account account = ActualAccount.getActual();
        return account.items.stream().mapToInt(x -> x.cursorCookiePower).sum();
    }

    private int calculatePassiveBonus() {
        Account account = ActualAccount.getActual();
        return account.assistants.stream().mapToInt(x -> x.assistantsCookiePower).sum();
    }

    private void createTable(JTable table, String name, DefaultCellEditor DCE) {
        Object[][] data = {};
        table.setModel(new DefaultTableModel(data, new String[]{name}));

        TableColumnModel columns = table.getColumnModel();

        table.getColumn(name).setCellRenderer(new ButtonRenderer());
        table.getColumn(name).setCellEditor(DCE);
    }

    private void addToShop(Item item) {
        String data[] = {item.name, "Cookie power: " + item.cursorCookiePower};

        DefaultTableModel tblModel = (DefaultTableModel) leftTable.getModel();
        tblModel.addRow(data);
    }

    private void addToShop(Assistant assistant) {
        String data[] = {assistant.name, "Cookie power: " + assistant.assistantsCookiePower};

        DefaultTableModel tblModel = (DefaultTableModel) rightTable.getModel();
        tblModel.addRow(data);
    }

    private void loadShop() {
        Items items = new Items(Database.getDB());
        ArrayList<Item> AllItems = items.Read();
        Assistants assistants = new Assistants(Database.getDB());
        ArrayList<Assistant> AllAsistants = assistants.Read();
        for (Item item : AllItems) {
            addToShop(item);
        }
        for (Assistant assistant : AllAsistants) {
            addToShop(assistant);
        }
    }

    private void save() {
        Scores scores = new Scores(Database.getDB());
        Account account = ActualAccount.getActual();
        Accounts accounts = new Accounts(Database.getDB());
        Score score = scores.Read().stream().filter(s -> account.score.key == (s.key)).findAny().orElse(null);
        if (score != null) {
            scores.Update(score);
        }
        accounts.Update(account);
    }
}
