package com.chemander.story.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.chemander.story.data.dao.RecentDao;
import com.chemander.story.data.model.ChapterDetail;
import com.chemander.story.data.model.ChapterInformation;
import com.chemander.story.data.model.StoryInformation;

@Database(entities = {StoryInformation.class, ChapterDetail.class, ChapterInformation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private final static String DATABASE_NAME = "Local";
    public abstract RecentDao recentDao();

    public static AppDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
