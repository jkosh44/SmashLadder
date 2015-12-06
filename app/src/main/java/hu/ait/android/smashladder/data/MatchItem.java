package hu.ait.android.smashladder.data;

import com.parse.ParseObject;

import hu.ait.android.smashladder.AddMatchDialog;

/**
 * Created by joe on 12/5/15.
 */
public class MatchItem {

    private String challengerName;
    private String opponentName;
    private String winner;
    private String loser;
    //private String stage;
    //private String challengerCharacter;
    //private String opponentCharacter;
    //private int stockCount;


    //TODO: add to this, also make sure it's a match object?
    public MatchItem(ParseObject match) {
        this.challengerName = match.get(AddMatchDialog.KEY_CHALLENGER).toString();
        this.opponentName = match.get(AddMatchDialog.KEY_OPPONENT).toString();
        this.winner = match.get(AddMatchDialog.KEY_WINNER).toString();
        //this.loser = match.get(loser);
    }

    public String getChallengerName() {
        return challengerName;
    }

    public void setChallengerName(String challengerName) {
        this.challengerName = challengerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }
}
