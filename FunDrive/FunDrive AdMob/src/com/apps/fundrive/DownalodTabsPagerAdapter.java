package com.apps.fundrive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DownalodTabsPagerAdapter extends FragmentPagerAdapter {

	public DownalodTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			//  ring fragment activity
			return new DownloadRingtone();
		case 1:
			// wall fragment activity
			return new DownloadWallpaper();
		 
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}
