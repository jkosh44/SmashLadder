package hu.ait.android.smashladder.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by joe on 12/5/15.
 */
public class FragmentPlayerMatch extends Fragment {

    //TODO: deal with the case that there

    private String challengerName;
    private String opponentName;
    private String winner;
    private String loser;
    //private String stage;
    //private String challengerCharacter;
    //private String opponentCharacter;
    //private int stockCount;


    public FragmentPlayerMatch() {

    }

    public FragmentPlayerMatch(String challengerName) {
        this.challengerName = challengerName;
    }
}
