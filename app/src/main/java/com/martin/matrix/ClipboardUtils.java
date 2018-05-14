package com.martin.matrix;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

public class ClipboardUtils {

    /**
     * 设置粘贴板上的内容
     */
    public static void setText(Context context, CharSequence msg) {
        android.content.ClipboardManager manager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, msg));
    }
}
