package com.example.haako.policymaker.Libraries;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.widget.RadioButton;

import com.example.haako.policymaker.Actors.Actor;
import com.example.haako.policymaker.Cards.ActorCard;
import com.example.haako.policymaker.Cards.AnswerCard;
import com.example.haako.policymaker.Cards.CardSelect;
import com.example.haako.policymaker.Cards.ChoiceCard;
import com.example.haako.policymaker.Fragments.CustomFragment;
import com.example.haako.policymaker.R;
import com.example.haako.policymaker.Summary;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by haako on 09.11.2016.
 */
public class GameLogic implements Serializable {

    /* TEMP DATA STORAGE */

    private int voteFive = 0;

    /* PLAYER INFORMATION */
    private int players = 0;

    public int getPlayers() { return players; }
    public void setPlayers(int players) { this.players = players; }

    //PROGRESS
    private int progress = 1;

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    //WORLD MAP?
    private Boolean map = false;

    public void setMap(Boolean map) { this.map = map; }

    //STATUS
    private int expectedInnovation = 8;
    private int currentInnovation = 0;
    private int expectedFeasibility = 5;
    private int currentFeasibility = 0;
    private int expectedPopularity = 5;
    private int currentPopularity= 0;

    public int getExpectedInnovation() { return expectedInnovation; }
    public int getCurrentInnovation() { return currentInnovation; }
    public int getExpectedFeasibility() { return expectedFeasibility; }
    public int getCurrentFeasibility() { return currentFeasibility; }
    public int getExpectedPopularity() { return expectedPopularity; }
    public int getCurrentPopularity() { return currentPopularity; }

    //RESOURCES
    private int passion = 2;
    private int influence = 2;
    private int knowledge = 2;
    private int legwork = 1;
    private int network = 2;
    private int politicalCapital = 4;

    public int getPassion() { return passion; }
    public int getInfluence() { return influence; }
    public int getKnowledge() { return knowledge; }
    public int getLegwork() { return legwork; }
    public int getNetwork() { return network; }
    public int getPoliticalCapital() { return politicalCapital; }

    //TRADE RESOURCES
    public void tradePassion(ResultManager rm) { passion++; politicalCapital--; rm.logEntry("KJØPT RESSURS - Engasjement"); }
    public void tradeInfluence(ResultManager rm) { influence++; politicalCapital--; rm.logEntry("KJØPT RESSURS - Innflytelse"); }
    public void tradeKnowledge(ResultManager rm) { knowledge++; politicalCapital--; rm.logEntry("KJØPT RESSURS - Kunnskap");}
    public void tradeLegwork(ResultManager rm) { legwork++; politicalCapital--; rm.logEntry("KJØPT RESSURS - Benarbeid"); }
    public void tradeNetwork(ResultManager rm) { network++; politicalCapital--; rm.logEntry("KJØPT RESSURS - Nettverk"); }

    /* ACTORS */
    private int requiredActors = 5;

    private int requiredPoliticians = 0;

    private int requiredReps = 0;

    public int getRequiredActors() { return requiredActors; }
    public void setRequiredActors(int actors) { this.requiredActors = actors; }
    public int getRequiredPoliticians() { return requiredPoliticians; }
    public void setRequiredPoliticians(int politicians) { this.requiredPoliticians = politicians; }
    public int getRequiredReps() { return requiredReps; }
    public void setRequiredReps(int reps) { this.requiredReps = reps; }

    //SELECTED ACTOR IDS
    private ArrayList<CustomFragment> selectedActors = new ArrayList<CustomFragment>();

    public ArrayList<CustomFragment> getSelectedActors() { return selectedActors; }
    public void addActor(CustomFragment cf) {  selectedActors.add(cf); }

    /* OPERATIONS */

