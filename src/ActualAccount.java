import java.util.ArrayList;

public class ActualAccount {
    private static Account actual = null;

    public static Account login(String name, String password) {
        Accounts accounts = new Accounts(Database.getDB());
        ArrayList<Account> allAccounts = accounts.Read();
        actual = allAccounts.stream().filter(s -> password.equals(s.user.password) && name.equals(s.user.name)).findAny().orElse(null);
        return actual;
    }

    public static Account getActual() {
        return actual;
    }

    public static boolean isLogged() {
        return actual != null;
    }
}
