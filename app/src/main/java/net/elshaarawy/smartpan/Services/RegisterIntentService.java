package net.elshaarawy.smartpan.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class RegisterIntentService extends IntentService {

    private static final String EXTRA_REGISTER_TOKEN = "extra_register_token";

    public RegisterIntentService() {
        super("RegisterIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
    public static void startMe(Context context, Parcelable registerToken){
        Intent intent = new Intent(context,RegisterIntentService.class);
        intent.putExtra(EXTRA_REGISTER_TOKEN,registerToken);
        context.startService(intent);
    }
}
