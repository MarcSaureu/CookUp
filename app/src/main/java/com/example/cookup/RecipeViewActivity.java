package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Ingredient;
import com.example.cookup.Logic.Preparation;
import com.example.cookup.Logic.Recipe;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeViewActivity extends AppCompatActivity {

    private ArrayList<String> ingr = new ArrayList<>();
    private ArrayList<String> prep = new ArrayList<>();

    ListView Ingredients;
    ListView Preparations;

    ArrayAdapter<String> ingradapter;
    ArrayAdapter<String> prepadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");

        Ingredients = findViewById(R.id.IngredientListView);
        Preparations = findViewById(R.id.PreparationListView);

        TextView name = findViewById(R.id.RecipeNameView);
        TextView serv = findViewById(R.id.RecipeServingView);
        TextView dish = findViewById(R.id.RecipeDishView);
        TextView food = findViewById(R.id.RecipeFoodView);
        TextView desc = findViewById(R.id.RecipeDescriptionView);

        for(Ingredient ingredient: recipe.getIngredients()){
            ingr.add(ingredient.toString());
        }

        for(Preparation preparation: recipe.getPreparations()){
            prep.add(preparation.toString());
        }

        name.setText(recipe.getName());
        serv.setText(recipe.getServings());
        dish.setText(recipe.getDishtype());
        food.setText(recipe.getFoodtype());
        desc.setText(recipe.getDescription());


        LoadAdapterIngredients();
        LoadAdapterPreparations();
    }

    public void LoadAdapterIngredients(){
        ingradapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , ingr);
        Ingredients.setAdapter(ingradapter);
    }

    public void LoadAdapterPreparations(){
        prepadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prep);
        Preparations.setAdapter(prepadapter);
    }
}
