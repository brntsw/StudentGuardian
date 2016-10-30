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
                    StudentGuardianContract.UserEntry.COLUMN_PROFILE + " INTEGER NOT NULL," +
                    StudentGuardianContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    StudentGuardianContract.UserEntry.COLUMN_DATE_BIRTH + " TEXT NOT NULL," +
                    StudentGuardianContract.UserEntry.COLUMN_LOGGED + " BIT NOT NULL);";

    final String SQL_CREATE_STUDENT_TABLE =
            "CREATE TABLE " + StudentGuardianContract.StudentEntry.TABLE_NAME +
                    " (" +StudentGuardianContract.StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StudentGuardianContract.StudentEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    StudentGuardianContract.StudentEntry.COLUMN_DATE_BIRTH + " TEXT NOT NULL, " +
                    StudentGuardianContract.StudentEntry.COLUMN_ACTIVE + " INTEGER NOT NULL);";

    final String SQL_CREATE_SUBJECT_TABLE =
            " CREATE TABLE " + StudentGuardianContract.SubjectEntry.TABLE_NAME +
                    " (" + StudentGuardianContract.SubjectEntry.COLUMN_CODE + " INTEGER PRIMARY KEY NOT NULL, " +
                    StudentGuardianContract.SubjectEntry.COLUMN_NAME + " TEXT NOT NULL);";

    final String SQL_CREATE_TYPE_EVALUATION_TABLE =
            "CREATE TABLE " + StudentGuardianContract.TypeEvaluationEntry.TABLE_NAME +
                    " (" + StudentGuardianContract.TypeEvaluationEntry.COLUMN_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StudentGuardianContract.TypeEvaluationEntry.COLUMN_TYPE + " TEXT NOT NULL);";

    final String SQL_CREATE_EVALUATION_TABLE =
            "CREATE TABLE " + StudentGuardianContract.EvaluationEntry.TABLE_NAME +
                    " (" + StudentGuardianContract.EvaluationEntry.COLUMN_CODE + " INTEGER PRIMARY KEY NOT NULL, " +
                    StudentGuardianContract.EvaluationEntry.COLUMN_CODE_SUBJECT + " INTEGER NOT NULL, " +
                    StudentGuardianContract.EvaluationEntry.COLUMN_TYPE_EVALUATION + " INTEGER NOT NULL, " +
                    StudentGuardianContract.EvaluationEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    StudentGuardianContract.EvaluationEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                    StudentGuardianContract.EvaluationEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    StudentGuardianContract.EvaluationEntry.COLUMN_GRADE + " DOUBLE NOT NULL);";

    final String SQL_CREATE_ABSENCE_TABLE =
            "CREATE TABLE " + StudentGuardianContract.AbsenceEntry.TABLE_NAME +
                    " (" + StudentGuardianContract.AbsenceEntry.COLUMN_CODE_SUBJECT + " INTEGER PRIMARY KEY NOT NULL, " +
                    StudentGuardianContract.AbsenceEntry.COLUMN_ABSENCES + " INTEGER NOT NULL);";

    final String SQL_CREATE_CONTENT_CLASS_TABLE =
            "CREATE TABLE " + StudentGuardianContract.ContentClassEntry.TABLE_NAME +
                    " (" + StudentGuardianContract.ContentClassEntry.COLUMN_CODE + " INTEGER PRIMARY KEY NOT NULL, " +
                    StudentGuardianContract.ContentClassEntry.COLUMN_CONTENT + " TEXT NOT NULL, "+
                    StudentGuardianContract.ContentClassEntry.COLUMN_DATE + " TEXT NOT NULL);";

    final String SQL_CREATE_NOTE_TABLE =
            "CREATE TABLE " + StudentGuardianContract.NoteEntry.TABLE_NAME +
                    " (" + StudentGuardianContract.NoteEntry.COLUMN_CODE + " INTEGER PRIMARY KEY NOT NULL, " +
                    StudentGuardianContract.NoteEntry.COLUMN_CODE_SUBJECT + " INTEGER NOT NULL, " +
                    StudentGuardianContract.NoteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    StudentGuardianContract.NoteEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    StudentGuardianContract.NoteEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                    StudentGuardianContract.NoteEntry.COLUMN_EVIDENCE_IMAGE + " TEXT NOT NULL, " +
                    StudentGuardianContract.NoteEntry.COLUMN_GRAVITY + " INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STUDENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SUBJECT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TYPE_EVALUATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_EVALUATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ABSENCE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CONTENT_CLASS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StudentGuardianContract.UserEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
