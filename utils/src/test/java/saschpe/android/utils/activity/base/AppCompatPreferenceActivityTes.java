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

package saschpe.android.utils.activity.base;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import saschpe.android.utils.BuildConfig;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
@Ignore // TODO: Fix support resource issue!
public final class AppCompatPreferenceActivityTes {
    private static final String TEST_ACTIONBAR_TITLE = "MyTitle";
    private static final String TEST_ACTIONBAR_SUBTITLE = "MySubTitle";
    private AppCompatPreferenceActivity activity;

    @Before
    public void setupActivity() {
        activity = Robolectric.setupActivity(TestActivity.class);
    }

    @Test
    public void getSupportActionBar() {
        // Arrange, act
        ActionBar actionBar = activity.getSupportActionBar();

        // Assert
        assertNotNull(actionBar);
    }

    @Test
    public void setSupportActionBar() {
        // Arrange
        Toolbar toolbar = new Toolbar(RuntimeEnvironment.application);
        toolbar.setTitle(TEST_ACTIONBAR_TITLE);
        toolbar.setSubtitle(TEST_ACTIONBAR_SUBTITLE);

        // Act
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();

        // Assert
        assertEquals(TEST_ACTIONBAR_TITLE, actionBar.getTitle());
        assertEquals(TEST_ACTIONBAR_SUBTITLE, actionBar.getSubtitle());
    }

    private static final class TestActivity extends AppCompatPreferenceActivity {
    }
}
