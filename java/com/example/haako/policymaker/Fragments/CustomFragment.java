package com.example.haako.policymaker.Fragments;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by haako on 14.11.2016.
 */
public abstract class CustomFragment extends Fragment implements Serializable {

    public abstract Boolean getPolitician();
    public abstract Boolean getRepresentative();
    public abstract Boolean getSelected();
    public abstract void setSelected(Boolean selected);
    public abstract String getDescription();
    public abstract String getInfo();
    public abstract int getID();

}
