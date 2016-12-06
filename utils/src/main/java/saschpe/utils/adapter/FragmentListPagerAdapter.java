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

package saschpe.utils.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic list-based {@link Fragment} pager adapter.
 * <p>
 * Simplifies usage of {@link android.support.v4.view.ViewPager} and
 * {@link android.support.design.widget.TabLayout}.
 *
 * <pre>
 * // Set up fragment pager adapter
 * FragmentListPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
 * pagerAdapter.addFragment(new FooFragment(), getString(R.string.foo));
 * pagerAdapter.addFragment(new BarFragment(), getString(R.string.bar));
 *
 * // Set up view pager
 * ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
 * viewPager.setAdapter(pagerAdapter);
 *
 * // Set up  tab layout
 * TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
 * tabLayout.setTabMode(TabLayout.MODE_FIXED);
 * tabLayout.setupWithViewPager(viewPager);
 * </pre>
 */
public final class FragmentListPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<CharSequence> titles = new ArrayList<>();

    public FragmentListPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragment(Fragment fragment, CharSequence title) {
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
