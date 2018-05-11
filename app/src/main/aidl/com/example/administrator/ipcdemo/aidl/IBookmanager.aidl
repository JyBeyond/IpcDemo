// IMyAidlInterface.aidl
package com.example.administrator.ipcdemo.aidl;

// Declare any non-default types here with import statements
import  com.example.administrator.ipcdemo.aidl.Book;

interface IBookmanager{
List<Book>getBookList();
void addBook(in Book book);
}
