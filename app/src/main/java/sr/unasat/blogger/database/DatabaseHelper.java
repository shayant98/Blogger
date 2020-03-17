package sr.unasat.blogger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import sr.unasat.blogger.Entity.Student;
import sr.unasat.blogger.Entity.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
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
                userContract.UserEntry.USER_LOGGED_IN + " INTEGER NOT NULL," +
                userContract.UserEntry.USERS_DATE_CREATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                userContract.UserEntry.USERS_DATE_UPDATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                " FOREIGN KEY(" + userContract.UserEntry.USERS_STUDENTS_ID + ") REFERENCES " +
                studentContract.StudentEntry.STUDENTS_ADRESS + "(" + studentContract.StudentEntry.STUDENTS_ID + "));";

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
                studentContract.StudentEntry.STUDENTS_DATE_UPDATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        db.execSQL(SQL_CREATE_STUDENTS_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
        setDummiCredentials(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + studentContract.StudentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + userContract.UserEntry.TABLE_NAME);

        onCreate(db);
    }

    private void setDummiCredentials(SQLiteDatabase db) {
//        Cursor cursor = db.query(userContract.UserEntry.TABLE_NAME, null,userContract.UserEntry.USERS_USERNAME,new String[]{"username"},null,null,null);
        String sql = "SELECT * FROM " + userContract.UserEntry.TABLE_NAME + " WHERE " + userContract.UserEntry.USERS_USERNAME + " = 'SE/1118/021'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() != 0) {
            cursor.close();
            return;
        }

        ContentValues cvStudent = new ContentValues();
        cvStudent.put(studentContract.StudentEntry.STUDENTS_FIRST_NAME, "Shayant");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_NAME, "Sital");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_BIRTHDATE, "01-08-98");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_ADRESS, "Kwattaweg 685");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_DISTRICT, "Wanica");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_STUDENT_NUMBER, "SE/1118/021");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_PHONE_NUMBER, "8789731");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_EMAIL, "sh.sital@unasat.sr");
        long StudentId = db.insert(studentContract.StudentEntry.TABLE_NAME, null, cvStudent);


        //Set default username and password
        ContentValues cvUser = new ContentValues();
        cvUser.put(userContract.UserEntry.USERS_STUDENTS_ID, StudentId);
        cvUser.put(userContract.UserEntry.USERS_USERNAME, "SE/1118/021");
        cvUser.put(userContract.UserEntry.USERS_EMAIL, "sh.sital@unasat.sr");
        cvUser.put(userContract.UserEntry.USERS_ROLE, "student");
        cvUser.put(userContract.UserEntry.USER_LOGGED_IN, 0);
        cvUser.put(userContract.UserEntry.USERS_PASSWORD, "123456");
        db.insert(userContract.UserEntry.TABLE_NAME, null, cvUser);


        //dummy students
        cvStudent.clear();
        cvStudent.put(studentContract.StudentEntry.STUDENTS_FIRST_NAME, "Jason");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_NAME, "Kasi");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_BIRTHDATE, "01-08-98");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_ADRESS, "Kwattaweg 685");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_DISTRICT, "Wanica");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_STUDENT_NUMBER, "SE/1118/022");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_PHONE_NUMBER, "8789731");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_EMAIL, "j.k@unasat.sr");
        db.insert(studentContract.StudentEntry.TABLE_NAME, null, cvStudent);
        cvStudent.clear();
        cvStudent.put(studentContract.StudentEntry.STUDENTS_FIRST_NAME, "Zareef");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_NAME, "Soet");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_BIRTHDATE, "01-08-98");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_ADRESS, "Kwattaweg 685");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_DISTRICT, "Wanica");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_STUDENT_NUMBER, "SE/1118/023");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_PHONE_NUMBER, "8789731");
        cvStudent.put(studentContract.StudentEntry.STUDENTS_EMAIL, "z.s@unasat.sr");
        db.insert(studentContract.StudentEntry.TABLE_NAME, null, cvStudent);

        Log.d(TAG, "setDummiCredentials: Data inserted");

    }

    public User getLoggedInUser() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE logged_in = ?", new String[]{"1"});

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToFirst();
            int userId = cursor.getInt(0);
            return getUser(userId);
        }
    }

    public User logInUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToFirst();

            ContentValues updateValues = new ContentValues();
            updateValues.put("logged_in", 1);
            int userId = cursor.getInt(0);
            updateUser(updateValues, userId);
            return getUser(userId);

        }
    }

    public boolean logOutUser(int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("logged_in", 0);
        return updateUser(contentValues, id);
    }


    private boolean updateUser(ContentValues contentValues, int id) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            int updateQuery = db.update(userContract.UserEntry.TABLE_NAME, contentValues, "id= ?", new String[]{String.valueOf(id)});

            return updateQuery > 0;
        }catch (SQLiteException e){
            return false;
        }
    }

    public boolean deleteUser(int id, String password) {
        SQLiteDatabase db = getWritableDatabase();
        int deleteQuery;
        try {
            deleteQuery = db.delete(userContract.UserEntry.TABLE_NAME, "id=? and password = ?", new String[]{String.valueOf(id), password});
            return deleteQuery > 0;
        }catch (SQLiteException e){
            return false;
        }


    }


    public User getUser(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT users.id,users.email,username,role,name,first_name,birthdate,adress,district,phone_number FROM users, students WHERE users.student_id = students.id AND users.id = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToFirst();
            return new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)

            );
        }
    }

    public boolean insertUser(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();

        long insertQuery = db.insert(userContract.UserEntry.TABLE_NAME, null, contentValues);

        return insertQuery > 0;
    }

    public boolean getUserByStudNr(String studentNr){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ userContract.UserEntry.TABLE_NAME +" WHERE username = ?", new String[]{ studentNr});
        if (cursor.getCount() == 0){
            return false;
        }
        cursor.moveToFirst();
        return true;
    }

    public Student getStudentByStudNr(String studentNr){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ studentContract.StudentEntry.TABLE_NAME +" WHERE student_number = ?", new String[]{ studentNr});
        if (cursor.getCount() == 0){
            return null;
        }
        cursor.moveToFirst();
        return new Student(cursor.getInt(0), cursor.getString(6), cursor.getString(8));
    }

    public boolean updateStudent(ContentValues contentValues){
        SQLiteDatabase db = getWritableDatabase();
        try {
            int updateQuery = db.update(studentContract.StudentEntry.TABLE_NAME, contentValues, "id= ?", new String[]{String.valueOf(1)});
            return updateQuery > 0;
        }catch (SQLiteException e){
            return false;
        }
    }

}
