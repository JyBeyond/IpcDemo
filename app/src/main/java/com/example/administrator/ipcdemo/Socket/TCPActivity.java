package com.example.administrator.ipcdemo.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.ipcdemo.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ljingya on 2018/5/13.
 */

public class TCPActivity extends Activity implements View.OnClickListener {
    private Button send;
    private TextView text;
    private EditText edtMsg;
    private PrintWriter printWriter;
    private static final int MSG_SOCKET_CONNETCTED = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SOCKET_CONNETCTED:
                    send.setEnabled(true);
                    break;
                case MESSAGE_RECIVER_NEW_MSG:
                    text.setText(text.getText() + (String) msg.obj);
                    break;
            }
        }
    };
    private static final int MESSAGE_RECIVER_NEW_MSG = 1;
    private Socket mClientSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        send = findViewById(R.id.send);
        text = findViewById(R.id.text);
        edtMsg = findViewById(R.id.edt_msg);
        send.setOnClickListener(this);
        Intent service = new Intent(this, TCPService.class);
        startService(service);
        new Thread() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MSG_SOCKET_CONNETCTED);
                printWriter.println("連接陳工");
                System.out.println("connetct server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                System.out.println("connect tcp server failed retry...");
            }
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TCPActivity.this.isFinishing()) {
                String str = br.readLine();
                System.out.println("reciver:" + str);
                if (str != null) {
                    String time = formateDateTime(System.currentTimeMillis());
                    String showMessage = "server " + time + ":" + str + "\n";
                    mHandler.obtainMessage(MESSAGE_RECIVER_NEW_MSG, showMessage).sendToTarget();
                }
            }
            System.out.println("quit...");
            printWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formateDateTime(long l) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(l));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                String msg = edtMsg.getText().toString();
                if (!TextUtils.isEmpty(msg) & printWriter != null) {
                    printWriter.println(msg);
                    edtMsg.setText("");
                    String time = formateDateTime(System.currentTimeMillis());
                    String showMessage = "server " + time + ":" + msg + "\n";
                    text.setText(text.getText() + msg);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
