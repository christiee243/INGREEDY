import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private float opacity = 0f;

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
            }
        };
        content.setLayout(new BorderLayout());

        // === Center Title ===
        JLabel title = new JLabel("INGREEDY", JLabel.CENTER);

        // Try to load Lobster font
        Font lobster;
        try {
            lobster = Font.createFont(Font.TRUETYPE_FONT,
                            getClass().getResourceAsStream("/fonts/Lobster-Regular.ttf"))
                    .deriveFont(Font.PLAIN, 48f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lobster);
        } catch (Exception e) {
            lobster = new Font("Segoe Script", Font.BOLD, 48);
        }

        title.setFont(lobster);
        title.setForeground(new Color(33, 47, 41)); // deep muted green
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        content.add(title, BorderLayout.CENTER);

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
            if (opacity >= 1f) {
                opacity = 1f;
                ((Timer) e.getSource()).stop();

                // Hold splash for the visible time, then fade out
                Timer holdTimer = new Timer(visibleTime, evt -> {
                    ((Timer) evt.getSource()).stop();
                    fadeOut(fadeOutTime);
                });
                holdTimer.setRepeats(false);
                holdTimer.start();
            }
            setOpacity(opacity);
        });
        fadeIn.start();
    }

    private void fadeOut(int fadeOutTime) {
        Timer fadeOut = new Timer(30, e -> {
            opacity -= 0.05f;
            if (opacity <= 0f) {
                opacity = 0f;
                ((Timer) e.getSource()).stop();
                setVisible(false);
                dispose();

                // ✅ Launch main GUI once
                SwingUtilities.invokeLater(HostelRecipesGUI::new);
            }
            setOpacity(opacity);
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
