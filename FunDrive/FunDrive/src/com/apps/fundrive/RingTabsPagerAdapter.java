package com.apps.fundrive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RingTabsPagerAdapter extends FragmentPagerAdapter {

	public RingTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new RingLatestFragment();
		case 1:
			return new RingCateFragment();
		case 2:
			return new RingMostPopularFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
