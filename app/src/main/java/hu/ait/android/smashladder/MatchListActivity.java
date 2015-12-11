package hu.ait.android.smashladder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import hu.ait.android.smashladder.adapter.MatchAdapter;
import hu.ait.android.smashladder.data.MatchItem;
import hu.ait.android.smashladder.fragment.AddMatchDialog;

public class MatchListActivity extends AppCompatActivity implements AddMatchDialog.AddMatchFragmentInterface {

    public static final String MATCHES_TAG = "Matches";
    public static final String MATCH_CHALLENGER_KEY = "MATCH_CHALLENGER_KEY";
    public static final String MATCH_OPPONENT_KEY = "MATCH_OPPONENT_KEY";
    public static final String MATCH_WINNER_KEY = "MATCH_WINNER_KEY";
    //TODO: initialize adapter
    private MatchAdapter matchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.playerListToolbar);
        setSupportActionBar(toolbar);

        final Context context = this;


        /*ParseQuery<ParseObject> query = ParseQuery.getQuery(MATCHES_TAG);
        //TODO: make sure the queries are all matches, uncomment when you add matches to parse
        //TODO: why does this crash?
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                matchAdapter = new MatchAdapter(objects, context);

                RecyclerView recyclerViewMatchItem = (RecyclerView) findViewById(R.id.player_recycler_view);
                recyclerViewMatchItem.setLayoutManager(new LinearLayoutManager(context));
                recyclerViewMatchItem.setAdapter(matchAdapter);
                recyclerViewMatchItem.setVisibility(View.VISIBLE);
            }
        });*/
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

    /*//TODO start matchdetails dialog, make sure to bundle matchItem. should this be implemented here?
    public void startMatchDetails() {
        final MatchDetailsDialog dialog = new MatchDetailsDialog();
        //TODO: fix
        dialog.show(getSupportFragmentManager(), MatchDetailsDialog.TAG);
    }*/


    @Override
    public void onAddMatchFragmentResult(MatchItem match) {
        matchAdapter.addMatchItem(match);
    }


}
