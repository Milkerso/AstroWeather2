package com.example.dawid.astro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btKonfiguracja:
                Intent intent = new Intent(MainActivity.this, konfiguracja.class);
                startActivity(intent);
                break;
            case R.id.btApka:
                intent = new Intent(MainActivity.this, Apka.class);
                startActivity(intent);
                break;
        }
    }

}
