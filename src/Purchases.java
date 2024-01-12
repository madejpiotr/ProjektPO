import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class Purchase {
    public String accountKey;
    public String itemName;
    public int key;

    public Purchase(String accountKey, String itemName) {
        this.key = -1;
        this.accountKey = accountKey;
        this.itemName = itemName;
    }

    public Purchase(int key, String accountKey, String itemName) {
        this.key = key;
        this.accountKey = accountKey;
        this.itemName = itemName;
    }
}

public class Purchases implements CRUD<Purchase> {
    private Database db;

    public Purchases(Database db) {
        this.db = db;
    }

    public void CreateTable() {
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE purchases ( id INT NOT NULL AUTO_INCREMENT, accountkey VARCHAR(40), itemname VARCHAR(40), PRIMARY KEY(id))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean Create(Purchase purchase) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO purchases (accountkey, itemname) VALUES (?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, purchase.accountKey);
            preparedStatement.setString(2, purchase.itemName);
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                ResultSet result = preparedStatement.getGeneratedKeys();
                if (result.next()) {
                    purchase.key = result.getInt(1);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Purchase> Read() {
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM purchases";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Purchase purchase = new Purchase(result.getInt("id"), result.getString("accountkey"), result.getString("itemname"));
                purchases.add(purchase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }

    @Override
    public void Update(Purchase purchase) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE purchases SET accountkey=?, itemname=? WHERE id=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, purchase.accountKey);
            preparedStatement.setString(2, purchase.itemName);
            preparedStatement.setInt(3, purchase.key);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Purchase purchase) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM purchase WHERE id=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, purchase.key);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
