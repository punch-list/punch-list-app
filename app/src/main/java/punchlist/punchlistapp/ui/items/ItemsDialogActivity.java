package punchlist.punchlistapp.ui.items;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import punchlist.punchlistapp.R;
import punchlist.punchlistapp.base.ActivityBase;
import punchlist.punchlistapp.data_model.Item;
import punchlist.punchlistapp.data_model.PLComponent;
import punchlist.punchlistapp.data_model.PLProject;
import punchlist.punchlistapp.settings.Globals;

public class ItemsDialogActivity extends ActivityBase {

    private final String TAG = "ITEMS_DIALOG_ACTIVITY";

    @Bind(R.id.itemRecyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_dialog);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        PLComponent mComponent = PLComponent.getComponentByFakeId(bundle.getInt(Globals.COMPONENT));
        setTitle("Select " + mComponent.name);

        Long projectId = bundle.getLong(Globals.PROJECT_ID);
        PLProject project = PLProject.findPLProject(projectId);

        List<Item> usedItems = project.getProjectItems();
        List<String> usedItemImageResources = new ArrayList<>();
        for (Item usedItem : usedItems) {
            usedItemImageResources.add(usedItem.imageResource);
        }

        List<Item> items = mComponent.getComponentItems();
        List<Item> unusedItems = new ArrayList<>();
        for (Item item : items) {
            if (!usedItemImageResources.contains(item.imageResource)) {
                unusedItems.add(item);
            }
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ItemsDialogActivity.this));
        ItemsRecyclerAdapter itemsRecyclerAdapter = new ItemsRecyclerAdapter(unusedItems, project, getResources(), getPackageName());
        mRecyclerView.setAdapter(itemsRecyclerAdapter);
    }
}
