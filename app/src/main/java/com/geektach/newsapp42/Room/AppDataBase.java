package com.geektach.newsapp42.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektach.newsapp42.models.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ArticleDao articleDao();
}
