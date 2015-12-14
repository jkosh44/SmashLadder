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
import com.parse.SaveCallback;

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

        //get all matches
        ParseQuery<ParseObject> query = ParseQuery.getQuery(RegisterActivity.RANK);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    playerAdapter = new PlayerAdapter(objects, context);

                    RecyclerView recyclerViewPlayerItem = (RecyclerView) findViewById(R.id.player_recycler_view);
                    recyclerViewPlayerItem.setLayoutManager(new LinearLayoutManager(context));
                    recyclerViewPlayerItem.setAdapter(playerAdapter);
                    recyclerViewPlayerItem.setVisibility(View.VISIBLE);
                } else {
                    e.printStackTrace();
                }
            }

        });

        Intent updateIntent = new Intent(PlayerListActivity.this, UpdateLadderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(PlayerListActivity.this, 0, updateIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Button btnTestUpdate = (Button) findViewById(R.id.btnTestUpdate);
        btnTestUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testUpdate();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.player_recycler_view);
                recyclerView.invalidate();
                Toast.makeText(PlayerListActivity.this, R.string.currently_updating, Toast.LENGTH_SHORT).show();
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
        if (ParseUser.getCurrentUser().get(RegisterActivity.NAME_TAG).toString().equals(getString(R.string.fayJ))) {
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
                            Toast.makeText(PlayerListActivity.this, R.string.done_updating, Toast.LENGTH_SHORT).show();
                        } else {
                            e.printStackTrace();
                        }
                    }
                });


            } catch (Exception err) {
                err.printStackTrace();
            }
        } else {
            Toast.makeText(PlayerListActivity.this, R.string.must_be_fayj, Toast.LENGTH_SHORT).show();
        }
    }
}

