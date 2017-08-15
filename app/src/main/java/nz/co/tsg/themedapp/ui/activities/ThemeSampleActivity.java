package nz.co.tsg.themedapp.ui.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.ui.Branding;
import nz.co.tsg.themedapp.ui.colorpicker.ColorPickerActivity;
import nz.co.tsg.themedapp.ui.foldinglist.FoldingListActivity;
import nz.co.tsg.themedapp.ui.foldingtabbar.FoldingTabBarActivity;

public class ThemeSampleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Branding mBranding;
    ThemedApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mApp = (ThemedApplication) getApplication();
        mBranding = mApp.getmBranding();

        setContentView(R.layout.activity_theme_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setBackgroundColor(mBranding.getMainBackgroundColor());

        int textColorOverMainColor = mBranding.getTextColorOverMainColor();

        toolbar.setTitleTextColor(textColorOverMainColor);
        toolbar.setBackgroundColor(mBranding.getColorMainColor());
        Drawable navIcon = getDrawable(R.drawable.menu_icon);
        if (navIcon != null) {
            navIcon.setTint(Color.BLACK);
            navIcon.setAlpha(255);
            navIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
            toolbar.setNavigationIcon(navIcon);
            Drawable overflowIconDrawable = toolbar.getOverflowIcon();
            if (overflowIconDrawable != null) {
                overflowIconDrawable.setTint(textColorOverMainColor);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setBackgroundTintList(ColorStateList.valueOf(mBranding.getColorMainColor()));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(mBranding.getMainBackgroundColor());

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked},
                new int[] { -android.R.attr.state_checked}  // pressed
        };

        int[] colors = new int[] {
                mBranding.getColorMainColor(),
                mBranding.getTextColorOverMainBackground()
        };

        ColorStateList navigationTextColorStateList = new ColorStateList(states, colors);
        navigationView.setItemTextColor(navigationTextColorStateList);
        navigationView.setItemIconTintList(navigationTextColorStateList);

        View headerLayout = navigationView.getHeaderView(0);
        headerLayout.setBackgroundColor(mBranding.getColorMainColor());
        TextView textView = (TextView) headerLayout.findViewById(R.id.navHeaderText1);
        textView.setTextColor(textColorOverMainColor);
        textView = (TextView) headerLayout.findViewById(R.id.navHeaderText2);
        textView.setTextColor(textColorOverMainColor);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, ColorPickerActivity.class), 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
        startActivity(getIntent());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_chat) {
            startActivity(new Intent(this, FoldingTabBarActivity.class));
       } else if (id == R.id.nav_folding_list) {
            startActivity(new Intent(this, FoldingListActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
