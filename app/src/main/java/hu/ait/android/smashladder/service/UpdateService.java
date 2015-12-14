package hu.ait.android.smashladder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

import hu.ait.android.smashladder.MatchListActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.RegisterActivity;

public class UpdateService extends Service {
    private boolean enabled = false;
    private MyUpdateThread myUpdateThread = null;

    public UpdateService() {
    }

    private class MyUpdateThread extends Thread {
        public void run() {
            android.os.Handler h = new android.os.Handler(UpdateService.this.getMainLooper());

            while (enabled) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        //TODO: add what to do here
                        try {
                            testUpdate();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        enabled = true;
        if(myUpdateThread == null) {
            myUpdateThread = new MyUpdateThread();
        }
        myUpdateThread.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        enabled = false;
        super.onDestroy();
    }

    private void testUpdate() {
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


                                    ParseQuery<ParseObject> query = ParseQuery.getQuery(RegisterActivity.RANK);
                                    query.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> objects, ParseException e) {
                                            int challengerRank = 0;
                                            int opponentRank = 0;

                                            for (int i = 0; i < objects.size(); i++) {
                                                ParseObject currRankClass = objects.get(i);

                                                if (currRankClass.get(RegisterActivity.PLAYER).toString().equals(challenger)) {
                                                    challengerRank = currRankClass.getInt(RegisterActivity.NUMBER);
                                                }

                                                if (currRankClass.get(RegisterActivity.PLAYER).toString().equals(opponent)) {
                                                    opponentRank = currRankClass.getInt(RegisterActivity.NUMBER);
                                                }
                                            }

                                            int movement;
                                            int diff = challengerRank - opponentRank;
                                            if (diff > 0) {
                                                if (diff == 1 || diff == 2) {
                                                    movement = 1;
                                                } else if (diff == 3 || diff == 4) {
                                                    movement = 2;
                                                } else {
                                                    movement = 3;
                                                }
                                                int newRank = challengerRank - movement;

                                                for (int i = 0; i < objects.size(); i++) {
                                                    ParseObject currRankClass = objects.get(i);

                                                    int currRank = (int) currRankClass.get(RegisterActivity.NUMBER);
                                                    String currUser = currRankClass.get(RegisterActivity.PLAYER).toString();


                                                    if (currUser.equals(challenger)) {
                                                        currRankClass.put(RegisterActivity.NUMBER, newRank);
                                                        currRankClass.saveInBackground();
                                                    } else if (currRank >= challengerRank - movement && currRank < challengerRank) {
                                                        currRankClass.put(RegisterActivity.NUMBER, currRank + 1);
                                                        currRankClass.saveInBackground();
                                                    }
                                                    currRankClass.saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if (e != null) {
                                                                 e.printStackTrace();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });


                                }
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }
                });


            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

}

