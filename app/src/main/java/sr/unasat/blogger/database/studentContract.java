package sr.unasat.blogger.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import sr.unasat.blogger.Entity.User;

public class studentContract {

    public studentContract() {

    }


    public static final class StudentEntry implements BaseColumns{
        public static final String TABLE_NAME = "students";
        public static final String STUDENTS_ID = "id";
        public static final String STUDENTS_FIRST_NAME = "first_name";
        public static final String STUDENTS_NAME = "name";
        public static final String STUDENTS_BIRTHDATE = "birthdate";
        public static final String STUDENTS_ADRESS = "adress";
        public static final String STUDENTS_DISTRICT = "district";
        public static final String STUDENTS_STUDENT_NUMBER = "student_number";
        public static final String STUDENTS_PHONE_NUMBER = "phone_number";
        public static final String STUDENTS_EMAIL = "email";
        public static final String STUDENTS_DATE_CREATED = "date_created";
        public static final String STUDENTS_DATE_UPDATED = "date_updated";
    }



}
