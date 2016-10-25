package bruno.udacity.com.studentguardian.provider.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import bruno.udacity.com.studentguardian.data.StudentGuardianContract;

/**
 * Created by BPardini on 24/10/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "studentsguardian.db";

    static final int DATABASE_VERSION = 1;

    final String SQL_CREATE_USER_TABLE =
            " CREATE TABLE " + StudentGuardianContract.UserEntry.TABLE_NAME +
                    " (" + StudentGuardianContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StudentGuardianContract.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                    StudentGuardianContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL," +
                    StudentGuardianContract.UserEntry.COLUMN_PROFILE + " INTEGER NOT NULL " +
                    StudentGuardianContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    StudentGuardianContract.UserEntry.COLUMN_DATE_BIRTH + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StudentGuardianContract.UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
