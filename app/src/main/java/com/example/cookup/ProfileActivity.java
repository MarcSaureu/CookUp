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
        TextView address = (TextView) findViewById(R.id.addressInput);
        TextView location = (TextView) findViewById(R.id.locationInput);


        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String alias = mySharedPreferences.getString(getString(R.string.name), "No name");
        String mail = mySharedPreferences.getString(getString(R.string.email), "No email");
        String place = mySharedPreferences.getString(getString(R.string.address), "No address");
        boolean locationEnable = mySharedPreferences.getBoolean(R.string.maps, false);

        name.setText(alias);
        email.setText(mail);
        address.setText(place);

        if(locationEnable){
            location.setText(R.string.locationOn);
        } else {
            location.setText(R.string.locationdefault);
        }


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, com.example.cookup.preferences.PreferencesActivity.class);
        startActivity(intent);
    }
}