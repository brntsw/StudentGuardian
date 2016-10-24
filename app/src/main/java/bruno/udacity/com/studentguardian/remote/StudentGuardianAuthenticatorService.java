package bruno.udacity.com.studentguardian.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by BPardini on 24/10/2016.
 */

public class StudentGuardianAuthenticatorService extends Service {

    private StudentGuardianAuthenticator authenticator;

    public void onCreate(){
        authenticator = new StudentGuardianAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
