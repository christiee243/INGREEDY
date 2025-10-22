import java.util.Set;

public class AppData {

    public static void addFavorite(String recipe) {
        User user = UserManager.getCurrentUser();
        if(user != null) user.addFavorite(recipe);
    }

    public static Set<String> getFavorites() {
        User user = UserManager.getCurrentUser();
        return user != null ? user.getFavorites() : Set.of();
    }

    public static void addEcoPoints(int points) {
        User user = UserManager.getCurrentUser();
        if(user != null) user.addEcoPoints(points);
    }
    public static int getEcoPoints() {
        return UserManager.getCurrentUser() != null
                ? UserManager.getCurrentUser().getEcoPoints()
                : 0;
    }
}

