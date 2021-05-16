package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Ingredient;
import com.example.cookup.Logic.Preparation;
import com.example.cookup.Logic.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdvancedSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_search);

        Button advSearch = findViewById(R.id.AdvSearcher_button);

        Button home = findViewById(R.id.HomeButton);
        Button search = findViewById(R.id.SearchButton);
        Button addR = findViewById(R.id.AddRecipeButton);
        Button profile = findViewById(R.id.ProfileButton);

        home.setOnClickListener(this);
        search.setOnClickListener(this);
        addR.setOnClickListener(this);
        profile.setOnClickListener(this);

        advSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.HomeButton:
                Intent mainintent = new Intent(AdvancedSearchActivity.this, MainActivity.class);
                startActivity(mainintent);
                finish();
                break;

            case R.id.SearchButton:
                Intent searchintent = new Intent(AdvancedSearchActivity.this, AdvancedSearchActivity.class);
                startActivity(searchintent);
                finish();
                break;

            case R.id.AddRecipeButton:
                Intent addrecipeintent = new Intent(AdvancedSearchActivity.this, RecipeActivity.class);
                startActivity(addrecipeintent);
                finish();
                break;

            case R.id.ProfileButton:
                Intent profileintent = new Intent(AdvancedSearchActivity.this, ProfileActivity.class);
                startActivity(profileintent);
                finish();
                break;

            case R.id.AdvSearcher_button:
                EditText nameRecipe = findViewById(R.id.nameRecipesearch);
                EditText foodtype = findViewById(R.id.foodtypesearch);
                EditText dishtype = findViewById(R.id.dishtypesearch);

                String name = nameRecipe.getText().toString();
                String food = foodtype.getText().toString();
                String dish = dishtype.getText().toString();

                //Buscar la recepta a firebase utilitzant aquestos valors si estan definits

                if(name.isEmpty() && food.isEmpty() && dish.isEmpty()){
                    //Retornar la view amb totes les receptes // Crida a la Main Activity Normal on ella fara la crida a FireBase per obtindre les receptes
                    Intent mainintent1 = new Intent(AdvancedSearchActivity.this, MainActivity.class);
                    startActivity(mainintent1);
                }
                //En altres casos crida a la Main Activity amb la query obtinguda per FireBase amb les receptes a mostrar
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                CollectionReference recipecollection = db.collection("recipes");

                if(!name.isEmpty()){
                    recipecollection.whereEqualTo("name", name);
                }
                if(!food.isEmpty()){
                    recipecollection.whereEqualTo("foodtype", food);
                }
                if(!dish.isEmpty()){
                    recipecollection.whereEqualTo("dishtype", dish);
                }

                recipecollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                createRecipefromDocument(document);
                            }
                        }
                    }
                });
                Intent mainintent1 = new Intent(AdvancedSearchActivity.this, MainActivity.class);
                mainintent1.putExtra("recipes", recipes);//Afegir el result de la query de Firebase
                startActivity(mainintent1);
        }


    }

    public void createRecipefromDocument(QueryDocumentSnapshot document){
        Recipe recipe = new Recipe(document.get("name").toString());
        recipe.setDishtype(document.get("dishtype").toString());
        recipe.setFoodtype(document.get("foodtype").toString());
        recipe.setDescription(document.get("description").toString());
        recipe.setServings(Integer.parseInt(document.get("servings").toString()));
        ArrayList<Ingredient> ingrlist = new ArrayList<>();
        ArrayList<Preparation> preplist = new ArrayList<>();
        ingrlist.addAll((ArrayList<Ingredient>) document.get("ingredients"));
        preplist.addAll((ArrayList<Preparation>) document.get("preparations"));
        recipe.setIngredients(ingrlist);
        recipe.setPreparations(preplist);
        recipes.add(recipe);
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
}
