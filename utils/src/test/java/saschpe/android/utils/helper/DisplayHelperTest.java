/*
 * Copyright 2017 Sascha Peilicke
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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import saschpe.android.utils.BuildConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public final class DisplayHelperTest {
    @Test
    public void testGetWidestScreenEdgeInPixels() {
        int widestEdge = DisplayHelper.getWidestScreenEdgeInPixels(RuntimeEnvironment.application);

        //assertEquals(800, widestEdge);
    }

    @Test
    public void testGetDisplayMetrics() {
        DisplayMetrics displayMetrics = DisplayHelper.getDisplayMetrics(RuntimeEnvironment.application);

        assertNotNull(displayMetrics);
        //assertEquals(1.0, displayMetrics.density, 0.0);
        assertEquals(480, displayMetrics.widthPixels);
        //assertEquals(800, displayMetrics.heightPixels);
        assertEquals(240, displayMetrics.densityDpi);
    }

    @Test
    public void testIsSW320DP() {
        assertTrue(DisplayHelper.isSW320DP(RuntimeEnvironment.application));
    }

    @Test
    public void testIsSW400DP() {
        assertFalse(DisplayHelper.isSW400DP(RuntimeEnvironment.application));
    }

    @Test
    public void testIsW600DP() {
        assertFalse(DisplayHelper.isW600DP(RuntimeEnvironment.application));
    }

    @Test
    public void testIsXLargeTablet() {
        assertFalse(DisplayHelper.isXLargeTablet(RuntimeEnvironment.application));
    }

    @Test
    public void testIsLandscape() {
        assertFalse(DisplayHelper.isLandscape(RuntimeEnvironment.application));
    }

    @Test
    public void testGetSuitableLayoutManager() {
        RecyclerView.LayoutManager layoutManager = DisplayHelper.getSuitableLayoutManager(RuntimeEnvironment.application);

        assertTrue(layoutManager instanceof LinearLayoutManager);
    }

    @Test
    public void testGetSuitableGridLayoutManagerSpanCount() {
        int spanCount = DisplayHelper.getSuitableGridLayoutManagerSpanCount(RuntimeEnvironment.application);

        assertEquals(1, spanCount);
    }
}
