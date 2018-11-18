package com.example.chiragmadan.mycompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
    String roll_no;
    public void leaveEntryPage(View view)
    {
        Intent i  = new Intent(HomeActivity.this,leaveEntry.class);
        i.putExtra("roll_no",roll_no);
        startActivity(i);
    }

    public void feedbackPage(View view)
    {
        Intent i  = new Intent(HomeActivity.this,FeedbackPage.class);
        i.putExtra("roll_no",roll_no);
        startActivity(i);
    }

    public void messBillPage(View view)
    {
        Intent i  = new Intent(HomeActivity.this,MessBillPage.class);
        i.putExtra("roll_no",roll_no);
        startActivity(i);
    }

    public void messMenuPage(View view)
    {
        Intent i  = new Intent(HomeActivity.this,MessMenuPage.class);
        i.putExtra("roll_no",roll_no);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        roll_no = getIntent().getExtras().getString("roll_no");
    }
}
