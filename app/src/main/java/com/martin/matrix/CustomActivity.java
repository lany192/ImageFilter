package com.martin.matrix;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;

import java.io.File;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class CustomActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private ImageView imageView;
    private SeekBar seekBarH;
    private SeekBar saturationSeekBar;
    private SeekBar seekBarR;
    private SeekBar seekBarG;
    private SeekBar seekBarB;
    private SeekBar seekBarA;
    private SeekBar lightnessSeekBar;
    private SeekBar contrastSeekBar;
    private View colorView;
    private TextView colorText, showInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        imageView = findViewById(R.id.imageView);
        colorView = findViewById(R.id.color_view);
        colorText = findViewById(R.id.color_text);
        showInfoText = findViewById(R.id.show_info_text_view);

        seekBarR = findViewById(R.id.bar_R);
        seekBarG = findViewById(R.id.bar_G);
        seekBarB = findViewById(R.id.bar_B);
        seekBarA = findViewById(R.id.bar_A);
        seekBarH = findViewById(R.id.bar_hue);
        saturationSeekBar = findViewById(R.id.bar_saturation);
        contrastSeekBar = findViewById(R.id.bar_contrast);
        lightnessSeekBar = findViewById(R.id.bar_lightness);

        seekBarH.setOnSeekBarChangeListener(this);
        saturationSeekBar.setOnSeekBarChangeListener(this);
        seekBarR.setOnSeekBarChangeListener(this);
        seekBarG.setOnSeekBarChangeListener(this);
        seekBarB.setOnSeekBarChangeListener(this);
        seekBarA.setOnSeekBarChangeListener(this);
        contrastSeekBar.setOnSeekBarChangeListener(this);
        lightnessSeekBar.setOnSeekBarChangeListener(this);

        findViewById(R.id.my_text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPicker.of().single(true)
                        .start(CustomActivity.this)
                        .subscribe(new Consumer<List<ImageItem>>() {
                            @Override
                            public void accept(@NonNull List<ImageItem> imageItems) {
                                imageView.setImageURI(Uri.fromFile(new File(imageItems.get(0).getPath())));

                                seekBarH.setProgress(128);
                                saturationSeekBar.setProgress(128);
                                seekBarR.setProgress(128);
                                seekBarG.setProgress(128);
                                seekBarB.setProgress(128);
                                seekBarA.setProgress(128);
                                contrastSeekBar.setProgress(0);
                                lightnessSeekBar.setProgress(255);
                            }
                        });
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected float caculate(int progress) {
        return progress / 128f;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int A = seekBarA.getProgress();
        int R = seekBarR.getProgress();
        int G = seekBarG.getProgress();
        int B = seekBarB.getProgress();
        int contrast = contrastSeekBar.getProgress();
        int H = seekBarH.getProgress();
        int saturation = saturationSeekBar.getProgress();
        int lightness = lightnessSeekBar.getProgress();

        String color = ("#" + Integer.toHexString(A) + Integer.toHexString(R) + Integer.toHexString(G) + Integer.toHexString(B)).toUpperCase();
        colorText.setText("颜色值：" + color);
        colorView.setBackgroundColor(Color.parseColor(color));

        float mHueValue = (H - 128f) * 1.0f / 128f * 180;
        float mSaturationValue = saturation / 128f;

        //亮度，值是处于0-255之间的整型数值
        float brightness = lightness - 255;

        //设置色相
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, mHueValue);
        hueMatrix.setRotate(1, mHueValue);
        hueMatrix.setRotate(2, mHueValue);

        //设置饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(mSaturationValue);

        //ARGB
        ColorMatrix argbMatrix = new ColorMatrix();
        argbMatrix.setScale(caculate(R), caculate(G), caculate(B), caculate(A));

        //亮度(N取值为-255到255)
        ColorMatrix brightnessMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, brightness,
                0, 1, 0, 0, brightness,
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0
        });

        //对比度
        float contrastValue = (contrast + 1) * 1.0f;
        ColorMatrix contrastMatrix = new ColorMatrix(new float[]{
                contrastValue, 0, 0, 0, 128 * (1 - contrastValue),
                0, contrastValue, 0, 0, 128 * (1 - contrastValue),
                0, 0, contrastValue, 0, 128 * (1 - contrastValue),
                0, 0, 0, 1, 0
        });

        ColorMatrix filter = new ColorMatrix();
        filter.postConcat(contrastMatrix);
        filter.postConcat(argbMatrix);
        filter.postConcat(saturationMatrix);
        filter.postConcat(hueMatrix);
        filter.postConcat(brightnessMatrix);

        imageView.setColorFilter(new ColorMatrixColorFilter(filter));

        String info = "A:" + A + " R:" + R + " B:" + B + " G:" + G
                + "\n色相:" + mHueValue
                + " 饱和度:" + mSaturationValue
                + " 对比度:" + contrastValue
                + " 亮度:" + brightness;
        showInfoText.setText(info);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
