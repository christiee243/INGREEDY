import java.util.*;

public class AppData {
    public static Set<String> favoriteRecipes = new HashSet<>();
    public static int ecoPoints = 0;

    // Optional helper methods
    public static void addFavorite(String recipe) {
        favoriteRecipes.add(recipe);
    }

    public static void addEcoPoints(int points) {
        ecoPoints += points;
    }

    public static int getEcoPoints() {
        return ecoPoints;
    }

    public static Set<String> getFavorites() {
        return favoriteRecipes;
    }
}
