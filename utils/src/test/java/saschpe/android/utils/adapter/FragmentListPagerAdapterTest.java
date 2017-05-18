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

package saschpe.android.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import saschpe.android.utils.BuildConfig;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FragmentListPagerAdapterTest {
    private FragmentListPagerAdapter adapter;

    @Before
    public void setupPagerAdapter() {
        FragmentManager fragmentManager = new FragmentActivity()
                .getSupportFragmentManager();

        adapter = new FragmentListPagerAdapter(fragmentManager);
    }

    @Test
    public void testAddingFragments() {
        TestFragment testFragment1 = new TestFragment();
        TestFragment testFragment2 = new TestFragment();

        adapter.addFragment(testFragment1, "one");
        adapter.addFragment(testFragment2, "two");

        assertEquals(2, adapter.getCount());
        assertEquals("one", adapter.getPageTitle(0 ));
        assertEquals("two", adapter.getPageTitle(1 ));
        assertSame(testFragment1, adapter.getItem(0));
        assertSame(testFragment2, adapter.getItem(1));
    }

    public static class TestFragment extends Fragment {
    }
}
