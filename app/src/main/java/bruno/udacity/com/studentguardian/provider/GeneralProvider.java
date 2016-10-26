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
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("user");

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
        long rowId = db.insert("user", "", contentValues);

        if(rowId > 0){
            Uri returnUri = StudentGuardianContract.UserEntry.buildUserUri(rowId);
            getContext().getContentResolver().notifyChange(returnUri, null);
            return returnUri;
        }

        throw new SQLException("Failed to add record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return db.update("user", contentValues, s, strings);
    }
}
