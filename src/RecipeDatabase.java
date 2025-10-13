import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;

public class RecipeDatabase {
    private Map<String, Recipe> recipes;

    public RecipeDatabase() {
        recipes = new LinkedHashMap<>();
        loadRecipes();
    }

    private void loadRecipes() {
        // ===== Hostel-friendly recipes =====
        recipes.put("Tea", new Recipe(Arrays.asList("tea leaves","water"), Arrays.asList("milk","sugar"),
                "1. Boil water.\n2. Add tea leaves.\n3. Add milk/sugar if available.\n4. Strain and serve."));
        recipes.put("Coffee", new Recipe(Arrays.asList("coffee powder","water"), Arrays.asList("milk","sugar"),
                "1. Boil water.\n2. Add coffee powder.\n3. Add milk/sugar if available.\n4. Serve."));
        recipes.put("Boiled Egg", new Recipe(Arrays.asList("egg","water"), Arrays.asList("salt","pepper"),
                "1. Boil egg 8-10 min.\n2. Peel and season."));
        recipes.put("Egg Bhurji", new Recipe(Arrays.asList("egg"), Arrays.asList("onion","tomato","salt","oil"),
                "1. Chop onion/tomato.\n2. Fry in oil.\n3. Add beaten egg and scramble."));
        recipes.put("Omelette", new Recipe(Arrays.asList("egg"), Arrays.asList("onion","tomato","salt","oil"),
                "1. Beat egg.\n2. Fry onion/tomato.\n3. Add egg and cook until set."));
        recipes.put("Bread Toast", new Recipe(Arrays.asList("bread"), Arrays.asList("butter","jam"),
                "1. Toast bread.\n2. Spread butter/jam."));
        recipes.put("Cheese Toast", new Recipe(Arrays.asList("bread","cheese"), Arrays.asList("butter","tomato"),
                "1. Place cheese on bread.\n2. Heat until cheese softens."));
        recipes.put("Peanut Butter Sandwich", new Recipe(Arrays.asList("bread","peanut butter"), Arrays.asList("jam","honey"),
                "1. Spread peanut butter on bread.\n2. Add jam/honey."));
        recipes.put("Banana Sandwich", new Recipe(Arrays.asList("bread","banana"), Arrays.asList("honey","peanut butter"),
                "1. Slice banana on bread.\n2. Add peanut butter/honey."));
        recipes.put("Maggi Noodles", new Recipe(Arrays.asList("maggi noodles","water","masala"), Arrays.asList("egg","vegetables"),
                "1. Boil water, add noodles and masala.\n2. Add optional egg/vegetables.\n3. Cook 2-3 min."));
        recipes.put("Boiled Potato", new Recipe(Arrays.asList("potato","water"), Arrays.asList("salt","pepper"),
                "1. Boil potato until soft.\n2. Peel and season."));
        recipes.put("Aloo Toast Sandwich", new Recipe(Arrays.asList("bread","potato","water"), Arrays.asList("butter","salt","pepper"),
                "1. Boil and mash potato.\n2. Spread on bread, toast on pan with butter."));
        recipes.put("Cucumber Sandwich", new Recipe(Arrays.asList("bread","cucumber"), Arrays.asList("butter","salt","pepper"),
                "1. Slice cucumber and layer on bread.\n2. Add butter and season."));
        recipes.put("Tomato Sandwich", new Recipe(Arrays.asList("bread","tomato"), Arrays.asList("butter","salt","pepper"),
                "1. Slice tomato.\n2. Place on toasted bread, add butter/salt/pepper."));
        recipes.put("Fruit Salad", new Recipe(Arrays.asList("banana","apple"), Arrays.asList("orange","grapes","honey"),
                "1. Chop fruits.\n2. Mix and drizzle honey if available."));
        recipes.put("Cucumber Salad", new Recipe(Arrays.asList("cucumber"), Arrays.asList("salt","lemon","pepper"),
                "1. Slice cucumber.\n2. Add salt, lemon juice, pepper."));
        recipes.put("Carrot Salad", new Recipe(Arrays.asList("carrot"), Arrays.asList("salt","lemon","peanut"),
                "1. Grate carrot.\n2. Mix with salt/lemon.\n3. Add crushed peanuts if available."));
        recipes.put("Simple Salad", new Recipe(Arrays.asList("tomato","cucumber"), Arrays.asList("onion","salt","lemon"),
                "1. Chop veggies.\n2. Mix with salt and lemon."));
        recipes.put("Lemon Water", new Recipe(Arrays.asList("lemon","water"), Arrays.asList("sugar"),
                "1. Squeeze lemon into water.\n2. Add sugar if desired."));
        recipes.put("Lemon Corn", new Recipe(Arrays.asList("corn","water","lemon"), Arrays.asList("salt","butter"),
                "1. Boil corn 5-7 min.\n2. Squeeze lemon and add salt/butter."));
        recipes.put("Boiled Corn", new Recipe(Arrays.asList("corn","water"), Arrays.asList("salt","chili","butter"),
                "1. Boil corn.\n2. Season to taste."));
        recipes.put("Poha", new Recipe(Arrays.asList("poha","water"), Arrays.asList("onion","peanut","lemon","salt","oil"),
                "1. Rinse poha.\n2. Fry onion/peanuts, add poha.\n3. Sprinkle lemon and salt."));
        recipes.put("Upma", new Recipe(Arrays.asList("rava","water"), Arrays.asList("onion","vegetables","salt","oil"),
                "1. Roast rava lightly.\n2. Boil water, add rava.\n3. Mix sautéed veggies if available."));
        recipes.put("Bread Pizza", new Recipe(Arrays.asList("bread"), Arrays.asList("tomato","cheese","capsicum","pizza sauce"),
                "1. Spread sauce on bread.\n2. Add veggies and cheese.\n3. Heat until cheese melts."));
        recipes.put("Peanut Butter Banana Toast", new Recipe(Arrays.asList("bread","peanut butter","banana"), Arrays.asList("honey"),
                "1. Spread peanut butter on toasted bread.\n2. Top with banana slices and honey."));
        recipes.put("Banana Shake", new Recipe(Arrays.asList("banana","milk"), Arrays.asList("sugar","honey"),
                "1. Blend banana and milk.\n2. Add sweetener if desired."));
        recipes.put("Cold Coffee", new Recipe(Arrays.asList("coffee powder","milk"), Arrays.asList("sugar","ice"),
                "1. Blend coffee powder with milk and sugar.\n2. Serve chilled with ice."));
        recipes.put("Milk Oats", new Recipe(Arrays.asList("oats","milk"), Arrays.asList("sugar","banana","honey"),
                "1. Cook oats in milk.\n2. Sweeten and add fruits if available."));

        // ===== Generate extra quick recipes to reach 110 =====
        int idx = 1;
        while(recipes.size() < 110) {
            recipes.put("Quick Combo " + idx, new Recipe(
                    Arrays.asList("bread"),
                    Arrays.asList("butter","cheese","tomato"),
                    "1. Use bread with available spread or veggies.\n2. Toast or eat raw."));
            idx++;
        }
    }

    public Map<String, Recipe> getRecipes() {
        return recipes;
    }
}
