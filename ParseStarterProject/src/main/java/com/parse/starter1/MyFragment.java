package com.parse.starter1;

/**
 * Created by ashishyadav271 on 2/12/2016.
 */
import android.support.v4.app.Fragment;

public class MyFragment extends Fragment {

    public static Fragment getInstance(int position) {
        if(position==0)
            return new DealsFragment();
        else
            return new FavFragment();
    }
}

