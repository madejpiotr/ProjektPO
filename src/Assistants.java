import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Assistants implements CRUD<Assistant> {
    private Database db;

    public Assistants(Database db) {
        this.db = db;
    }

    public void CreateTable() {
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE assistants ( name VARCHAR(40) NOT NULL, assistantCookiePower INT, price INT, PRIMARY KEY(name))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean Create(Assistant assistant) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO assistants (name, assistantCookiePower, price) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, assistant.name);
            preparedStatement.setInt(2, assistant.assistantsCookiePower);
            preparedStatement.setInt(3, assistant.price);
            int addedRows = preparedStatement.executeUpdate();

            return addedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Assistant> Read() {
        ArrayList<Assistant> assistants = new ArrayList<Assistant>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM assistants";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Assistant assistant = new Assistant(result.getString("name"), result.getInt("assistantCookiePower"), result.getInt("price"));
                assistants.add(assistant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assistants;
    }

    @Override
    public void Update(Assistant assistant) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE assistants SET assistantCookiePower=?, price=? WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, assistant.assistantsCookiePower);
            preparedStatement.setInt(2, assistant.price);
            preparedStatement.setString(3, assistant.name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Assistant assistant) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM assistants WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, assistant.name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
