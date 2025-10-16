import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class EcoPointsPage extends JFrame {

    public EcoPointsPage() {
        setTitle("INGREEDY - Eco Points");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // === Background with soft gradient ===
        JPanel background = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(245, 255, 245),
                        0, getHeight(), new Color(230, 250, 230)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // === Title ===
        JLabel title = new JLabel("🌿 Your Eco Points 🌿", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(new Color(46, 110, 76));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        background.add(title, BorderLayout.NORTH);

        // === Center panel with the rotating coin ===
        CoinPanel coinPanel = new CoinPanel("/images/eco_coin.png");
        background.add(coinPanel, BorderLayout.CENTER);

        // === Points + Back Button ===
        JLabel pointsLabel = new JLabel("120 Eco Coins Earned!", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        pointsLabel.setForeground(new Color(46, 110, 76));
        pointsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton backButton = new JButton("← Back to Home");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setForeground(new Color(46, 110, 76));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(pointsLabel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.SOUTH);
        background.add(bottomPanel, BorderLayout.SOUTH);

        add(background);
        setVisible(true);
    }

    // === Inner Class: Rotating Coin Panel ===
    class CoinPanel extends JPanel {
        private Image coinImage;
        private double angle = 0;  // rotation angle in radians
        private Timer timer;

        public CoinPanel(String imagePath) {
            setOpaque(false);

            // ✅ Load coin image safely
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                coinImage = new ImageIcon(imageUrl).getImage();
            } else {
                coinImage = new ImageIcon("src/images/eco_coin.png").getImage();
            }

            // === Timer for rotation animation (steady pace) ===
            // 40ms per frame → 25 FPS, increase angle slightly each frame
            timer = new Timer(40, (ActionEvent e) -> {
                angle += Math.toRadians(2); // rotate slowly
                if (angle >= Math.PI * 2) angle = 0;
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (coinImage == null) {
                g.drawString("⚠️ Coin image not found!", getWidth() / 2 - 70, getHeight() / 2);
                return;
            }

            Graphics2D g2d = (Graphics2D) g.create();
            int size = 200;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            // === Smooth rotation ===
            AffineTransform transform = new AffineTransform();
            transform.translate(x + size / 2.0, y + size / 2.0); // move to center
            transform.rotate(angle);
            transform.translate(-size / 2.0, -size / 2.0); // move back

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(coinImage, transform, this);

            g2d.dispose();
        }
    }

    // === Main for testing ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EcoPointsPage::new);
    }
}
