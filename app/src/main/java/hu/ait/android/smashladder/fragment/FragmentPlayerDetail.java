package hu.ait.android.smashladder.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hu.ait.android.smashladder.PlayerDetailActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.adapter.PlayerAdapter;

/**
 * Created by joe on 12/5/15.
 */
public class FragmentPlayerDetail extends Fragment {

    private String playerName;
    private int playerRank;

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

        TextView tvDetailPlayer = (TextView) rootView.findViewById(R.id.tvPlayerDetails);
        tvDetailPlayer.setText(playerName);

        TextView tvDetailRank = (TextView) rootView.findViewById(R.id.tvPlayerRank);
        tvDetailRank.setText("Rank: " + playerRank);

        Button btnChallenge = (Button) rootView.findViewById(R.id.btnChallenge);
        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, playerName + " has been notified", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
