import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostelRecipesGUI {
    private RecipeDatabase db;
    private RecipeMatcher matcher;
    private JTextArea outputArea;
    private JTextField ingredientField;
    private Set<String> userIngredients;
    private DefaultListModel<String> ingredientListModel;
    private JList<String> ingredientList;
    private DefaultListModel<String> recipeListModel;
    private JList<String> recipeList;
    private void populateRecipeList() {
        recipeListModel.clear();
        for (String recipeName : db.getRecipes().keySet()) {
            if (!recipeName.toLowerCase().startsWith("quick combo")) {
                recipeListModel.addElement(recipeName);
            }
        }
    }

    public HostelRecipesGUI() {
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.BOLD, 14));
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        db = new RecipeDatabase();
        matcher = new RecipeMatcher(db.getRecipes());
        userIngredients = new HashSet<>();
        recipeListModel = new DefaultListModel<>();
        recipeList = new JList<>(recipeListModel);
        populateRecipeList();

        // === Frame ===
        JFrame frame = new JFrame("INGREEDY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null); // Center window

        // === Title Banner ===
        JLabel title = new JLabel("INGREEDY", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(236, 240, 241));
        title.setBorder(new EmptyBorder(15, 0, 15, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(52, 73, 94)); // Dark banner
        titlePanel.add(title);
        frame.add(titlePanel, BorderLayout.NORTH);

        // === Left Panel (Ingredients List) ===
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5)) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(46, 204, 113), getWidth(), getHeight(),
                        new Color(39, 174, 96));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel ingLabel = new JLabel("Your Ingredients:");
        ingLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ingLabel.setForeground(Color.WHITE);

        ingredientListModel = new DefaultListModel<>();
        ingredientList = new JList<>(ingredientListModel);
        ingredientList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ingredientList.setBackground(new Color(236, 240, 241));
        ingredientList.setBorder(BorderFactory.createLineBorder(new Color(34, 49, 63)));

        JScrollPane ingScroll = new JScrollPane(ingredientList);

        leftPanel.add(ingLabel, BorderLayout.NORTH);
        leftPanel.add(ingScroll, BorderLayout.CENTER);

        // === Input Panel (Bottom of Left Panel) ===
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);

        ingredientField = new JTextField(15);
        ingredientField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton addBtn = new JButton("Add");
        JButton resetBtn = new JButton("Reset");
        styleButton(addBtn, new Color(52, 152, 219));
        styleButton(resetBtn, new Color(231, 76, 60));

        inputPanel.add(ingredientField);
        inputPanel.add(addBtn);
        inputPanel.add(resetBtn);
        leftPanel.add(inputPanel, BorderLayout.SOUTH);

        // === Right Panel (Output Recipes) ===
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(new Color(236, 240, 241));

        JLabel outLabel = new JLabel("Recipe Suggestions:");
        outLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        outLabel.setForeground(new Color(44, 62, 80));

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(new Color(250, 250, 250));
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JButton findBtn = new JButton("Find Recipes");
        styleButton(findBtn, new Color(155, 89, 182));

        rightPanel.add(outLabel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(findBtn, BorderLayout.SOUTH);

        // === Add to frame ===
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        // === Button Actions ===
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addIngredient(); }
        });
        findBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { findRecipes(); }
        });
        resetBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { resetAll(); }
        });

        frame.setVisible(true);
    }

    private void styleButton(JButton btn, Color baseColor) {
        btn.setFocusPainted(false);
        btn.setBackground(baseColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(baseColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(baseColor);
            }
        });
    }

    private void addIngredient() {
        String ing = ingredientField.getText().toLowerCase().trim();
        if (!ing.isEmpty() && !userIngredients.contains(ing)) {
            userIngredients.add(ing);
            ingredientListModel.addElement("✅ " + ing);
            ingredientField.setText("");
        }
    }

    private void findRecipes() {
        outputArea.setText("");
        outputArea.append("🍳 Your Ingredients: " + userIngredients + "\n\n");

        List<String> possible = matcher.findPossibleRecipes(userIngredients);
        List<String> close = matcher.findCloseRecipes(userIngredients);

        possible.removeIf(name -> name.toLowerCase().startsWith("quick combo"));
        close.removeIf(name -> name.toLowerCase().startsWith("quick combo"));

        if (!possible.isEmpty()) {
            outputArea.append("✅ Recipes you can make (" + possible.size() + "):\n");
            for (String name : possible) {
                Recipe r = db.getRecipes().get(name);
                outputArea.append("\n🍽 " + name +
                        "\n  Essentials: " + r.getEssential() +
                        "\n  Optional: " + r.getOptional() +
                        "\n  Instructions:\n" + r.getInstructions() + "\n");
            }
        } else {
            outputArea.append("❌ No exact recipe matches.\n");
        }

        if (!close.isEmpty()) {
            outputArea.append("\n⚡ Close matches (" + close.size() + "):\n");
            for (String name : close) {
                outputArea.append("➡ " + name + "\n");
            }
        }
    }

    private void resetAll() {
        userIngredients.clear();
        ingredientListModel.clear();
        outputArea.setText("🔄 Reset successful. Start adding ingredients again.\n");
        ingredientField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.showSplash(30, 1500, 30);
        });
    }
}