import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;

    // Log in (or create if first time)
    public static User login(String username) {
        currentUser = users.computeIfAbsent(username, User::new);
        return currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    // Optional: log out
    public static void logout() {
        currentUser = null;
    }
}

