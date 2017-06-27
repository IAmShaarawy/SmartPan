package net.elshaarawy.smartpan.Activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import net.elshaarawy.smartpan.R;
import net.elshaarawy.smartpan.Services.CountriesIntentService;
import net.elshaarawy.smartpan.Utils.PreferenceUtil;

import static net.elshaarawy.smartpan.Utils.PreferenceUtil.DefaultKeys.*;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener,
        FacebookCallback<LoginResult> {

    private static final String BROADCAST_ACTION = "net.elshaarawy.smartpan.Activities.LoginActivity";
    private static final String LOGIN_API = "api";
    private static final String LOGIN_FACEBOOK = "facebook";

    private EditText et_userName, et_password;
    private Button btn_login, btn_register;
    private PreferenceUtil mPreferenceUtil;
    private ProgressDialog mProgressDialog;
    private LocalBroadcastManager mLocalBroadcastManager;
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_userName = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        mPreferenceUtil = new PreferenceUtil(this, DEFAULT_SHARED_PREFERENCE);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Please wait ...");

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        mCallbackManager = CallbackManager.Factory.create();

        mLoginButton = (LoginButton) findViewById(R.id.fb_login);
        mLoginButton.setReadPermissions("email");
        mLoginButton.registerCallback(mCallbackManager, this);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(intent.EXTRA_RESULT_RECEIVER, false);
            String name = intent.getStringExtra(intent.EXTRA_USER);
            if (isSuccess) {
                mPreferenceUtil.editValue(PREF_IS_AUTHENTICATED, true);
                mPreferenceUtil.editValue(PREF_USER_NAME,name);
                Toast.makeText(LoginActivity.this, "welcome, " + name, Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                if (mProgressDialog.isShowing())
                    mProgressDialog.hide();
                Toast.makeText(LoginActivity.this, "Authentication Error", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        mLocalBroadcastManager.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocalBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                register();
                break;
            default:
                throw new IllegalArgumentException("not found id: " + v.getId());
        }
    }

    private void login() {
        //TODO login implementation
        mProgressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CountriesIntentService.startMe(LoginActivity.this,"Demo");
            }
        }, 2000);
    }

    private void register() {
        //TODO Register implementation

    }

    public static void sendBroadCastToMe(Context context, boolean isSuccess,String userName) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, isSuccess);
        intent.putExtra(Intent.EXTRA_USER,userName);
        broadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        String name = Profile.getCurrentProfile().getName();
        mLoginButton.setVisibility(View.INVISIBLE);
        mProgressDialog.show();
        CountriesIntentService.startMe(this,name);

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        sendBroadCastToMe(LoginActivity.this, false,null);
    }
}
