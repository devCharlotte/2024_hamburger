import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;

public class Toast extends JFrame {
    private Map<String, Integer> productPrices;
    private DefaultTableModel model;
    private JLabel totalLabel;
    private int total = 0;
    private JTable table;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Integer> cart;
    private Panel CenterPanel;
    private JButton orderButton;
    private JComboBox<String> languageComboBox;
    private JDialog receiptDialog;

    public Toast() {
        cart = new HashMap<>();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        productPrices = new HashMap<>();

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 400));
        orderButton = new JButton("주문하기");
        orderButton.setPreferredSize(new Dimension(100, 30));
        orderButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        languageComboBox = new JComboBox<>(new String[]{"한국어", "English", "中文"});
        languageComboBox.setSelectedItem("한국어");
        languageComboBox.addActionListener(new LanguageComboBoxListener(orderButton));
        startPanel.add(orderButton);
        startPanel.add(languageComboBox);

        JPanel menuPanel = new JPanel(new BorderLayout());
        JPanel menuButtonPanel = new JPanel(new FlowLayout());

        Panel CategoryPanel = new Panel();
        CategoryPanel.setLayout(new FlowLayout());
        CategoryPanel.setBackground(Color.GRAY);

        String[] categories = {"햄버거 단품", "햄버거 세트", "음료", "사이드"};
        JButton[] categoryButtons = new JButton[categories.length];

        for (int i = 0; i < categories.length; i++) {
            categoryButtons[i] = new JButton(categories[i]);
            categoryButtons[i].addActionListener(new CategoryButtonListener(CenterPanel, productPrices, model, totalLabel, this));
            CategoryPanel.add(categoryButtons[i]);
        }

        Panel NorthPanel = new Panel();
        NorthPanel.setLayout(new FlowLayout());

        Label highbar = new Label();
        highbar.setText("<상상 토스트 키오스크>");
        highbar.setFont(new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 25));
        NorthPanel.add(highbar);

        CenterPanel = new Panel();
        CenterPanel.setLayout(new GridLayout(3, 3, 10, 10));
        CenterPanel.setBackground(Color.LIGHT_GRAY);

        Panel SouthPanel = new Panel();
        SouthPanel.setLayout(new BorderLayout());
        String[][] data = new String[0][0];
        String[] title = {"상품명", "단가", "수량", "합계", "조절"};
        model = new DefaultTableModel(data, title);
        table = new JTable(model);
        table.getColumn("조절").setCellRenderer(new ButtonRenderer());
        table.getColumn("조절").setCellEditor(new ButtonEditor(new JCheckBox(), model, table, totalLabel));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1150, 200));
        SouthPanel.add(scrollPane, BorderLayout.CENTER);

        totalLabel = new JLabel("총 금액: 0원");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        SouthPanel.add(totalLabel, BorderLayout.SOUTH);

        Panel SelectPanel = new Panel();
        SelectPanel.setLayout(new GridLayout(3, 1, 50, 0));
        JButton[] order = new JButton[3];

        order[0] = new JButton("닫기");
        order[1] = new JButton("초기화");
        order[2] = new JButton("주문");
        SelectPanel.add(order[0]);
        SelectPanel.add(order[1]);
        SelectPanel.add(order[2]);

        order[0].addActionListener(e -> System.exit(0));
        order[1].addActionListener(e -> {
            model.setNumRows(0);
            total = 0;
            Utilities.updateTotal(model, totalLabel);
        });
        order[2].addActionListener(e -> Utilities.handleOrder(model, totalLabel, table, mainPanel, cardLayout, receiptDialog, total));

        menuPanel.add(CategoryPanel, BorderLayout.NORTH);
        menuPanel.add(NorthPanel, BorderLayout.CENTER);
        menuPanel.add(CenterPanel, BorderLayout.CENTER);
        menuPanel.add(SouthPanel, BorderLayout.SOUTH);
        menuPanel.add(SelectPanel, BorderLayout.EAST);

        JPanel orderResultPanel = new JPanel(new BorderLayout());
        JPanel orderResultButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel orderResultLabel = new JLabel("주문이 완료되었습니다. 이용해주셔서 감사합니다.", SwingConstants.CENTER);
        orderResultLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        orderResultButtonPanel.add(orderResultLabel);
        JButton orderResultCloseButton = new JButton("닫기");
        orderResultCloseButton.setPreferredSize(new Dimension(100, 50));
        orderResultCloseButton.addActionListener(e -> System.exit(0));
        orderResultButtonPanel.add(orderResultCloseButton);
        orderResultPanel.add(orderResultButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(startPanel, "Start");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(orderResultPanel, "OrderResult");

        this.add(mainPanel);
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        cardLayout.show(mainPanel, "Start");
    }

    public static void main(String[] args) {
        new Toast();
    }
}
