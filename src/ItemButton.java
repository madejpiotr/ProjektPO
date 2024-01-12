import javax.swing.*;

public class ItemButton extends JButton {
    private String name;
    private String description;
    public ItemButton(Item item) {
        this.name = item.name;
        this.description = "Cookie power: "+item.cursorCookiePower;
    }
}
