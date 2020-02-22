package sr.unasat.blogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import sr.unasat.blogger.database.userContract;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "blogger.db";
    public static int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_STUDENT_TABLE = "";

        final String SQL_CREATE_USER_TABLE = " CREATE TABLE " + userContract.UserEntry.TABLE_NAME +
                " (" +
                userContract.UserEntry.USERS_ID + " INTEGER PRIMARY KEY," +
                userContract.UserEntry.USERS_PERSON_ID + " INTEGER NOT NULL," +
                " FOREIGN KEY(" + userContract.UserEntry.USERS_PERSON_ID + ") REFERENCES" + "Hier_moet foreign table name en (PK)"
                + userContract.UserEntry.USERS_USERNAME + " STRING NOT NULL UNIQUE," + userContract.UserEntry.USERS_EMAIL + " STRING NOT NULL UNIQUE," + userContract.UserEntry.USERS_PASSWORD + " STRING NOT NULL," + userContract.UserEntry.USERS_BIO + " TEXT," + userContract.UserEntry.USERS_ROLE + " STRING NOT NULL," + userContract.UserEntry.USERS_DATE_CREATED + "TEXT," + userContract.UserEntry.USERS_DATE_UPDATED + " TEXT)";



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
