import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.CardLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Utilities {
    public static void updateTotal(DefaultTableModel model, JLabel totalLabel) {
        int total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += Integer.parseInt(model.getValueAt(i, 3).toString());
        }
        totalLabel.setText("총 금액: " + total + "원");
    }

    public static void handleOrder(DefaultTableModel model, JLabel totalLabel, JTable table, JPanel mainPanel, CardLayout cardLayout, JDialog receiptDialog, int total) {
        if (model.getRowCount() == 0) {
            return;
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("주문 내역:\n\n");
        for (int i = 0; i < model.getRowCount(); i++) {
            String productName = model.getValueAt(i, 0).toString();
            int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());
            int sum = Integer.parseInt(model.getValueAt(i, 3).toString());
            receipt.append(productName).append(" x ").append(quantity).append(" = ").append(sum).append("원\n");
        }
        receipt.append("\n총 금액: ").append(total).append("원\n");

        JOptionPane.showMessageDialog(receiptDialog, receipt.toString(), "영수증", JOptionPane.INFORMATION_MESSAGE);
        model.setRowCount(0);
        total = 0;
        updateTotal(model, totalLabel);
        cardLayout.show(mainPanel, "OrderResult");
    }
}
