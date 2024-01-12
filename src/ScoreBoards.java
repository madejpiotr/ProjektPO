import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ScoreBoards implements CRUD<ScoreBoard>{
    private Database db;
    public ScoreBoards(Database db) {
        this.db = db;
    }
    public void CreateTable(){
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE scoreboards ( name VARCHAR(40) NOT NULL, PRIMARY KEY(name))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean Create(ScoreBoard scoreboard){
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO scoreboards (name) VALUES (?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, scoreboard.name);
            int addedRows = preparedStatement.executeUpdate();

            return addedRows>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public ArrayList<ScoreBoard> Read(){
        ArrayList<ScoreBoard> scoreboards = new ArrayList<ScoreBoard>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM scoreboards";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                ScoreBoard scoreboard = new ScoreBoard(result.getString("name"));
                scoreboards.add(scoreboard);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return scoreboards;
    }
    @Override
    public void Update(ScoreBoard scoreboard){
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE scoreboards SET =? WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, scoreboard.name);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void Delete(ScoreBoard scoreboard){
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM scoreboards WHERE name=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, scoreboard.name);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
