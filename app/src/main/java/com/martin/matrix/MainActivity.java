package com.martin.matrix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_color_hue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomActivity.class));
            }
        });
        findViewById(R.id.bt_color_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });
        RxPicker.init(new RxPickerImageLoader() {

            @Override
            public void display(ImageView imageView, String path, int width, int height) {
                RequestOptions options = new RequestOptions()
                        .fitCenter()
                        .override(width, height)
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                Glide.with(imageView.getContext())
                        .setDefaultRequestOptions(options)
                        .load(path)
                        .into(imageView);
            }
        });
    }
}
