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

package saschpe.android.utils.widget;

import android.graphics.Rect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import saschpe.android.utils.BuildConfig;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public final class SpacesItemDecorationTest {
    private static final int TEST_SPACE = 10;

    @Test
    public void testNewSpacesItemDecorationWithVerticalOrientation() {
        // Arrange
        SpacesItemDecoration decoration = new SpacesItemDecoration(TEST_SPACE, SpacesItemDecoration.VERTICAL);

        // Act
        Rect outRect = new Rect();
        decoration.getItemOffsets(outRect, null, null, null);

        // Assert
        assertEquals(TEST_SPACE, outRect.bottom);
        assertEquals(0, outRect.left);
        assertEquals(0, outRect.right);
        assertEquals(TEST_SPACE, outRect.top);
    }

    @Test
    public void testNewSpacesItemDecorationWithHorizontalOrientation() {
        // Arrange
        SpacesItemDecoration decoration = new SpacesItemDecoration(TEST_SPACE, SpacesItemDecoration.HORIZONTAL);

        // Act
        Rect outRect = new Rect();
        decoration.getItemOffsets(outRect, null, null, null);

        // Assert
        assertEquals(0, outRect.bottom);
        assertEquals(TEST_SPACE, outRect.left);
        assertEquals(TEST_SPACE, outRect.right);
        assertEquals(0, outRect.top);
    }
}
