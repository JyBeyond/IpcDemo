package com.example.administrator.ipcdemo.manuabinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.example.administrator.ipcdemo.aidl.Book;

import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */

public interface IBookmanager extends IInterface {
    //binder的唯一标识
    static final java.lang.String DESCRIPTOR = "com.example.administrator.ipcdemo.manuabinder.IBookmanager";

    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public List<Book> getBookList() throws RemoteException;

    public void addBook(Book book) throws RemoteException;

    public static abstract class BookmanagerImpl extends Binder implements IBookmanager {


        public BookmanagerImpl() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public IBookmanager asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof com.example.administrator.ipcdemo.aidl.IBookmanager))) {
                return ((IBookmanager) iin);
            }
            return new BookmanagerImpl.Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<com.example.administrator.ipcdemo.aidl.Book> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(DESCRIPTOR);
                    com.example.administrator.ipcdemo.aidl.Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = com.example.administrator.ipcdemo.aidl.Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }


        public class Proxy implements IBookmanager {
            private IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public List<Book> getBookList() throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.util.List<com.example.administrator.ipcdemo.aidl.Book> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(IBookmanager.BookmanagerImpl.TRANSACTION_getBookList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(com.example.administrator.ipcdemo.aidl.Book.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((book != null)) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(IBookmanager.BookmanagerImpl.TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return null;
        }

        @Override
        public void addBook(Book book) throws RemoteException {

        }


    }
}
