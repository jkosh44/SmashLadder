package hu.ait.android.smashladder.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.smashladder.MatchListActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.RegisterActivity;
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
    private ArrayList<String> players = new ArrayList<String>();

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

        final Spinner spinnerChallenger = (Spinner) v.findViewById(R.id.spinnerChallenger);
        final Spinner spinnerOpponent = (Spinner) v.findViewById(R.id.spinnerOpponent);
        final Spinner spinnerWinner = (Spinner) v.findViewById(R.id.spinnerWinner);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        players.add(objects.get(i).get(RegisterActivity.NAME_TAG).toString());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        //TODO: make this work
        ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, players);
        spinnerChallenger.setAdapter(playerAdapter);
        spinnerOpponent.setAdapter(playerAdapter);
        spinnerWinner.setAdapter(playerAdapter);

        Button btnAddMatch = (Button) v.findViewById(R.id.btnAddMatch);
        btnAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: this if statement also doesn't work right
                if(spinnerChallenger.getSelectedItem() == null || spinnerOpponent.getSelectedItem() == null || spinnerWinner.getSelectedItem() == null) {
                    Toast.makeText(getContext(), R.string.add_match_error_no_selection, Toast.LENGTH_SHORT).show();
                }
                if(spinnerWinner.getSelectedItem() == spinnerChallenger.getSelectedItem() || spinnerWinner.getSelectedItem() == spinnerOpponent.getSelectedItem()) {
                    //TODO: add other fields possibly
                    ParseObject newMatchParse = new ParseObject(MatchListActivity.MATCHES_TAG);
                    //TODO: do we want to only allow the challenger to add matches of themselves?
                    //      we could change match_challenger_key to ParseUser.getCurrentUser()
                    newMatchParse.put(MatchListActivity.MATCH_CHALLENGER_KEY, spinnerChallenger.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_OPPONENT_KEY, spinnerOpponent.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_WINNER_KEY, spinnerWinner.getSelectedItem().toString());
                    newMatchParse.saveInBackground();

                    newMatch = new MatchItem(newMatchParse);
                    //TODO: this also crashes the app, it works without this line
                    addMatchFragmentInterface.onAddMatchFragmentResult(newMatch);
                    dismiss();
                }
                else {
                    Toast.makeText(getContext(), R.string.add_match_bad_winner_error, Toast.LENGTH_SHORT).show();
                }
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
