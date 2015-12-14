package hu.ait.android.smashladder.fragment;

import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.smashladder.MatchListActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.adapter.MatchAdapter;
import hu.ait.android.smashladder.data.MatchItem;
import hu.ait.android.smashladder.data.PlayerItem;

/**
 * Created by joe on 12/5/15.
 */
public class FragmentPlayerMatch extends Fragment {

    //TODO: deal with the case that there is no matches

    MatchAdapter matchAdapter;


    private ArrayList<MatchItem> playerMatches = new ArrayList<>();
    //private ArrayList<ParseObject> playerMatches = new ArrayList<>();
    private String playerName;


    public FragmentPlayerMatch() {

    }

    /*public FragmentPlayerMatch(ArrayList<MatchItem> playerMatches) {
        this.playerMatches = playerMatches;
    }*/


    public FragmentPlayerMatch(String playerName) {

        this.playerName = playerName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_player_matches, container, false);


        //TODO: only matches with playerName
        ParseQuery<ParseObject> query = ParseQuery.getQuery(MatchListActivity.MATCHES_TAG);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        if (objects.get(i).get(MatchListActivity.MATCH_OPPONENT_KEY).toString().equals(playerName) || objects.get(i).get(MatchListActivity.MATCH_CHALLENGER_KEY).toString().equals(playerName)) {
                            playerMatches.add(new MatchItem(objects.get(i)));
                        }
                    }
                   matchAdapter = new MatchAdapter(playerMatches, getContext(), getActivity().getSupportFragmentManager());
                    //matchAdapter = new MatchAdapter(objects, getContext(), getActivity().getSupportFragmentManager());
                    RecyclerView recyclerViewMatchItem = (RecyclerView) rootView.findViewById(R.id.playerMatchesRecyclerView);
                    recyclerViewMatchItem.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewMatchItem.setAdapter(matchAdapter);
                    recyclerViewMatchItem.setVisibility(View.VISIBLE);
                    recyclerViewMatchItem.invalidate();
                    matchAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });



        return rootView;
    }

    /*public FragmentPlayerMatch(String challengerName) {
        this.challengerName = challengerName;
    }*/
}


