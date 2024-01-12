
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Items implements CRUD<Item> {
    private Database db;

    public Items(Database db) {
        this.db = db;
    }

    public void CreateTable() {
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE items ( name VARCHAR(40) NOT NULL, cursorCookiePower INT, price INT, PRIMARY KEY(name))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean Create(Item item) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO items (name, cursorCookiePower, price) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.name);
            preparedStatement.setInt(2, item.cursorCookiePower);
            preparedStatement.setInt(3, item.price);
            int addedRows = preparedStatement.executeUpdate();

            return addedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Item> Read() {
        ArrayList<Item> items = new ArrayList<Item>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM items";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Item item = new Item(result.getString("name"), result.getInt("cursorCookiePower"), result.getInt("price"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void Update(Item item) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE items SET cursorCookiePower=?, price=? WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, item.cursorCookiePower);
            preparedStatement.setInt(2, item.price);
            preparedStatement.setString(3, item.name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Item item) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM items WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
