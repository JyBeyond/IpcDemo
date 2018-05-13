package com.example.administrator.ipcdemo.contentProvide;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ljingya on 2018/5/12.
 */

public class User implements Parcelable {
    public int userId;
    public String name;
    public int sex;

    public User() {
    }

    protected User(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        sex = in.readInt();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeInt(sex);
    }
}
