import java.util.*;

public class AppData {
    private static Set<String> favoriteRecipes = new HashSet<>();
    private static int ecoPoints = 0;
    private static Map<String, String> expiryData = new LinkedHashMap<>();

    // Favorites
    public static void addFavorite(String recipe) {
        favoriteRecipes.add(recipe);
    }

    public static Set<String> getFavorites() {
        return favoriteRecipes;
    }

    // Eco Points
    public static void addEcoPoints(int points) {
        ecoPoints += points;
    }

    public static int getEcoPoints() {
        return ecoPoints;
    }

    // Expiry Data
    public static void addExpiry(String itemName, String expiryDate) {
        expiryData.put(itemName, expiryDate);
    }

    public static Map<String, String> getExpiryData() {
        return expiryData;
    }
}
