package nz.co.tsg.themedapp.ui.colorpicker;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.JsonArray;
import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.model.GeneratePaletteResponse;
import nz.co.tsg.themedapp.network.ApiUtils;
import nz.co.tsg.themedapp.network.ColormindService;
import nz.co.tsg.themedapp.ui.Branding;
import nz.co.tsg.themedapp.utilities.ColorUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.JsonObject;

import java.util.List;


public class ColorPickerActivity extends AppCompatActivity {

    int mCurrentMainColor;
    int mCurrentLightShade;
    int mCurrentLightAccent;
    int mCurrentDarkAccent;
    int mCurrentDarkShade;
    int mCurrentMainBackgroundColor;
    boolean mCurrentIsAutoTheme;
    boolean mCurrentIsManualDark;
    EditText mColorEditText;
    HSLColorPicker mColorPicker;
    Button mSetMainColorButton;
    Branding mBranding;
    private Button mFetchColorsButton;
    private Button mApplyPaletteButton;
    private ColormindService mColormindService;
    private CheckBox mAutoThemeCheckBox;
    private ToggleButton mLightDarkToggle;

    View previewColorLightShade;
    View previewColorLightAccent;
    View previewColorMainColor;
    View previewColorDarkAccent;
    View previewColorDarkShade;
    LinearLayout previewMainBackgroundColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        ThemedApplication app = (ThemedApplication) getApplication();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBranding = app.getmBranding();
        mCurrentLightShade = mBranding.getColorLightShade();
        mCurrentLightAccent = mBranding.getColorLightAccent();
        mCurrentMainColor = mBranding.getColorMainColor();
        mCurrentDarkAccent = mBranding.getColorDarkAccent();
        mCurrentDarkShade = mBranding.getColorDarkShade();
        mCurrentMainBackgroundColor = mBranding.getMainBackgroundColor();
        mCurrentIsAutoTheme = mBranding.isAutoTheme();
        mCurrentIsManualDark = mBranding.isManualDark();

        toolbar.setTitleTextColor(mBranding.getTextColorOverMainColor());
        toolbar.setBackgroundColor(mCurrentMainColor);

