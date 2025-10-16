import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private float opacity = 0f;         // overall window opacity
    private float titleAlpha = 0f;      // title text fade alpha
    private float taglineAlpha = 0f;    // tagline fade alpha
    private JLabel title, tagline;

    public SplashScreen() {
        // === Background panel with beige gradient ===
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // 🌾 Warm beige gradient background (INGREEDY theme)
                Color start = new Color(243, 238, 223);
                Color end = new Color(230, 225, 210);
                GradientPaint gp = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Draw title manually with fade alpha
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

                // Draw tagline with independent fade alpha
                if (tagline != null) {
                    Graphics2D g3 = (Graphics2D) g.create();
                    g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, taglineAlpha));
                    g3.setFont(tagline.getFont());
                    FontMetrics fm2 = g3.getFontMetrics();
                    String text2 = tagline.getText();
                    int x2 = (getWidth() - fm2.stringWidth(text2)) / 2;
                    int y2 = getHeight() / 2 + fm2.getAscent() / 2 + 20;
                    g3.setColor(tagline.getForeground());
                    g3.drawString(text2, x2, y2);
                    g3.dispose();
                }
            }
        };
        content.setLayout(new BorderLayout());

        // === Title ===
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
            lobster = new Font("Serif", Font.BOLD, 64);
        }

        // === Tagline ===
        tagline = new JLabel("Smarter. Greener. Tastier.", JLabel.CENTER);
        tagline.setFont(new Font("SansSerif", Font.ITALIC, 22));

        // === Colors ===
        Color darkMintGreen = new Color(36, 95, 70); // darker, elegant mint green
        title.setFont(lobster);
        title.setForeground(darkMintGreen);
        tagline.setForeground(new Color(60, 100, 75)); // softer tone of mint

        // === Window setup ===
        setContentPane(content);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setOpacity(0f); // start transparent
    }

    public void showSplash(int fadeInTime, int visibleTime, int fadeOutTime) {
        setVisible(true);

        Timer fadeIn = new Timer(30, e -> {
            opacity += 0.05f;
            titleAlpha += 0.04f;
            taglineAlpha += 0.03f;

            if (titleAlpha > 1f) titleAlpha = 1f;
            if (taglineAlpha > 1f) taglineAlpha = 1f;

            if (opacity >= 1f) {
                opacity = 1f;
                ((Timer) e.getSource()).stop();

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
            taglineAlpha -= 0.05f;

            if (opacity <= 0f) {
                opacity = 0f;
                ((Timer) e.getSource()).stop();
                setVisible(false);
                dispose();
                // ✅ Direct to LoginPage.java after fade-out
                SwingUtilities.invokeLater(LoginPage::new);
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
