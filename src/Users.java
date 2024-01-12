import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Users implements CRUD<User>{
    private Database db;
    public Users(Database db) {
        this.db = db;
    }
    public void CreateTable(){
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE users ( name VARCHAR(40) NOT NULL, email VARCHAR(40) NOT NULL, password VARCHAR(40) NOT NULL PRIMARY KEY(name))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean Create(User user){
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.name);
            preparedStatement.setString(2, user.email);
            preparedStatement.setString(3, user.password);
            int addedRows = preparedStatement.executeUpdate();

            return addedRows>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public ArrayList<User> Read(){
        ArrayList<User> users = new ArrayList<User>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                User user = new User(result.getString("name"), result.getString("email"), result.getString("password"));
                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void Update(User user){
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE users SET email=?, password=? WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.email);
            preparedStatement.setString(2, user.password);
            preparedStatement.setString(3, user.name);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void Delete(User user){
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM users WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.name);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
