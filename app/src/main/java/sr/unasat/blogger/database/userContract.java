package sr.unasat.blogger.database;

import android.provider.BaseColumns;

public class userContract {
    public userContract() {
    }

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String USERS_ID = "id";
        public static final String USERS_PERSON_ID = "person_id"; //Fk op persons tabel
        public static final String USERS_USERNAME = "username";
        public static final String USERS_EMAIL = "email";
        public static final String USERS_PASSWORD = "password";
        public static final String USERS_BIO = "bio";
        public static final String USERS_ROLE = "role";
        public static final String USERS_DATE_CREATED = "date_created";
        public static final String USERS_DATE_UPDATED = "date_updated";

//        TODO: ADD COLUMNS
    }
}
