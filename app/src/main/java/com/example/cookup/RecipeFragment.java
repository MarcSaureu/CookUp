package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookup.Logic.Ingredient;
import com.example.cookup.Logic.Preparation;
import com.example.cookup.Logic.Recipe;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {


    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Preparation> preparations = new ArrayList<>();

    private ArrayList<String> ingr = new ArrayList<>();
    private ArrayList<String> prep = new ArrayList<>();

    ListView Ingredients;
    ListView Preparations;

    ArrayAdapter<String> ingradapter;
    ArrayAdapter<String> prepadapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Ingredients = getActivity().findViewById(R.id.IngredientList);
        Preparations = getActivity().findViewById(R.id.PreparationList);

        Button addIngredient = getActivity().findViewById(R.id.addIngredientButton);
        Button addPreparation = getActivity().findViewById(R.id.addPreparationButton);
        Button createRecipe = getActivity().findViewById(R.id.createRecipeButton);

        /*
        addIngredient.setOnClickListener(this);
        addPreparation.setOnClickListener(this);
        createRecipe.setOnClickListener(this);*/


        return inflater.inflate(R.layout.fragment_addrecipe, container, false);


    }

    /*case R.id.createRecipeButton:
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
                break;*/

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 2){
            if(resultCode == getActivity().RESULT_OK){
                Ingredient ingredient = (Ingredient) data.getSerializableExtra("ingredient");
                ingredients.add(ingredient);
                ingr.add(ingredient.toString());
                LoadAdapterIngredients();
            }
        }else if(requestCode == 3){
            if(resultCode == getActivity().RESULT_OK){
                Preparation preparation =(Preparation) data.getSerializableExtra("preparation");
                preparations.add(preparation);
                prep.add(preparation.toString());
                LoadAdapterPreparations();
            }
        }
    }

    public void LoadAdapterIngredients(){
        ingradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , ingr);
        Ingredients.setAdapter(ingradapter);
    }

    public void LoadAdapterPreparations(){
        prepadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, prep);
        Preparations.setAdapter(prepadapter);
    }

    public Recipe createRecipeWithValues(){
        EditText nameRecipe = getActivity().findViewById(R.id.nameRecipe);
        EditText foodtype = getActivity().findViewById(R.id.foodtype);
        EditText dishtype = getActivity().findViewById(R.id.dishtype);
        EditText description = getActivity().findViewById(R.id.description);
        EditText servings = getActivity().findViewById(R.id.servings);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.config:
                startActivity(new Intent(getActivity(), com.example.cookup.preferences.PreferencesActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addRecipeToFirebase(Recipe recipe , FirebaseFirestore db){
        db.collection("recipes").document(recipe.getName()).set(recipe);
    }
}
