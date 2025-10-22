import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class EcoPointsPage extends JFrame {
    private JLabel pointsLabel;

    public EcoPointsPage() {
        setTitle("🌿 Eco Points");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 242, 229));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Your Eco Points", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));

        pointsLabel = new JLabel("Total Points: " + AppData.getEcoPoints(), JLabel.CENTER);
        pointsLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.addActionListener(e ->
                pointsLabel.setText("Total Points: " + AppData.getEcoPoints()));

        panel.add(title, BorderLayout.NORTH);
        panel.add(pointsLabel, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);

        add(panel);
    }
}
