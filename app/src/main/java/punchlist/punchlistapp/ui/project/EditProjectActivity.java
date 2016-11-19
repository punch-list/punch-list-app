package punchlist.punchlistapp.ui.project;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

import static punchlist.punchlistapp.base.ApplicationBase.getContext;

public class EditProjectActivity extends ActivityBase {

    final int RC_SELECT_ITEM = 9002;

    String TAG = "EDIT PROJECT ACTIVITY";
    @Bind(R.id.ibToilet)
    ImageView mToiletButton;

    PLComponent selectedComponent;
    PLProject mProject;

    @Bind(R.id.flFloorplan)
    FrameLayout mFloorplan;

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
        renderItems();
    }

    private void centerNewItems() {
        List<Item> items = Item.getItems();
        for (Item item : items) {
            if (!item.placed) {
                item.positionX = 50;
                item.positionY = 50;
                item.placed = true;
                item.save();
            }
        }
    }

    private void renderItems() {
        List<Item> items = mProject.getProjectItems();
        for (Item item : items) {
            if (item != null) {
                ImageView imageView = new ImageView(getContext());
                imageView.setTag(item.getId());

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(item.width, item.height);
                params.leftMargin = item.positionX;
                params.topMargin = item.positionY;
                imageView.setLayoutParams(params);

                imageView.setImageResource(getResources().getIdentifier(item.imageResource, "mipmap", getPackageName()));

                mFloorplan.addView(imageView);

                setClickListener(imageView);
            }
        }
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
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(currentImageView.getLayoutParams());

                        int newX = (int) event.getX() - currentImageView.getWidth() / 2;
                        int newY = (int) event.getY() - currentImageView.getHeight() / 2;

                        params.leftMargin = newX;
                        params.topMargin = newY;
                        currentImageView.setLayoutParams(params);
                        currentImageView.setVisibility(View.VISIBLE);

                        Item item = Item.findItem((long) currentImageView.getTag());
                        item.positionX = newX;
                        item.positionY = newY;
                        item.save();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
