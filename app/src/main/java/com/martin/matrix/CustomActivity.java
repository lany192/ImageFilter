package com.martin.matrix;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * https://www.cnblogs.com/tinytiny/p/3317372.html
 */
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
    private String info = "";
    private String colorMatrix = "";

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
                            }
                        });
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        findViewById(R.id.copy_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardUtils.setText(CustomActivity.this, colorMatrix);
                Toast.makeText(CustomActivity.this, "复制成功：" + colorMatrix, Toast.LENGTH_SHORT).show();
            }
        });
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
        int H = seekBarH.getProgress();

        String color = ("#" + Integer.toHexString(A) + Integer.toHexString(R) + Integer.toHexString(G) + Integer.toHexString(B)).toUpperCase();
        colorText.setText("颜色值：" + color);
        colorView.setBackgroundColor(Color.parseColor(color));

        float hue = (H - 128f) * 1.0f / 128f * 180;
        float mSaturationValue = saturationSeekBar.getProgress() / 128f;
        //亮度，值是处于0-255之间的整型数值
        float brightness = lightnessSeekBar.getProgress() - 255;

        //对比度
        float contrast = contrastSeekBar.getProgress() * 0.1f;
        imageView.setColorFilter(getFilter(contrast, hue, mSaturationValue, brightness, caculate(R), caculate(G), caculate(B), caculate(A)));

        info = "A:" + A + "   R:" + R + "   B:" + B + "   G:" + G
                + "\n色相:" + hue
                + "   饱和度:" + mSaturationValue
                + "   对比度:" + contrast
                + "   亮度:" + brightness;

    }

    private ColorMatrixColorFilter getFilter(float contrast, float hue, float saturation, float brightness,
                                             float R, float G, float B, float A) {

        Log.i("TAG", "contrast==" + contrast + " hue==" + hue + " saturation==" + saturation + " brightness==" + brightness);
        Log.i("TAG", "A==" + A + " B==" + B + " G==" + G + " R==" + R);
        //设置色相
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        //设置饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        //ARGB
        ColorMatrix argbMatrix = new ColorMatrix();
        argbMatrix.setScale(R, G, B, A);

        //亮度(N取值为-255到255)
        ColorMatrix brightnessMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, brightness,
                0, 1, 0, 0, brightness,
                0, 0, 1, 0, brightness,
                0, 0, 0, 1, 0
        });

        //对比度
        ColorMatrix contrastMatrix = new ColorMatrix(new float[]{
                contrast, 0, 0, 0, 128 * (1 - contrast),
                0, contrast, 0, 0, 128 * (1 - contrast),
                0, 0, contrast, 0, 128 * (1 - contrast),
                0, 0, 0, 1, 0
        });

        ColorMatrix filter = new ColorMatrix();
        filter.postConcat(contrastMatrix);
        filter.postConcat(argbMatrix);
        filter.postConcat(saturationMatrix);
        filter.postConcat(hueMatrix);
        filter.postConcat(brightnessMatrix);


        DecimalFormat format = new DecimalFormat("###0.0f");
        float result[] = filter.getArray();
        colorMatrix = "\nColorMatrix参数:\n";
        for (int i = 0; i < result.length; i++) {
            String item = format.format(result[i]);
            if (i == 0) {
                colorMatrix = colorMatrix + "[" + item + ", ";
            } else if (i == 19) {
                colorMatrix += (item + "]\n");
            } else {
                colorMatrix += (item + ", ");
            }
            if (i == 4 || i == 9 || i == 14) {
                colorMatrix += "\n";
            }
        }
        Log.i("TAG", colorMatrix);
        showInfoText.setText(info + colorMatrix);
        return new ColorMatrixColorFilter(filter);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
