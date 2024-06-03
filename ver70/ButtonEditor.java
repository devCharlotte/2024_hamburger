import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private String label;
    private boolean isPushed;
    private DefaultTableModel model;
    private JTable table;
    private JLabel totalLabel;

    public ButtonEditor(JCheckBox checkBox, DefaultTableModel model, JTable table, JLabel totalLabel) {
        this.button = new JButton();
        this.button.setOpaque(true);
        this.button.addActionListener(this);
        this.model = model;
        this.table = table;
        this.totalLabel = totalLabel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.label = (value == null) ? "" : value.toString();
        this.button.setText(this.label);
        this.isPushed = true;
        return this.button;
    }

    @Override
    public Object getCellEditorValue() {
        return this.label;
    }

    @Override
    public boolean stopCellEditing() {
        this.isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isPushed) {
            int row = this.table.getSelectedRow();
            int quantity = Integer.parseInt(this.model.getValueAt(row, 2).toString());
            int price = Integer.parseInt(this.model.getValueAt(row, 1).toString());
            if (this.label.equals("+")) {
                quantity++;
            } else if (this.label.equals("-") && quantity > 1) {
                quantity--;
            } else {
                this.model.removeRow(row);
                Utilities.updateTotal(this.model, this.totalLabel);
                return;
            }
            this.model.setValueAt(quantity, row, 2);
            this.model.setValueAt(quantity * price, row, 3);
            Utilities.updateTotal(this.model, this.totalLabel);
        }
        fireEditingStopped();
    }
}
