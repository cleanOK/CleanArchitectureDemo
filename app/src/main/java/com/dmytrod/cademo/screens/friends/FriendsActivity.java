package com.dmytrod.cademo.screens.friends;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dmytrod.cademo.R;

public class FriendsActivity extends AppCompatActivity implements FriendsContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void displayFriend(@NonNull Friend user) {

    }

    @Override
    public void showError(String message) {

    }
}
