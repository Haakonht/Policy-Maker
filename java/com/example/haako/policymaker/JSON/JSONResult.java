package com.example.haako.policymaker.JSON;

/**
 * Created by haako on 15.11.2016.
 */
import java.util.ArrayList;

import com.example.haako.policymaker.Fragments.CustomFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONResult {

    public JSONObject generateResult(int totalTime, int score, int players, String user, ArrayList<int[]> votes, ArrayList<Integer> timePerCard, ArrayList<CustomFragment> selectedActors, ArrayList<String> stringResults) {

        JSONArray gameVotes = new JSONArray();
        for (int[] arr : votes) {
            for (int i : arr) {
                gameVotes.put(i);
            }
        }

        JSONArray tpc = new JSONArray();
        for (int i : timePerCard) {
            tpc.put(i);
        }

        JSONArray actors = new JSONArray();
        for (int i = 0; i < selectedActors.size(); i++) {
            actors.put(selectedActors.get(i).getDescription());
        }

        JSONArray reflection = new JSONArray();
        for (String s : stringResults) {
            reflection.put(s);
        }

        JSONObject gameResult = new JSONObject();
        try {
            gameResult.put("totalTime", totalTime);
            gameResult.put("score", score);
            gameResult.put("players", players);
            gameResult.put("username", user);
            gameResult.put("votes", gameVotes);
            gameResult.put("timePerCard", tpc);
            gameResult.put("actors", actors);
            gameResult.put("answers", reflection);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gameResult;
    }



}
