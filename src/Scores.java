import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Scores implements CRUD<Score> {
    private Database db;

    public Scores(Database db) {
        this.db = db;
    }

    public void CreateTable() {
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE scores ( id INT NOT NULL AUTO_INCREMENT, overallCookies INT, cursorCookiePower INT, assistantCookiePower INT, PRIMARY KEY(id))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean Create(Score score) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO scores (overallCookies, cursorCookiePower, assistantCookiePower) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, score.overallCookies);
            preparedStatement.setInt(2, score.cursorCookiePower);
            preparedStatement.setInt(3, score.assistantsCookiePower);
            int addedRows = preparedStatement.executeUpdate();

            if (addedRows > 0) {
                ResultSet result = preparedStatement.getGeneratedKeys();
                if (result.next()) {
                    score.key = result.getInt(1);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Score> Read() {
        ArrayList<Score> scores = new ArrayList<Score>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM scores";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Score score = new Score(result.getInt("id"), result.getInt("overallCookies"), result.getInt("cursorCookiePower"), result.getInt("assistantCookiePower"));
                scores.add(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;
    }

    @Override
    public void Update(Score score) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE scores SET overallCookies=?, cursorCookiePower=?, assistantCookiePower=? WHERE id=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, score.overallCookies);
            preparedStatement.setInt(2, score.cursorCookiePower);
            preparedStatement.setInt(3, score.assistantsCookiePower);
            preparedStatement.setInt(4, score.key);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Score score) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM scores WHERE id=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, score.key);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
