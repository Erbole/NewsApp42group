package com.geektach.newsapp42;

import android.app.Application;

import androidx.room.Room;

import com.geektach.newsapp42.Room.AppDataBase;

public class App extends Application {

    private static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room
                .databaseBuilder(this, AppDataBase.class, "myDatabase")
                .allowMainThreadQueries()
                .build();
    }

    public static AppDataBase getDataBase() {
        return dataBase;
    }
}
