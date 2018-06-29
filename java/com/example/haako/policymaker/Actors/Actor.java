package com.example.haako.policymaker.Actors;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.haako.policymaker.Fragments.CustomFragment;
import com.example.haako.policymaker.R;

/**
 * Created by haako on 23/05/2017.
 */

public class Actor extends CustomFragment {

    private int id = R.layout.actor;
    private String description = "";
    private String info = "";
    private String value = "";
    private int imageID;
    private Boolean isSelected = false;
    private Boolean isPolitician = false;
    private Boolean isRepresentative = false;
    private Boolean forced = false;

    public Actor() { }

    public Actor(int imageID, String headline, String description, String isRep, String isPol, String value) {
        this.description = headline;
        this.info = description;
        this.imageID = imageID;
        this.value = value;
        if (isRep.equals("Yes")) {
            isRepresentative = true;
        }
        if (isPol.equals("Yes")) {
            isPolitician = true;
        }
    }

    public Actor(int imageID, String headline, String description, String isRep, String isPol, String value, Boolean forced) {
        this.description = headline;
        this.info = description;
        this.imageID = imageID;
        this.value = value;
        this.forced = forced;
        if (isRep.equals("Yes")) {
            isRepresentative = true;
        }
        if (isPol.equals("Yes")) {
            isPolitician = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(id, container, false);
        TextView actorHeadline = (TextView) v.findViewById(R.id.actor_headline);
        actorHeadline.setText(description);
        TextView actorDescription = (TextView) v.findViewById(R.id.actor_description);
        actorDescription.setText(info);
        TextView actorValue = (TextView) v.findViewById(R.id.actor_value);
        actorValue.setText(value);
        ImageView image = (ImageView) v.findViewById(R.id.actor_image);
        image.setImageResource(imageID);
        ToggleButton tb = (ToggleButton)v.findViewById(R.id.actor_selectedBtn);
        tb.setSelected(isSelected);
        if (isSelected) {
            tb.setTextColor(Color.GREEN);
        }
        if (forced) {
            tb.setVisibility(View.GONE);
        }
        return v;
    }

    public int getID() { return id; }
    public Boolean getSelected() { return isSelected; }
    public void setSelected(Boolean selected) { this.isSelected = selected; }
    public Boolean getPolitician() { return isPolitician; }
    public Boolean getRepresentative()  { return isRepresentative; }
    public String getDescription() { return description; }
    public String getInfo() { return info; }

}
