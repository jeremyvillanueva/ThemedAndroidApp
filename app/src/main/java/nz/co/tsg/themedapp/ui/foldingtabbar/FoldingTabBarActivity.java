package nz.co.tsg.themedapp.ui.foldingtabbar;

import android.app.Fragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;

import client.yalantis.com.foldingtabbar.FoldingTabBar;
import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.ui.Branding;
import nz.co.tsg.themedapp.utilities.ColorUtil;

public class FoldingTabBarActivity extends AppCompatActivity {

    ThemedApplication mApp;
    Branding mBranding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folding_tabbar);

        mApp = (ThemedApplication) getApplication();
        mBranding = mApp.getmBranding();

        RelativeLayout activityLayout = (RelativeLayout) findViewById(R.id.activity_folding_tabbar);
        activityLayout.setBackgroundColor(mBranding.getMainBackgroundColor());

        FoldingTabBar tabBar = (FoldingTabBar) findViewById(R.id.folding_tab_bar);
        changeFragment(new ProfileFragment());


        tabBar.setOnFoldingItemClickListener(new FoldingTabBar.OnFoldingItemSelectedListener() {
            @Override
            public boolean onFoldingItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ftb_menu_nearby:
                        break;
                    case R.id.ftb_menu_new_chat:
                        changeFragment(new ChatsFragment());
                        break;
                    case R.id.ftb_menu_profile:
                        changeFragment(new ProfileFragment());
                        break;
                    case R.id.ftb_menu_settings:
                        break;
                }
                return false;
            }
        });

        ImageView searchButton = (ImageView) findViewById(R.id.profileSearchButton);
        GradientDrawable sbBackground = (GradientDrawable) searchButton.getBackground();
        if (ColorUtil.isLuminanceDark(mBranding.getMainBackgroundColor())) {
            sbBackground.setColor(mBranding.getColorLightAccent());
        } else {
            sbBackground.setColor(mBranding.getColorDarkAccent());
        }

        ColorUtil.changeBackgroundDrawableColor(tabBar, mBranding.getColorMainColor());

    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}
