package punchlist.punchlistapp;

import java.util.List;

/**
 * Created by joshg on 11/19/16.
 */

public class Schematic {
    private List<Schematic> children;
    private String image;
    private Position position;

    public Schematic(List<Schematic> children, String image, Position position) {
        this.children = children;
        this.image = image;
        this.position = position;
    }

    public List<Schematic> getChildren() {
        return children;
    }

    public String getImage() {
        return image;
    }

    public Position getPosition() {
        return position;
    }
}
