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

package saschpe.android.utils.adapter.base;

import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import saschpe.android.utils.BuildConfig;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public final class ArrayAdapterTest {
    private static final List<String> TEST_VALUES = Arrays.asList("one", "two", "three");
    private TestArrayAdapter arrayAdapter;

    @Before
    public void setupArrayAdapter() {
        arrayAdapter = new TestArrayAdapter(TEST_VALUES);
    }

    @Test
    public void testConstructorNull() {
        TestArrayAdapter arrayAdapterNull = new TestArrayAdapter(null);
        assertEquals(0, arrayAdapterNull.getItemCount());
    }

    @Test
    public void testGetItemCount() {
        assertEquals(TEST_VALUES.size(), arrayAdapter.getItemCount());
    }

    @Test
    public void testGetItem() {
        assertEquals(TEST_VALUES.get(0), arrayAdapter.getItem(0));
        assertEquals(TEST_VALUES.get(1), arrayAdapter.getItem(1));
        assertEquals(TEST_VALUES.get(2), arrayAdapter.getItem(2));
    }

    @Test
    public void testGetPosition() {
        assertEquals(0, arrayAdapter.getPosition("one"));
        assertEquals(1, arrayAdapter.getPosition("two"));
        assertEquals(2, arrayAdapter.getPosition("three"));
    }

    @Ignore
    public void testClear() {
        arrayAdapter.clear();
        assertEquals(0, arrayAdapter.getItemCount());
    }

    @Ignore
    public void testInsert() {
        arrayAdapter.insert(1, "new");
        assertEquals(1, arrayAdapter.getPosition("new"));
        assertEquals("new", arrayAdapter.getItem(1));
    }

    @Ignore
    public void testRemove() {
        arrayAdapter.remove("two");
        assertEquals(-1, arrayAdapter.getPosition("two"));
    }

    private static final class TestArrayAdapter extends ArrayAdapter<String, RecyclerView.ViewHolder> {
        TestArrayAdapter(final List<String> objects) {
            super(objects);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        }
    }
}
