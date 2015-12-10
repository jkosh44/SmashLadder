package hu.ait.android.smashladder.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.data.MatchItem;

/**
 * Created by joe on 12/10/15.
 */
public class MatchDetailsDialog extends DialogFragment {

    public static final String TAG = "MatchDetailsDialogFragment";

    private String challenger;
    private String opponent;
    private String winner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_details_dialog, container, false);

        TextView tvChallenger = (TextView) v.findViewById(R.id.tvChallengerDetails);
        TextView tvOpponent = (TextView) v.findViewById(R.id.tvOpponentDetails);
        TextView tvWinner = (TextView) v.findViewById(R.id.tvWinnerDetails);

        tvChallenger.setText(challenger);
        tvOpponent.setText(opponent);
        tvWinner.setText(winner);

        return v;
    }
}
