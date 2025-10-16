import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public LoginPage() {
        setTitle("INGREEDY - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // === Background Panel (Beige Color) ===
        JPanel background = new JPanel();
        background.setBackground(new Color(245, 240, 225)); // Beige
        background.setLayout(new GridBagLayout());

        // === Card Layout ===
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createSignupPanel(), "signup");

        background.add(mainPanel);
        add(background);

        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Color mintGreen = new Color(46, 110, 76); // 🌿 Dark Mint Leaf Green

        JLabel title = new JLabel("Welcome Back to INGREEDY", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(mintGreen);

        JTextField username = new JTextField(15);
        JPasswordField password = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        styleTextField(username, mintGreen);
        styleTextField(password, mintGreen);
        styleButton(loginButton, mintGreen);
        styleButton(signupButton, mintGreen);

        signupButton.addActionListener(e -> cardLayout.show(mainPanel, "signup"));

        loginButton.addActionListener(e -> {
            // For now, assume login is always successful
            dispose(); // Close login window
            new HomePage(); // Open home page
        });
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        panel.add(new JLabel("Username: ") {{
            setForeground(mintGreen);
            setFont(new Font("SansSerif", Font.BOLD, 16));
        }}, gbc);
        gbc.gridx = 1; panel.add(username, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Password: ") {{
            setForeground(mintGreen);
            setFont(new Font("SansSerif", Font.BOLD, 16));
        }}, gbc);
        gbc.gridx = 1; panel.add(password, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        gbc.gridy++;
        panel.add(signupButton, gbc);

        return panel;
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Color mintGreen = new Color(46, 110, 76);

        JLabel title = new JLabel("Create an INGREEDY Account", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(mintGreen);

        JTextField username = new JTextField(15);
        JPasswordField password = new JPasswordField(15);
        JPasswordField confirmPassword = new JPasswordField(15);
        JButton signupButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back to Login");

        styleTextField(username, mintGreen);
        styleTextField(password, mintGreen);
        styleTextField(confirmPassword, mintGreen);
        styleButton(signupButton, mintGreen);
        styleButton(backButton, mintGreen);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        panel.add(new JLabel("Username: ") {{
            setForeground(mintGreen);
            setFont(new Font("SansSerif", Font.BOLD, 16));
        }}, gbc);
        gbc.gridx = 1; panel.add(username, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Password: ") {{
            setForeground(mintGreen);
            setFont(new Font("SansSerif", Font.BOLD, 16));
        }}, gbc);
        gbc.gridx = 1; panel.add(password, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Confirm Password: ") {{
            setForeground(mintGreen);
            setFont(new Font("SansSerif", Font.BOLD, 16));
        }}, gbc);
        gbc.gridx = 1; panel.add(confirmPassword, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(signupButton, gbc);

        gbc.gridy++;
        panel.add(backButton, gbc);

        return panel;
    }

    private void styleTextField(JTextField field, Color mintGreen) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setForeground(mintGreen);
        field.setCaretColor(mintGreen);
        field.setBackground(new Color(245, 240, 225));
        field.setBorder(BorderFactory.createLineBorder(mintGreen, 2));
    }

    private void styleButton(JButton btn, Color mintGreen) {
        btn.setBackground(mintGreen);
        btn.setForeground(new Color(245, 240, 225));
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(mintGreen.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(mintGreen);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
