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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
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
    public static final String MATCH_RELATION = "MATCH_RELATION";

    public interface AddMatchFragmentInterface {
        public void onAddMatchFragmentResult(MatchItem match);
    }


    private MatchItem newMatch;
    private AddMatchFragmentInterface addMatchFragmentInterface;
    private ArrayList<String> players = new ArrayList<String>();

    Spinner spinnerChallenger;
    Spinner spinnerChallengerCharacter;
    Spinner spinnerOpponent;
    Spinner spinnerOpponentCharacter;
    Spinner spinnerStage;
    Spinner spinnerWinner;


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

        spinnerChallenger = (Spinner) v.findViewById(R.id.spinnerChallenger);
        spinnerChallengerCharacter = (Spinner) v.findViewById(R.id.spinnerChallengerCharacter);
        spinnerOpponent = (Spinner) v.findViewById(R.id.spinnerOpponent);
        spinnerOpponentCharacter = (Spinner) v.findViewById(R.id.spinnerOpponentCharacter);
        spinnerStage = (Spinner) v.findViewById(R.id.spinnerStage);
        spinnerWinner = (Spinner) v.findViewById(R.id.spinnerWinner);

        final ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        players.add(objects.get(i).get(RegisterActivity.NAME_TAG).toString());
                    }
                    ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, players);
                    playerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                    spinnerChallenger.setAdapter(playerAdapter);
                    spinnerOpponent.setAdapter(playerAdapter);
                    spinnerWinner.setAdapter(playerAdapter);
                } else {
                    e.printStackTrace();
                }
            }
        });

        ArrayAdapter<CharSequence> characterAdapter = ArrayAdapter.createFromResource(getContext(), R.array.characters, android.R.layout.simple_spinner_item);
        characterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> stageAdapter = ArrayAdapter.createFromResource(getContext(), R.array.stages, android.R.layout.simple_spinner_item);
        characterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerChallengerCharacter.setAdapter(characterAdapter);
        spinnerOpponentCharacter.setAdapter(characterAdapter);
        spinnerStage.setAdapter(stageAdapter);


        Button btnAddMatch = (Button) v.findViewById(R.id.btnAddMatch);
        btnAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerWinner.getSelectedItem() == spinnerChallenger.getSelectedItem() || spinnerWinner.getSelectedItem() == spinnerOpponent.getSelectedItem()) {
                    //TODO: add other fields possibly
                    final ParseObject newMatchParse = new ParseObject(MatchListActivity.MATCHES_TAG);
                    //TODO: do we want to only allow the challenger to add matches of themselves?
                    //      we could change match_challenger_key to ParseUser.getCurrentUser()
                    newMatchParse.put(MatchListActivity.MATCH_CHALLENGER_KEY, spinnerChallenger.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_OPPONENT_KEY, spinnerOpponent.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_WINNER_KEY, spinnerWinner.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_CHALLENGER_CHARACHTER_KEY, spinnerChallengerCharacter.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_OPPONENT_CHARACTER_KEY, spinnerOpponentCharacter.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_STAGE_KEY, spinnerStage.getSelectedItem().toString());
                    newMatchParse.put(MatchListActivity.MATCH_UPDATED, false);


                    //TODO: update wins and losses of each player
                    newMatchParse.saveInBackground();

                    newMatch = new MatchItem(newMatchParse);
                    addMatchFragmentInterface.onAddMatchFragmentResult(newMatch);
                    dismiss();
                } else {
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
