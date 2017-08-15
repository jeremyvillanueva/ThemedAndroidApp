package nz.co.tsg.themedapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.ui.Branding;

import com.microsoft.azure.mobile.MobileCenter;
import com.microsoft.azure.mobile.analytics.Analytics;
import com.microsoft.azure.mobile.crashes.Crashes;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileCenter.start(getApplication(), "c2ff57ed-55b1-48f9-886c-85371ca57d59",
                Analytics.class, Crashes.class);

        Branding branding = new Branding(this);
        ThemedApplication app = (ThemedApplication) getApplication();
        app.setmBranding(branding);

        Intent intent = new Intent(this, ThemeSampleActivity.class);
        startActivity(intent);
        finish();

    }

}
