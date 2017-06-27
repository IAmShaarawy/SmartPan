package net.elshaarawy.smartpan.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.elshaarawy.smartpan.Utils.KeyHashesUtil;
import net.elshaarawy.smartpan.Utils.PreferenceUtil;
import static  net.elshaarawy.smartpan.Utils.PreferenceUtil.DefaultKeys.*;
public class SplashActivity extends AppCompatActivity {

    private PreferenceUtil mPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get key Hashes
//        new KeyHashesUtil().getKey(this);

        mPreferenceUtil = new PreferenceUtil(this, DEFAULT_SHARED_PREFERENCE);

        if (mPreferenceUtil.getBoolean(PREF_IS_AUTHENTICATED)){

            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                            SplashActivity.this.finish();
                        }
                    },
                    700
            );
        }else {
            startActivity(new Intent(this,LoginActivity.class));
            this.finish();
        }
    }
}
