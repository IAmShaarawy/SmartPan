package net.elshaarawy.smartpan.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.elshaarawy.smartpan.Fragments.ContactsFragment;
import net.elshaarawy.smartpan.Fragments.CountriesFragment;
import net.elshaarawy.smartpan.Fragments.MapFragment;

import java.util.HashMap;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private HashMap<String , Fragment> fragmentsMap;
    private static final String COUNTRIES = "countries";
    private static final String CONTACTS = "contacts";
    private static final String MAP = "map";
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentsMap = new HashMap<>();
        fragmentsMap.put(COUNTRIES,new CountriesFragment());
        fragmentsMap.put(CONTACTS,new ContactsFragment());
        fragmentsMap.put(MAP,new MapFragment());

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragmentsMap.get(COUNTRIES);
            case 1:
                return fragmentsMap.get(CONTACTS);
            case 2:
                return fragmentsMap.get(MAP);
            default:
                throw  new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return COUNTRIES;
            case 1:
                return CONTACTS;
            case 2:
                return MAP;
            default:
                throw  new IndexOutOfBoundsException();
        }
    }
}
