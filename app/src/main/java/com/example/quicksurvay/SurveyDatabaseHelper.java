package com.example.quicksurvay;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SurveyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "survey.db";
    private static final int DATABASE_VERSION = 1;

    public SurveyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables for surveys, questions, options, and responses
        db.execSQL("CREATE TABLE surveys (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE questions (id INTEGER PRIMARY KEY AUTOINCREMENT, survey_id INTEGER, question_text TEXT)");
        db.execSQL("CREATE TABLE options (id INTEGER PRIMARY KEY AUTOINCREMENT, question_id INTEGER, option_text TEXT)");
        db.execSQL("CREATE TABLE responses (id INTEGER PRIMARY KEY AUTOINCREMENT, question_id INTEGER, response_text TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if the database is upgraded
        db.execSQL("DROP TABLE IF EXISTS surveys");
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS options");
        db.execSQL("DROP TABLE IF EXISTS responses");
        onCreate(db);
    }

    // Method to delete all data from the database
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM surveys");
        db.execSQL("DELETE FROM questions");
        db.execSQL("DELETE FROM options");
        db.execSQL("DELETE FROM responses");
        db.close();
    }
}

