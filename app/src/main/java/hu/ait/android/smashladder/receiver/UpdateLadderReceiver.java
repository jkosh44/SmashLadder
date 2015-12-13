package hu.ait.android.smashladder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import hu.ait.android.smashladder.MatchListActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.RegisterActivity;
import hu.ait.android.smashladder.data.UserComparator;
import hu.ait.android.smashladder.fragment.AddMatchDialog;

public class UpdateLadderReceiver extends BroadcastReceiver {
    public UpdateLadderReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (ParseUser.getCurrentUser().get(RegisterActivity.NAME_TAG).toString().equals("FayJ")) {
            try {

                //Get all users
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(final List<ParseUser> users, final ParseException e) {
                        if (e == null) {
                            Collections.sort(users, new UserComparator());

                            //Go through every user
                            for (int i = users.size() - 1; i >= 0; i--) {
                                final int challengerRank = users.get(i).getInt(RegisterActivity.RANK_TAG);
                                final ParseUser currUser = users.get(i);

                                //get his matches that haven't been updated yet
                                final ParseRelation<ParseObject> relation = users.get(i).getRelation(AddMatchDialog.MATCH_RELATION);
                                relation.getQuery().findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> matches, ParseException er) {
                                        if (e == null) {

                                            //Go through every match
                                            for (int posit = 0; posit < matches.size(); posit++) {
                                                String opponent = matches.get(posit).getString(MatchListActivity.MATCH_OPPONENT_KEY);
                                                int opponentRank;

                                                //gets the opponent rank
                                                for (int pos = 0; pos < users.size(); pos++) {
                                                    if (users.get(pos).get(RegisterActivity.NAME_TAG).toString().equals(opponent)) {
                                                        opponentRank = users.get(pos).getInt(RegisterActivity.RANK_TAG);
                                                        break;
                                                    }
                                                }
                                                //TODO: what to do here?
                                                if (opponentRank == null) {
                                                    opponentRank = 1;
                                                }
                                                int movement;
                                                int diff = challengerRank - opponentRank;
                                                if (diff == 1 || diff == 2) {
                                                    movement = 1;
                                                } else if (diff == 3 || diff == 4) {
                                                    movement = 2;
                                                } else {
                                                    movement = 3;
                                                }

                                                //Updates the rankings
                                                currUser.put(RegisterActivity.RANK_TAG, challengerRank - 3);
                                                for (int spot = users.size() - challengerRank + 1; spot <= users.size() - challengerRank + movement; spot++) {
                                                    int currRank = users.get(spot).getInt(RegisterActivity.RANK_TAG);
                                                    users.get(spot).put(RegisterActivity.RANK_TAG, currRank - 1);
                                                }

                                                relation.remove(matches.get(posit));
                                            }
                                        } else {
                                            er.printStackTrace();
                                        }
                                    }
                                });
                                currUser.saveInBackground();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception err) {
                Toast.makeText(context, R.string.update_failed, Toast.LENGTH_SHORT).show();
                err.printStackTrace();
            }
        }
    }
}
