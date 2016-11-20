package punchlist.punchlistapp.ui.punch_list;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import punchlist.punchlistapp.R;
import punchlist.punchlistapp.data_model.Item;
import punchlist.punchlistapp.settings.Globals;

/**
 * Created by carlos on 11/20/16.
 */

class PunchListRecyclerAdapter extends RecyclerView.Adapter<PunchListRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItemsList;
    private Resources mResources;
    private String mPackageName;
    private String TAG = "PunchList RECYCLER";

    private final int thumbnailDimensions = 200;


    PunchListRecyclerAdapter(List<Item> myDataset, Resources resources, String packageName) {
        mItemsList = myDataset;
        mResources = resources;
        mPackageName = packageName;
    }

    @Override
    public PunchListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_punchlist, parent, false);
        return new PunchListRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PunchListRecyclerAdapter.ViewHolder holder, int position) {
        final Item item = mItemsList.get(position);

        holder.mItemName.setText(item.name);
        holder.mItemQuantity.setText("");
        holder.mTotalPrice.setText("Total: $" + String.valueOf(item.cost));
        if (item.component.placeholderId == Globals.TILE) {
            int quantity = 40;
            holder.mItemQuantity.setText("Quantity: " + String.valueOf(quantity) + " sq.ft.");
            holder.mTotalPrice.setText("Total: $" + String.valueOf(item.cost * quantity));
        }
        holder.mItemPrice.setText("$" + String.valueOf(item.cost) + " / unit");
        holder.mItemImage.setImageResource(mResources.getIdentifier(item.imageResource + "_thumbnail", "drawable", mPackageName));
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvItemPrice)
        TextView mItemPrice;
        @Bind(R.id.tvItemName)
        TextView mItemName;
        @Bind(R.id.tvItemImage)
        ImageView mItemImage;
        @Bind(R.id.punchListCardView)
        CardView mCardView;
        @Bind(R.id.tvItemQuantity)
        TextView mItemQuantity;
        @Bind(R.id.tvTotalPrice)
        TextView mTotalPrice;

        ViewHolder(View v) {
            super(v);

            mContext = v.getContext();

            ButterKnife.bind(this, v);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailDimensions, thumbnailDimensions);
            mItemImage.setLayoutParams(params);
        }
    }
}
