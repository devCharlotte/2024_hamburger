package kiosk;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class kiosk extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Integer> cart;
    private JTextArea cartTextArea;
    private JLabel totalLabel;
    private Map<String, Integer> productPrices;

    public kiosk() {
        cart = new HashMap<>();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        productPrices = new HashMap<>();

        // 첫 화면
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 400));
        JButton orderButton = new JButton("주문하기");
        orderButton.setPreferredSize(new Dimension(100, 30));
        orderButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        startPanel.add(orderButton);

        // 메뉴 화면
        JPanel menuPanel = new JPanel(new BorderLayout());
        JPanel menuButtonPanel = new JPanel(new FlowLayout());

        // 상단 메뉴 버튼 패널
        String[] menuCategories = { "햄버거 단품", "햄버거 세트", "음료", "사이드" };
        for (String category : menuCategories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setPreferredSize(new Dimension(150, 50));
            categoryButton.addActionListener(e -> cardLayout.show(mainPanel, category));
            menuButtonPanel.add(categoryButton);
        }

        // Product Prices Initialization
        productPrices.put("불고기버거", 5000);
        productPrices.put("쉬림프버거", 5500);
        productPrices.put("치즈버거", 4000);
        productPrices.put("어니언버거", 4500);
        productPrices.put("한우불고기버거", 7000);
        productPrices.put("치킨버거", 5000);
        productPrices.put("핫크리스피버거", 5500);
        productPrices.put("베이컨버거", 6000);
        productPrices.put("모짜렐라베이컨버거", 6500);

        // 하단 상품 버튼 패널
        JPanel productButtonPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        for (String productName : productPrices.keySet()) {
            JButton productButton = new JButton(productName + " - " + productPrices.get(productName) + "원");
            productButton.setPreferredSize(new Dimension(200, 50));
            productButton.addActionListener(this::addToCart);
            productButtonPanel.add(productButton);
        }

        // 하단 장바구니 목록 표시 영역
        JPanel cartItemsPanel = new JPanel(new BorderLayout());
        cartTextArea = new JTextArea();
        cartTextArea.setEditable(false);
        cartItemsPanel.add(new JScrollPane(cartTextArea), BorderLayout.CENTER);

        // 총 가격 표시 레이블
        totalLabel = new JLabel("result : 0원");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        cartItemsPanel.add(totalLabel, BorderLayout.SOUTH);

        menuPanel.add(menuButtonPanel, BorderLayout.NORTH);
        menuPanel.add(productButtonPanel, BorderLayout.CENTER);
        menuPanel.add(cartItemsPanel, BorderLayout.SOUTH);

        // 결제 버튼 추가
        JButton checkoutButton = new JButton("결제하기");
        checkoutButton.setPreferredSize(new Dimension(200, 50));
        checkoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Payment"));
        menuPanel.add(checkoutButton, BorderLayout.NORTH);

        // 결제 화면
        JPanel paymentPanel = new JPanel();
        JButton couponButton = new JButton("쿠폰");
        JButton pointButton = new JButton("포인트");
        JButton cardButton = new JButton("카드결제");
        cardButton.addActionListener(e -> cardLayout.show(mainPanel, "Receipt"));

        couponButton.setPreferredSize(new Dimension(200, 50));
        pointButton.setPreferredSize(new Dimension(200, 50));
        cardButton.setPreferredSize(new Dimension(200, 50));

        paymentPanel.add(couponButton);
        paymentPanel.add(pointButton);
        paymentPanel.add(cardButton);

        // 영수증 화면
        JPanel receiptPanel = new JPanel();
        JLabel receiptLabel = new JLabel("영수증을 출력하시겠습니까?");
        receiptLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        yesButton.setPreferredSize(new Dimension(100, 50));
        noButton.setPreferredSize(new Dimension(100, 50));
        ActionListener resetAction = e -> {
            Timer timer = new Timer(5000, event -> cardLayout.show(mainPanel, "Start"));
            timer.setRepeats(false);
            timer.start();
            cardLayout.show(mainPanel, "Start");
        };
        yesButton.addActionListener(resetAction);
        noButton.addActionListener(resetAction);
        receiptPanel.add(receiptLabel);
        receiptPanel.add(yesButton);
        receiptPanel.add(noButton);

        // 패널들 메인 패널에 추가
        mainPanel.add(startPanel, "Start");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(paymentPanel, "Payment");
        mainPanel.add(receiptPanel, "Receipt");

        add(mainPanel);
        cardLayout.show(mainPanel, "Start");

        setTitle("패스트푸드 키오스크");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addToCart(ActionEvent e) {
        String buttonText = ((JButton) e.getSource()).getText();
        // 버튼 텍스트에서 제품 이름을 추출합니다.
        String productName = buttonText.split(" - ")[0];
        Integer price = productPrices.get(productName); // 상품 이름에 해당하는 가격 가져오기
        if (price != null) {
            cart.put(productName, cart.getOrDefault(productName, 0) + 1);
            updateCart();
        } else {
            System.out.println("Error: Product not found in productPrices map");
        }
    }

    private void updateCart() {
        int total = 0;
        StringBuilder cartContent = new StringBuilder();
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            int price = productPrices.get(productName);
            cartContent.append(productName).append(" x ").append(quantity).append(" - ").append(price * quantity).append("원\n");
            total += price * quantity;
        }
        cartTextArea.setText(cartContent.toString());
        totalLabel.setText("총 합계: " + total + "원");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(kiosk::new);
    }
}

class MenuItem {
    private String name;
    private int price;

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
