package com.example.cookup.Logic;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Recipe {
    private String name;
    private String dishtype;
    private String foodtype;
    private String description;
    private int servings;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Preparation> preparations = new ArrayList<>();

    public Recipe(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    public void addPreparation(Preparation preparation){
        this.preparations.add(preparation);
    }
}
