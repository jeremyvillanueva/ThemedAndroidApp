package nz.co.tsg.themedapp.ui.foldingtabbar;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.ui.Branding;
import nz.co.tsg.themedapp.utilities.ColorUtil;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class ProfileFragment extends Fragment {

    ThemedApplication mApp;
    Branding mBranding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mApp = (ThemedApplication) getActivity().getApplication();
        mBranding = mApp.getmBranding();

        CustomFontTextView profileUserName = (CustomFontTextView) view.findViewById(R.id.profile_user_name);
        CustomFontTextView profileAsl = (CustomFontTextView) view.findViewById(R.id.profile_asl);
        CustomFontTextView profileDescription = (CustomFontTextView) view.findViewById(R.id.profile_description);

        int textColorOverMainBackgroundColor = mBranding.getTextColorOverMainBackground();
        if (ColorUtil.isLuminanceDark(mBranding.getMainBackgroundColor())) {
            profileUserName.setTextColor(mBranding.getColorLightShade());
        } else {
            profileUserName.setTextColor(mBranding.getColorDarkShade());
        }
        profileAsl.setTextColor(textColorOverMainBackgroundColor);
        profileDescription.setTextColor(textColorOverMainBackgroundColor);

        return view;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentApiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    animateRevealColorFromCoordinates((ViewGroup) view, right / 2, bottom);
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }
}
