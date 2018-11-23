package com.example.chiragmadan.mycompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminHomePage extends AppCompatActivity {

    public void adminLeaveEntryPage(View view)
    {
        Intent i = new Intent(AdminHomePage.this,AdminLeaveEntryPage.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
    }
}
