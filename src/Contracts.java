import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class Contract {
    public String accountKey;
    public String assistantName;
    public int key;

    public Contract(int key, String accountKey, String assistantName) {
        this.key = key;
        this.accountKey = accountKey;
        this.assistantName = assistantName;
    }

    public Contract(String accountKey, String assistantName) {
        this.key = -1;
        this.accountKey = accountKey;
        this.assistantName = assistantName;
    }
}

public class Contracts implements CRUD<Contract> {
    private Database db;

    public Contracts(Database db) {
        this.db = db;
    }

    public void CreateTable() {
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE contracts ( id INT NOT NULL AUTO_INCREMENT, accountkey VARCHAR(40), assistantname VARCHAR(40), PRIMARY KEY(id))";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean Create(Contract contract) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "INSERT INTO contracts (accountkey, assistantname) VALUES (?, ?)";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, contract.accountKey);
            preparedStatement.setString(2, contract.assistantName);
            int addedRows = preparedStatement.executeUpdate();

            if (addedRows > 0) {
                ResultSet result = preparedStatement.getGeneratedKeys();
                if (result.next()) {
                    contract.key = result.getInt(1);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Contract> Read() {
        ArrayList<Contract> contracts = new ArrayList<Contract>();
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "SELECT * FROM contracts";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Contract contract = new Contract(result.getInt("id"), result.getString("accountkey"), result.getString("assistantname"));
                contracts.add(contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contracts;
    }

    @Override
    public void Update(Contract contract) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "UPDATE contracts SET accountkey=?, assistantname=? WHERE id=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, contract.accountKey);
            preparedStatement.setString(2, contract.assistantName);
            preparedStatement.setInt(3, contract.key);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(Contract contract) {
        try {
            Statement statement = db.getConnection().createStatement();
            String sql = "DELETE FROM contract WHERE id=?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, contract.key);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

