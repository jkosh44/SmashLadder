package hu.ait.android.smashladder.data;

import com.parse.ParseObject;

import hu.ait.android.smashladder.MatchListActivity;

/**
 * Created by joe on 12/5/15.
 */
public class MatchItem {

    private String challengerName;
    private String opponentName;
    private String winner;
    //private String loser;
    private String stage;
    private String challengerCharacter;
    private String opponentCharacter;
    //private int stockCount;


    //TODO: add to this, also make sure it's a match object?
    public MatchItem(ParseObject match) {
        this.challengerName = match.get(MatchListActivity.MATCH_CHALLENGER_KEY).toString();
        this.challengerCharacter = match.get(MatchListActivity.MATCH_CHALLENGER_CHARACHTER_KEY).toString();
        this.opponentName = match.get(MatchListActivity.MATCH_OPPONENT_KEY).toString();
        this.opponentCharacter = match.get(MatchListActivity.MATCH_OPPONENT_CHARACTER_KEY).toString();
        this.stage = match.get(MatchListActivity.MATCH_STAGE_KEY).toString();
        this.winner = match.get(MatchListActivity.MATCH_WINNER_KEY).toString();
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getChallengerCharacter() {
        return challengerCharacter;
    }

    public void setChallengerCharacter(String challengerCharacter) {
        this.challengerCharacter = challengerCharacter;
    }

    public String getOpponentCharacter() {
        return opponentCharacter;
    }

    public void setOpponentCharacter(String opponentCharacter) {
        this.opponentCharacter = opponentCharacter;
    }
}
