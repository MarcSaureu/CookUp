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

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookup.Logic.Preparation;

public class PreparationActivity extends AppCompatActivity implements View.OnClickListener  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpreparation);

        Button createPreparation = findViewById(R.id.createPreparationButton);

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
