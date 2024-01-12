import javax.swing.*;

public class ItemButton extends JButton {
    private JLabel name = new JLabel("default name");
    private JLabel description = new JLabel("default description");

    public ItemButton() {
        super();
    }

    public ItemButton(Item item) {
        super();
        this.name.setText(item.name);
        this.description.setText("Cookie power: " + item.cursorCookiePower);
        this.add(this.name);
        this.add(this.description);
    }

    public void setItem(String name, int cookiepower) {
        this.name.setText(name);
        this.description.setText("Cookie power: " + cookiepower);
    }
}
