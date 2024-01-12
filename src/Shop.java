import java.util.ArrayList;

public class Shop {
    public ArrayList<Item> items;
    public ArrayList<Assistant> assistants;
    public Database db;

    public Shop(Database db) {
        this.db = db;
    }

    public void buyItem(Account account, Item item) {
        if (!canBuy(account.cookies, item.price)) {
            return;
        }
        account.cookies -= item.price;
        Purchase purchase = new Purchase(account.user.name, item.name);
        Purchases purchases = new Purchases(db);
        purchases.Create(purchase);
        account.items.add(item);
    }

    public void buyAssistant(Account account, Assistant assistant) {
        if (!canBuy(account.cookies, assistant.price)) {
            return;
        }
        account.cookies -= assistant.price;
        Contract contract = new Contract(account.user.name, assistant.name);
        Contracts contracts = new Contracts(db);
        contracts.Create(contract);
        account.assistants.add(assistant);
    }

    public boolean canBuy(int cookies, int price) {
        return cookies >= price;
    }
}
