package punchlist.punchlistapp.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by joshg on 11/19/16.
 */

public class ActivityBase extends AppCompatActivity {

    protected Toolbar toolbar;
    private ProgressDialog progressDialog;

    public android.support.v7.widget.Toolbar getToolbar() {
        return toolbar;
    }

    protected void hideProgress() {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress(); //To handle screen rotation
    }

}
