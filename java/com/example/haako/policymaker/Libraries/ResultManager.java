package com.example.haako.policymaker.Libraries;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by haako on 09.11.2016.
 */
public class ResultManager implements Serializable {

    /* USERNAME */

    private String username = "";

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    /* LOG */
    private ArrayList<String> log = new ArrayList<String>();

    public ArrayList<String> getLog() { return log; }
    public void logEntry(String entry) { log.add(entry); }

    /* TIME */
    private int totalTime = 0;

    public int getTotalTime() { return totalTime; }
    public void setTotalTime(int totalTime) { this.totalTime = totalTime; }

    private ArrayList<Integer> timePerCard = new ArrayList<Integer>();

    public ArrayList<Integer> getTimePerCard() { return timePerCard; }
    public void addTimePerCard(int timePerCard) { this.timePerCard.add(timePerCard); }

    /* CHOICES */
    private ArrayList<int[]> intResults = new ArrayList<int[]>();

    public ArrayList<int[]> getIntResults() { return intResults; }
    public void addArray(int[] array) {
        intResults.add(array);
        for (int i : array) {
            if (i == 0) {
                logEntry("STEMME FOR - Alternativ A");
            }
            else if (i == 1) {
                logEntry("STEMME FOR - Alternativ B");
            }
            else {
                logEntry("STEMME FOR - Alternativ C");
            }
        }
    }

    /* QUESTIONS */
    private ArrayList<String> stringResults = new ArrayList<String>();

    public ArrayList<String> getStringResults() { return stringResults; }
    public void addString(String result) { stringResults.add(result); }
}
