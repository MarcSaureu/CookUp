package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Ingredient;
import com.example.cookup.Logic.Preparation;
import com.example.cookup.Logic.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

        Button home = findViewById(R.id.HomeButton);
        Button search = findViewById(R.id.SearchButton);
        Button addR = findViewById(R.id.AddRecipeButton);
        Button profile = findViewById(R.id.ProfileButton);

        home.setOnClickListener(this);
        search.setOnClickListener(this);
        addR.setOnClickListener(this);
        profile.setOnClickListener(this);

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
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Recipe recipe = createRecipeWithValues();
                for(Ingredient ingredient: ingredients){
                    recipe.addIngredient((ingredient));
                }
                for(Preparation preparation: preparations){
                    recipe.addPreparation(preparation);
                }
                addRecipeToFirebase(recipe,db);
                //Guardar a Firebase
                Intent mainintent2 = new Intent(RecipeActivity.this, MainActivity.class);
                startActivity(mainintent2);
                finish();
                break;

            case R.id.HomeButton:
                Intent mainintent = new Intent(RecipeActivity.this, MainActivity.class);
                startActivity(mainintent);
                finish();
                break;

            case R.id.SearchButton:
                Intent searchintent = new Intent(RecipeActivity.this, AdvancedSearchActivity.class);
                startActivity(searchintent);
                finish();
                break;

            case R.id.AddRecipeButton:
                Intent addrecipeintent = new Intent(RecipeActivity.this, RecipeActivity.class);
                startActivity(addrecipeintent);
                finish();
                break;

            case R.id.ProfileButton:
                Intent profileintent = new Intent(RecipeActivity.this, ProfileActivity.class);
                startActivity(profileintent);
                finish();
                break;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.config:
                startActivity(new Intent(this, com.example.cookup.preferences.PreferencesActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addRecipeToFirebase(Recipe recipe , FirebaseFirestore db){
        db.collection("recipes").document(recipe.getName()).set(recipe);
    }
}
