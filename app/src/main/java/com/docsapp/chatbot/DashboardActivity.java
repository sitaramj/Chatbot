package com.docsapp.chatbot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        setupFragment();
    }

    private void setupFragment() {
        Fragment chatFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        chatFragment = new ChatFragment();
        fragmentManager.beginTransaction().replace(R.id.mainContainer, chatFragment).commit();
    }
}
