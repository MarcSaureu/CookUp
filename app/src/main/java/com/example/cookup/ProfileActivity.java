package com.example.cookup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button editBtn = findViewById(R.id.editProfileButton);
        editBtn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildTitle();
    }

    private void buildTitle() {

        TextView name = (TextView) findViewById(R.id.namedefault);
        TextView email = (TextView) findViewById(R.id.emailInput);


        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String alias = mySharedPreferences.getString(getString(R.string.namedefault), "No name");
        String mail = mySharedPreferences.getString(getString(R.string.emaildefault), "No email");

        name.setText(alias);
        email.setText(mail);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, com.example.cookup.preferences.PreferencesActivity.class);
        startActivity(intent);
    }
}