    //FIND MOST POPULAR ALTERNATIVE
    public int getPopularElement(int[] a) {
        int count = 1, tempCount;
        int popular = a[0];
        int temp = 0;
        for (int i = 0; i < (a.length - 1); i++)
        {
            temp = a[i];
            tempCount = 0;
            for (int j = 1; j < a.length; j++)
            {
                if (temp == a[j])
                    tempCount++;
            }
            if (tempCount > count)
            {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }

    //POPULATE GAME CARD WITH STRINGS
    public String getStringByName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

    public int getImageIDByName(Context context, String name) {
        Resources res = context.getResources();
        return res.getIdentifier(name, "drawable", context.getPackageName());
    }

    //CHECK AVAILIABLE RESOURCES
    public void checkResources(RadioButton a, RadioButton b, RadioButton c) {
        if (progress == 1) {
            if (network < 1 || knowledge < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            else if (legwork < 1 || knowledge < 1 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            else if (passion < 1 || influence < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 2) {
            if (influence < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            else if (network < 1 || passion < 1 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            else if (network < 2 || knowledge < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 3) {
            //ACTOR CARD
        }
        else if (progress == 4) {
            //ANSWER CARD
        }
        else if (progress == 5) {
            if (influence < 1  ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            else if (legwork < 1 || network < 2 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            else if (passion < 1 || knowledge < 1 || legwork < 2 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 6) {
            if (knowledge < 1 || passion < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            else if (passion < 1 || influence < 1 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            else if (network < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 7) {
            if (network < 1 || passion < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            else if (knowledge < 1 || influence < 1 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            else if (knowledge< 1 || passion < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 8) {
            if (legwork < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            else if (legwork < 1 || influence < 1 || passion < 1 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            else if (network < 2 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 9) {
            //ACTOR CARD
        }
        else if (progress == 10) {
            if (passion < 2 || network < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            if (knowledge < 2 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            if (network < 1 || legwork < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 11) {
            if (passion < 2 || network < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            if (knowledge < 2 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            if (network < 1 || legwork < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 12) {
            //ANSWER CARD
        }
        else if (progress == 13) {
            if (passion < 2 || network < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            if (knowledge < 2 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            if (network < 1 || legwork < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
        else if (progress == 14) {
            if (passion < 2 || network < 1 ) {
                a.setClickable(false);
                a.setTextColor(Color.LTGRAY);
            }
            if (knowledge < 2 ) {
                b.setClickable(false);
                b.setTextColor(Color.LTGRAY);
            }
            if (network < 1 || legwork < 1 ) {
                c.setClickable(false);
                c.setTextColor(Color.LTGRAY);
            }
        }
    }

    //TRADE RESOURCES AFTER CHOICE
    public void tradeResources(Context context, int vote) {
        if (progress == 1) {
            if (vote == 0) {
                knowledge -= 1;
                network -= 1;
                expectedPopularity += 2;
                expectedInnovation += 1;
            }
            else if (vote == 1) {
                knowledge -= 1;
                legwork -= 1;
                expectedFeasibility += 2;
            }
            else if (vote == 2) {
                passion -= 1;
                influence -= 1;
                expectedInnovation += 2;
                expectedFeasibility += 1;
            }
        }
        else if (progress == 2) {
            if (vote == 0) {
                influence -= 1;
                currentFeasibility += 1;
                requiredPoliticians = 3;
            }
            else if (vote == 1) {
                network -= 1;
                passion -= 1;
                currentFeasibility += 1;
                currentPopularity += 1;
                requiredReps = 3;
            }
            else if (vote == 2) {
                network -= 2;
                knowledge -= 1;
                currentInnovation += 2;
                currentPopularity += 1;
            }
        }
        else if (progress == 3) {
            for(CustomFragment cf : selectedActors) {
                tradeActor(cf);
            }
        }
        else if (progress == 4) {
            //ANSWER CARD
        }
        else if (progress == 5) {
            voteFive = vote;
            if (vote == 0) {
                influence -= 1;
                currentFeasibility += 1;
                if (passion > 0) {
                    passion -= 1;
                }
            }
            else if (vote == 1) {
                network -= 2;
                legwork -= 1;
                for (CustomFragment cf : selectedActors) {
                    if (cf.getDescription().equals(context.getString(R.string.actor4head)) || cf.getDescription().equals(context.getString(R.string.actor12head))) {
                        network += 2;
                    }
                }
                currentInnovation += 1;
                currentPopularity += 1;
                currentFeasibility += 1;
            }
            else if (vote == 2) {
                passion -= 1;
                knowledge -= 1;
                legwork -= 2;
                currentInnovation += 1;
                currentPopularity += 2;
                currentFeasibility -= 1;
            }
        }
        else if (progress == 6) {
            if (vote == 0) {
                knowledge -= 1;
                passion -= 1;
                currentFeasibility += 1;
                currentPopularity += 1;
            }
            else if (vote == 1) {
                passion -= 1;
                influence -= 1;
                currentInnovation += 1;
                currentFeasibility += 1;
            }
            else if (vote == 2) {
                network -= 1;
                currentInnovation -= 1;
            }
        }
        else if (progress == 7) {
            if (vote == 0) {
                network -= 1;
                passion -= 1;
                currentFeasibility += 1;
                influence += 2;
                addActor(new Actor(getImageIDByName(context, "actor14"), context.getString(R.string.actor14head),context.getString(R.string.actor14desc), context.getString(R.string.actor14rep), context.getString(R.string.actor14pol), context.getString(R.string.actor14value)));
            }
            else if (vote == 1) {
                knowledge -= 1;
                influence -= 1;
                currentInnovation += 1;
                currentFeasibility -= 1;
                if (influence > 0) {
                    influence -= 1;
                }
            }
            else if (vote == 2) {
                knowledge -= 1;
                passion -= 1;
                currentFeasibility += 2;
                if (passion > 0 && knowledge > 0) {
                    passion -= 1;
                    knowledge -= 1;
                    influence += 1;
                }
            }
        }
        else if (progress == 8) {
            if (vote == 0) {
                legwork -= 1;
                currentInnovation += 1;
                currentPopularity -= 1;
                currentFeasibility -= 1;
                for (CustomFragment cf : selectedActors) {
                    if (cf.getDescription().equals(context.getString(R.string.actor4head))) {
                        currentFeasibility += 1;
                    }
                }
            }
            else if (vote == 1) {
                legwork -= 1;
                influence -= 1;
                passion -= 1;
                for(CustomFragment cf : selectedActors){
                    if(cf.getDescription().equals(context.getString(R.string.actor5head)) || cf.getDescription().equals(context.getString(R.string.actor11head))){
                        legwork += 1;
                    }
                }
                currentInnovation += 2;
                currentPopularity += 1;
                if (legwork > 0 ) {
                    legwork -= 1;
                }
                else {
                    if (passion > 0 ) {
                        passion -= 1;
                    }
                }
            }
            else if (vote == 2) {
                network -= 2;
                currentInnovation += 1;
                currentFeasibility += 1;
                if (passion > 0 ) {
                    passion -= 1;
                }
            }
        }
        else if (progress == 9) {
            ArrayList<CustomFragment> temp = new ArrayList<CustomFragment>();
            temp.add(selectedActors.get(selectedActors.size() - 1));
            temp.add(selectedActors.get(selectedActors.size() - 2));
            for(CustomFragment cf : temp){
               tradeActor(cf);
            }
        }
        else if (progress == 10) {
            if (vote == 0) {
                passion -= 2;
                network -= 1;
                currentInnovation += 2;
                currentPopularity -= 1;
                if(passion > 0){
                    passion -= 1;
                    knowledge += 1;
                }
            }
            else if (vote == 1) {
                for(CustomFragment cf : selectedActors) {
                    if(cf.getDescription().equals(context.getString(R.string.actor5head))){
                        if(legwork > 0){
                            legwork -= 1;
                        }
                        else{
                            knowledge -= 2;
                        }
                    }

                }
                currentFeasibility += 1;
                currentInnovation += 1;
                passion += 1;
                knowledge += 1;
            }
            else if (vote == 2) {
                network -= 1;
                legwork -= 1;
                currentInnovation += 1;
                currentPopularity += 2;
                if(legwork > 0){
                    legwork -= 1;
                }
            }
        }
        else if (progress == 11) {
            if (vote == 0) {
                legwork -= 1;
                knowledge -= 1;
                currentPopularity += 1;
                currentFeasibility += 1;
                currentInnovation -= 1;
            }
            else if (vote == 1) {
                knowledge -= 1;
                passion -= 1;
                influence -= 1;
                currentInnovation += 2;
                currentFeasibility += 1;
                if(passion > 0){
                    passion -= 1;
                }
            }
            else if (vote == 2) {
                passion -= 1;
                legwork -= 1;
                currentInnovation += 2;
                currentPopularity += 1;
                currentFeasibility -= 1;
            }
        }
        else if (progress == 12) {
            //ANSWER CARD
        }
        else if (progress == 13) {
            if (vote == 0) {
                influence -= 2;
                currentFeasibility += 2;
                currentInnovation -= 1;
            }
            else if (vote == 1) {
                network -= 2;
                for(CustomFragment cf : selectedActors){
                    if(cf.getDescription().equals(context.getString(R.string.actor14head))){
                        network += 1;
                    }
                }
                currentInnovation += 1;
                currentFeasibility += 1;
                if (voteFive == 1) {
                    currentInnovation -= 2;
                    currentFeasibility -= 2;
                }

            }
            else if (vote == 2) {
                legwork -= 1;
                knowledge -= 2;
                influence -= 1;
                currentInnovation += 2;
            }
        }
        else if (progress == 14) {
            if (vote == 0) {
                influence -= 1;
                legwork -= 1;
                for(CustomFragment cf : selectedActors){
                    if(cf.getDescription().equals(context.getString(R.string.actor12head)) || cf.getDescription().equals(context.getString(R.string.actor9head))){
                        influence += 1;
                        legwork += 1;
                    }
                }
                currentFeasibility += 1;
                currentInnovation -= 1;
                currentPopularity -= 1;
            }
            else if (vote == 1) {
                network -= 1;
                knowledge -= 1;
                currentFeasibility += 1;
                currentInnovation += 1;
                currentPopularity += 1;
            }
            else if (vote == 2) {
                influence -= 2;
                network -= 1;
                legwork -= 1;
                for(CustomFragment cf : selectedActors){
                    if(cf.getDescription().equals(context.getString(R.string.actor4head))){
                        influence += 1;
                        network += 1;
                    }
                }
                currentFeasibility += 1;
                currentInnovation += 1;
                currentPopularity += 2;
                for(CustomFragment cf : selectedActors){
                    if(cf.getDescription().equals(context.getString(R.string.actor3head))){
                        currentFeasibility += 1;
                    }
                }
            }
        }
    }

    //ACTOR TRADE VALUES
    private void tradeActor(CustomFragment cf) {
        if(cf.getDescription().equals("Lederen for det største opposisjonspartiet")){
            influence += 1;
            network += 2;
        }
        else if (cf.getDescription().equals("Engasjert forelder")){
            passion += 2;
            legwork += 1;
            knowledge += 1;
        }
        else if (cf.getDescription().equals("Læreren")){
            passion += 1;
            knowledge += 1;
            legwork += 2;
        }
        else if (cf.getDescription().equals("Fellestillitsmannen")){
            network += 1;
            legwork += 1;
            knowledge += 1;
        }
        else if (cf.getDescription().equals("Utviklingskonsulent")){
            passion += 1;
            knowledge += 1;
            legwork += 1;
        }
        else if (cf.getDescription().equals("Elevrådsformannen")){
            passion += 1;
            legwork += 2;
        }
        else if (cf.getDescription().equals("Lederen for Nordregårdsskolen")){
            passion += 1;
            network += 1;
            knowledge += 1;
        }
        else if (cf.getDescription().equals("Lokalformann for skole og foreldre")){
            passion += 1;
            influence += 1;
            network += 1;
        }
        else if (cf.getDescription().equals("Borgermesteren")){
            influence += 3;
            expectedPopularity += 1;
        }
        else if (cf.getDescription().equals("Rektor for teknisk skole")){
            passion += 1;
            network += 2;
        }
        else if (cf.getDescription().equals("Professor i pedagogikk")){
            knowledge += 3;
            expectedInnovation += 1;
        }
        else if (cf.getDescription().equals("Skoledirektøren")){
            influence += 1;
            knowledge += 1;
            network += 1;
            expectedFeasibility += 1;
        }
        else if (cf.getDescription().equals("Politiker for regionsrådet")){
            passion += 1;
            influence += 1;
            network += 1;
        }
    }

    //LOAD APPROPRIATE CARD
    public Intent loadCard(Context context, ResultManager rm) {
        if (map) {
            map = false;
            Intent intent = new Intent(context, CardSelect.class);
            intent.putExtra("GameLogic", this);
            intent.putExtra("ResultManager", rm);
            return intent;
        }
        else if (progress == 1 || progress == 2 || progress == 5 || progress == 6 || progress == 7 || progress == 8 || progress == 10 || progress == 11 || progress == 13 || progress == 14) {
            Intent intent = new Intent(context, ChoiceCard.class);
            intent.putExtra("GameLogic", this);
            intent.putExtra("ResultManager", rm);
            return intent;
        }
        else if (progress == 3 || progress == 9) {
            Intent intent = new Intent(context, ActorCard.class);
            intent.putExtra("GameLogic", this);
            intent.putExtra("ResultManager", rm);
            return intent;
        }
        else if (progress == 4 || progress == 12) {
            Intent intent = new Intent(context, AnswerCard.class);
            intent.putExtra("GameLogic", this);
            intent.putExtra("ResultManager", rm);
            return intent;
        }
        else if (progress == 15) {
            Intent intent = new Intent(context, Summary.class);
            intent.putExtra("GameLogic", this);
            intent.putExtra("ResultManager", rm);
            return intent;
        }
        return null;
    }
}
