package bruno.udacity.com.studentguardian.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
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

import java.util.HashMap;

import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.provider.sqlite.DatabaseHelper;

/**
 * Created by BPardini on 21/10/2016.
 */

public class GeneralProvider extends ContentProvider {
    private SQLiteDatabase db;

    static final int USER = 1;
    static final int SUBJECT = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, "user", USER);
        uriMatcher.addURI(StudentGuardianContract.CONTENT_AUTHORITY, "subject", SUBJECT);
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
            case SUBJECT:
                qb.setTables(StudentGuardianContract.SubjectEntry.TABLE_NAME);
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
            case SUBJECT:
                return StudentGuardianContract.SubjectEntry.CONTENT_TYPE;
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
            case SUBJECT:
                rowId = db.insert(StudentGuardianContract.SubjectEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = StudentGuardianContract.SubjectEntry.buildSubjectUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
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
            case SUBJECT:
                return db.update(StudentGuardianContract.SubjectEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            default:
                return 0;
        }
    }
}
