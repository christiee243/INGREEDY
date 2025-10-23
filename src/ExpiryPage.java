import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ExpiryPage extends JFrame {
    public ExpiryPage() {
        setTitle("INGREEDY - Expiry of Items");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Background gradient panel
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color start = new Color(240, 245, 230);
                Color end = new Color(225, 235, 215);
                GradientPaint gp = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new BorderLayout());

        JLabel title = new JLabel("⏳ Expiry of Items", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        title.setForeground(new Color(46, 110, 76));
        background.add(title, BorderLayout.NORTH);

        // Fetch expiry data from AppData
        Map<String, String> expiryData = AppData.getExpiryData();
        DefaultListModel<String> listModel = new DefaultListModel<>();

        if (expiryData.isEmpty()) {
            listModel.addElement("⚠ No expiry data logged yet.");
        } else {
            for (Map.Entry<String, String> entry : expiryData.entrySet()) {
                listModel.addElement("🕒 " + entry.getKey() + " → " + entry.getValue());
            }
        }

        JList<String> itemList = new JList<>(listModel);
        itemList.setFont(new Font("SansSerif", Font.PLAIN, 22));
        itemList.setForeground(new Color(46, 110, 76));
        itemList.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        background.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("⬅ Back to Home");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(46, 110, 76));
        backButton.setBorder(BorderFactory.createLineBorder(new Color(46, 110, 76), 2, true));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        background.add(backButton, BorderLayout.SOUTH);
        add(background);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpiryPage::new);
    }
}
