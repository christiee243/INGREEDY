import java.util.*;

public class Recipe {
    private List<String> essential;
    private List<String> optional;
    private String instructions;

    public Recipe(List<String> essential, List<String> optional, String instructions) {
        this.essential = essential;
        this.optional = optional;
        this.instructions = instructions;
    }

    public List<String> getEssential() {
        return essential;
    }

    public List<String> getOptional() {
        return optional;
    }

    public String getInstructions() {
        return instructions;
    }
}
