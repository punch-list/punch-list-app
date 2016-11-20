package punchlist.punchlistapp.base;

import android.content.Context;

import punchlist.punchlistapp.settings.Globals;

/**
 * Created by joshg on 11/19/16.
 */

public class ApplicationBase extends com.activeandroid.app.Application {
    private static final String TAG = ApplicationBase.class.getSimpleName();
    private static ApplicationBase instance;
    private static Context context;

    public static Context getContext() {
        return instance;
    }

    public static Context getAppContext() {
        return ApplicationBase.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ApplicationBase.context = getApplicationContext();

        Globals.getInstance(getResources()).prepopulateDb();
    }
}

