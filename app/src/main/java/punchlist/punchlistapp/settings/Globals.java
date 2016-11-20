package punchlist.punchlistapp.settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.List;

import punchlist.punchlistapp.base.ApplicationBase;
import punchlist.punchlistapp.data_model.Item;
import punchlist.punchlistapp.data_model.PLComponent;
import punchlist.punchlistapp.data_model.PLProject;
import punchlist.punchlistapp.data_model.enums.ProjectType;


public class Globals {
    public static final String APP_NAME = "PunchList";
    public static final String PREFS_NAME = "USER_INFO";
    public static final String PREFS_PERMISSIONS = "PERMISSIONS";
    public static final String PREFS_USER_INFO = "PREF_USER_DATA";
    public static final String DEVICE_UUID = "deviceUUID";
    public static final String PROJECT_ID = "project_id";
    //Component Ids
    public static final int TOILET = 1;
    public static final int SINK = 2;
    public static final int TILE = 3;
    public static final int BATHTUB = 4;
    public static final String COMPONENT = "COMPONENT";
    public static final String PROJECT = "PROJECT";
    private static final String TAG = Globals.class.getSimpleName();
    private static Globals sInstance = null;
    public SharedPreferences mPrefs = null;
    public SharedPreferences mUserInfo;
    public SharedPreferences mPermissions;
    public SharedPreferences mSettings;
    private String deviceUuid;

    private Globals() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ApplicationBase.getAppContext());
        mUserInfo = ApplicationBase.getAppContext().getSharedPreferences(PREFS_USER_INFO, 0);
        mSettings = ApplicationBase.getAppContext().getSharedPreferences(PREFS_NAME, 0);
    }

    public static Globals getInstance() {
        if (sInstance == null) {
            synchronized (Globals.class) {
                if (sInstance == null) {
                    sInstance = new Globals();
                }
            }
        }
        return sInstance;
    }

    public String getDeviceUuid() {
        if (this.deviceUuid == null) {
            this.deviceUuid = mPrefs.getString(DEVICE_UUID, "");
        }
        return this.deviceUuid;
    }

    public void setDeviceUuid(String uuid) {
        mPrefs.edit().putString(DEVICE_UUID, uuid).apply();
        this.deviceUuid = uuid;
    }

    public static void clearDatabase() {
        new Delete().from(Item.class).execute();
        new Delete().from(PLProject.class).execute();
        new Delete().from(PLComponent.class).execute();
    }

    public static void prepopulateDb() {
        if (PLProject.getPLProjects().isEmpty()) {
            List<PLProject> projects = new ArrayList<>();
            projects.add(new PLProject("My New Bathroom", ProjectType.BATHROOM));
            projects.add(new PLProject("My New Kitchen", ProjectType.KITCHEN));
            projects.add(new PLProject("My New Living Room", ProjectType.LIVING_ROOM));
            PLProject.updatePLProjects(projects);
        }

        if (PLComponent.getPLComponents().isEmpty()) {
            List<PLComponent> components = new ArrayList<>();
            components.add(new PLComponent("Toilet", Globals.TOILET));
            components.add(new PLComponent("Tile", Globals.TILE));
            components.add(new PLComponent("Bathtub", Globals.BATHTUB));
            components.add(new PLComponent("Sink", Globals.SINK));
            PLComponent.updatePLComponents(components);
        }

        if (Item.getItems().isEmpty()) {
            List<Item> items = new ArrayList<>();
            items.add(new Item("Toilet #1", "Luxurious throne", 10, 50, null, PLComponent.getComponentByFakeId(Globals.TOILET), 150, 150, "ic_select_toilet"));
            items.add(new Item("Toilet #2", "A slightly more luxurious throne", 10, 50, null, PLComponent.getComponentByFakeId(Globals.TOILET), 150, 150, "ic_select_sink"));

            items.add(new Item("Tile #1", "The shiniest thing you've ever seen, guaranteed!™", 10, 50, null, PLComponent.getComponentByFakeId(Globals.TILE), 300, 300, "tile"));
            Item.updateItems(items);
        }
    }

    public void clearPreferences() {
        mPrefs.edit().clear().commit();
        mUserInfo.edit().clear().commit();
        mSettings.edit().clear().commit();
    }
}
