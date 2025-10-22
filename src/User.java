import java.util.HashSet;
import java.util.Set;

public class User {
    private String username;
    private Set<String> favoriteRecipes;
    private int ecoPoints;

    public User(String username) {
        this.username = username;
        this.favoriteRecipes = new HashSet<>();
        this.ecoPoints = 0;
    }

    public String getUsername() { return username; }

    // Favorite Recipes
    public void addFavorite(String recipe) { favoriteRecipes.add(recipe); }
    public void removeFavorite(String recipe) { favoriteRecipes.remove(recipe); }
    public Set<String> getFavorites() { return favoriteRecipes; }

    // Eco Points
    public void addEcoPoints(int points) { if(points>0) ecoPoints += points; }
    public int getEcoPoints() { return ecoPoints; }
}
