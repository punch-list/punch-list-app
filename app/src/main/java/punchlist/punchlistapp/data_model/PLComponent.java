package punchlist.punchlistapp.data_model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * Created by joshg on 11/19/16.
 */

@Table(name = "Components")
public class PLComponent extends Model implements Serializable {
    public static String TAG = PLComponent.class.getSimpleName();

    @Column(name = "Name")
    public String name;

    @Column(name = "placeholderId")
    public int placeholderId;


    public PLComponent() {
        super();
    }

    public PLComponent(String name, int placeholderId) {
        super();
        this.name = name;
        this.placeholderId = placeholderId;
    }

    public static void updatePLComponent(PLComponent apiPLComponent) {
        if (apiPLComponent.getId() == null) {
            PLComponent PLComponent = new PLComponent(apiPLComponent.name, apiPLComponent.placeholderId);
            PLComponent.save();
        } else {
            PLComponent component = apiPLComponent;
            if (apiPLComponent.name != null) {
                component.name = apiPLComponent.name;
            }

            component.placeholderId = apiPLComponent.placeholderId;

            component.save();
        }
    }

    public static void updatePLComponents(List<PLComponent> mPLComponentList) {
        for (int i = 0; i < mPLComponentList.size(); i++) {
            PLComponent apiPLComponent = mPLComponentList.get(i);
            updatePLComponent(apiPLComponent);
        }
    }

    public static PLComponent findPLComponent(Long PLComponentId) {
        PLComponent PLComponentQuery = new Select().from(PLComponent.class)
                .where("id = ?", PLComponentId).executeSingle();
        return PLComponentQuery;
    }

    public static PLComponent getComponentByFakeId(int id) {
        return new Select().from(PLComponent.class).where("placeholderId = ?", id).executeSingle();
    }

    public static List<PLComponent> getPLComponents() {
        return new Select().from(PLComponent.class).execute();
    }

    public List<Item> getComponentItems() {
        return new Select().from(Item.class).where("Component = ? AND Project IS NULL", this.getId()).execute();
    }
}
