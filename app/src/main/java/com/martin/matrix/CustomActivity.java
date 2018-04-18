package com.martin.matrix;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private SeekBar seekBarS;
    private SeekBar seekBarR;
    private SeekBar seekBarG;
    private SeekBar seekBarB;
    private SeekBar seekBarA;
    private SeekBar seekBarL;
    //对比度
    private SeekBar seekBarC;
    private View colorView;
    private TextView colorText, showInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_hue);
        imageView = findViewById(R.id.imageView);
        colorView = findViewById(R.id.color_view);
        colorText = findViewById(R.id.color_text);
        showInfoText = findViewById(R.id.show_info_text_view);

        seekBarR = findViewById(R.id.bar_R);
        seekBarG = findViewById(R.id.bar_G);
        seekBarB = findViewById(R.id.bar_B);
        seekBarA = findViewById(R.id.bar_A);
        seekBarH = findViewById(R.id.bar_hue);
        seekBarS = findViewById(R.id.bar_saturation);
        seekBarC = findViewById(R.id.bar_contrast);
        seekBarL = findViewById(R.id.bar_lightness);

        seekBarH.setOnSeekBarChangeListener(this);
        seekBarS.setOnSeekBarChangeListener(this);
        seekBarR.setOnSeekBarChangeListener(this);
        seekBarG.setOnSeekBarChangeListener(this);
        seekBarB.setOnSeekBarChangeListener(this);
        seekBarA.setOnSeekBarChangeListener(this);
        seekBarC.setOnSeekBarChangeListener(this);
        seekBarL.setOnSeekBarChangeListener(this);

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
                                seekBarS.setProgress(128);
                                seekBarR.setProgress(128);
                                seekBarG.setProgress(128);
                                seekBarB.setProgress(128);
                                seekBarA.setProgress(128);
                                seekBarC.setProgress(128);
                                seekBarL.setProgress(128);
                            }
                        });
            }
        });
    }

    protected float caculate(int progress) {
        float scale = progress / 128f;
        return scale;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int A = seekBarA.getProgress();
        int R = seekBarR.getProgress();
        int G = seekBarG.getProgress();
        int B = seekBarB.getProgress();
        int C = seekBarC.getProgress();
        int H = seekBarH.getProgress();
        int S = seekBarS.getProgress();
        int L = seekBarL.getProgress();

        String color = ("#" + Integer.toHexString(A) + Integer.toHexString(R) + Integer.toHexString(G) + Integer.toHexString(B)).toUpperCase();
        colorText.setText("颜色值：" + color);
        colorView.setBackgroundColor(Color.parseColor(color));


        float mHueValue = (H - 128f) * 1.0f / 128f * 180;
        float mSaturationValue = S / 128f;
        //对比度
        float contrastValue = C / 128f;
        //亮度，值是处于0-255之间的整型数值
        float brightness = L / 128f;

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

        //亮度
        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.setScale(brightness, brightness, brightness, 1);

        //对比度
        ColorMatrix contrastMatrix = new ColorMatrix(new float[]{
                contrastValue, 0, 0, 0, 0,
                0, contrastValue, 0, 0, 0,
                0, 0, contrastValue, 0, 0,
                0, 0, 0, contrastValue, 0
        });

        ColorMatrix all = new ColorMatrix();
        all.postConcat(contrastMatrix);
        all.postConcat(argbMatrix);
        all.postConcat(saturationMatrix);
        all.postConcat(hueMatrix);
        all.postConcat(brightnessMatrix);

        showInfoText.setText("A:" + A + " R:" + R + " B:" + B + " G:" + G + " \n色相:" + mHueValue + " 饱和度:" + mSaturationValue + " 对比度:" + contrastValue + " 亮度:" + brightness);

        imageView.setColorFilter(new ColorMatrixColorFilter(all));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
