package punchlist.punchlistapp.settings;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.TypedValue;

import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.List;

import punchlist.punchlistapp.base.ApplicationBase;
import punchlist.punchlistapp.data_model.Item;
import punchlist.punchlistapp.data_model.PLComponent;
import punchlist.punchlistapp.data_model.PLProject;
import punchlist.punchlistapp.data_model.enums.ProjectType;
import punchlist.punchlistapp.ui.project.EditProjectActivity;


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
    public static final int PAINT = 5;

    public static final String COMPONENT = "COMPONENT";
    public static final String PROJECT = "PROJECT";
    private static final String TAG = Globals.class.getSimpleName();
    private static Globals sInstance = null;
    public SharedPreferences mPrefs = null;
    public SharedPreferences mUserInfo;
    public SharedPreferences mPermissions;
    public SharedPreferences mSettings;
    private String deviceUuid;

    private static Resources mResources;

    private Globals(Resources resources) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ApplicationBase.getAppContext());
        mUserInfo = ApplicationBase.getAppContext().getSharedPreferences(PREFS_USER_INFO, 0);
        mSettings = ApplicationBase.getAppContext().getSharedPreferences(PREFS_NAME, 0);
        mResources = resources;
    }

    public static Globals getInstance(Resources resources) {
        if (sInstance == null) {
            synchronized (Globals.class) {
                if (sInstance == null) {
                    sInstance = new Globals(resources);
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

    public void clearDatabase() {
        new Delete().from(Item.class).execute();
        new Delete().from(PLProject.class).execute();
        new Delete().from(PLComponent.class).execute();
    }

    public void prepopulateDb() {
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
            components.add(new PLComponent("Paint", Globals.PAINT));

            PLComponent.updatePLComponents(components);
        }

        if (Item.getItems().isEmpty()) {
            List<Item> items = new ArrayList<>();

            items.add(new Item("Drake 2-piece", "", 216, 4, null, PLComponent.getComponentByFakeId(Globals.TOILET), 420 * 3, 162 * 3, "toilet1", mResources));
            items.add(new Item("Memoirs Stately 2-piece", "A slightly more luxurious throne", 349, 4, null, PLComponent.getComponentByFakeId(Globals.TOILET), 420 * 3, 162 * 3, "toilet2", mResources));

            items.add(new Item("Solerno Moderna", "", 2, 0, null, PLComponent.getComponentByFakeId(Globals.TILE), 300, 300, "tile1", mResources));
            items.add(new Item("Daltile Porcelain - Yacht Club Series", "", 3, 0, null, PLComponent.getComponentByFakeId(Globals.TILE), 300, 300, "tile2", mResources));

            items.add(new Item("Glidden Purple Periwinkle", "", 25, 0, null, PLComponent.getComponentByFakeId(Globals.PAINT), EditProjectActivity.FLOORPLAN_WIDTH, EditProjectActivity.FLOORPLAN_HEIGHT, "paint2", mResources));
            items.add(new Item("Benjamin Moore Balboa Mist", "", 55, 0, null, PLComponent.getComponentByFakeId(Globals.PAINT), EditProjectActivity.FLOORPLAN_WIDTH, EditProjectActivity.FLOORPLAN_HEIGHT, "paint1", mResources));

            items.add(new Item("Hillsbury 36\" + Pfister Courant", "", 577, 0, null, PLComponent.getComponentByFakeId(Globals.SINK), 420 * 3, 160 * 3, "sink1", mResources));
            items.add(new Item("Vinnova Gela 36\" + Delta Two", "", 1073, 0, null, PLComponent.getComponentByFakeId(Globals.SINK), 420 * 3, 160 * 3, "sink2", mResources));

            items.add(new Item("Sterling Ensemble", "", 235, 0, null, PLComponent.getComponentByFakeId(Globals.BATHTUB), 420 * 3, 160 * 3, "bathtub1", mResources));

            Item.updateItems(items);
        }
    }

    public static int pixelsToDp(int pixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, mResources.getDisplayMetrics());
    }

    public void clearPreferences() {
        mPrefs.edit().clear().commit();
        mUserInfo.edit().clear().commit();
        mSettings.edit().clear().commit();
    }
}
