package com.example.cookup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IngredientActivity extends AppCompatActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingredient);
    }

    @Override
    public void onClick(View v) {
        //Crear Ingrediente i devolver el ingrediente creado al startActivityForResult
    }
}
