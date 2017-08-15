package nz.co.tsg.themedapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.utilities.ColorUtil;

/**
 * Created by Jeremy.V on 4/08/17.
 */

public class Branding {

    Context mContext;

    int mColorLightShade;
    int mColorLightAccent;
    int mColorMainColor;
    int mColorDarkAccent;
    int mColorDarkShade;
    int mMainBackgroundColor;
    boolean mAutoTheme = true;
    boolean mManualDark = true;


    boolean mDarkTheme = true;

    public boolean isAutoTheme() {
        return mAutoTheme;
    }

    public void setAutoTheme(boolean mAutoTheme) {
        this.mAutoTheme = mAutoTheme;
    }
    public boolean isDarkTheme() {
        return mDarkTheme;
    }

    public boolean isManualDark() {
        return mManualDark;
    }

    public void setManualDark(boolean mManualDark) {
        this.mManualDark = mManualDark;
    }

    public void setColorLightShade(int mColorLightShade) {
        this.mColorLightShade = mColorLightShade;
    }

    public void setColorLightAccent(int mColorLightAccent) {
        this.mColorLightAccent = mColorLightAccent;
    }

    public void setColorMainColor(int mColorMainColor) {
        this.mColorMainColor = mColorMainColor;
    }

    public void setColorDarkAccent(int mColorDarkAccent) {
        this.mColorDarkAccent = mColorDarkAccent;
    }

    public void setColorDarkShade(int mColorDarkShade) {
        this.mColorDarkShade = mColorDarkShade;
    }

    int mTextColorOverLightShade;
    int mTextColorOverLightAccent;
    int mTextColorOverMainColor;
    int mTextColorOverDarkAccent;
    int mTextColorOverDarkShade;
    int mTextColorOverMainBackground;

    public Branding(Context context) {

        mContext = context;
        mColorLightShade = ContextCompat.getColor(context, R.color.colorLightShade);
        mColorLightAccent = ContextCompat.getColor(context, R.color.colorLightAccent);
        mColorMainColor = ContextCompat.getColor(context, R.color.colorMainColor);
        mColorDarkAccent = ContextCompat.getColor(context, R.color.colorDarkAccent);
        mColorDarkShade = ContextCompat.getColor(context, R.color.colorDarkShade);
        mMainBackgroundColor = ColorUtil.getMainBackgroundColor(context, mColorMainColor);

        computeTextColors();

    }


    public int getColorLightShade() {
        return mColorLightShade;
    }

    public int getColorLightAccent() {
        return mColorLightAccent;
    }

    public int getColorMainColor() {
        return mColorMainColor;
    }

    public int getColorDarkAccent() {
        return mColorDarkAccent;
    }

    public int getColorDarkShade() {
        return mColorDarkShade;
    }

    public int getTextColorOverLightShade() {
        return mTextColorOverLightShade;
    }

    public int getTextColorOverLightAccent() {
        return mTextColorOverLightAccent;
    }

    public int getTextColorOverMainColor() {
        return mTextColorOverMainColor;
    }

    public int getTextColorOverDarkAccent() {
        return mTextColorOverDarkAccent;
    }

    public int getTextColorOverDarkShade() {
        return mTextColorOverDarkShade;
    }

    public int getTextColorOverMainBackground() {
        return mTextColorOverMainBackground;
    }

    public void setTextColorOverMainBackground(int mTextColorOverMainBackground) {
        this.mTextColorOverMainBackground = mTextColorOverMainBackground;
    }

    public int getMainBackgroundColor() {
        return mMainBackgroundColor;
    }

    public void setMainBackgroundColor(int mMainBackgroundColor) {
        this.mMainBackgroundColor = mMainBackgroundColor;
        this.mDarkTheme = ColorUtil.isLuminanceDark(mMainBackgroundColor);
    }

    public void computeTextColors() {

        if (mContext != null) {


            mTextColorOverLightShade = ColorUtil.computeTextColorFromBackgroundColorComplex(mColorLightShade);
            mTextColorOverLightAccent = ColorUtil.computeTextColorFromBackgroundColorComplex(mColorLightAccent);
            mTextColorOverMainColor = ColorUtil.computeTextColorFromBackgroundColorComplex(mColorMainColor);
            mTextColorOverDarkAccent = ColorUtil.computeTextColorFromBackgroundColorComplex(mColorDarkAccent);
            mTextColorOverDarkShade = ColorUtil.computeTextColorFromBackgroundColorComplex(mColorDarkShade);
            mTextColorOverMainBackground = ColorUtil.isLuminanceDark(mMainBackgroundColor) ? Color.WHITE : Color.BLACK;

        }
    }


}
