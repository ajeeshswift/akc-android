package com.swift.akc;

import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.swift.akc.activity.LoginActivity;
import com.swift.akc.activity.MainActivity;

public class LandingPageCompactActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.harvestentry:
                startActivity(new Intent(LandingPageCompactActivity.this, harvestentry.class));
                break;
            case R.id.harvestvisit:
                startActivity(new Intent(LandingPageCompactActivity.this, harvestvisit.class));
                break;
            case R.id.harvestforcasting:
                startActivity(new Intent(LandingPageCompactActivity.this, harvestingforcast.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
