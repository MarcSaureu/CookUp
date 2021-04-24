package com.example.cookup.Logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "text";
    public static final String EMAIL = "text";
    public static final String MEMBERSHIP = "text";
    public static final String ADDRESS = "text";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {



            default:


        }
    }

    public void setDefaults(String key, String value, Context context) {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        editor.apply();
    }
}
