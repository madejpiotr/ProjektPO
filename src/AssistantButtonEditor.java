import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AssistantButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private CookieClicker parent = null;

    public AssistantButtonEditor(CookieClicker parent, JCheckBox checkBox) {
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
        Assistants assistants = new Assistants(Database.getDB());
        ArrayList<Assistant> allAssistants = assistants.Read();
        Assistant boughtAssistant = allAssistants.stream().filter(s -> name.equals(s.name)).findFirst().orElse(null);
        if (boughtAssistant != null) {
            shop.buyAssistant(ActualAccount.getActual(), boughtAssistant);
        }
        Account account = ActualAccount.getActual();
        parent.update();
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
