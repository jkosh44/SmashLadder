package hu.ait.android.smashladder.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.ait.android.smashladder.R;

/**
 * Created by joe on 12/10/15.
 */
public class MatchDetailsDialog extends DialogFragment {

    public static final String TAG = "MatchDetailsDialogFragment";

    private String challenger;
    private String opponent;
    private String winner;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_details_dialog, container, false);

        TextView tvChallenger = (TextView) v.findViewById(R.id.tvChallengerDetails);
        TextView tvOpponent = (TextView) v.findViewById(R.id.tvOpponentDetails);
        TextView tvWinner = (TextView) v.findViewById(R.id.tvWinnerDetails);

        //TODO: change this when you get match item
        tvChallenger.setText("test"/*challenger*/);
        tvOpponent.setText("test"/*opponent*/);
        tvWinner.setText("test"/*winner*/);

        return v;
    }


}
