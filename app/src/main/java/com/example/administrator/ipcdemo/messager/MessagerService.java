package com.example.administrator.ipcdemo.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.ipcdemo.util.MyConstants;

/**
 * Created by Administrator on 2018/5/11.
 */

public class MessagerService extends Service {

    private static final String TAG = "MessagerService";

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.d(TAG, "reciver  msg  from  Client" + msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message replyMsg = Message.obtain(null, MyConstants.MSG_FROM_SERVER);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","收到");
                    replyMsg.setData(bundle);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }


    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
