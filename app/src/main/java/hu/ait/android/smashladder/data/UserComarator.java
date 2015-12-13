package hu.ait.android.smashladder.data;

import com.parse.ParseUser;

import java.util.Comparator;

import hu.ait.android.smashladder.RegisterActivity;

/**
 * Created by joe on 12/13/15.
 */
public class UserComarator implements Comparator<ParseUser> {

    @Override
    public int compare(ParseUser lhs, ParseUser rhs) {
        if(lhs.getInt(RegisterActivity.RANK_TAG) < rhs.getInt(RegisterActivity.RANK_TAG)) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
