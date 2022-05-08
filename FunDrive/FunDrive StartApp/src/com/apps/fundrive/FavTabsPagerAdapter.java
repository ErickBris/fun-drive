package com.apps.fundrive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FavTabsPagerAdapter extends FragmentPagerAdapter {

	public FavTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// ring fragment activity
			return new FavRingFragment();
		case 1:
			// wall fragment activity
			return new FavWallFragment();
		case 2:
			// video fragment activity
			return new FavVideoFragment();
		 
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
