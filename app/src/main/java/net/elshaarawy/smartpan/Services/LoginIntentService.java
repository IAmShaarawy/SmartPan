package net.elshaarawy.smartpan.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class LoginIntentService extends IntentService {

    private static final String EXTRA_AUTH_TOKEN = "extra_authtoken";

    public LoginIntentService() {
        super("LoginIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public static void startMe(Context context, Parcelable authToken){
        Intent intent = new Intent(context,LoginIntentService.class);
        intent.putExtra(EXTRA_AUTH_TOKEN,authToken);
        context.startService(intent);
    }
}
