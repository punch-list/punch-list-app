package punchlist.punchlistapp.ui.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import punchlist.punchlistapp.R;
import punchlist.punchlistapp.data_model.PLProject;

public class ProjectsActivity extends Activity {

    @Bind(R.id.projectsRecyclerView)
    RecyclerView mRecyclerView;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, ProjectsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        ButterKnife.bind(this);

        List<PLProject> projects = PLProject.getPLProjects();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ProjectsActivity.this));
        ProjectRecyclerAdapter projectRecyclerAdapter = new ProjectRecyclerAdapter(projects);
        mRecyclerView.setAdapter(projectRecyclerAdapter);
    }

}
