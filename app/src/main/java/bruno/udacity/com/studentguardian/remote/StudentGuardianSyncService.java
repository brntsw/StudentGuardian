package bruno.udacity.com.studentguardian.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Bruno on 24/10/2016.
 */
public class StudentGuardianSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static LoginSync loginSync = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (loginSync == null) {
                loginSync = new LoginSync(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return loginSync.getSyncAdapterBinder();
    }

}
