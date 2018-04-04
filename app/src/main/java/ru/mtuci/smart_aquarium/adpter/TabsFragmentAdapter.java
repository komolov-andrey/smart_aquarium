package ru.mtuci.smart_aquarium.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.mtuci.smart_aquarium.fragment.ParameterFragment;
import ru.mtuci.smart_aquarium.fragment.StateFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private String [] tabs;

    public TabsFragmentAdapter(FragmentManager fm) {
        super(fm);

        tabs = new String[] {"tab 1", "tab 2"};
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return StateFragment.getInstance();
            case 1:
                return ParameterFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

}