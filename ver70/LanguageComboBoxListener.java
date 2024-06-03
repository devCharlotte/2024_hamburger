import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class LanguageComboBoxListener implements ActionListener {
    private JButton orderButton;

    public LanguageComboBoxListener(JButton orderButton) {
        this.orderButton = orderButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> languageComboBox = (JComboBox<String>) e.getSource();
        String selectedLanguage = (String) languageComboBox.getSelectedItem();
        switch (selectedLanguage) {
            case "한국어":
                orderButton.setText("주문하기");
                break;
            case "English":
                orderButton.setText("Order");
                break;
            case "中文":
                orderButton.setText("订单");
                break;
        }
    }
}
