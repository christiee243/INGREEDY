import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class HostelRecipesGUI {
    private RecipeDatabase db;
    private RecipeMatcher matcher;
    private Set<String> userIngredients;
    private Set<String> favoriteRecipes = new HashSet<>();
    private int ecoPoints = 0;

    private DefaultListModel<String> ingredientListModel;
    private JTextField ingredientField;
    private JTextArea outputArea;
    private JTextArea detailArea;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    private String selectedRecipe = null;

    // === Color Palette ===
    private final Color BEIGE = new Color(245, 242, 229);
    private final Color MINT = new Color(155, 213, 178);
    private final Color DARK_MINT = new Color(116, 189, 156);
    private final Color TEXT_COLOR = new Color(50, 50, 50);

    private final Font SERIF_FONT = new Font("Serif", Font.PLAIN, 16);

    public HostelRecipesGUI() {
        db = new RecipeDatabase();
        matcher = new RecipeMatcher(db.getRecipes());
        userIngredients = new HashSet<>();

        JFrame frame = new JFrame("INGREEDY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 600);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BEIGE);

        // Add all screens
        mainPanel.add(createIngredientScreen(frame), "Ingredients");
        mainPanel.add(createRecipeScreen(frame), "Recipes");
        mainPanel.add(createRecipeDetailScreen(frame), "RecipeDetail");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // ======================== INGREDIENT SCREEN ========================
    private JPanel createIngredientScreen(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BEIGE);
        panel.setBorder(new EmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("What ingredients do you have?", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        ingredientListModel = new DefaultListModel<>();
        JList<String> ingredientList = new JList<>(ingredientListModel);
        ingredientList.setFont(SERIF_FONT);
        ingredientList.setBackground(Color.WHITE);
        ingredientList.setBorder(new LineBorder(MINT, 2, true));
        ingredientList.setSelectionBackground(DARK_MINT);

        JScrollPane scrollPane = new JScrollPane(ingredientList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setOpaque(false);

        ingredientField = new JTextField(18);
        ingredientField.setFont(SERIF_FONT);
        ingredientField.setBorder(new LineBorder(DARK_MINT, 2, true));
        ingredientField.setBackground(Color.WHITE);
        ingredientField.setForeground(TEXT_COLOR);

        JButton addBtn = createButton("Add", MINT, DARK_MINT);
        JButton resetBtn = createButton("Reset", new Color(231, 76, 60), DARK_MINT);
        JButton findBtn = createButton("Find Recipes ➜", MINT, DARK_MINT);
        JButton homeBtn = createButton("🏠 Back to Home", MINT, DARK_MINT);

        addBtn.addActionListener(e -> addIngredient());
        resetBtn.addActionListener(e -> resetIngredients());
        findBtn.addActionListener(e -> showRecipeScreen());
        homeBtn.addActionListener(e -> goHome(frame));

        inputPanel.add(ingredientField);
        inputPanel.add(addBtn);
        inputPanel.add(resetBtn);

        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(findBtn);
        bottomPanel.add(homeBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ======================== RECIPE LIST SCREEN ========================
    private JPanel createRecipeScreen(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BEIGE);
        panel.setBorder(new EmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("🍳 Recipe Suggestions", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(SERIF_FONT);
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(TEXT_COLOR);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBorder(new LineBorder(MINT, 2, true));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = createButton("⬅ Back", MINT, DARK_MINT);
        JButton homeBtn = createButton("🏠 Back to Home", MINT, DARK_MINT);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Ingredients"));
        homeBtn.addActionListener(e -> goHome(frame));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(backBtn);
        btnPanel.add(homeBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ======================== RECIPE DETAIL SCREEN ========================
    private JPanel createRecipeDetailScreen(JFrame frame) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BEIGE);
        panel.setBorder(new EmptyBorder(30, 60, 30, 60));

        JLabel title = new JLabel("📖 Recipe Details", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);

        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setFont(SERIF_FONT);
        detailArea.setBackground(Color.WHITE);
        detailArea.setForeground(TEXT_COLOR);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setBorder(new LineBorder(MINT, 2, true));

        JScrollPane scrollPane = new JScrollPane(detailArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);

        JButton backBtn = createButton("⬅ Back to Recipes", MINT, DARK_MINT);
        JButton favBtn = createButton("❤️ Like Recipe", MINT, DARK_MINT);
        JButton cookedBtn = createButton("✅ Mark as Cooked", MINT, DARK_MINT);
        JButton expiryBtn = createButton("🕒 Log Expiry", MINT, DARK_MINT);
        JButton homeBtn = createButton("🏠 Back to Home", MINT, DARK_MINT);

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Recipes"));
        favBtn.addActionListener(e -> addToFavorites());
        cookedBtn.addActionListener(e -> addEcoPoints());
        expiryBtn.addActionListener(e -> logExpiry());
        homeBtn.addActionListener(e -> goHome(frame));

        btnPanel.add(backBtn);
        btnPanel.add(favBtn);
        btnPanel.add(cookedBtn);
        btnPanel.add(expiryBtn);
        btnPanel.add(homeBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ======================== LOGIC METHODS ========================
    private void showRecipeScreen() {
        outputArea.setText("");
        outputArea.append("🍳 Your Ingredients: " + userIngredients + "\n\n");

        java.util.List<String> possible = matcher.findPossibleRecipes(userIngredients);
        possible.removeIf(name -> name.toLowerCase().startsWith("quick combo"));

        if (!possible.isEmpty()) {
            outputArea.append("✅ Recipes you can make (" + possible.size() + "):\n");
            for (String name : possible) {
                outputArea.append("\nPossible Dish: " + name + "\n");
            }
        } else {
            outputArea.append("❌ No exact recipe matches.\n");
        }

        outputArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int offset = outputArea.viewToModel2D(e.getPoint());
                try {
                    int line = outputArea.getLineOfOffset(offset);
                    String text = outputArea.getText(outputArea.getLineStartOffset(line),
                            outputArea.getLineEndOffset(line) - outputArea.getLineStartOffset(line)).trim();
                    if (text.startsWith("Possible Dish:")) {
                        selectedRecipe = text.replace("Possible Dish:", "").trim();
                        showRecipeDetail(selectedRecipe);
                    }
                } catch (Exception ignored) {}
            }
        });

        cardLayout.show(mainPanel, "Recipes");
    }

    private void showRecipeDetail(String name) {
        Recipe r = db.getRecipes().get(name);
        detailArea.setText("");
        detailArea.append("🍽 " + name + "\n\n");
        detailArea.append("Essentials: " + r.getEssential() + "\n");
        detailArea.append("Optional: " + r.getOptional() + "\n\n");
        detailArea.append("Instructions:\n" + r.getInstructions() + "\n");

        cardLayout.show(mainPanel, "RecipeDetail");
    }

    private void addIngredient() {
        String ing = ingredientField.getText().trim().toLowerCase();
        if (!ing.isEmpty() && !userIngredients.contains(ing)) {
            userIngredients.add(ing);
            ingredientListModel.addElement("• " + ing);
            ingredientField.setText("");
        }
    }

    private void resetIngredients() {
        userIngredients.clear();
        ingredientListModel.clear();
        ingredientField.setText("");
    }

    private void addToFavorites() {
        if (selectedRecipe != null) {
            AppData.addFavorite(selectedRecipe);
            JOptionPane.showMessageDialog(null, "❤️ Added " + selectedRecipe + " to Favorites!");
        }
    }

    private void addEcoPoints() {
        AppData.addEcoPoints(10);
        JOptionPane.showMessageDialog(null, "✅ You earned +10 Eco Points! (Total: " + AppData.getEcoPoints() + ")");
    }


    private void logExpiry() {
        String expiry = JOptionPane.showInputDialog("Enter expiry date for " + selectedRecipe + ":");
        if (expiry != null && !expiry.isEmpty()) {
            JOptionPane.showMessageDialog(null, "🕒 Logged expiry for " + selectedRecipe + ": " + expiry);
        }
    }

    // ======================== HELPER: Redirect to Home ========================
    private void goHome(JFrame frame) {
        frame.dispose(); // Close current window
        new HomePage();  // Launch your existing HomePage.java
    }

    // ======================== UI HELPERS ========================
    private JButton createButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(baseColor);
        button.setForeground(TEXT_COLOR);
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setBorder(new LineBorder(DARK_MINT, 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HostelRecipesGUI::new);
    }
}
