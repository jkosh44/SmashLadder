package hu.ait.android.smashladder;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import hu.ait.android.smashladder.adapter.MatchAdapter;

public class MatchListActivity extends AppCompatActivity {

    private MatchAdapter matchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        final Context context = this;

        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("Matches");
        //TODO: make sure the queries are all matches, uncomment when you add matches to parse
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
}
