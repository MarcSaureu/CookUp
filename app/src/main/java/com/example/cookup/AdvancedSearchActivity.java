package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Preparation;

public class AdvancedSearchActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_search);

        Button advSearch = findViewById(R.id.AdvSearcher_button);

        advSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText nameRecipe = findViewById(R.id.nameRecipe);
        EditText foodtype = findViewById(R.id.foodtype);
        EditText dishtype = findViewById(R.id.dishtype);

        String name = nameRecipe.getText().toString();
        String food = foodtype.getText().toString();
        String dish = dishtype.getText().toString();

        //Buscar la recepta a firebase utilitzant aquestos valors si estan definits

        if(name.isEmpty() && food.isEmpty() && dish.isEmpty()){
            //Retornar la view amb totes les receptes // Crida a la Main Activity Normal on ella fara la crida a FireBase per obtindre les receptes
            Intent mainintent = new Intent(AdvancedSearchActivity.this, MainActivity.class);
            startActivityForResult(mainintent,2);
        }
        //En altres casos crida a la Main Activity amb la query obtinguda per FireBase amb les receptes a mostrar
    }
}
