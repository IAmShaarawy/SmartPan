package net.elshaarawy.smartpan;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class GooglePlayServicesManager {
    private static GooglePlayServicesManager managerInstance;
    private Activity activity;
    private GoogleApiAvailability apiAvailability;
    private int isAvailable;
    private boolean userResolvableError;


    public GooglePlayServicesManager(Activity activity) {
        this.activity = activity;
        apiAvailability = GoogleApiAvailability.getInstance();
        isAvailable = apiAvailability.isGooglePlayServicesAvailable(activity);
        userResolvableError = apiAvailability.isUserResolvableError(isAvailable);

    }

    public boolean isPlayServicesAvailable() {

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (userResolvableError) {
            apiAvailability.getErrorDialog(activity, isAvailable, 9001).show();
        } else {
            Toast.makeText(activity, "Error Can't Connect", Toast.LENGTH_LONG).show();
        }
        return false;
    }

}
