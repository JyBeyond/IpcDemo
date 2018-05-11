// IMyAidlInterface.aidl
package com.example.administrator.ipcdemo.aidl;

// Declare any non-default types here with import statements
import  com.example.administrator.ipcdemo.aidl.Book;

interface IOnNewBookArrivedListener{
void onNewBookArrived(in Book newBook);
}
