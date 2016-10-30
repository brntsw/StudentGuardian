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
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                        JSONArray jsonArrayData = new JSONArray(result.getResultString());

                        //Student
                        JSONObject jsonStudent = jsonArrayData.getJSONObject(0);
                        JSONObject student = jsonStudent.getJSONObject("student");
                        String name = student.getString("name");
                        String dateBirth = student.getString("dateBirth");
                        int active = student.getInt("active");

                        ContentValues studentValues = new ContentValues();
                        studentValues.put(StudentGuardianContract.StudentEntry.COLUMN_NAME, name);
                        studentValues.put(StudentGuardianContract.StudentEntry.COLUMN_DATE_BIRTH, dateBirth);
                        studentValues.put(StudentGuardianContract.StudentEntry.COLUMN_ACTIVE, active);

                        context.getContentResolver().insert(StudentGuardianContract.StudentEntry.CONTENT_URI, studentValues);

                        for(int i = 1; i < jsonArrayData.length(); i++){
                            if(jsonArrayData.getJSONObject(i).has("code")) {
                                //Subject
                                JSONObject jsonSubject = jsonArrayData.getJSONObject(i);
                                int codeSubject = Integer.valueOf(jsonSubject.getString("code"));
                                String subjectName = jsonSubject.getString("name");

                                ContentValues subjectValues = new ContentValues();
                                subjectValues.put(StudentGuardianContract.SubjectEntry.COLUMN_CODE, codeSubject);
                                subjectValues.put(StudentGuardianContract.SubjectEntry.COLUMN_NAME, subjectName);

                                int rowsAffected = context.getContentResolver().update(StudentGuardianContract.SubjectEntry.CONTENT_URI, subjectValues, "code = ?", new String[]{String.valueOf(codeSubject)});

                                if (rowsAffected == 0) {
                                    context.getContentResolver().insert(StudentGuardianContract.SubjectEntry.CONTENT_URI, subjectValues);
                                }

                                //Evaluation
                                JSONObject objEvaluation = jsonSubject.getJSONObject("0");
                                JSONArray jsonArrayEvaluations = objEvaluation.getJSONArray("evaluations");
                                JSONArray evaluations = jsonArrayEvaluations.getJSONArray(0);

                                for (int j = 0; j < evaluations.length(); j++) {
                                    JSONObject evaluation = evaluations.getJSONObject(j);
                                    int code = Integer.valueOf(evaluation.getString("id"));
                                    int codeTypeEvaluation = Integer.valueOf(evaluation.getString("code_type_evaluation"));
                                    String evaluationName = evaluation.getString("name");
                                    String description = evaluation.getString("description");
                                    String date = evaluation.getString("date");
                                    double grade = Double.valueOf(evaluation.getString("grade"));

                                    ContentValues evaluationValues = new ContentValues();
                                    evaluationValues.put(StudentGuardianContract.EvaluationEntry.COLUMN_CODE, code);
                                    evaluationValues.put(StudentGuardianContract.EvaluationEntry.COLUMN_CODE_SUBJECT, codeSubject);
                                    evaluationValues.put(StudentGuardianContract.EvaluationEntry.COLUMN_NAME, evaluationName);
                                    evaluationValues.put(StudentGuardianContract.EvaluationEntry.COLUMN_TYPE_EVALUATION, codeTypeEvaluation);
                                    evaluationValues.put(StudentGuardianContract.EvaluationEntry.COLUMN_DESCRIPTION, description);
                                    evaluationValues.put(StudentGuardianContract.EvaluationEntry.COLUMN_DATE, date);
                                    evaluationValues.put(StudentGuardianContract.EvaluationEntry.COLUMN_GRADE, grade);

                                    rowsAffected = context.getContentResolver().update(StudentGuardianContract.EvaluationEntry.CONTENT_URI, evaluationValues, "code = ?", new String[]{String.valueOf(code)});

                                    if (rowsAffected == 0) {
                                        context.getContentResolver().insert(StudentGuardianContract.EvaluationEntry.CONTENT_URI, evaluationValues);
                                    }
                                }

                                //Absence
                                JSONObject objAbsence = jsonSubject.getJSONObject("1");
                                int absenceCount = Integer.valueOf(objAbsence.getString("absenceCount"));

                                ContentValues absenceValues = new ContentValues();
                                absenceValues.put(StudentGuardianContract.AbsenceEntry.COLUMN_CODE_SUBJECT, codeSubject);
                                absenceValues.put(StudentGuardianContract.AbsenceEntry.COLUMN_ABSENCES, absenceCount);

                                rowsAffected = context.getContentResolver().update(StudentGuardianContract.AbsenceEntry.CONTENT_URI, absenceValues, "code_subject = ?", new String[]{String.valueOf(codeSubject)});

                                if (rowsAffected == 0) {
                                    context.getContentResolver().insert(StudentGuardianContract.AbsenceEntry.CONTENT_URI, absenceValues);
                                }

                                //Content class
                                JSONObject objContentClass = jsonSubject.getJSONObject("2");
                                JSONArray jsonArrayContentClasses = objContentClass.getJSONArray("contentClasses");
                                JSONArray contentClasses = jsonArrayContentClasses.getJSONArray(0);

                                for (int j = 0; j < contentClasses.length(); j++) {
                                    JSONObject contentClass = contentClasses.getJSONObject(j);
                                    int code = Integer.valueOf(contentClass.getString("id"));
                                    String content = contentClass.getString("content");
                                    String date = contentClass.getString("date");

                                    ContentValues contentClassValues = new ContentValues();
                                    contentClassValues.put(StudentGuardianContract.ContentClassEntry.COLUMN_CODE, code);
                                    contentClassValues.put(StudentGuardianContract.ContentClassEntry.COLUMN_CONTENT, content);
                                    contentClassValues.put(StudentGuardianContract.ContentClassEntry.COLUMN_DATE, date);

                                    rowsAffected = context.getContentResolver().update(StudentGuardianContract.ContentClassEntry.CONTENT_URI, contentClassValues, "code = ?", new String[]{String.valueOf(code)});

                                    if (rowsAffected == 0) {
                                        context.getContentResolver().insert(StudentGuardianContract.ContentClassEntry.CONTENT_URI, contentClassValues);
                                    }
                                }
                            }
                        }

                        for(int i = 0; i < jsonArrayData.length(); i++){
                            if(jsonArrayData.getJSONObject(i).has("type_evaluations")){ //TypeEvaluations
                                JSONArray typeEvaluations = jsonArrayData.getJSONObject(i).getJSONArray("type_evaluations");
                                for(int j = 0; j < typeEvaluations.length(); j++){
                                    int rowsAffected = 0;

                                    JSONObject typeEvaluation = typeEvaluations.getJSONObject(j);
                                    int code = Integer.valueOf(typeEvaluation.getString("id"));
                                    String type = typeEvaluation.getString("type");

                                    ContentValues typeEvaluationValues = new ContentValues();
                                    typeEvaluationValues.put(StudentGuardianContract.TypeEvaluationEntry.COLUMN_CODE, code);
                                    typeEvaluationValues.put(StudentGuardianContract.TypeEvaluationEntry.COLUMN_TYPE, type);

                                    rowsAffected = context.getContentResolver().update(StudentGuardianContract.TypeEvaluationEntry.CONTENT_URI, typeEvaluationValues, "code = ?", new String[]{String.valueOf(code)});

                                    if(rowsAffected == 0) {
                                        context.getContentResolver().insert(StudentGuardianContract.TypeEvaluationEntry.CONTENT_URI, typeEvaluationValues);
                                    }
                                }
                            }
                            else if(jsonArrayData.getJSONObject(i).has("notes")){ //Notes
                                JSONArray notes = jsonArrayData.getJSONObject(i).getJSONArray("notes");
                                for(int j = 0; j < notes.length(); j++){
                                    int rowsAffected = 0;

                                    JSONObject note = notes.getJSONObject(j);
                                    final int code = Integer.valueOf(note.getString("id"));
                                    int codeSubject = Integer.valueOf(note.getString("code_subject"));
                                    String title = note.getString("title");
                                    String description = note.getString("description");
                                    String date = note.getString("date");
                                    String imageEvidence = note.getString("image_evidence");

                                    int gravity = Integer.valueOf(note.getString("gravity"));

                                    int colorGravity = 0;

                                    switch (gravity){
                                        case 1:
                                            colorGravity = R.color.gravity_urgent;
                                            break;
                                        case 2:
                                            colorGravity = R.color.gravity_alert;
                                            break;
                                        case 3:
                                            colorGravity = R.color.gravity_not_serious;
                                            break;
                                    }

                                    ContentValues noteValues = new ContentValues();
                                    noteValues.put(StudentGuardianContract.NoteEntry.COLUMN_CODE, code);
                                    noteValues.put(StudentGuardianContract.NoteEntry.COLUMN_CODE_SUBJECT, codeSubject);
                                    noteValues.put(StudentGuardianContract.NoteEntry.COLUMN_TITLE, title);
                                    noteValues.put(StudentGuardianContract.NoteEntry.COLUMN_DESCRIPTION, description);
                                    noteValues.put(StudentGuardianContract.NoteEntry.COLUMN_DATE, date);
                                    noteValues.put(StudentGuardianContract.NoteEntry.COLUMN_EVIDENCE_IMAGE, imageEvidence);
                                    noteValues.put(StudentGuardianContract.NoteEntry.COLUMN_GRAVITY, colorGravity);

                                    rowsAffected = context.getContentResolver().update(StudentGuardianContract.NoteEntry.CONTENT_URI, noteValues, "code = ?", new String[]{String.valueOf(code)});

                                    if(rowsAffected == 0) {
                                        context.getContentResolver().insert(StudentGuardianContract.NoteEntry.CONTENT_URI, noteValues);
                                    }
                                }
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
