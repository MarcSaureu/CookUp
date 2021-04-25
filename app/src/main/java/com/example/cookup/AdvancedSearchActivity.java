package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Preparation;

public class AdvancedSearchActivity extends AppCompatActivity implements View.OnClickListener {

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
                EditText nameRecipe = findViewById(R.id.nameRecipe);
                EditText foodtype = findViewById(R.id.foodtype);
                EditText dishtype = findViewById(R.id.dishtype);

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

                Intent mainintent1 = new Intent(AdvancedSearchActivity.this, MainActivity.class);
                mainintent1.putExtra("recipe", "");//Afegir el result de la query de Firebase
                startActivity(mainintent1);
        }


    }
}
