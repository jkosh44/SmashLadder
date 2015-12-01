package hu.ait.android.smashladder2;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by joe on 11/23/15.
 */
public class SmashLadder2Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //gotten from settings->keys use the first two keys (Application Key and Client ID)
        Parse.initialize(this,
                "EqugRREWOMJSOGZspzBn8uszkOaiRzd3jq73smJY",
                "xUzOdHUUgtfckVpmLhBAaatYeWW5erRB5UjxPNE6");

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("SmashLadder");
    }
}
