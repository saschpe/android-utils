/*
 * Copyright 2016 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package saschpe.android.utils.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public final class DisplayHelper {
    private DisplayHelper() {
        // Intentionally left blank
    }

    public static int getWidestScreenEdgeInPixels(final @NonNull Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            return displayMetrics.widthPixels;
        }
        return displayMetrics.heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(final @NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return getRealMetrics(context);
        } else {
            // Get metrics without navigation bar
            return context.getResources().getDisplayMetrics();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static DisplayMetrics getRealMetrics(final @NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);

        return outMetrics;
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    public static boolean isXLargeTablet(final @NonNull Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    public static boolean isSW320DP(final @NonNull Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 320;
    }

    public static boolean isSW400DP(final @NonNull Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 400;
    }

    public static boolean isW600DP(final @NonNull Context context) {
        return context.getResources().getConfiguration().screenWidthDp >= 600;
    }

    public static boolean isLandscape(final @NonNull Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static RecyclerView.LayoutManager getSuitableLayoutManager(final @NonNull Context context) {
        if (isXLargeTablet(context)) {
            return new GridLayoutManager(context, isLandscape(context) ? 3 : 2);
        } else if (isW600DP(context)) {
            return new GridLayoutManager(context, isLandscape(context) ? 2 : 1);
        } else {
            return new LinearLayoutManager(context);
        }
    }

    public static int getSuitableGridLayoutManagerSpanCount(final @NonNull Context context) {
        if (isXLargeTablet(context)) {
            return isLandscape(context) ? 3 : 2;
        } else if (isW600DP(context)) {
            return isLandscape(context) ? 2 : 1;
        } else {
            return 1;
        }
    }
}
