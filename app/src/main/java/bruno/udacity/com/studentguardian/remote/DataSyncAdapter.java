package bruno.udacity.com.studentguardian.remote;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.User;
import bruno.udacity.com.studentguardian.remote.task.JsonReturn;
import bruno.udacity.com.studentguardian.ui.activity.HomeActivity;

/**
 * Created by BPardini on 24/10/2016.
 */

public class DataSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = DataSyncAdapter.class.getSimpleName();
    public static final String ACTION_DATA_UPDATED =
            "bruno.udacity.com.studentguardian.ACTION_DATA_UPDATED";
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    private Context context;
    private static Activity activity;
    private static ProgressDialog progressDialog;
    private static User user;

    ContentResolver contentResolver;

    public DataSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        this.context = context;

        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        JsonReturn jsonReturn;

        try{
            final String LOGIN_URL = "http://brunopardini.com/ws_student_guardian/data.php";

            Uri uri = Uri.parse(LOGIN_URL).buildUpon().build();

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            //set the sending type and receiving type to json
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            urlConnection.connect();

            Reader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            jsonReturn = new JsonReturn(urlConnection.getResponseCode(), response);
            getDataFromJson(jsonReturn);
        } catch (IOException e) {
            jsonReturn = new JsonReturn(999, e.getMessage());
            getDataFromJson(jsonReturn);
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                }
                catch (IOException e){
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    public void getDataFromJson(final JsonReturn result){
        if (result.getReturnStatus() == JsonReturn.SUCCESS_RETURN) {
            new Thread(){
                @Override
                public void run() {
                    super.run();

                    try {
                        JSONObject subjectObj = new JSONObject(result.getResultString());
                        JSONArray subjectsArray = subjectObj.getJSONArray("subjects");

                        for(int i = 0; i < subjectsArray.length(); i++){
                            JSONObject obj = subjectsArray.getJSONObject(i);
                            int code = obj.getInt("code");
                            String name = obj.getString("name");

                            ContentValues subjectValues = new ContentValues();
                            subjectValues.put(StudentGuardianContract.SubjectEntry.COLUMN_CODE, code);
                            subjectValues.put(StudentGuardianContract.SubjectEntry.COLUMN_NAME, name);

                            int rowsAffected = context.getContentResolver().update(StudentGuardianContract.SubjectEntry.CONTENT_URI, subjectValues, "code = ?", new String[]{String.valueOf(code)});

                            if(rowsAffected == 0) {
                                context.getContentResolver().insert(StudentGuardianContract.SubjectEntry.CONTENT_URI, subjectValues);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.run();

            if(DataSyncAdapter.progressDialog != null) {
                DataSyncAdapter.progressDialog.dismiss();

                Intent intent = new Intent(context, HomeActivity.class);
                intent.putExtra("user", user);
                DataSyncAdapter.activity.startActivity(intent);
            }
        }
        else{
            switch (result.getReturnStatus()){
                case JsonReturn.ERROR:
                    Log.d("Response", "Error");
                    break;
                case JsonReturn.NOT_FOUND:
                    Log.d("Response", "Not found");
                    break;
                case JsonReturn.NOT_AVAILABLE:
                    Log.d("Response", "Not available");
                    break;
                default:
                    Log.d("Response", "Error");
                    break;
            }
        }
    }

    public static void initializeSyncAdapter(Activity activity, Context context, User user, ProgressDialog progressDialog) {
        DataSyncAdapter.activity = activity;
        DataSyncAdapter.progressDialog = progressDialog;
        DataSyncAdapter.user = user;

        getSyncAccount(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        DataSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }


}
