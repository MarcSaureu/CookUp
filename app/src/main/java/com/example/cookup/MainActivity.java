package com.example.cookup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cookup.Logic.Recipe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Recipe> recipes = new ArrayList<>();

    private ArrayList<String> rec = new ArrayList<>();

    ListView Recipe;

    ArrayAdapter<String> recipeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button home = findViewById(R.id.HomeButton);
        Button search = findViewById(R.id.SearchButton);
        Button addR = findViewById(R.id.AddRecipeButton);
        Button profile = findViewById(R.id.ProfileButton);

        home.setOnClickListener(this);
        search.setOnClickListener(this);
        addR.setOnClickListener(this);
        profile.setOnClickListener(this);

        //Obtindre les receptes de Firebase

        Recipe = findViewById(R.id.RecipeList);

        recipeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , rec);
        Recipe.setAdapter(recipeadapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
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