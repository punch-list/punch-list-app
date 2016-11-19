package punchlist.punchlistapp.data_model;

/**
 * Created by joshg on 11/19/16.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "Items")
public class Item extends Model implements Serializable {
    public static String TAG = Item.class.getSimpleName();

    @Column(name = "Name")
    public String name;

    @Column(name = "Description")
    public String description;

    @Column(name = "Cost")
    public int cost;

    @Column(name = "Area")
    public int area;

    @Column(name = "ImageResource")
    public String imageResource;

    @Column(name = "Project")
    public PLProject project;

    @Column(name = "Component")
    public PLComponent component;

    @Column(name = "Placed")
    public boolean placed;

    @Column(name = "PositionX")
    public Integer positionX;

    @Column(name = "PositionY")
    public Integer positionY;

    @Column(name = "Width")
    public int width;

    @Column(name = "Height")
    public int height;

    public Item() {
        super();
    }

    public Item(String name, String description, int cost, int area, PLProject project, PLComponent component, int width, int height, String imageResource) {
        super();
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.area = area;
        this.project = project;
        this.component = component;
        this.placed = false;
        positionX = null;
        positionY = null;
        this.width = width;
        this.height = height;
        this.imageResource = imageResource;
    }

    private static void updateItem(Item apiItem) {
        if (apiItem.getId() == null) {
            Item item = new Item(apiItem.name, apiItem.description, apiItem.cost, apiItem.area, apiItem.project, apiItem.component, apiItem.width, apiItem.height, apiItem.imageResource);
            item.save();
        } else {
            Item item = apiItem;
            if (apiItem.name != null) {
                item.name = apiItem.name;
            }
            if (apiItem.description != null) {
                item.description = apiItem.description;
            }
            item.cost = apiItem.cost;
            item.area = apiItem.area;

            if (apiItem.project != null) {
                item.project = apiItem.project;
            }

            if (apiItem.component != null) {
                item.component = apiItem.component;
            }

            item.placed = apiItem.placed;

            item.positionX = apiItem.positionX;
            item.positionY = apiItem.positionY;

            item.width = apiItem.width;
            item.height = apiItem.height;

            item.imageResource = apiItem.imageResource;

            item.save();
        }
    }

    public static void updateItems(List<Item> mItemList) {
        for (int i = 0; i < mItemList.size(); i++) {
            Item apiItem = mItemList.get(i);
            updateItem(apiItem);
        }
    }

    public static Item findItem(Long itemId) {
        Item itemQuery = new Select().from(Item.class)
                .where("id = ?", itemId).executeSingle();
        return itemQuery;
    }

    public static List<Item> getItems() {
        return new Select().from(Item.class).execute();
    }

}

