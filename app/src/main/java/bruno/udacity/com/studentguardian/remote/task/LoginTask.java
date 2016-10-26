package bruno.udacity.com.studentguardian.remote.task;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;

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

import bruno.udacity.com.studentguardian.data.StudentGuardianContract;
import bruno.udacity.com.studentguardian.model.User;
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
    protected JsonReturn doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        JsonReturn jsonReturn;

        try{
            final String LOGIN_URL = "http://brunopardini.com/ws_student_guardian/login.php";

            Uri uri = Uri.parse(LOGIN_URL).buildUpon().build();

            URL url = new URL(uri.toString());

            JSONObject objParams = new JSONObject();
            objParams.put("email", user.getEmail());
            objParams.put("password", user.getPassword());

            urlConnection = (HttpURLConnection) url.openConnection();
            //set the sending type and receiving type to json
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            //send the json as body of the request
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(objParams.toString().getBytes("UTF-8"));
            outputStream.close();

            urlConnection.connect();

            Reader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            jsonReturn = new JsonReturn(urlConnection.getResponseCode(), response);
        } catch (IOException | JSONException e) {
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
                    userValues.put(StudentGuardianContract.UserEntry.COLUMN_LOGGED, 1);

                    context.getContentResolver().insert(StudentGuardianContract.UserEntry.CONTENT_URI, userValues);

                    //Call the SyncAdapter to get all data from network and save it in ContentProvider
                    // -->> DataSyncAdapter.initializeSyncAdapter(this);
                    //- Change the name to DataSyncAdapter (LoginSync)
                    //- Implement the DataSync onPerformSync to call a url and receive the data from ws (almost like LoginTask)
                    //- Save the data in ContentProvider inside onPerformSync (Look to SunshineWear as example [SunshineSyncAdapter])
                    //- After saving all the data in ContentProvider, implement a callback function to be called here and then instantiate the User and
                        //pass the intent to the HomeActivity, which code is below

                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(user.getPassword());
                    user.setDateBirth(dateBirth);
                    user.setName(name);

                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("user", user);
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
