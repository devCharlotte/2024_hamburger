import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class ProductButtonListener implements ActionListener {
    private Map<String, Integer> productPrices;
    private DefaultTableModel model;
    private JLabel totalLabel;
    private JFrame frame;

    public ProductButtonListener(Map<String, Integer> productPrices, DefaultTableModel model, JLabel totalLabel, JFrame frame) {
        this.productPrices = productPrices;
        this.model = model;
        this.totalLabel = totalLabel;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String productName = clickedButton.getText();
        int price = productPrices.get(productName);

        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(productName)) {
                int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());
                model.setValueAt(++quantity, i, 2);
                model.setValueAt(price * quantity, i, 3);
                Utilities.updateTotal(model, totalLabel);
                return;
            }
        }
        model.addRow(new Object[]{productName, price, 1, price, "조절"});
        Utilities.updateTotal(model, totalLabel);
    }
}
