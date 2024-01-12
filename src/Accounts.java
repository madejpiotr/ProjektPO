import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Accounts implements CRUD<Account> {
    private Database db;

    public Accounts(Database db) {
        this.db = db;
    }

    public void CreateTable() {
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE accounts ( name VARCHAR(40) NOT NULL, cookies INT, scorekey INT, PRIMARY KEY(name))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean Create(Account account) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO accounts (name, cookies, scorekey) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, account.user.name);
            preparedStatement.setInt(2, account.cookies);
            preparedStatement.setInt(3, account.score.key);
            int addedRows = preparedStatement.executeUpdate();

            return addedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Account> Read() {
        ArrayList<Account> accounts = new ArrayList<Account>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String username = result.getString("name");
                User user = findUser(username);
                Score score = findScore(result.getInt("scorekey"));
                ArrayList<Item> items = getItems(user.name);
                ArrayList<Assistant> assistants = getAssistants(user.name);
                if (user != null && score != null) {
                    Account account = new Account(result.getInt("cookies"), user, score, items, assistants);
                    accounts.add(account);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void Update(Account account) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE accounts SET cookies=?, scorekey=? WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, account.cookies);
            preparedStatement.setInt(2, account.score.key);
            preparedStatement.setString(3, account.user.name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Account account) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM accounts WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, account.user.name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findUser(String userkey) {
        Users usersdb = new Users(db);
        ArrayList<User> users = usersdb.Read();
        return users.stream().filter(u -> userkey.equals(u.name)).findFirst().orElse(null);
    }

    public Score findScore(int scorekey) {
        Scores scoredb = new Scores(db);
        ArrayList<Score> scores = scoredb.Read();
        return scores.stream().filter(s -> scorekey == s.key).findAny().orElse(null);
    }

    private ArrayList<Purchase> getPurchase(String userkey) {
        Purchases purchasesdb = new Purchases(db);
        ArrayList<Purchase> purchase = purchasesdb.Read();
        return new ArrayList<Purchase>(purchase.stream().filter(s -> userkey.equals(s.accountKey)).collect(Collectors.toList()));
    }

    private ArrayList<Item> getItemList() {
        Items itemsdb = new Items(db);
        return itemsdb.Read();
    }

    public ArrayList<Item> getItems(String userkey) {
        ArrayList<Item> items = new ArrayList<Item>();
        ArrayList<Purchase> purchases = getPurchase(userkey);
        ArrayList<Item> shopItems = getItemList();
        for (Purchase purchase : purchases) {
            Item item = shopItems.stream().filter(s -> s.name.equals(purchase.itemName)).findAny().orElse(null);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    private ArrayList<Contract> getContract(String userkey) {
        Contracts contractsdb = new Contracts(db);
        ArrayList<Contract> contract = contractsdb.Read();
        return new ArrayList<Contract>(contract.stream().filter(s -> userkey.equals(s.accountKey)).collect(Collectors.toList()));
    }

    private ArrayList<Assistant> getAssistantList() {
        Assistants assistantsdb = new Assistants(db);
        return assistantsdb.Read();
    }

    public ArrayList<Assistant> getAssistants(String userkey) {
        ArrayList<Assistant> assistants = new ArrayList<Assistant>();
        ArrayList<Contract> contracts = getContract(userkey);
        ArrayList<Assistant> shopAssistants = getAssistantList();
        for (Contract contract : contracts) {
            Assistant assistant = shopAssistants.stream().filter(s -> s.name.equals(contract.assistantName)).findAny().orElse(null);
            if (assistant != null) {
                assistants.add(assistant);
            }
        }
        return assistants;
    }
}
