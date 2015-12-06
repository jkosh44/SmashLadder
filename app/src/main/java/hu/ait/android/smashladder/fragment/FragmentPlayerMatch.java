package hu.ait.android.smashladder.fragment;

import android.support.v4.app.Fragment;

import com.parse.ParseObject;

import java.util.List;

import hu.ait.android.smashladder.data.MatchItem;
import hu.ait.android.smashladder.data.PlayerItem;

/**
 * Created by joe on 12/5/15.
 */
public class FragmentPlayerMatch extends Fragment {

    //TODO: deal with the case that there is no matches

    //private String challengerName;
    //private String opponentName;
    //private String winner;
    //private String loser;
    //private String stage;
    //private String challengerCharacter;
    //private String opponentCharacter;
    //private int stockCount;
    //this fragment needs a list of matches not just one match

    private List<MatchItem> playerMatches;


    public FragmentPlayerMatch() {

    }

    public FragmentPlayerMatch(List<MatchItem> matches) {

        playerMatches = matches;
    }

    /*public FragmentPlayerMatch(String challengerName) {
        this.challengerName = challengerName;
    }*/
}
