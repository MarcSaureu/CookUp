package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Ingredient;
import com.example.cookup.Logic.Preparation;
import com.example.cookup.Logic.Recipe;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recipe> recipes = new ArrayList<>();

    private ArrayList<String> rec = new ArrayList<>();

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    ListView Recipe;

    ArrayAdapter<String> recipeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), 5);*/

        Intent intent = getIntent();
        if(intent.hasExtra("recipes")){
            recipes.addAll((ArrayList<Recipe>) intent.getSerializableExtra("recipes"));
        }else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            createRecipefromDocument(document);
                        }
                    }
                }
            });
        }

        Recipe = findViewById(R.id.RecipeList);

        recipeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , rec);
        Recipe.setAdapter(recipeadapter);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //BadgeDrawable current = bottomNav.getBadge(R.id.SearchButton);
        //current.setVisible(true);

    }

    @SneakyThrows
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 5){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println("Success");
            }else{
                System.out.println("Fail");
                finish();
            }
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
        rec.add(recipe.toString());
        LoadRecipeAdapter();
    }

    public void LoadRecipeAdapter(){
        recipeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , rec);
        Recipe.setAdapter(recipeadapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.HomeButton:
                    Intent mainintent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(mainintent);
                    finish();
                    break;
                case R.id.SearchButton:
                    Intent searchintent = new Intent(MainActivity.this, AdvancedSearchActivity.class);
                    startActivity(searchintent);
                    finish();
                    break;
                case R.id.AddRecipeButton:
                    Intent addrecipeintent = new Intent(MainActivity.this, RecipeActivity.class);
                    startActivity(addrecipeintent);
                    finish();
                    break;
                case R.id.ProfileButton:
                    Intent profileintent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(profileintent);
                    finish();
                    break;
            }
            return true;
        }
    };
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