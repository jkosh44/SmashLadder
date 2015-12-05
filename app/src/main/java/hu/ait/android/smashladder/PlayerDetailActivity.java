package hu.ait.android.smashladder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import hu.ait.android.smashladder.adapter.PlayerAdapter;

public class PlayerDetailActivity extends AppCompatActivity {

    private String playerName;
    private int playerRank;

    private TextView tvDetailPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        Bundle extras = getIntent().getExtras();
        playerName = extras.getString(PlayerAdapter.BUNDLE_NAME_KEY);
        playerRank = extras.getInt(PlayerAdapter.BUNDLE_RANK_KEY);

        tvDetailPlayer = (TextView) findViewById(R.id.tvDetailPlayer);
        tvDetailPlayer.setText("Tag: " + playerName + "\nRank: " + playerRank);
    }
}
