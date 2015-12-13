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
    public static final String CHALLENGER_CHARACTER_KEY = "CHALLENGER_CHARACTER_KEY";
    public static final String OPPPONENT_CHARACTER_KEY = "OPPPONENT_CHARACTER_KEY";
    public static final String STAGE_KEY = "STAGE_KEY";

    private String challenger;
    private String challengerCharacter;
    private String opponent;
    private String opponentCharacter;
    private String stage;
    private String winner;

    public static MatchDetailsDialog newInstance(String fChallenger,String fChallengerCharacter, String fOppoonent, String fOpponentCharacter, String fStage, String fWinner) {
        MatchDetailsDialog f = new MatchDetailsDialog();

        //TODO: we should really figure out how to just pass an entire MatchItem
        Bundle args = new Bundle();
        args.putString(CHALLENGER_KEY, fChallenger);
        args.putString(CHALLENGER_CHARACTER_KEY, fChallengerCharacter);
        args.putString(OPPONENT_KEY, fOppoonent);
        args.putString(OPPPONENT_CHARACTER_KEY, fOpponentCharacter);
        args.putString(STAGE_KEY, fStage);
        args.putString(WINNER_KEY, fWinner);

        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challenger = getArguments().getString(CHALLENGER_KEY);
        challengerCharacter = getArguments().getString(CHALLENGER_CHARACTER_KEY);
        opponent = getArguments().getString(OPPONENT_KEY);
        opponentCharacter = getArguments().getString(OPPPONENT_CHARACTER_KEY);
        stage = getArguments().getString(STAGE_KEY);
        winner = getArguments().getString(WINNER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.match_details_dialog, container, false);

        TextView tvChallenger = (TextView) v.findViewById(R.id.tvChallengerDetails);
        TextView tvChallengerCharacter = (TextView) v.findViewById(R.id.tvChallengerCharacterDetails);
        TextView tvOpponent = (TextView) v.findViewById(R.id.tvOpponentDetails);
        TextView tvOpponentCharacter = (TextView) v.findViewById(R.id.tvOpponentCharacterDetails);
        TextView tvStage = (TextView) v.findViewById(R.id.tvStageDetails);
        TextView tvWinner = (TextView) v.findViewById(R.id.tvWinnerDetails);

        tvChallenger.setText(challenger);
        tvChallengerCharacter.setText(challengerCharacter);
        tvOpponent.setText(opponent);
        tvOpponentCharacter.setText(opponentCharacter);
        tvStage.setText(stage);
        tvWinner.setText(winner);

        return v;
    }


}
