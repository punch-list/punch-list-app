package punchlist.punchlistapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProjectsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        List<Project> projectsList = new ArrayList<>();
        ArrayAdapter projectsAdapter = new ArrayAdapter<>(this, R.layout.projects_list, projectsList);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(projectsAdapter);
    }

}
