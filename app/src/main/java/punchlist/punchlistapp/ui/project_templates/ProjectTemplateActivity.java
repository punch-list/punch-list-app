package punchlist.punchlistapp.ui.project_templates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import punchlist.punchlistapp.R;
import punchlist.punchlistapp.data_model.Item;
import punchlist.punchlistapp.data_model.PLComponent;
import punchlist.punchlistapp.data_model.PLProject;
import punchlist.punchlistapp.data_model.enums.ProjectType;
import punchlist.punchlistapp.settings.Globals;
import punchlist.punchlistapp.ui.project.EditProjectActivity;

public class ProjectTemplateActivity extends AppCompatActivity {

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, ProjectTemplateActivity.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.ibFirst)
    public void clickedFirstImage() {
        createTemplate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_template);

        ButterKnife.bind(this);
    }


    private void createTemplate() {
        PLProject project = new PLProject("Affordable Traditional Bathroom", ProjectType.BATHROOM);
        project.save();

        List<Item> items = new ArrayList<>();

        items.add(new Item("Solerno Moderna", "", 2, 0, null, PLComponent.getComponentByFakeId(Globals.TILE), 300, 300, "tile1", getResources(), 205, 579, true));
        items.add(new Item("Tile #1", "Luxurious throne", 10, 50, project, PLComponent.getComponentByFakeId(Globals.TILE), 150, 150, "tile1", getResources(), 579, 205, true));
        items.add(new Item("Hillsbury 36\" + Pfister Courant", "", 577, 0, null, PLComponent.getComponentByFakeId(Globals.SINK), 420 * 3, 160 * 3, "sink1", getResources(), 100, 100, true));
        items.add(new Item("Sterling Ensemble", "", 235, 0, null, PLComponent.getComponentByFakeId(Globals.BATHTUB), 420 * 3, 160 * 3, "bathtub1", getResources(), 500, 500, true));
        items.add(new Item("Glidden Purple Periwinkle", "", 25, 0, null, PLComponent.getComponentByFakeId(Globals.PAINT), EditProjectActivity.FLOORPLAN_WIDTH, EditProjectActivity.FLOORPLAN_HEIGHT, "paint2", getResources(), 0, 0, true));

        Item.updateItems(items);

        Intent intent = new Intent(this, EditProjectActivity.class);
        intent.putExtra(Globals.PROJECT_ID, project.getId());
        startActivity(intent);
    }
}
