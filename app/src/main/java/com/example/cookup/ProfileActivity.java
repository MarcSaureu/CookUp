package com.example.cookup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    TextView coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button home = findViewById(R.id.HomeButton);
        Button search = findViewById(R.id.SearchButton);
        Button addR = findViewById(R.id.AddRecipeButton);
        Button profile = findViewById(R.id.ProfileButton);
        Button maps = (Button) findViewById(R.id.LocationButton);

        home.setOnClickListener(this);
        search.setOnClickListener(this);
        addR.setOnClickListener(this);
        profile.setOnClickListener(this);
        maps.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildTitle();
    }

    private void buildTitle() {

        TextView name = (TextView) findViewById(R.id.namedefault);
        TextView email = (TextView) findViewById(R.id.emailInput);
        coord = (TextView) findViewById(R.id.coordinates);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String alias = mySharedPreferences.getString(getString(R.string.name), "No name");
        String mail = mySharedPreferences.getString(getString(R.string.email), "No email");

        name.setText(alias);
        email.setText(mail);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HomeButton:
                Intent mainintent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(mainintent);
                finish();
                break;

            case R.id.SearchButton:
                Intent searchintent = new Intent(ProfileActivity.this, AdvancedSearchActivity.class);
                startActivity(searchintent);
                finish();
                break;

            case R.id.AddRecipeButton:
                Intent addrecipeintent = new Intent(ProfileActivity.this, RecipeActivity.class);
                startActivity(addrecipeintent);
                finish();
                break;

            case R.id.ProfileButton:
                Intent profileintent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(profileintent);
                finish();
                break;

            case R.id.LocationButton:
                Intent locationIntent = new Intent(ProfileActivity.this, MapsActivity.class);
                startActivityForResult(locationIntent, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String lat = data.getStringExtra("Lat");
                String lon = data.getStringExtra("Lon");
                //coord.setText(lat + "\n" + lon);
                Toast.makeText(getApplicationContext(),"Lat: " + lat + "\n" + "Lon: " + lon, Toast.LENGTH_LONG).show();

            }
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