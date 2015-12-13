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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import hu.ait.android.smashladder.adapter.PlayerAdapter;

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
}
