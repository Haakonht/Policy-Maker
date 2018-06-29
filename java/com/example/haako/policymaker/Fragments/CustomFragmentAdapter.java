package com.example.haako.policymaker.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.haako.policymaker.Actors.Actor;
import com.example.haako.policymaker.Libraries.GameLogic;

import java.util.ArrayList;

/**
 * Created by haako on 14.11.2016.
 */
public class CustomFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<CustomFragment> actors = new ArrayList<CustomFragment>();

    public CustomFragmentAdapter(FragmentManager fm, Context context, GameLogic gl)
    {
        super(fm);
        for (int i = 1; i < 14; i++) {
            actors.add(new Actor(gl.getImageIDByName(context, "actor"+i), gl.getStringByName(context, "actor"+i+"head"), gl.getStringByName(context, "actor"+i+"desc"), gl.getStringByName(context, "actor"+i+"rep"), gl.getStringByName(context, "actor"+i+"pol"), gl.getStringByName(context, "actor"+i+"value")));
        }
    }

    public void removeDuplicates(ArrayList<CustomFragment> selectedActors) {
        for (int i = 0; i < actors.size(); i++) {
            for (CustomFragment cf : selectedActors) {
                if (actors.get(i).equals(cf)) {
                    actors.remove(i);
                }
            }
        }
    }

    public ArrayList<CustomFragment> getActors() { return actors; }

    @Override
    public int getCount()
    {
        return actors.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0: return actors.get(0);
            case 1: return actors.get(1);
            case 2: return actors.get(2);
            case 3: return actors.get(3);
            case 4: return actors.get(4);
            case 5: return actors.get(5);
            case 6: return actors.get(6);
            case 7: return actors.get(7);
            case 8: return actors.get(8);
            case 9: return actors.get(9);
            case 10: return actors.get(10);
            case 11: return actors.get(11);
            case 12: return actors.get(12);
            case 13: return actors.get(13);
            default : return actors.get(2);
        }
    }
}
