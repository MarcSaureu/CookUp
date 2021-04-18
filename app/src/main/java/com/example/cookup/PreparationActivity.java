package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Preparation;

public class PreparationActivity extends AppCompatActivity implements View.OnClickListener  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpreparation);

        Button createPreparation = findViewById(R.id.createIngredientButton);

        createPreparation.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        EditText passpreparation = findViewById(R.id.passPreparation);

        String pass = passpreparation.getText().toString();

        Preparation preparation = new Preparation(pass);

        Intent intent = new Intent();
        intent.putExtra("preparation", preparation);
        setResult(RESULT_OK, intent);
        finish();
    }
}
