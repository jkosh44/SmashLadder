package hu.ait.android.smashladder.data;

import java.util.Comparator;

/**
 * Created by joe on 12/5/15.
 */
public class PlayerComparator implements Comparator<PlayerItem> {

    @Override
    public int compare(PlayerItem lhs, PlayerItem rhs) {
        if(lhs.getRank() < rhs.getRank()) {
            return -1;
        }
        else {
            return 1;
        }
    }

}
