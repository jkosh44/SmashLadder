package hu.ait.android.smashladder;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.smashladder.adapter.PlayerAdapter;
import hu.ait.android.smashladder.data.MatchItem;
import hu.ait.android.smashladder.fragment.FragmentPlayerDetail;
import hu.ait.android.smashladder.fragment.FragmentPlayerMatch;

public class PlayerDetailActivity extends AppCompatActivity {

    //TODO: we can send push notifications with ParsePush, may want to add a challenge feature

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private String playerName;
    private int playerRank;
    private ArrayList<MatchItem> playerMatches;
    //TODO: get all matches such that either the challenger or opponent is playerName and create MatchItems out of them to fill up playerMatches
    //private ArrayList<MatchItem> playerMatches = new ArrayList<>();

    private ViewPager playerDetailsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.playerDetailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        playerDetailsContainer = (ViewPager) findViewById(R.id.player_details_container);
        playerDetailsContainer.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(playerDetailsContainer);

        Bundle extras = getIntent().getExtras();
        playerName = extras.getString(PlayerAdapter.BUNDLE_NAME_KEY);
        playerRank = extras.getInt(PlayerAdapter.BUNDLE_RANK_KEY);
        //playerMatches = (ArrayList<MatchItem>) extras.getSerializable(PlayerAdapter.BUNDLE_MATCHES_KEY);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new FragmentPlayerDetail(playerName, playerRank);
                case 1:
                    return new FragmentPlayerMatch(playerName);
                default:
                    return new FragmentPlayerDetail(playerName, playerRank);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.player_details);
                case 1:
                    return getString(R.string.previous_player_matches);
            }
            return null;
        }

    }
}
