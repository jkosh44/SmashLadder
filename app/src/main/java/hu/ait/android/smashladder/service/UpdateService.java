package hu.ait.android.smashladder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import hu.ait.android.smashladder.MatchListActivity;
import hu.ait.android.smashladder.RegisterActivity;

public class UpdateService extends Service {
    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (ParseUser.getCurrentUser().get(RegisterActivity.NAME_TAG).toString().equals("FayJ")) {
            try {

                //get all matches
                ParseQuery<ParseObject> query = ParseQuery.getQuery(MatchListActivity.MATCHES_TAG);
                query.findInBackground(new FindCallback<ParseObject>() {
                                           public void done(List<ParseObject> objects, ParseException e) {
                                               if (e == null) {

                                                   //go through every match
                                                   for (int i = 0; i < objects.size(); i++) {
                                                       ParseObject currMatch = objects.get(i);

                                                       //if the match hasn't been updated
                                                       if ((boolean) currMatch.get(MatchListActivity.MATCH_UPDATED) == false) {
                                                           currMatch.put(MatchListActivity.MATCH_UPDATED, true);
                                                           currMatch.saveInBackground();

                                                           //get the challenger user
                                                           final String challenger = currMatch.get(MatchListActivity.MATCH_CHALLENGER_KEY).toString();
                                                           final String opponent = currMatch.get(MatchListActivity.MATCH_OPPONENT_KEY).toString();


                                                           ParseQuery<ParseUser> query = ParseUser.getQuery();
                                                           query.findInBackground(new FindCallback<ParseUser>() {
                                                               @Override
                                                               public void done(List<ParseUser> objects, ParseException e) {
                                                                   int challengerRank = 0;
                                                                   int opponentRank = 0;

                                                                   for (int i = 0; i < objects.size(); i++) {
                                                                       ParseUser currUser = objects.get(i);

                                                                       if (currUser.get(RegisterActivity.NAME_TAG).toString().equals(challenger)) {
                                                                           challengerRank = currUser.getInt(RegisterActivity.RANK_TAG);
                                                                       }

                                                                       if (currUser.get(RegisterActivity.NAME_TAG).toString().equals(opponent)) {
                                                                           opponentRank = currUser.getInt(RegisterActivity.RANK_TAG);
                                                                       }
                                                                   }

                                                                   final int movement;
                                                                   int diff = challengerRank - opponentRank;
                                                                   if (diff == 1 || diff == 2) {
                                                                       movement = 1;
                                                                   } else if (diff == 3 || diff == 4) {
                                                                       movement = 2;
                                                                   } else {
                                                                       movement = 3;
                                                                   }
                                                                   int newRank = challengerRank - movement;


                                                                   for (int i = 0; i < objects.size(); i++) {
                                                                       ParseUser currUser = objects.get(i);

                                                                       int currRank = (int) currUser.get(RegisterActivity.RANK_TAG);

                                                                       if (currRank == challengerRank) {
                                                                           currUser.put(RegisterActivity.RANK_TAG, newRank);
                                                                           currUser.saveInBackground();
                                                                       } else if (currRank >= challengerRank - movement && currRank < challengerRank) {
                                                                           currUser.put(RegisterActivity.RANK_TAG, currRank + 1);
                                                                           currUser.saveInBackground();
                                                                       }
                                                                       currUser.saveInBackground();
                                                                   }
                                                               }
                                                           });

                                                       }
                                                   }
                                               } else {
                                                   e.printStackTrace();
                                               }
                                           }
                                       }

                );


            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

}

