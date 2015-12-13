package hu.ait.android.smashladder;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.AEADBadTagException;

import hu.ait.android.smashladder.adapter.PlayerAdapter;
import hu.ait.android.smashladder.data.UserComparator;

public class PlayerListActivity extends AppCompatActivity {
    //TODO: add way to get to MatchListActivity

    private PlayerAdapter playerAdapter;

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

                //Get all users
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(final List<ParseUser> users, final ParseException e) {
                        if (e == null) {
                            Collections.sort(users, new UserComparator());

                            //Go through every user
                            for (int i = users.size() - 1; i >= 0; i--) {
                                final int challengerRank = (int) users.get(i).get(RegisterActivity.RANK_TAG);
                                final ParseUser currUser = users.get(i);

                                //get his matches that haven't been updated yet
                                ArrayList<ParseObject> matches = (ArrayList<ParseObject>) currUser.get(RegisterActivity.CURRENT_MATCHES);

                                //Go through every match
                                for (int posit = 0; posit < matches.size(); posit++) {
                                    String opponent = matches.get(posit).getString(MatchListActivity.MATCH_OPPONENT_KEY);
                                    int opponentRank = 0;

                                    //gets the opponent rank
                                    for (int pos = 0; pos < users.size(); pos++) {
                                        if (users.get(pos).get(RegisterActivity.NAME_TAG).toString().equals(opponent)) {
                                            opponentRank = users.get(pos).getInt(RegisterActivity.RANK_TAG);
                                            break;
                                        }

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

                                }

                                currUser.put(RegisterActivity.CURRENT_MATCHES, new ArrayList<ParseObject>());
                                currUser.saveInBackground();
                            }
                        } else {
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

