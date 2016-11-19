package punchlist.punchlistapp.ui.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import punchlist.punchlistapp.R;
import punchlist.punchlistapp.data_model.PLProject;
import punchlist.punchlistapp.data_model.enums.ProjectType;
import punchlist.punchlistapp.settings.Globals;

class ProjectRecyclerAdapter extends RecyclerView.Adapter<ProjectRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<PLProject> mProjectList;
    private String TAG = "Project RECYCLER";

    ProjectRecyclerAdapter(List<PLProject> myDataset) {
        mProjectList = myDataset;
    }

    @Override
    public ProjectRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PLProject project = mProjectList.get(position);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditProjectActivity.class);
                intent.putExtra(Globals.PROJECT_ID, project.getId());
                mContext.startActivity(intent);
            }
        });
        holder.mProjectName.setText(project.name);
        holder.mProjectType.setText(projectTypeToString(project.type));
    }

    private String projectTypeToString(ProjectType projectType) {
        switch (projectType) {
            case BATHROOM:
                return "Bathroom";
            case KITCHEN:
                return "Kitchen";
            case LIVING_ROOM:
                return "Living Room";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvProjectName)
        TextView mProjectName;
        @Bind(R.id.tvProjectType)
        TextView mProjectType;
        @Bind(R.id.projectCardView)
        CardView mCardView;

        ViewHolder(View v) {
            super(v);
            mContext = v.getContext();
            ButterKnife.bind(this, v);
        }
    }
}
