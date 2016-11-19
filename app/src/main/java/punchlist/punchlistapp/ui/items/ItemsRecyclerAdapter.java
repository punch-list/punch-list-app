package punchlist.punchlistapp.ui.items;

import android.app.Activity;
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
import punchlist.punchlistapp.data_model.PLProject;


class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<Item> mItemsList;
    private PLProject mProject;
    private String TAG = "Items RECYCLER";
    private Resources mResources;
    private String mPackageName;

    private final int thumbnailDimensions = 200;


    ItemsRecyclerAdapter(List<Item> myDataset, PLProject project, Resources resources, String packageName) {
        mItemsList = myDataset;
        mProject = project;
        mResources = resources;
        mPackageName = packageName;
    }

    @Override
    public ItemsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsRecyclerAdapter.ViewHolder holder, int position) {
        final Item item = mItemsList.get(position);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item copyItem = new Item(item.name, item.description, item.cost, item.area, mProject, item.component, item.width, item.height, item.imageResource);
                copyItem.save();

                ((Activity) mContext).finish();
            }
        });
        holder.mItemName.setText(item.name);

        holder.mItemImage.setImageResource(mResources.getIdentifier(item.imageResource, "mipmap", mPackageName));
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvItemName)
        TextView mItemName;
        @Bind(R.id.tvItemImage)
        ImageView mItemImage;
        @Bind(R.id.itemCardView)
        CardView mCardView;

        ViewHolder(View v) {
            super(v);

            mContext = v.getContext();

            ButterKnife.bind(this, v);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailDimensions, thumbnailDimensions);
            mItemImage.setLayoutParams(params);
        }
    }
}

