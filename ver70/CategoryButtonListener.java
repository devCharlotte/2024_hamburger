import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class CategoryButtonListener implements ActionListener {
    private Panel CenterPanel;
    private Map<String, Integer> productPrices;
    private DefaultTableModel model;
    private JLabel totalLabel;
    private JFrame frame;

    public CategoryButtonListener(Panel CenterPanel, Map<String, Integer> productPrices, DefaultTableModel model, JLabel totalLabel, JFrame frame) {
        this.CenterPanel = CenterPanel;
        this.productPrices = productPrices;
        this.model = model;
        this.totalLabel = totalLabel;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String category = clickedButton.getText();
        productPrices.clear();

        if (category.equals("햄버거 단품")) {
            productPrices.put("불고기버거", 5000);
            productPrices.put("쉬림프버거", 5500);
            productPrices.put("치즈버거", 4000);
            productPrices.put("어니언버거", 4500);
            productPrices.put("한우불고기버거", 7000);
            productPrices.put("치킨버거", 5000);
            productPrices.put("핫크리스피버거", 5500);
            productPrices.put("베이컨버거", 6000);
            productPrices.put("모짜렐라베이컨버거", 6500);
        } else if (category.equals("햄버거 세트")) {
            productPrices.put("불고기버거 세트", 7000);
            productPrices.put("쉬림프버거 세트", 7500);
            productPrices.put("치즈버거 세트", 6000);
            productPrices.put("어니언버거 세트", 6500);
            productPrices.put("한우불고기버거 세트", 9000);
            productPrices.put("치킨버거 세트", 7000);
            productPrices.put("핫크리스피버거 세트", 7500);
            productPrices.put("베이컨버거 세트", 8000);
            productPrices.put("모짜렐라베이컨버거 세트", 8500);
        } else if (category.equals("음료")) {
            productPrices.put("콜라", 2000);
            productPrices.put("사이다", 2000);
            productPrices.put("환타", 2000);
            productPrices.put("아메리카노", 3000);
            productPrices.put("카페라떼", 3500);
            productPrices.put("핫초코", 3500);
            productPrices.put("딸기쉐이크", 4000);
            productPrices.put("초코쉐이크", 4000);
            productPrices.put("바나나쉐이크", 4000);
        } else if (category.equals("사이드")) {
            productPrices.put("감자튀김", 2500);
            productPrices.put("양념감자", 3000);
            productPrices.put("치즈스틱", 3500);
            productPrices.put("어니언링", 3000);
            productPrices.put("치킨너겟", 4000);
            productPrices.put("콘샐러드", 3500);
            productPrices.put("코울슬로", 3000);
            productPrices.put("치킨텐더", 4000);
            productPrices.put("핫윙", 4500);
        }

        CenterPanel.removeAll();
        for (String productName : productPrices.keySet()) {
            JButton productButton = new JButton(productName);
            productButton.addActionListener(new ProductButtonListener(productPrices, model, totalLabel, frame));
            CenterPanel.add(productButton);
        }
        CenterPanel.revalidate();
        CenterPanel.repaint();
    }
}
