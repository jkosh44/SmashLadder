package hu.ait.android.smashladder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.smashladder.PlayerDetailActivity;
import hu.ait.android.smashladder.PlayerDetailFragment;
import hu.ait.android.smashladder.R;
import hu.ait.android.smashladder.data.PlayerItem;
import hu.ait.android.smashladder.dummy.DummyContent;

/**
 * Created by joe on 12/4/15.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private final List<PlayerItem> mValues;

    public PlayerAdapter(List<ParseUser> users) {
        List<PlayerItem> resValues = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            resValues.add(new PlayerItem(users.get(i)));
        }

        mValues = resValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_list_content, parent, false);
        return new ViewHolder(view);

        @Override
        public void onBindViewHolder ( final ViewHolder holder, int position){
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, PlayerDetailActivity.class);
                    intent.putExtra(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                    context.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount () {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

    }
}
