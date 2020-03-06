package sr.unasat.blogger.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import sr.unasat.blogger.Entity.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "blogger.db";
    public static int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERS_TABLE = " CREATE TABLE " + userContract.UserEntry.TABLE_NAME + " (" +
                userContract.UserEntry.USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                userContract.UserEntry.USERS_STUDENTS_ID + " INTEGER NOT NULL," +
                userContract.UserEntry.USERS_USERNAME + " TEXT NOT NULL UNIQUE," +
                userContract.UserEntry.USERS_EMAIL + " TEXT NOT NULL UNIQUE," +
                userContract.UserEntry.USERS_PASSWORD + " TEXT NOT NULL," +
                userContract.UserEntry.USERS_ROLE + " TEXT NOT NULL," +
                userContract.UserEntry.USERS_DATE_CREATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                userContract.UserEntry.USERS_DATE_UPDATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                " FOREIGN KEY(" + userContract.UserEntry.USERS_STUDENTS_ID + ") REFERENCES " +
                studentContract.StudentEntry.STUDENTS_ADRESS + "(" + studentContract.StudentEntry.STUDENTS_ID + "));"
        ;

        final String SQL_CREATE_STUDENTS_TABLE = " CREATE TABLE " + studentContract.StudentEntry.TABLE_NAME + " (" +
                studentContract.StudentEntry.STUDENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                studentContract.StudentEntry.STUDENTS_FIRST_NAME + " TEXT NOT NULL," +
                studentContract.StudentEntry.STUDENTS_NAME + " TEXT NOT NULL," +
                studentContract.StudentEntry.STUDENTS_BIRTHDATE + " DATE," +
                studentContract.StudentEntry.STUDENTS_ADRESS + " TEXT NOT NULL," +
                studentContract.StudentEntry.STUDENTS_DISTRICT + " TEXT NOT NULL," +
                studentContract.StudentEntry.STUDENTS_STUDENT_NUMBER + " TEXT NOT NULL," +
                studentContract.StudentEntry.STUDENTS_PHONE_NUMBER + " TEXT NOT NULL," +
                studentContract.StudentEntry.STUDENTS_EMAIL + " TEXT NOT NULL," +
                studentContract.StudentEntry.STUDENTS_DATE_CREATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                studentContract.StudentEntry.STUDENTS_DATE_UPDATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);"
        ;

        db.execSQL(SQL_CREATE_STUDENTS_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + studentContract.StudentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + userContract.UserEntry.TABLE_NAME);
    }

    private void setDummiCredentials(SQLiteDatabase db) {
//        Cursor cursor = db.query(userContract.UserEntry.TABLE_NAME, null,userContract.UserEntry.USERS_USERNAME,new String[]{"username"},null,null,null);
//        Ik weet niet als dit werkt ^
        String sql= "SELECT * FROM " + userContract.UserEntry.TABLE_NAME + " WHERE " + userContract.UserEntry.USERS_USERNAME + " = 'ssital'";
        Cursor cursor = db.rawQuery(sql,null);
        if ( cursor!= null) {
            return;
        }
        //Set default username and password
        ContentValues contentValues = new ContentValues();
        contentValues.put(userContract.UserEntry.USERS_USERNAME, "ssital");
        contentValues.put(userContract.UserEntry.USERS_PASSWORD, "qwerty");
        db.insert(userContract.UserEntry.TABLE_NAME, null, contentValues);
    }

}
