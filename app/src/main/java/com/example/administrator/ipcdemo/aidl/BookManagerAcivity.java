package com.example.administrator.ipcdemo.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.ipcdemo.R;
import com.example.administrator.ipcdemo.util.MyConstants;

import java.util.List;


/**
 * Created by ljingya on 2018/5/11.
 */

public class BookManagerAcivity extends Activity {

    private static final String TAG = "BookManagerAcivity";
    private IBookmanager mRemoteBookManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "reciver new book" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookmanager bookmanager = IBookmanager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookmanager;
                List<Book> list = bookmanager.getBookList();
                Log.d(TAG, "query book list ,list type" + list.getClass().getCanonicalName());
                Log.d(TAG, "query book list:" + list.toString());
                Book book = new Book(3, "JAVA");
                bookmanager.addBook(book);
                List<Book> newList = bookmanager.getBookList();
                Log.d(TAG, "query book newList:" + newList.toString());
                bookmanager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
        }
    };


    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MyConstants.MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            Log.d(TAG, "unregister listener:" + mIOnNewBookArrivedListener);
            try {
                mRemoteBookManager.unregisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        unbindService(connection);
        super.onDestroy();
    }
}
