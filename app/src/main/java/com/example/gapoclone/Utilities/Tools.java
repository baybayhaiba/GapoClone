package com.example.gapoclone.Utilities;

import android.text.format.DateUtils;

import com.google.firebase.Timestamp;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tools {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String convertTimeAgo(Timestamp timestamp) {
        return (String) DateUtils.getRelativeTimeSpanString(timestamp.getSeconds() * 1000);
    }

    public static String convertTimeAgo(long time) {
        return (String) DateUtils.getRelativeTimeSpanString(time * 1000);
    }

    public static final int BACKGROUND_DEFAULT = android.R.drawable.screen_background_light_transparent;
}
