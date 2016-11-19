package punchlist.punchlistapp.data_model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

import punchlist.punchlistapp.data_model.enums.ProjectType;


@Table(name = "Projects")
public class PLProject extends Model implements Serializable {
    public static String TAG = PLProject.class.getSimpleName();

    @Column(name = "Name")
    public String name;

    @Column(name = "Type")
    public ProjectType type;

    public PLProject() {
        super();
    }

    public PLProject(String name, ProjectType type) {
        super();
        this.name = name;
        this.type = type;
    }

    public static void updatePLProject(PLProject apiPLProject) {
        if (apiPLProject.getId() == null) {
            PLProject PLProject = new PLProject(apiPLProject.name, apiPLProject.type);
            PLProject.save();
        } else {
            PLProject PLProject = apiPLProject;
            if (apiPLProject.name != null) {
                PLProject.name = apiPLProject.name;
            }
            if (apiPLProject.type != null) {
                PLProject.type = apiPLProject.type;
            }

            PLProject.save();
        }
    }

    public static void updatePLProjects(List<PLProject> mPLProjectList) {
        for (int i = 0; i < mPLProjectList.size(); i++) {
            PLProject apiPLProject = mPLProjectList.get(i);
            updatePLProject(apiPLProject);
        }
    }

    public static PLProject findPLProject(Long PLProjectId) {
        PLProject PLProjectQuery = new Select().from(PLProject.class)
                .where("id = ?", PLProjectId).executeSingle();
        return PLProjectQuery;
    }

    public static List<PLProject> getPLProjects() {
        return new Select().from(PLProject.class).execute();
    }

    public List<Item> getProjectItems() {
        return getMany(Item.class, "Project");
    }
}
