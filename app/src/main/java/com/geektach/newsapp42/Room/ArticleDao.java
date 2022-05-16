package com.geektach.newsapp42.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.geektach.newsapp42.models.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM article ORDER BY date DESC")
    List<Article> getAll();

    @Insert
    void insert(Article article);

    @Query("SELECT * FROM article WHERE text LIKE '%' || :search || '%'")
    List<Article> getSearch(String search);

    @Query("SELECT * FROM article ORDER BY date DESC")
    List<Article> sort();
}
