package net.elshaarawy.smartpan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import net.elshaarawy.smartpan.Adapters.ViewPagerAdapter;
import net.elshaarawy.smartpan.R;
import net.elshaarawy.smartpan.Services.CountriesIntentService;
import net.elshaarawy.smartpan.Utils.PreferenceUtil;

import static net.elshaarawy.smartpan.Utils.PreferenceUtil.DefaultKeys.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PreferenceUtil mPreferenceUtil;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferenceUtil = new PreferenceUtil(this, DEFAULT_SHARED_PREFERENCE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Are you sure to logout ?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(v.getContext(), "logout", Toast.LENGTH_LONG).show();
                                mPreferenceUtil.editValue(PREF_IS_AUTHENTICATED, false);
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                MainActivity.this.finish();
                                LoginManager.getInstance().logOut();
                            }
                        }).show();
                break;
        }
    }
}
