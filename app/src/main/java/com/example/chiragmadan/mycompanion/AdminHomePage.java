package com.example.chiragmadan.mycompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminHomePage extends AppCompatActivity {

    public void adminLeaveEntryPage(View view)
    {
        Intent i = new Intent(AdminHomePage.this,AdminLeaveEntryPage.class);
        startActivity(i);
    }

    public void adminFeedbackPage(View view)
    {
        Intent i = new Intent(AdminHomePage.this,AdminFeedbackPage.class);
        startActivity(i);
    }

    public void feedbackPage(View view)
    {
        Toast.makeText(this,"feature development in progress",Toast.LENGTH_SHORT).show();
    }

    public void broadcastMessagePage(View view)
    {
        Toast.makeText(this,"feature development in progress",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
    }
}
