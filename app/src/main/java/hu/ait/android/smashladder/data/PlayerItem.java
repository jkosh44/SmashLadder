package hu.ait.android.smashladder.data;

import com.parse.ParseUser;

import java.util.Comparator;

import hu.ait.android.smashladder.RegisterActivity;

/**
 * Created by joe on 12/4/15.
 */
public class PlayerItem {

    private String name;
    private int rank;
    //private int wins;
    //private int losses;

    public PlayerItem() {

    }

    public PlayerItem(ParseUser user) {
        this.name = user.get(RegisterActivity.NAME_TAG).toString();
        this.rank = 1; user.get(RegisterActivity.RANK_TAG).toString();
        //this.wins = 1; //user.getWins;
        //this.losses = 1; //user.getLosses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }



    /*public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }*/
}
