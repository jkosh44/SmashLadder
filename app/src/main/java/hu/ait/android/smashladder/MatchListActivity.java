package hu.ait.android.smashladder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
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
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import hu.ait.android.smashladder.adapter.MatchAdapter;
import hu.ait.android.smashladder.data.MatchItem;
import hu.ait.android.smashladder.fragment.AddMatchDialog;

public class MatchListActivity extends AppCompatActivity implements AddMatchDialog.AddMatchFragmentInterface {

    //TODO: should these be moved to AddMatchDialog?
    public static final String MATCHES_TAG = "Matches";
    public static final String MATCH_CHALLENGER_KEY = "MATCH_CHALLENGER_KEY";
    public static final String MATCH_OPPONENT_KEY = "MATCH_OPPONENT_KEY";
    public static final String MATCH_WINNER_KEY = "MATCH_WINNER_KEY";
    public static final String MATCH_CHALLENGER_CHARACHTER_KEY = "MATCH_CHALLENGER_CHARACTER_KEY";
    public static final String MATCH_OPPONENT_CHARACTER_KEY = "MATCH_OPPONENT_CHARACTER_KEY";
    public static final String MATCH_STAGE_KEY = "MATCH_STAGE_KEY";
    public static final String MATCH_UPDATED = "MATCH_UPDATED";

    //TODO: initialize adapter
    private MatchAdapter matchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.matchListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Context context = this;


        ParseQuery<ParseObject> query = ParseQuery.getQuery(MATCHES_TAG);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    matchAdapter = new MatchAdapter(objects, context, getSupportFragmentManager());

                    RecyclerView recyclerViewMatchItem = (RecyclerView) findViewById(R.id.match_recycler_view);
                    recyclerViewMatchItem.setLayoutManager(new LinearLayoutManager(context));
                    recyclerViewMatchItem.setAdapter(matchAdapter);
                    recyclerViewMatchItem.setVisibility(View.VISIBLE);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.match_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_add_match:
                addMatch();
                return true;
            default:
                return true;
        }
    }

    public void addMatch() {
        final AddMatchDialog dialog = new AddMatchDialog();

        dialog.setCancelable(false);

        dialog.show(getSupportFragmentManager(), AddMatchDialog.TAG);
    }


    @Override
    public void onAddMatchFragmentResult(MatchItem match) {
        matchAdapter.addMatchItem(match);
    }


}
