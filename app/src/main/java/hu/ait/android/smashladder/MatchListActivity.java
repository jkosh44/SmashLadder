package hu.ait.android.smashladder;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hu.ait.android.smashladder.adapter.MatchAdapter;
import hu.ait.android.smashladder.data.MatchItem;
import hu.ait.android.smashladder.fragment.AddMatchDialog;
import hu.ait.android.smashladder.fragment.MatchDetailsDialog;

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

        //TODO: change this to something else
        Button btnTemp = (Button) findViewById(R.id.btnTemp);
        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatch();
            }
        });
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
