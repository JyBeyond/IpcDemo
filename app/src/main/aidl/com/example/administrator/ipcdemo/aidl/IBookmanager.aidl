// IMyAidlInterface.aidl
package com.example.administrator.ipcdemo.aidl;

// Declare any non-default types here with import statements
import  com.example.administrator.ipcdemo.aidl.Book;
import  com.example.administrator.ipcdemo.aidl.IOnNewBookArrivedListener;
interface IBookmanager{
List<Book>getBookList();
void addBook(in Book book);
void registerListener(IOnNewBookArrivedListener listener);
void unregisterListener(IOnNewBookArrivedListener listener);
}
