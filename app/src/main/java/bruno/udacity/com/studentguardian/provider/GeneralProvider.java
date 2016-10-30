package bruno.udacity.com.studentguardian.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.provider.sqlite.DatabaseHelper;

/**
 * Created by BPardini on 21/10/2016.
 */

public class GeneralProvider extends ContentProvider {
    private SQLiteDatabase db;

    static final int USER = 1;
    static final int STUDENT = 2;
    static final int SUBJECT = 3;
    static final int TYPE_EVALUATION = 4;
    static final int EVALUATION = 5;
    static final int ABSENCE = 6;
    static final int CONTENT_CLASS = 7;
    static final int NOTE = 8;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_USER, USER);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_STUDENT, STUDENT);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_SUBJECT, SUBJECT);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_TYPE_EVALUATION, TYPE_EVALUATION);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_EVALUATION, EVALUATION);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_ABSENCE, ABSENCE);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_CONTENT_CLASS, CONTENT_CLASS);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, StudentGuardianContract.PATH_NOTE, NOTE);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = uriMatcher.match(uri);

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (match){
            case USER:
                qb.setTables(StudentGuardianContract.UserEntry.TABLE_NAME);
                break;
            case STUDENT:
                qb.setTables(StudentGuardianContract.StudentEntry.TABLE_NAME);
                break;
            case SUBJECT:
                qb.setTables(StudentGuardianContract.SubjectEntry.TABLE_NAME);
                break;
            case TYPE_EVALUATION:
                qb.setTables(StudentGuardianContract.TypeEvaluationEntry.TABLE_NAME);
                break;
            case EVALUATION:
                qb.setTables(StudentGuardianContract.EvaluationEntry.TABLE_NAME);
                break;
            case ABSENCE:
                qb.setTables(StudentGuardianContract.AbsenceEntry.TABLE_NAME);
                break;
            case CONTENT_CLASS:
                qb.setTables(StudentGuardianContract.ContentClassEntry.TABLE_NAME);
                break;
            case NOTE:
                qb.setTables(StudentGuardianContract.NoteEntry.TABLE_NAME);
                break;
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match){
            case USER:
                return StudentGuardianContract.UserEntry.CONTENT_TYPE;
            case STUDENT:
                return StudentGuardianContract.StudentEntry.CONTENT_TYPE;
            case SUBJECT:
                return StudentGuardianContract.SubjectEntry.CONTENT_TYPE;
            case TYPE_EVALUATION:
                return StudentGuardianContract.TypeEvaluationEntry.CONTENT_TYPE;
            case EVALUATION:
                return StudentGuardianContract.EvaluationEntry.CONTENT_TYPE;
            case ABSENCE:
                return StudentGuardianContract.AbsenceEntry.CONTENT_TYPE;
            case CONTENT_CLASS:
                return StudentGuardianContract.ContentClassEntry.CONTENT_TYPE;
            case NOTE:
                return StudentGuardianContract.NoteEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        int match = uriMatcher.match(uri);

        long rowId;

        switch (match){
            case USER:
                rowId = db.insert(StudentGuardianContract.UserEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.UserEntry.buildUserUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case STUDENT:
                rowId = db.insert(StudentGuardianContract.StudentEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.StudentEntry.buildStudentUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case SUBJECT:
                rowId = db.insert(StudentGuardianContract.SubjectEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.SubjectEntry.buildSubjectUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case TYPE_EVALUATION:
                rowId = db.insert(StudentGuardianContract.TypeEvaluationEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.TypeEvaluationEntry.buildTypeEvaluationUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case EVALUATION:
                rowId = db.insert(StudentGuardianContract.EvaluationEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.EvaluationEntry.buildEvaluationUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case ABSENCE:
                rowId = db.insert(StudentGuardianContract.AbsenceEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.AbsenceEntry.buildAbsenceUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case CONTENT_CLASS:
                rowId = db.insert(StudentGuardianContract.ContentClassEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.ContentClassEntry.buildContentClassUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case NOTE:
                rowId = db.insert(StudentGuardianContract.NoteEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.NoteEntry.buildNoteUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
        }

        throw new SQLException("Failed to add record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);

        switch (match){
            case USER:
                return db.update(StudentGuardianContract.UserEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            case STUDENT:
                return db.update(StudentGuardianContract.StudentEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            case SUBJECT:
                return db.update(StudentGuardianContract.SubjectEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            case TYPE_EVALUATION:
                return db.update(StudentGuardianContract.TypeEvaluationEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            case EVALUATION:
                return db.update(StudentGuardianContract.EvaluationEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            case ABSENCE:
                return db.update(StudentGuardianContract.AbsenceEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            case CONTENT_CLASS:
                return db.update(StudentGuardianContract.ContentClassEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            case NOTE:
                return db.update(StudentGuardianContract.NoteEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            default:
                return 0;
        }
    }
}
