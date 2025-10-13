import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private float opacity = 0f;         // overall window opacity
    private float titleAlpha = 0f;      // title text fade alpha
    private JLabel title;

    public SplashScreen() {
        // === Background panel with gradient ===
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Muted jungle green → cream gradient
                Color start = new Color(95, 167, 120);  // muted jungle green
                Color end = new Color(245, 241, 222);   // soft cream
                GradientPaint gp = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Draw title manually with alpha (smooth fade)
                if (title != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, titleAlpha));
                    g2.setFont(title.getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    String text = title.getText();
                    int x = (getWidth() - fm.stringWidth(text)) / 2;
                    int y = getHeight() / 2 + fm.getAscent() / 2 - 40;
                    g2.setColor(title.getForeground());
                    g2.drawString(text, x, y);
                    g2.dispose();
                }
            }
        };
        content.setLayout(new BorderLayout());

        // === Create title label (for font loading only) ===
        title = new JLabel("INGREEDY", JLabel.CENTER);

        // Try to load Lobster font
        Font lobster;
        try {
            lobster = Font.createFont(Font.TRUETYPE_FONT,
                            getClass().getResourceAsStream("/fonts/Lobster-Regular.ttf"))
                    .deriveFont(Font.PLAIN, 64f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lobster);
        } catch (Exception e) {
            lobster = new Font("Segoe Script", Font.BOLD, 64);
        }

        title.setFont(lobster);
        title.setForeground(new Color(33, 47, 41)); // deep muted green

        // === Bottom Images ===
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Load and scale images from resources
        ImageIcon leftSnackIcon = new ImageIcon(getClass().getResource("/images/snack_left.png"));
        ImageIcon rightSnackIcon = new ImageIcon(getClass().getResource("/images/snack_right.png"));

        // Scale to 80×80 px for both images
        Image leftScaled = leftSnackIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        Image rightScaled = rightSnackIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        JLabel leftSnack = new JLabel(new ImageIcon(leftScaled));
        JLabel rightSnack = new JLabel(new ImageIcon(rightScaled));

        bottomPanel.add(leftSnack, BorderLayout.WEST);
        bottomPanel.add(rightSnack, BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        content.add(bottomPanel, BorderLayout.SOUTH);

        // === Window setup ===
        setContentPane(content);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setOpacity(0f); // start transparent
    }

    public void showSplash(int fadeInTime, int visibleTime, int fadeOutTime) {
        setVisible(true);

        // === Fade-in animation ===
        Timer fadeIn = new Timer(30, e -> {
            opacity += 0.05f;
            titleAlpha += 0.03f;
            if (titleAlpha > 1f) titleAlpha = 1f;
            if (opacity >= 1f) {
                opacity = 1f;
                ((Timer) e.getSource()).stop();

                // Hold splash for visibleTime ms, then fade out
                Timer holdTimer = new Timer(visibleTime, evt -> {
                    ((Timer) evt.getSource()).stop();
                    fadeOut(fadeOutTime);
                });
                holdTimer.setRepeats(false);
                holdTimer.start();
            }
            setOpacity(opacity);
            repaint();
        });
        fadeIn.start();
    }

    private void fadeOut(int fadeOutTime) {
        Timer fadeOut = new Timer(30, e -> {
            opacity -= 0.05f;
            titleAlpha -= 0.05f;
            if (titleAlpha < 0f) titleAlpha = 0f;
            if (opacity <= 0f) {
                opacity = 0f;
                ((Timer) e.getSource()).stop();
                setVisible(false);
                dispose();
                // ✅ Launch only once after fade-out completes
                SwingUtilities.invokeLater(HostelRecipesGUI::new);
            }
            setOpacity(opacity);
            repaint();
        });
        fadeOut.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.showSplash(1000, 2000, 1000);
        });
    }
}
