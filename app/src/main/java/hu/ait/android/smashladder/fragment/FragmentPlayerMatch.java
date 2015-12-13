package hu.ait.android.smashladder.fragment;

import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

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


    private ArrayList<MatchItem> playerMatches;


    public FragmentPlayerMatch() {

    }

    public FragmentPlayerMatch(ArrayList<MatchItem> matches) {

        playerMatches = matches;
    }

    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_matches, container, false);

        matchAdapter = new MatchAdapter(playerMatches, getContext(), getActivity().getSupportFragmentManager());

        RecyclerView recyclerViewMatchItem = (RecyclerView) rootView.findViewById(R.id.playerMatchesRecyclerView);
        recyclerViewMatchItem.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMatchItem.setAdapter(matchAdapter);
        recyclerViewMatchItem.setVisibility(View.VISIBLE);

        return rootView;
    }

    /*public FragmentPlayerMatch(String challengerName) {
        this.challengerName = challengerName;
    }*/
}


