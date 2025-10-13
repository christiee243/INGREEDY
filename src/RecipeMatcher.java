import java.util.*;

public class RecipeMatcher {

    private Map<String, Recipe> recipeMap;

    public RecipeMatcher(Map<String, Recipe> recipeMap) {
        this.recipeMap = recipeMap;
    }

    // Returns list of recipe names you can make (all essential ingredients present)
    public List<String> findPossibleRecipes(Set<String> userIngredients) {
        List<String> canMake = new ArrayList<>();

        for (Map.Entry<String, Recipe> entry : recipeMap.entrySet()) {
            Recipe r = entry.getValue();
            if (userIngredients.containsAll(r.getEssential())) {
                canMake.add(entry.getKey());
            }
        }

        return canMake;
    }

    // Returns list of close matches (1–2 missing essential ingredients)
    public List<String> findCloseRecipes(Set<String> userIngredients) {
        List<String> closeMatches = new ArrayList<>();

        for (Map.Entry<String, Recipe> entry : recipeMap.entrySet()) {
            Recipe r = entry.getValue();
            List<String> missing = new ArrayList<>();
            for (String ing : r.getEssential()) {
                if (!userIngredients.contains(ing)) {
                    missing.add(ing);
                }
            }
            if (!missing.isEmpty() && missing.size() <= 2) {
                closeMatches.add(entry.getKey());
            }
        }

        return closeMatches;
    }
}
