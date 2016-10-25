package bruno.udacity.com.studentguardian.task;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.User;
import bruno.udacity.com.studentguardian.provider.UserProvider;
import bruno.udacity.com.studentguardian.ui.activity.HomeActivity;

/**
 * Created by BPardini on 25/10/2016.
 */

public class LoginTask extends AsyncTask<Void, Void, JsonReturn> {
    public static final String LOG_TAG = "LoginTask";

    private Context context;
    private User user;
    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog progressDialog;

    public LoginTask(Context context, User user, CoordinatorLayout coordinatorLayout){
        this.context = context;
        this.user = user;
        this.coordinatorLayout = coordinatorLayout;
    }

    protected void onPreExecute(){
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Wait, authenticating user...");
        progressDialog.show();
    }

    @Override
    protected JsonReturn doInBackground(Void... param) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        JsonReturn jsonReturn;

        try{
            final String LOGIN_URL = "http://brunopardini.com/ws_student_guardian/login.php";

            Uri uri = Uri.parse(LOGIN_URL).buildUpon().build();

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", user.getEmail())
                    .appendQueryParameter("password", user.getPassword());
            String query = builder.build().getEncodedQuery();

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            jsonReturn = new JsonReturn(urlConnection.getResponseCode(), buffer.toString());
        } catch (IOException e) {
            jsonReturn = new JsonReturn(999, e.getMessage());
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

        return jsonReturn;
    }

    protected void onPostExecute(JsonReturn result){
        progressDialog.dismiss();

        if (result.getReturnStatus() == JsonReturn.SUCCESS_RETURN) {
            try{
                JSONArray arrayUser = new JSONArray(result.getResultString());
                JSONObject userJson = arrayUser.getJSONObject(0);

                String email = userJson.getString("email");

                if(!email.equals("")){
                    String name = userJson.getString("name");
                    int profile = userJson.getInt("profile");
                    String dateBirth = userJson.getString("dateBirth");

                    ContentValues userValues = new ContentValues();
                    userValues.put(StudentGuardianContract.UserEntry.COLUMN_EMAIL, email);
                    userValues.put(StudentGuardianContract.UserEntry.COLUMN_PASSWORD, user.getPassword());
                    userValues.put(StudentGuardianContract.UserEntry.COLUMN_NAME, name);
                    userValues.put(StudentGuardianContract.UserEntry.COLUMN_PROFILE, profile);
                    userValues.put(StudentGuardianContract.UserEntry.COLUMN_DATE_BIRTH, dateBirth);

                    UserProvider userProvider = new UserProvider();
                    userProvider.insert(StudentGuardianContract.UserEntry.CONTENT_URI, userValues);

                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                }
                else{
                    Log.d("Response", "Not found");
                    Snackbar.make(coordinatorLayout, "User not found", Snackbar.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e){
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        }
        else{
            switch (result.getReturnStatus()){
                case JsonReturn.ERROR:
                    Log.d("Response", "Error");
                    Snackbar.make(coordinatorLayout, "An error has occurred. Try again later", Snackbar.LENGTH_SHORT).show();
                    break;
                case JsonReturn.NOT_FOUND:
                    Log.d("Response", "Not found");
                    Snackbar.make(coordinatorLayout, "User not found", Snackbar.LENGTH_SHORT).show();
                    break;
                case JsonReturn.NOT_AVAILABLE:
                    Log.d("Response", "Not available");
                    Snackbar.make(coordinatorLayout, "The service is not available at this moment", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d("Response", "Error");
                    Snackbar.make(coordinatorLayout, "An error has occurred. Try again later", Snackbar.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
