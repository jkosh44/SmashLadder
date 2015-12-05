package hu.ait.android.smashladder.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.adapter.PlayerAdapter;

/**
 * Created by joe on 12/5/15.
 */
public class FragmentPlayerDetail extends Fragment {

    private String playerName;
    private int playerRank;

    private TextView tvDetailPlayer;

    public FragmentPlayerDetail() {

    }

    public FragmentPlayerDetail(String playerName, int playerRank) {
        this.playerName = playerName;
        this.playerRank = playerRank;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_detail, container, false);

        tvDetailPlayer = (TextView) rootView.findViewById(R.id.tvPlayerDetails);
        tvDetailPlayer.setText("Tag: " + playerName + "\nRank: " + playerRank);

        return rootView;
    }
}
