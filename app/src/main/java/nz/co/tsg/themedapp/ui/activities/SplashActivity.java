package nz.co.tsg.themedapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.ui.Branding;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Branding branding = new Branding(this);
        ThemedApplication app = (ThemedApplication) getApplication();
        app.setmBranding(branding);

        Intent intent = new Intent(this, ThemeSampleActivity.class);
        startActivity(intent);
        finish();

    }

}
