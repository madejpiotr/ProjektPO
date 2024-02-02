import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class CookieClicker extends JFrame {
    private JPanel panel1;
    private JButton clickButton;
    private JLabel Score;
    private JLabel Bonus;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTable leftTable;
    private JTable rightTable;
    private JLabel PassiveBonus;
    private CookieTimer cookieTimer = null;
    ImageIcon logo = new ImageIcon(".//src//cookie.png");
    private Account account;

//    public static void main(String[] args) {
//        CookieClicker c = new CookieClicker();
//        c.setVisible(true);
//    }

    public CookieClicker(Account account) {
        super("CookieClicker");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800, height = 600;
        this.setSize(width, height);
        this.setIconImage(logo.getImage());
        this.account = account;
        Database.getDB().Connect();
        createTable(leftTable, "Items");
        leftTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = leftTable.rowAtPoint(e.getPoint());
                int column = 1;
                if (row >= 0) {
                    String name = leftTable.getModel().getValueAt(row, column).toString();
                    Shop shop = new Shop(Database.getDB());
                    Items items = new Items(Database.getDB());
                    ArrayList<Item> allItems = items.Read();
                    Item boughtItem = allItems.stream().filter(s -> name.equals(s.name)).findFirst().orElse(null);
                    if (boughtItem != null) {
                        shop.buyItem(account, boughtItem);
                    }
                    update();
                }
            }
        });
        createTable(rightTable, "Assistants");
        rightTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = rightTable.rowAtPoint(e.getPoint());
                int column = 1;
                if (row >= 0) {
                    String name = rightTable.getModel().getValueAt(row, column).toString();
                    Shop shop = new Shop(Database.getDB());
                    Assistants assistants = new Assistants(Database.getDB());
                    ArrayList<Assistant> allAssistants = assistants.Read();
                    Assistant boughtAssistant = allAssistants.stream().filter(s -> name.equals(s.name)).findFirst().orElse(null);
                    if (boughtAssistant != null) {
                        shop.buyAssistant(account, boughtAssistant);
                    }
                    update();
                }
            }
        });
        loadShop();
        update();
        this.cookieTimer = new CookieTimer();
        this.cookieTimer.schedule(() -> onTick(), TimeUnit.SECONDS.toMillis(10));
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        account.cookies += calculatePassiveBonus();
        Score.setText("Score:" + account.cookies);
    }

    public void update() {
        Score.setText("Score:" + account.cookies);
        Bonus.setText("Bonus: " + calculateBonus());
        PassiveBonus.setText("Passive bonus: " + calculatePassiveBonus());
    }

    private int calculateBonus() {
        return account.items.stream().mapToInt(x -> x.cursorCookiePower).sum();
    }

    private int calculatePassiveBonus() {
        return account.assistants.stream().mapToInt(x -> x.assistantsCookiePower).sum();
    }

    private void createTable(JTable table, String name) {
        Object[][] data = {};
        table.setModel(new DefaultTableModel(data, new String[]{name, "id"}));

        TableColumnModel columns = table.getColumnModel();
        table.getTableHeader().setBackground(table.getBackground());
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        table.removeColumn(table.getColumnModel().getColumn(1));

    }

    private void addToShop(Item item) {
        String data[] = {item.name + " Price: " + item.price, item.name};

        DefaultTableModel tblModel = (DefaultTableModel) leftTable.getModel();
        tblModel.addRow(data);
    }

    private void addToShop(Assistant assistant) {
        String data[] = {assistant.name + " Price: " + assistant.price, assistant.name};

        DefaultTableModel tblModel = (DefaultTableModel) rightTable.getModel();
        tblModel.addRow(data);
    }

    private void loadShop() {
        Items items = new Items(Database.getDB());
        ArrayList<Item> AllItems = items.Read();
        Assistants assistants = new Assistants(Database.getDB());
        ArrayList<Assistant> AllAsistants = assistants.Read();
        Collections.sort(AllItems, (left, right) -> {
            return left.price - right.price;
        });
        Collections.sort(AllAsistants, (left, right) -> {
            return left.price - right.price;
        });
        for (Item item : AllItems) {
            addToShop(item);
        }
        for (Assistant assistant : AllAsistants) {
            addToShop(assistant);
        }
    }

    private void save() {
        Scores scores = new Scores(Database.getDB());
        Accounts accounts = new Accounts(Database.getDB());
        Score score = scores.Read().stream().filter(s -> account.score.key == (s.key)).findAny().orElse(null);
        if (score != null) {
            scores.Update(score);
        }
        accounts.Update(account);
    }
}
