package com.example.cookup;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Ingredient;
import com.example.cookup.Logic.Preparation;
import com.example.cookup.Logic.Recipe;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Preparation> preparations = new ArrayList<>();

    ListView Ingredients;
    ListView Preparations;

    ArrayAdapter<Ingredient> AdapterIngredient;
    ArrayAdapter<Preparation> AdapterPreparation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);

        Ingredients = findViewById(R.id.IngredientList);
        Preparations = findViewById(R.id.PreparationList);

        Button addIngredient = findViewById(R.id.addIngredientButton);
        Button addPreparation = findViewById(R.id.addPreparationButton);
        Button createRecipe = findViewById(R.id.createRecipeButton);

        addIngredient.setOnClickListener(this);
        addPreparation.setOnClickListener(this);
        createRecipe.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addIngredientButton:
                //ActivityForResult Ingredient
                break;

            case R.id.addPreparationButton:
                //ActivityForResult Preparation
                break;

            case R.id.createRecipeButton:
                Recipe recipe = createRecipeWithValues();
                for(Ingredient ingredient: ingredients){
                    recipe.addIngredient((ingredient));
                }
                for(Preparation preparation: preparations){
                    recipe.addPreparation(preparation);
                }

                //Guardar a Firebase

                finish();
        }

    }

    public Recipe createRecipeWithValues(){
        EditText nameRecipe = findViewById(R.id.nameRecipe);
        EditText foodtype = findViewById(R.id.foodtype);
        EditText dishtype = findViewById(R.id.dishtype);
        EditText description = findViewById(R.id.description);
        EditText servings = findViewById(R.id.servings);

        String name = nameRecipe.getText().toString();
        String food = foodtype.getText().toString();
        String dish = dishtype.getText().toString();
        String descrip = description.getText().toString();
        int serv = Integer.parseInt(servings.getText().toString());

        Recipe recipe = new Recipe(name);
        recipe.setFoodtype(food);
        recipe.setDishtype(dish);
        recipe.setDescription(descrip);
        recipe.setServings(serv);

        return recipe;
    }
}
