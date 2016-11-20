package punchlist.punchlistapp.ui.punch_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import punchlist.punchlistapp.R;
import punchlist.punchlistapp.base.ActivityBase;
import punchlist.punchlistapp.data_model.Item;
import punchlist.punchlistapp.data_model.PLProject;
import punchlist.punchlistapp.settings.Globals;
import punchlist.punchlistapp.ui.project.ProjectsActivity;

public class PunchListActivity extends ActivityBase {

    PLProject mProject;


    @Bind(R.id.punchListRecyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.tvProjectName)
    TextView mProjectName;

    @Bind(R.id.tvTotalPrice)
    TextView mTotalPrice;

    @OnClick(R.id.bRequestBids)
    public void clickedRequestBids() {
        requestBids();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_list);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Long projectId = bundle.getLong(Globals.PROJECT_ID);
        mProject = PLProject.findPLProject(projectId);

        mProjectName.setText(mProject.name);

        List<Item> items = mProject.getProjectItems();
        int totalPrice = 0;
        for (Item item : items) {
            if (item.component.placeholderId == Globals.TILE) {
                totalPrice += item.cost * 40;
            } else {
                totalPrice += item.cost;
            }
        }
        mTotalPrice.setText("Total Cost: $" + String.valueOf(totalPrice));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PunchListActivity.this));
        PunchListRecyclerAdapter punchListRecyclerAdapter = new PunchListRecyclerAdapter(items, getResources(), getPackageName());
        mRecyclerView.setAdapter(punchListRecyclerAdapter);
    }

    private void requestBids() {
        Toast.makeText(getApplicationContext(), "Your Punch List Has Been Sent To Contractors in Your Area.  You will receive bids via email.", Toast.LENGTH_SHORT).show();
        ProjectsActivity.startActivity(this);
    }
}
