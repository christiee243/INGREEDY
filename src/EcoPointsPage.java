import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcoPointsPage extends JFrame {

    private int ecoPoints = 120; // Example: points earned by the user (can be loaded from a file or DB)
    private JLabel coinLabel;
    private Timer spinTimer;
    private int angle = 0;

    public EcoPointsPage() {
        setTitle("INGREEDY - Eco Points");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        Color mintGreen = new Color(46, 110, 76);

        // === Background Gradient ===
        JPanel background = new JPanel(new BorderLayout()) {
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

        // === Title ===
        JLabel title = new JLabel("🌱 Your Eco Points 🌱", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(mintGreen);
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        // === Coin Animation ===
        coinLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int size = Math.min(getWidth(), getHeight()) - 10;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                // Create 3D spinning illusion by changing width with sine wave
                double scaleX = Math.abs(Math.sin(Math.toRadians(angle))) * 0.8 + 0.2;

                // Coin base
                g2d.setColor(new Color(255, 215, 0));
                g2d.fillOval(x, y, size, size);

                // Shading ellipse (rotating look)
                g2d.setColor(new Color(255, 200, 0, 180));
                g2d.fillOval((int) (x + size * (1 - scaleX) / 2), y, (int) (size * scaleX), size);

                // Coin border
                g2d.setColor(new Color(200, 150, 0));
                g2d.setStroke(new BasicStroke(4));
                g2d.drawOval(x, y, size, size);
            }
        };
        coinLabel.setPreferredSize(new Dimension(200, 200));

        // === Animate spinning coin ===
        spinTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle = (angle + 10) % 360;
                coinLabel.repaint();
            }
        });
        spinTimer.start();

        // === Eco Points Label ===
        JLabel pointsLabel = new JLabel(ecoPoints + " Eco Coins Earned!", JLabel.CENTER);
        pointsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        pointsLabel.setForeground(mintGreen);
        pointsLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // === Back Button ===
        JButton backButton = new JButton("⬅ Back to Home");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(mintGreen);
        backButton.setBorder(BorderFactory.createLineBorder(mintGreen, 2, true));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        // === Center Panel ===
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(coinLabel, BorderLayout.CENTER);
        centerPanel.add(pointsLabel, BorderLayout.SOUTH);

        background.add(title, BorderLayout.NORTH);
        background.add(centerPanel, BorderLayout.CENTER);
        background.add(backButton, BorderLayout.SOUTH);

        add(background);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EcoPointsPage::new);
    }
}

