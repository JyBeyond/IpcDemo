package com.example.administrator.ipcdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.ipcdemo.messager.MessagerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button messager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messager = findViewById(R.id.messager);
        messager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messager:
                Intent intent = new Intent(this, MessagerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
