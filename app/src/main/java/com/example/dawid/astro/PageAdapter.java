package com.example.dawid.astro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;




    public class PageAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return moon.newInstance("0", "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return sunFragment.newInstance("1", "Page # 2");
                    case 2: // Fragment # 0 - This will show FirstFragment different title
                    return Time.newInstance("2", "Page # 3");
                case 3: // Fragment # 0 - This will show FirstFragment different title
                    return SimpleFragment.newInstance("3", "Page # 4");
                    default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
}