        mColorPicker = (HSLColorPicker) findViewById(R.id.colorPicker);
        mColorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                mCurrentMainColor = color;
                String hexColor = String.format("%06X", (0xFFFFFF & color));
                mColorEditText.setText(hexColor);
                mColorEditText.setBackgroundColor(color);
                mColorEditText.setTextColor(ColorUtil.computeTextColorFromBackgroundColorComplex(color));
            }
        });

        mColorEditText = (EditText) findViewById(R.id.colorValue);
        mColorEditText.setBackgroundColor(mCurrentMainColor);
        mColorPicker.setColor(mCurrentMainColor);

        mColorEditText.setText(String.format("%06X", (0xFFFFFF & mCurrentMainColor)));

        mColorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSetMainColorButton.setEnabled(s.length() == 6);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSetMainColorButton = (Button) findViewById(R.id.setMainColorButton);
        mSetMainColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String s = mColorEditText.getText().toString();
                    mCurrentMainColor = Color.parseColor("#".concat(s));
                    mColorPicker.setColor(mCurrentMainColor);
                    mColorEditText.setBackgroundColor(mCurrentMainColor);
                    int newTextColor = ColorUtil.computeTextColorFromBackgroundColorComplex(mCurrentMainColor);
                    mColorEditText.setTextColor(newTextColor);
                    mSetMainColorButton.setBackgroundColor(mCurrentMainColor);
                    mSetMainColorButton.setTextColor(newTextColor);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        mFetchColorsButton = (Button) findViewById(R.id.fetchColorButton);
        mFetchColorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchColors();

            }
        });

        mAutoThemeCheckBox = (CheckBox) findViewById(R.id.autoThemeCheckBox);
        mAutoThemeCheckBox.setChecked(mCurrentIsAutoTheme);
        mAutoThemeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCurrentIsAutoTheme = ! mCurrentIsAutoTheme;
                if (isChecked) {
                    int previewMainColor = ((ColorDrawable)previewColorMainColor.getBackground()).getColor();
                    mCurrentMainBackgroundColor = ColorUtil.getMainBackgroundColor(buttonView.getContext(), previewMainColor);
                    mLightDarkToggle.setEnabled(false);
                } else {
                    mLightDarkToggle.setEnabled(true);
                    if (mCurrentIsManualDark) {
                        mCurrentMainBackgroundColor = ContextCompat.getColor(buttonView.getContext(), R.color.colorDarkMainBackground);
                    } else {
                        mCurrentMainBackgroundColor = ContextCompat.getColor(buttonView.getContext(), R.color.colorLightMainBackground);
                    }
                }
                previewMainBackgroundColor.setBackgroundColor(mCurrentMainBackgroundColor);

            }
        });

        mLightDarkToggle = (ToggleButton) findViewById(R.id.lightDarkToggle);
        mLightDarkToggle.setEnabled(!mCurrentIsAutoTheme);
        mLightDarkToggle.setChecked(!mCurrentIsManualDark);
        mLightDarkToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIsManualDark = !mCurrentIsManualDark;
                if (mCurrentIsManualDark) {
                    mCurrentMainBackgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorDarkMainBackground);
                } else {
                    mCurrentMainBackgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorLightMainBackground);
                }
                previewMainBackgroundColor.setBackgroundColor(mCurrentMainBackgroundColor);
            }
        });

        mApplyPaletteButton = (Button) findViewById(R.id.applyPaletteButton);
        mApplyPaletteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyPaletteOnApp();
            }
        });

        refreshActivityColors();
        applyPaletteLocallyOnPreview(true);
        mColormindService = ApiUtils.getColormindService();

    }

    private void refreshActivityColors () {
        int buttonBackgroundColor = mCurrentMainColor;
        int buttonTextColor = ColorUtil.computeTextColorFromBackgroundColorComplex(mCurrentMainColor);
        mSetMainColorButton.setBackgroundColor(buttonBackgroundColor);
        mSetMainColorButton.setTextColor(buttonTextColor);
        mFetchColorsButton.setBackgroundColor(buttonBackgroundColor);
        mFetchColorsButton.setTextColor(buttonTextColor);
        mApplyPaletteButton.setBackgroundColor(buttonBackgroundColor);
        mApplyPaletteButton.setTextColor(buttonTextColor);

    }


    private void fetchColors() {
        JsonArray paletteInput = new JsonArray();
        JsonArray mainColorInput = new JsonArray();

        paletteInput.add("N");
        paletteInput.add("N");
        mainColorInput.add(Color.red(mCurrentMainColor));
        mainColorInput.add(Color.green(mCurrentMainColor));
        mainColorInput.add(Color.blue(mCurrentMainColor));
        paletteInput.add(mainColorInput);
        paletteInput.add("N");
        paletteInput.add("N");


        JsonObject requestBody = null;
        try {
            requestBody = new JsonObject();
            requestBody.add("input", paletteInput);
            requestBody.addProperty("model", "ui");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (requestBody != null) {
            mColormindService.fetchPalette(requestBody).enqueue(new Callback<GeneratePaletteResponse>() {
                @Override
                public void onResponse(Call<GeneratePaletteResponse> call, Response<GeneratePaletteResponse> response) {

                    if (response.isSuccessful()) {
                        Log.d("MainActivity", "posts loaded from API");
                        Toast.makeText(getApplicationContext(), "Palette successfully retrieved", Toast.LENGTH_SHORT).show();
                        GeneratePaletteResponse paletteResponse = response.body();
                        if (paletteResponse != null) {
                            List<List<Integer>> palette = paletteResponse.getResult();
                            mCurrentLightShade = Color.argb(255, palette.get(0).get(0),palette.get(0).get(1),palette.get(0).get(2));
                            mCurrentLightAccent = Color.argb(255, palette.get(1).get(0),palette.get(1).get(1),palette.get(1).get(2));
                            mCurrentDarkAccent = Color.argb(255, palette.get(3).get(0),palette.get(3).get(1),palette.get(3).get(2));
                            mCurrentDarkShade = Color.argb(255, palette.get(4).get(0),palette.get(4).get(1),palette.get(4).get(2));
                            if (mCurrentIsAutoTheme) {
                                mCurrentMainBackgroundColor = ColorUtil.getMainBackgroundColor(getApplicationContext(), mCurrentMainColor);
                            } else {
                                if (mCurrentIsManualDark) {
                                    mCurrentMainBackgroundColor = ContextCompat.getColor(getApplicationContext(), R.color.colorDarkMainBackground);
                                } else {
                                    mCurrentMainBackgroundColor = ContextCompat.getColor(getApplicationContext(), R.color.colorLightMainBackground);
                                }
                            }
                            applyPaletteLocallyOnPreview(false);
                        }

                    } else {
                        int statusCode = response.code();
                        Toast.makeText(getApplicationContext(), "Failed to fetch new palette from Colormind.io, status code =  " + statusCode, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeneratePaletteResponse> call, Throwable t) {
                    Log.d("MainActivity", "error loading from API");
                    Toast.makeText(getApplicationContext(), "Failed to fetch new palette from Colormind.io " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Failed to send request", Toast.LENGTH_SHORT).show();
        }
    }


    private void applyPaletteLocallyOnPreview(boolean fromSavedBranding) {
        previewColorLightShade = findViewById(R.id.previewColorLightShade);
        previewColorLightAccent = findViewById(R.id.previewColorLightAccent);
        previewColorMainColor = findViewById(R.id.previewColorMainColor);
        previewColorDarkAccent = findViewById(R.id.previewColorDarkAccent);
        previewColorDarkShade = findViewById(R.id.previewColorDarkShade);
        previewMainBackgroundColor = (LinearLayout) findViewById(R.id.previewMainBackgroundColor);

        if (fromSavedBranding) {
            previewColorLightShade.setBackgroundColor(mBranding.getColorLightShade());
            previewColorLightAccent.setBackgroundColor(mBranding.getColorLightAccent());
            previewColorMainColor.setBackgroundColor(mBranding.getColorMainColor());
            previewColorDarkAccent.setBackgroundColor(mBranding.getColorDarkAccent());
            previewColorDarkShade.setBackgroundColor(mBranding.getColorDarkShade());
            previewMainBackgroundColor.setBackgroundColor(mBranding.getMainBackgroundColor());
        } else {
            previewColorLightShade.setBackgroundColor(mCurrentLightShade);
            previewColorLightAccent.setBackgroundColor(mCurrentLightAccent);
            previewColorMainColor.setBackgroundColor(mCurrentMainColor);
            previewColorDarkAccent.setBackgroundColor(mCurrentDarkAccent);
            previewColorDarkShade.setBackgroundColor(mCurrentDarkShade);
            previewMainBackgroundColor.setBackgroundColor(mCurrentMainBackgroundColor);
        }
        refreshActivityColors();

    }

    private void applyPaletteOnApp() {
        mBranding.setColorLightShade(mCurrentLightShade);
        mBranding.setColorLightAccent(mCurrentLightAccent);
        mBranding.setColorMainColor(mCurrentMainColor);
        mBranding.setColorDarkAccent(mCurrentDarkAccent);
        mBranding.setColorDarkShade(mCurrentDarkShade);
        mBranding.setMainBackgroundColor(mCurrentMainBackgroundColor);
        mBranding.setAutoTheme(mCurrentIsAutoTheme);
        mBranding.setManualDark(mCurrentIsManualDark);
        mBranding.computeTextColors();
        finish();
        startActivity(getIntent());
    }

}
