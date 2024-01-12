import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class ItemButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private CookieClicker parent = null;

    public ItemButtonEditor(CookieClicker parent, JCheckBox checkBox) {
        super(checkBox);
        this.parent = parent;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            onPush();
        }
        isPushed = false;
        return label;
    }

    public void onPush() {
        String name = button.getText();
        Shop shop = new Shop(Database.getDB());
        Items items = new Items(Database.getDB());
        ArrayList<Item> allItems = items.Read();
        Item boughtItem = allItems.stream().filter(s -> name.equals(s.name)).findFirst().orElse(null);
        if (boughtItem != null) {
            shop.buyItem(ActualAccount.getActual(), boughtItem);
        }
        parent.update();
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
