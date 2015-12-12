package hu.ait.android.smashladder.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
    public static final String CHALLENGER_KEY = "CHALLENGER_KEY";
    public static final String OPPONENT_KEY = "OPPONENT_KEY";
    public static final String WINNER_KEY = "WINNER_KEY";

    private String challenger;
    private String opponent;
    private String winner;

    public static MatchDetailsDialog newInstance(String fChallenger, String fOppoonent, String fWinner) {
        MatchDetailsDialog f = new MatchDetailsDialog();

        Bundle args = new Bundle();
        args.putString(CHALLENGER_KEY, fChallenger);
        args.putString(OPPONENT_KEY, fOppoonent);
        args.putString(WINNER_KEY, fWinner);

        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challenger = getArguments().getString(CHALLENGER_KEY);
        opponent = getArguments().getString(OPPONENT_KEY);
        winner = getArguments().getString(WINNER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_details_dialog, container, false);

        TextView tvChallenger = (TextView) v.findViewById(R.id.tvChallengerDetails);
        TextView tvOpponent = (TextView) v.findViewById(R.id.tvOpponentDetails);
        TextView tvWinner = (TextView) v.findViewById(R.id.tvWinnerDetails);

        //TODO: change this when you get match item
        tvChallenger.setText("Challenger: "+challenger);
        tvOpponent.setText("Opponent: "+opponent);
        tvWinner.setText("Winner: "+winner);

        return v;
    }


}
