package com.martin.matrix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.widget.ImageView;

/**
 * 我收集的颜色滤镜
 * Created by Martin on 2016/8/1 0001.
 */
public class ColorFilter {

    /**
     * 为imageView设置颜色滤镜
     *
     * @param imageView
     * @param colormatrix
     */
    public static void imageViewColorFilter(ImageView imageView, float[] colormatrix) {
        setColorMatrixColorFilter(imageView, new ColorMatrixColorFilter(new ColorMatrix(colormatrix)));
    }

    /**
     * 为imageView设置颜色偏向滤镜
     *
     * @param imageView
     * @param color
     */
    public static void imageViewColorFilter(ImageView imageView, int color) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
        setColorMatrixColorFilter(imageView, new ColorMatrixColorFilter(colorMatrix));
    }


    /**
     * 生成对应颜色偏向滤镜的图片，并回收原图
     *
     * @param bitmap
     * @param color
     * @return
     */
    public static Bitmap bitmapColorFilter(Bitmap bitmap, int color) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
        return setColorMatrixColorFilter(bitmap, new ColorMatrixColorFilter(colorMatrix), true);
    }

    /**
     * 生成对应颜色滤镜的图片，并回收原图
     *
     * @param bitmap
     * @param colormatrix
     * @return
     */
    public static Bitmap bitmapColorFilter(Bitmap bitmap, float[] colormatrix) {
        return setColorMatrix(bitmap, colormatrix, true);
    }

    /**
     * 生成对应颜色滤镜的图片
     *
     * @param bitmap
     * @param colormatrix
     * @param isRecycle
     * @return
     */
    public static Bitmap setColorMatrix(Bitmap bitmap, float[] colormatrix, boolean isRecycle) {
        return setColorMatrixColorFilter(bitmap, new ColorMatrixColorFilter(new ColorMatrix(colormatrix)), isRecycle);
    }


    public static void setColorMatrixColorFilter(ImageView imageView, ColorMatrixColorFilter matrixColorFilter) {
        imageView.setColorFilter(matrixColorFilter);
    }

    public static Bitmap setColorMatrixColorFilter(Bitmap bitmap, ColorMatrixColorFilter matrixColorFilter, boolean isRecycle) {
        Bitmap resource = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(matrixColorFilter);
        Canvas canvas = new Canvas(resource);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (isRecycle)
            destroyBitmap(bitmap);
        return resource;
    }

    public static void destroyBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            Bitmap b = bitmap;
            if (b != null && !b.isRecycled()) {
                b.recycle();
            }
            bitmap = null;
        }
    }

    // 黑白
    public static final float HEIBAI[] = {
            //R  ,  G,    B,   A,    offset
            0.8f, 1.6f, 0.2f, 0.0f, -163.9f,// red
            0.8f, 1.6f, 0.2f, 0.0f, -163.9f,// green
            0.8f, 1.6f, 0.2f, 0.0f, -163.9f,// blue
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};  // alpha

    // 怀旧
    public static final float HUAJIU[] = {
            0.2f, 0.5f, 0.1f, 0.0f, 40.8f,
            0.2f, 0.5f, 0.1f, 0.0f, 40.8f,
            0.2f, 0.5f, 0.1f, 0.0f, 40.8f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    // 哥特
    public static final float GETE[] = {
            1.9f, -0.3f, -0.2f, 0.0f, -87.0f,
            -0.2f, 1.7f, -0.1f, 0.0f, -87.0f,
            -0.1f, -0.6f, 2.0f, 0.0f, -87.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    // 淡雅
    public static final float DANYA[] = {
            0.6f, 0.3f, 0.1f, 0.0f, 73.3f,
            0.2f, 0.7f, 0.1f, 0.0f, 73.3f,
            0.2f, 0.3f, 0.4f, 0.0f, 73.3f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    // 蓝调
    public static final float LANDIAO[] = {
            2.1f, -1.4f, 0.6f, 0.0f, -71.0f,
            -0.3f, 2.0f, -0.3f, 0.0f, -71.0f,
            -1.1f, -0.2f, 2.6f, 0.0f, -71.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    // 光晕
    public static final float GUANGYUN[] = {
            0.9f, 0, 0, 0, 64.9f,
            0, 0.9f, 0, 0, 64.9f,
            0, 0, 0.9f, 0, 64.9f,
            0, 0, 0, 1.0f, 0};

    // 梦幻
    public static final float MENGHUAN[] = {
            0.8f, 0.3f, 0.1f, 0.0f, 46.5f,
            0.1f, 0.9f, 0.0f, 0.0f, 46.5f,
            0.1f, 0.3f, 0.7f, 0.0f, 46.5f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    // 酒红
    public static final float JIUHONG[] = {
            1.2f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.9f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.8f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 胶片
    public static final float JIAOPIAN1[] = {
            -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
            0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
            0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 湖光掠影
    public static final float HUGUANG[] = {
            0.8f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.9f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 褐片
    public static final float HEPIAN[] = {
            1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.8f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.8f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 复古
    public static final float FUGU[] = {
            0.9f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.8f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.5f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 泛黄
    public static final float FANHUANG[] = {
            1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.5f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 传统
    public static final float CHUANTONG[] = {
            1.0f, 0.0f, 0.0f, 0, -10f,
            0.0f, 1.0f, 0.0f, 0, -10f,
            0.0f, 0.0f, 1.0f, 0, -10f,
            0.0f, 0.0f, 0.0f, 1, 0.0f};
    // 胶片2
    public static final float JIAOPIAN2[] = {
            0.71f, 0.2f, 0.0f, 0.0f, 60.0f,
            0.0f, 0.94f, 0.0f, 0.0f, 60.0f,
            0.0f, 0.0f, 0.62f, 0.0f, 60.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 锐色
    public static final float RUISE[] = {
            4.8f, -1.0f, -0.1f, 0.0f, -388.4f,
            -0.5f, 4.4f, -0.1f, 0.0f, -388.4f,
            -0.5f, -1.0f, 5.2f, 0.0f, -388.4f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 清宁
    public static final float QINGNING[] = {
            0.9f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.1f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.9f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f};
    // 浪漫
    public static final float LANGMAN[] = {
            0.9f, 0.0f, 0.0f, 0.0f, 63.0f,
            0.0f, 0.9f, 0.0f, 0.0f, 63.0f,
            0.0f, 0.0f, 0.9f, 0.0f, 63.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 夜色
    public static final float YESE[] = {
            1.0f, 0.0f, 0.0f, 0.0f, -66.6f,
            0.0f, 1.1f, 0.0f, 0.0f, -66.6f,
            0.0f, 0.0f, 1.0f, 0.0f, -66.6f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
}
