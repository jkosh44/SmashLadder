package hu.ait.android.smashladder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.smashladder.PlayerDetailActivity;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.data.PlayerItem;


/**
 * Created by joe on 12/4/15.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    public static final String PLAYER_NAME = "PLAYER_NAME";
    private Intent detailsIntent;

    private List<PlayerItem> playerItemsList;
    private Context context;


    public PlayerAdapter(List<ParseUser> users) {
        List<PlayerItem> resValues = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            resValues.add(new PlayerItem(users.get(i)));
        }

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
        holder.tvPlayer.setText(playerItemsList.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerDetailActivity.class);
                intent.putExtra(PLAYER_NAME, playerItemsList.get(position).getName());

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvPlayer;
        public LinearLayout mView;

        public ViewHolder(View view) {
            super(view);
            tvPlayer = (TextView) view.findViewById(R.id.tvPlayer);
            mView = (LinearLayout) view.findViewById(R.id.mView);
        }

    }

}



