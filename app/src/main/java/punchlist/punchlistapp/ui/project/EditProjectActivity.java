package punchlist.punchlistapp.ui.project;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import punchlist.punchlistapp.R;
import punchlist.punchlistapp.base.ActivityBase;
import punchlist.punchlistapp.data_model.Item;
import punchlist.punchlistapp.data_model.PLComponent;
import punchlist.punchlistapp.data_model.PLProject;
import punchlist.punchlistapp.settings.Globals;
import punchlist.punchlistapp.ui.items.ItemsDialogActivity;
import punchlist.punchlistapp.ui.punch_list.PunchListActivity;

import static punchlist.punchlistapp.base.ApplicationBase.getContext;

public class EditProjectActivity extends ActivityBase {

    final int RC_SELECT_ITEM = 9002;

    String TAG = "EDIT PROJECT ACTIVITY";

    PLComponent selectedComponent;
    PLProject mProject;

    @Bind(R.id.flFloorplan)
    RelativeLayout mFloorplan;

    public static final int FLOORPLAN_WIDTH = 831;
    public static final int FLOORPLAN_HEIGHT = 1320;

    @OnClick(R.id.ibToilet)
    public void openToilet() {
        selectedComponent = PLComponent.getComponentByFakeId(Globals.TOILET);
        openItemSelectionDialog();
    }

    @OnClick(R.id.ibSink)
    public void openSink() {
        selectedComponent = PLComponent.getComponentByFakeId(Globals.SINK);
        openItemSelectionDialog();
    }

    @OnClick(R.id.ibBathtub)
    public void openBathtub() {
        selectedComponent = PLComponent.getComponentByFakeId(Globals.BATHTUB);
        openItemSelectionDialog();
    }

    @OnClick(R.id.ibTile)
    public void openTile() {
        selectedComponent = PLComponent.getComponentByFakeId(Globals.TILE);
        openItemSelectionDialog();
    }

    @OnClick(R.id.ibPaint)
    public void openPaint() {
        selectedComponent = PLComponent.getComponentByFakeId(Globals.PAINT);
        openItemSelectionDialog();
    }

    @OnClick(R.id.bCreatePunchList)
    public void clickedCreatePunchList() {
        createPunchList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Long projectId = bundle.getLong(Globals.PROJECT_ID);
        mProject = PLProject.findPLProject(projectId);

        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        centerNewItems();
        render();
    }

    private void render() {
        mFloorplan.removeAllViews();
        renderTile();
        renderItems();
    }

    private void centerNewItems() {
        List<Item> items = Item.getItems();
        for (Item item : items) {
            if (!item.placed) {
                item.positionX = FLOORPLAN_WIDTH / 2 - item.width / 2;
                item.positionY = FLOORPLAN_HEIGHT / 2 - item.height / 2;
                item.placed = true;
                item.save();
            }
        }
    }

    private void renderTile() {
        List<Item> items = mProject.getProjectItems();
        Item tile = null;
        for (Item item : items) {
            if (item.component.placeholderId == Globals.TILE) {
                tile = item;
            }
        }

        if (tile != null) {
            RelativeLayout tileView = new RelativeLayout(getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(FLOORPLAN_WIDTH, FLOORPLAN_HEIGHT);
            tileView.setLayoutParams(params);
            tileView.setBackgroundResource(getResources().getIdentifier(tile.imageResource, "drawable", getPackageName()));
            mFloorplan.addView(tileView);
        }
    }

    private void renderItems() {
        List<Item> items = mProject.getProjectItems();
        for (Item item : items) {
            if (item != null) {
                if (item.component.placeholderId == Globals.TILE) {
                    continue;
                }

                ImageView imageView = new ImageView(getContext());
                imageView.setTag(item.getId());

                imageView.setImageResource(getResources().getIdentifier(item.imageResource, "drawable", getPackageName()));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(item.width, item.height);
                params.leftMargin = item.positionX;
                params.topMargin = item.positionY;
                imageView.setLayoutParams(params);

                mFloorplan.addView(imageView);

                setClickListener(imageView);
            }
        }
    }

    private void createPunchList() {
        Intent intent = new Intent(this, PunchListActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putLong(Globals.PROJECT_ID, mProject.getId().intValue());
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    private void openItemSelectionDialog() {
        Intent intent = new Intent(this, ItemsDialogActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putInt(Globals.COMPONENT, selectedComponent.placeholderId);
        mBundle.putLong(Globals.PROJECT_ID, mProject.getId().intValue());

        intent.putExtras(mBundle);
        startActivityForResult(intent, RC_SELECT_ITEM);
    }

    private void setClickListener(final ImageView imageView) {
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData.Item item = new ClipData.Item(view.getTag().toString());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(view.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(imageView);

                view.startDrag(dragData, shadow, imageView, 0);
                return true;
            }
        });

        mFloorplan.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                ImageView currentImageView = (ImageView) event.getLocalState();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        currentImageView.setVisibility(View.INVISIBLE);
                        break;
                    case DragEvent.ACTION_DROP:
                        Item item = Item.findItem((long) currentImageView.getTag());
                        item.positionX = clamp(0, FLOORPLAN_WIDTH - item.width, (int) event.getX() - item.width / 2);
                        item.positionY = clamp(0, FLOORPLAN_HEIGHT - item.height, (int) event.getY() - item.height / 2);
                        item.save();

                        currentImageView.setVisibility(View.VISIBLE);
                        render();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private int clamp(int min, int max, int value) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
