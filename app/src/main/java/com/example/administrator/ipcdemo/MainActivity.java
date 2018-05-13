package com.example.administrator.ipcdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.ipcdemo.Socket.TCPActivity;
import com.example.administrator.ipcdemo.aidl.BookManagerAcivity;
import com.example.administrator.ipcdemo.contentProvide.ProvideActivity;
import com.example.administrator.ipcdemo.messager.MessagerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button messager, aidl, contentProvide, socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messager = findViewById(R.id.messager);
        aidl = findViewById(R.id.aidl);
        contentProvide = findViewById(R.id.contentProvide);
        socket = findViewById(R.id.socket);
        messager.setOnClickListener(this);
        aidl.setOnClickListener(this);
        contentProvide.setOnClickListener(this);
        socket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messager:
                Intent intent = new Intent(this, MessagerActivity.class);
                startActivity(intent);
                break;
            case R.id.aidl:
                Intent intent1 = new Intent(this, BookManagerAcivity.class);
                startActivity(intent1);
                break;
            case R.id.contentProvide:
                Intent intent2 = new Intent(this, ProvideActivity.class);
                startActivity(intent2);
                break;
            case R.id.socket:
                Intent intent3 = new Intent(this, TCPActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
