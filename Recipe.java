package com.example.smartpantrymanager;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private String steps;
    private List<String> ingredients;

    public Recipe(int id, String name, String steps, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSteps() { return steps; }
    public List<String> getIngredients() { return ingredients; }



}
