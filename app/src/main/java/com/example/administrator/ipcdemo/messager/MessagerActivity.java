package com.example.administrator.ipcdemo.messager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.ipcdemo.R;
import com.example.administrator.ipcdemo.util.MyConstants;


/**
 * Created by Administrator on 2018/5/11.
 */

public class MessagerActivity extends AppCompatActivity {

    private Messenger mReplyMessager = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVER:
                    Log.d(TAG, "recive msg from server" + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }


    private static final String TAG = "MessagerActivity";
    private Messenger mService;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "hello,this is clent");
            msg.setData(data);
            msg.replyTo = mReplyMessager;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesager);
        Intent mIntent = new Intent(this, MessagerService.class);
        bindService(mIntent, con, Context.BIND_AUTO_CREATE);

    }
}
