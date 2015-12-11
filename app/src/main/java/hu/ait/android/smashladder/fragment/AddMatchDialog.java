package hu.ait.android.smashladder.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;

import hu.ait.android.smashladder.MatchListActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.data.MatchItem;

/**
 * Created by joe on 12/6/15.
 */
public class AddMatchDialog extends DialogFragment {

    public static final String TAG = "AddMatchDialogFragment";

    public interface AddMatchFragmentInterface {
        public void onAddMatchFragmentResult(MatchItem match);
    }


    private MatchItem newMatch;
    private AddMatchFragmentInterface addMatchFragmentInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addMatchFragmentInterface = (AddMatchFragmentInterface) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + activity.getString(R.string.add_match_fragment_interface_error));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_match_dialog, container, false);

        final EditText etChallenger = (EditText) v.findViewById(R.id.etChallenger);
        final EditText etOpponent = (EditText) v.findViewById(R.id.etOpponent);
        final EditText etWinner = (EditText) v.findViewById(R.id.etWinner);

        Button btnAddMatch = (Button) v.findViewById(R.id.btnAddMatch);
        btnAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add other fields possibly
                ParseObject newMatchParse = new ParseObject(MatchListActivity.MATCHES_TAG);
                //TODO: do we want to only allow the challenger to add matches of themselves?
                //      we could change key_challenger to ParseUser.getCurrentUser()
                newMatchParse.put(MatchListActivity.MATCH_CHALLENGER_KEY, etChallenger.getText().toString());
                newMatchParse.put(MatchListActivity.MATCH_OPPONENT_KEY, etOpponent.getText().toString());
                newMatchParse.put(MatchListActivity.MATCH_WINNER_KEY, etWinner.getText().toString());
                newMatchParse.saveInBackground();

                newMatch = new MatchItem(newMatchParse);
                //TODO: this also crashes the app
                addMatchFragmentInterface.onAddMatchFragmentResult(newMatch);
                dismiss();
            }
        });

        Button btnMatchCancel = (Button) v.findViewById(R.id.btnMatchCancel);
        btnMatchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

}
