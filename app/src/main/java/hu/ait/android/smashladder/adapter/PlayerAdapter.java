package hu.ait.android.smashladder.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.android.smashladder.PlayerDetailActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.data.PlayerComparator;
import hu.ait.android.smashladder.data.PlayerItem;


/**
 * Created by joe on 12/4/15.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    public static final String PLAYER_ITEM_KEY = "PLAYER_ITEM_KEY";
    public static final String BUNDLE_RANK_KEY = "BUNDLE_RANK_KEY";
    public static final String BUNDLE_NAME_KEY = "BUNDLE_NAME_KEY";
    private Intent detailsIntent;

    public class ViewHolder extends RecyclerView.ViewHolder {

        //TODO: add more views
        public TextView tvPlayer;
        public LinearLayout playerItemView;

        public ViewHolder(View view) {
            super(view);
            tvPlayer = (TextView) view.findViewById(R.id.tvPlayer);
            playerItemView = (LinearLayout) view.findViewById(R.id.playerItemView);
        }

    }

    private List<PlayerItem> playerItemsList;
    private Context context;


    public PlayerAdapter(List<ParseUser> users, Context context) {
        this.context = context;

        List<PlayerItem> resValues = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            resValues.add(new PlayerItem(users.get(i)));
        }

        Collections.sort(resValues, new PlayerComparator());

        playerItemsList = resValues;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.player_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //TODO: add more stuff
        holder.tvPlayer.setText((position + 1)+". "+playerItemsList.get(position).getName());

        holder.playerItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle playerBundle = new Bundle();
                playerBundle.putString(BUNDLE_NAME_KEY, playerItemsList.get(position).getName());
                playerBundle.putInt(BUNDLE_RANK_KEY, playerItemsList.get(position).getRank());

                Intent intent = new Intent(context, PlayerDetailActivity.class);
                intent.putExtras(playerBundle);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return playerItemsList.size();
    }

    public PlayerItem getItem(int i) {
        return playerItemsList.get(i);
    }

}



