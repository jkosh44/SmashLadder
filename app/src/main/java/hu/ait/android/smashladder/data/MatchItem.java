package hu.ait.android.smashladder.data;

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

    public MatchItem(String challengerName, String opponentName, String winner, String loser) {
        this.challengerName = challengerName;
        this.opponentName = opponentName;
        this.winner = winner;
        this.loser = loser;
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
