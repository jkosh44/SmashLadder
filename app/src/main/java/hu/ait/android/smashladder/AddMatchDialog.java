package hu.ait.android.smashladder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import hu.ait.android.smashladder.data.MatchItem;

/**
 * Created by joe on 12/6/15.
 */
public class AddMatchDialog extends DialogFragment {

    public static final String TAG = "DialogFragment";

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

        EditText etChallenger = (EditText) v.findViewById(R.id.etChallenger);
        EditText etOpponent = (EditText) v.findViewById(R.id.etOpponent);
        EditText etWinner = (EditText) v.findViewById(R.id.etWinner);

        Button btnAddMatch = (Button) v.findViewById(R.id.btnAddMatch);
        btnAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add match to parse
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
