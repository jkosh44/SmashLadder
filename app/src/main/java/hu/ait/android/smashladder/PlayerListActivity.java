package hu.ait.android.smashladder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

import hu.ait.android.smashladder.adapter.PlayerAdapter;
import hu.ait.android.smashladder.receiver.UpdateLadderReceiver;

public class PlayerListActivity extends AppCompatActivity {
    //TODO: add way to get to MatchListActivity

    private PlayerAdapter playerAdapter;

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.playerListToolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                //playerUserList = objects;
                playerAdapter = new PlayerAdapter(objects, context);

                RecyclerView recyclerViewPlayerItem = (RecyclerView) findViewById(R.id.player_recycler_view);
                recyclerViewPlayerItem.setLayoutManager(new LinearLayoutManager(context));
                recyclerViewPlayerItem.setAdapter(playerAdapter);
                recyclerViewPlayerItem.setVisibility(View.VISIBLE);
            }
        });

        Intent updateIntent = new Intent(PlayerListActivity.this, UpdateLadderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(PlayerListActivity.this, 0, updateIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        Button btnTestUpdate = (Button) findViewById(R.id.btnTestUpdate);
        btnTestUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testUpdate();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.player_recycler_view);
                recyclerView.invalidate();
                Toast.makeText(PlayerListActivity.this, "Currently updating", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.player_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start_match_list:
                Intent matchListIntent = new Intent(PlayerListActivity.this, MatchListActivity.class);
                startActivity(matchListIntent);
                return true;
            default:
                return true;
        }
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

                                            Toast.makeText(PlayerListActivity.this, "Challenger Rank: " + challengerRank + "\n Opponent Rank: " + opponentRank + "\nMovement: " + movement + "\nnew rank: " + newRank, Toast.LENGTH_LONG).show();

                                            for (int i = 0; i < objects.size(); i++) {
                                                ParseUser currUser = objects.get(i);

                                                int currRank = (int) currUser.get(RegisterActivity.RANK_TAG);

                                                if (currRank == challengerRank) {
                                                    currUser.put(RegisterActivity.RANK_TAG, newRank);
                                                    Toast.makeText(PlayerListActivity.this, currUser.get(RegisterActivity.NAME_TAG).toString() + ", the challenger " + newRank, Toast.LENGTH_SHORT).show();
                                                    currUser.saveInBackground();
                                                } else if (currRank >= challengerRank - movement && currRank < challengerRank) {
                                                    currUser.put(RegisterActivity.RANK_TAG, currRank + 1);
                                                    Toast.makeText(PlayerListActivity.this, currUser.get(RegisterActivity.NAME_TAG).toString() + " new rank" + (currRank + 1), Toast.LENGTH_SHORT).show();
                                                    currUser.saveInBackground();
                                                }
                                                currUser.saveInBackground();
                                            }
                                        }
                                    });

                                }
                            }
                        } else {
                            Toast.makeText(PlayerListActivity.this, R.string.update_failed, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });


            } catch (Exception err) {
                Toast.makeText(this, R.string.update_failed, Toast.LENGTH_SHORT).show();
                err.printStackTrace();
            }
        }
    }
}

