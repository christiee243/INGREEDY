import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FavoriteRecipesPage extends JFrame {
    private static FavoriteRecipesPage instance; // Singleton instance
    private DefaultListModel<String> listModel;
    private JList<String> favoritesList;
    private JTextArea detailsArea;

    // === Singleton Access ===
    public static FavoriteRecipesPage getInstance() {
        if (instance == null) {
            instance = new FavoriteRecipesPage();
        }
        return instance;
    }

    private FavoriteRecipesPage() {
        setTitle("❤️ Favorite Recipes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // hides instead of disposing

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 242, 229));
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Your Favorite Recipes", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        favoritesList = new JList<>(listModel);
        favoritesList.setFont(new Font("Serif", Font.PLAIN, 18));
        favoritesList.setBorder(new LineBorder(new Color(155, 213, 178), 2, true));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Serif", Font.PLAIN, 16));
        detailsArea.setBorder(new LineBorder(new Color(155, 213, 178), 2, true));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(favoritesList), new JScrollPane(detailsArea));
        splitPane.setDividerLocation(250);
        panel.add(splitPane, BorderLayout.CENTER);

        JButton closeBtn = new JButton("❌ Close");
        closeBtn.addActionListener(e -> setVisible(false)); // hide instead of close
        panel.add(closeBtn, BorderLayout.SOUTH);

        add(panel);

        // Show recipe details when clicked
        favoritesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selected = favoritesList.getSelectedValue();
                if (selected != null) {
                    Recipe recipe = new RecipeDatabase().getRecipes().get(selected);
                    if (recipe != null) {
                        detailsArea.setText("🍽 " + selected + "\n\n" +
                                "Essentials: " + recipe.getEssential() + "\n" +
                                "Optional: " + recipe.getOptional() + "\n\n" +
                                "Instructions:\n" + recipe.getInstructions());
                    }
                }
            }
        });
    }

    // 🔹 Called by other classes to update the favorite list anytime
    public void updateFavorites() {
        Set<String> favorites = AppData.getFavorites();
        listModel.clear();
        for (String recipe : favorites) {
            listModel.addElement(recipe);
        }
    }
}
