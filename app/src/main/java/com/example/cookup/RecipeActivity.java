package com.example.cookup;

import android.content.Intent;
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

    private ArrayList<String> ingr = new ArrayList<>();
    private ArrayList<String> prep = new ArrayList<>();

    ListView Ingredients;
    ListView Preparations;

    ArrayAdapter<String> ingradapter;
    ArrayAdapter<String> prepadapter;

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
                Intent ingintent = new Intent(RecipeActivity.this, IngredientActivity.class);
                startActivityForResult(ingintent,2);
                break;

            case R.id.addPreparationButton:
                Intent prepintent = new Intent(RecipeActivity.this, PreparationActivity.class);
                startActivityForResult(prepintent,3);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Ingredient ingredient = (Ingredient) data.getSerializableExtra("ingredient");
                ingredients.add(ingredient);
                ingr.add(ingredient.toString());
                LoadAdapterIngredients();
            }
        }else if(requestCode == 3){
            if(resultCode == RESULT_OK){
                Preparation preparation =(Preparation) data.getSerializableExtra("preparation");
                preparations.add(preparation);
                prep.add(preparation.toString());
                LoadAdapterPreparations();
            }
        }
    }

    public void LoadAdapterIngredients(){
        ingradapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , ingr);
        Ingredients.setAdapter(ingradapter);
    }

    public void LoadAdapterPreparations(){
        prepadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prep);
        Preparations.setAdapter(prepadapter);
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
