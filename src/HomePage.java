import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("INGREEDY - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // === Background panel with gradient ===
        JPanel background = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color start = new Color(245, 240, 225);
                Color end = new Color(230, 235, 220);
                GradientPaint gp = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        Color mintGreen = new Color(46, 110, 76);

        // === Title ===
        JLabel title = new JLabel("Welcome to INGREEDY!", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(mintGreen);
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));

        // === Grid Panel for Categories ===
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 40, 60));

        // === Category Buttons ===
        gridPanel.add(createCategoryButton("🍳  Make Your Dish", mintGreen));
        gridPanel.add(createCategoryButton("🌱  Check Eco Points", mintGreen));
        gridPanel.add(createCategoryButton("⏳  Expiry of Dishes", mintGreen));
        gridPanel.add(createCategoryButton("❤️  Favorite Recipes", mintGreen));

        background.add(title, BorderLayout.NORTH);
        background.add(gridPanel, BorderLayout.CENTER);

        add(background);
        setVisible(true);
    }

    // === Button creator with hover animation ===
    private JButton createCategoryButton(String text, Color mintGreen) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 22));
        button.setForeground(mintGreen);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(mintGreen, 3, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        // Hover + Action effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(mintGreen);
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(mintGreen.darker(), 3, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(mintGreen);
                button.setBorder(BorderFactory.createLineBorder(mintGreen, 3, true));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(mintGreen.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(mintGreen);

                // --- Navigation Logic ---
                if (text.contains("Make Your Dish")) {
                    dispose(); // close HomePage
                    SwingUtilities.invokeLater(() -> new HostelRecipesGUI()); // open recipe GUI
                }
                else if (text.contains("Check Eco Points")) {
                    dispose();
                    new EcoPointsPage(); // assuming this class exists
                }
                else if (text.contains("Expiry of Dishes")) {
                    dispose();
                    new ExpiryPage(); // assuming you have an expiry page
                }
                else if (text.contains("Favorite Recipes")) {
                    dispose();
                    new FavoriteRecipesPage(); // assuming you have this page
                }
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}