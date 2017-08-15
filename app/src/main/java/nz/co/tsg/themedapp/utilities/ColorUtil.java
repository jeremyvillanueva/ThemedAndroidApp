package nz.co.tsg.themedapp.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import nz.co.tsg.themedapp.R;

/**
 * Created by lhtan on 24/8/15.
 * Based on How to decide font color in white or black depending on background color?
 * http://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
 */
public class ColorUtil {
    /**
     * compute text color, either white or black based on color
     * @param color color string in format #XXXXXX
     * @return int Color.WHITE or Color.BLACK based on the color
     */
    public static int computeTextColorFromBackgroundColorSimple(int color){

        final int red = Color.red(color);
        final int green = Color.green(color);
        final int blue = Color.blue(color);

        if ((red*0.299 + green*0.587 + blue*0.114) > 186){
            return Color.BLACK;
        }else{
            return Color.WHITE;
        }

    }

    public static int computeTextColorFromBackgroundColorComplex(int color){

        if(isLuminanceDark(color)){
            return Color.WHITE;
        }else {
            return Color.BLACK;
        }

    }

    private static double gammaCorrect(int value){
        double c = value/255.0;
        if (c <= 0.03928){
            c = c/12.92;
        } else {
            c = Math.pow(((c+0.055)/1.055), 2.4);
        }
        return c;
    }

    public static int adjustAlpha(int color, int percentOpacity) {
        int alpha = Math.round(Color.alpha(color) * percentOpacity / 100);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static boolean isLuminanceDark(int color) {
        final int red = Color.red(color);
        final int green = Color.green(color);
        final int blue = Color.blue(color);

        double rd = gammaCorrect(red);
        double gd = gammaCorrect(green);
        double bd = gammaCorrect(blue);
        double luminance = 0.2126 * rd + 0.7152 * gd + 0.0722 * bd;

        return  (luminance <= 0.179);
    }

    public static int getMainBackgroundColor(Context context, int color) {
        return ColorUtil.isLuminanceDark(color) ? ContextCompat.getColor(context, R.color.colorLightMainBackground) : ContextCompat.getColor(context, R.color.colorDarkMainBackground);
    }

    public static void changeBackgroundDrawableColor(View view, int color) {
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(color);
        }
        view.setBackground(background);
    }


}