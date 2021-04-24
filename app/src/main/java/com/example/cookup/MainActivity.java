package com.example.cookup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cookup.Logic.Recipe;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Recipe> recipes = new ArrayList<>();

    private ArrayList<String> rec = new ArrayList<>();

    ListView Recipe;

    ArrayAdapter<String> recipeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtindre les receptes de Firebase

        Recipe = findViewById(R.id.RecipeList);

        recipeadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , rec);
        Recipe.setAdapter(recipeadapter);
    }
}