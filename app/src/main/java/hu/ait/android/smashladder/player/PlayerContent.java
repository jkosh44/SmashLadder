package hu.ait.android.smashladder.player;

import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample rank for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlayerContent {

    /**
     * An array of users.
     */
    // Get the user from a non-authenticated manner
    public static List<PlayerItem> ITEMS;

    ParseQuery<ParseUser> query = ParseUser.getQuery();
    //TODO: why doesn't this work????
    query.findInBackground(new FindCallback<ParseUser>() {
        public void done(List<ParseUser> objects, ParseException e) {
            // The query was successful.
            if (e == null) {
                setListAdapter(new ArrayAdapter<PlayerContent.PlayerItem>(
                        ITEMS = objects;
            }
            else {
                // Something went wrong.
                e.printStackTrace();
            }
        }
    });


    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, PlayerItem> ITEM_MAP = new HashMap<String, PlayerItem>();


    private static void addItem(PlayerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static PlayerItem createPlayerItem(int position) {
        return new PlayerItem(ITEMS.get(position).getName(), ITEMS.get(position).getRank(), ITEMS.get(position).getWins(), ITEMS.get(position).getLoss());
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about ").append(Items.get(position).getName());
        builder.append("\nRank: ").append(Items.get(position).getRank());
        builder.append("\nRecord: ").append(Items.get(position).getWins());
        builder.append("-").append(Items.get(position).getLoss());
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of rank.
     */
    public static class PlayerItem {
        public String name;
        public int rank;
        public int wins;
        public int loss;

        public PlayerItem(String name, int rank, int wins, int loss) {
            this.name = name;
            this.rank = rank;
            this.wins = wins;
            this.loss = loss;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
