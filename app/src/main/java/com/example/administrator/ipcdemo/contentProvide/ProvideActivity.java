package com.example.administrator.ipcdemo.contentProvide;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.ipcdemo.R;
import com.example.administrator.ipcdemo.aidl.Book;

/**
 * Created by ljingya on 2018/5/12.
 */

public class ProvideActivity extends Activity {
    private static final String TAG = "ProvideActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide);
        Uri bookUri = Uri.parse("content://com.example.administrator.ipcdemo.book.provide/book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "python");
        getContentResolver().insert(bookUri, values);
        Cursor mCUrsor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (mCUrsor.moveToNext()) {
            Book book = new Book();
            book.bookId = mCUrsor.getInt(0);
            book.bookName = mCUrsor.getString(1);
            Log.d(TAG, "book:" + book.toString());
        }
        mCUrsor.close();


        Uri userUri = Uri.parse("content://com.example.administrator.ipcdemo.book.provide/user");
        Cursor mUserCussor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (mUserCussor.moveToNext()) {
            User user = new User();
            user.userId = mUserCussor.getInt(0);
            user.name = mUserCussor.getString(1);
            user.sex = mUserCussor.getInt(2);
            Log.d(TAG, "user:" + user.toString());
        }
        mUserCussor.close();
    }
}
