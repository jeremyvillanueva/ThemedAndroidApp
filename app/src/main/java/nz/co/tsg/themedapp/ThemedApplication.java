package nz.co.tsg.themedapp;

import android.app.Application;

import nz.co.tsg.themedapp.ui.Branding;

/**
 * Created by Jeremy.V on 4/08/17.
 */

public class ThemedApplication extends Application{

    public Branding getmBranding() {
        return mBranding;
    }

    public void setmBranding(Branding mBranding) {
        this.mBranding = mBranding;
    }

    private Branding mBranding;



}
